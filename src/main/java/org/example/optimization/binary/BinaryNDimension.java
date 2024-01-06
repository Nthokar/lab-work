package org.example.optimization.binary;

import org.example.NDimension;
import org.example.localization.RandomLocalization;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class BinaryNDimension {
    Double accuracy;
    Function<NDimension, Double> f;
    RandomLocalization local;
    NDimension h;


    public BinaryNDimension(Function<NDimension, Double> f, Double accuracy, RandomLocalization local) {
        this.local = local;
        this.accuracy = accuracy;
        this.f = f;
    }

    public List<NDimension> findExtremes() {
        List<NDimension> extremes = new ArrayList<>(    );
        for (var i = 0; i < local.tries; i++) {
            var min = findMinExtremum(local.getStart(), local.direction);
            if (!contains(extremes, min, accuracy))
            extremes.add(min);
//            findMaxExtremumRange(local.getStart(), local.h);
        }
        return extremes;
    }

    public NDimension findMinExtremumBetween(NDimension left, NDimension right) {

        if (left.compare(right, local.direction, local.pointOnLine) > 0) {
            var temp = left;
            left = right;
            right = temp;
        }

        while (left.isAny((x, y) -> Math.abs(x - y) > accuracy, right)) {
            var l = right.dif(left);
            var mid = left.sum(l.multiply(0.5d));
            var x1 = left.sum(l.multiply(0.25d));
            var x2 = right.dif(l.multiply(0.25d));

            if (f.apply(x1) < f.apply(mid)) {
                right = mid;
            }
            else if (f.apply(x2) < f.apply(mid)) {
                left = mid;
            }
            else {
                left = x1;
                right = x2;
            }
        }
        return left.sum(right.dif(left));
    }

    public NDimension findMaxExtremumBetween(NDimension left, NDimension right) {

        if (left.compare(right, local.direction, local.pointOnLine) > 0) {
            var temp = left;
            left = right;
            right = temp;
        }

        while (left.isAny((x, y) -> Math.abs(x - y) > accuracy, right))  {
            var l = right.dif(left);
            var mid = left.sum(l.multiply(0.5d));
            var x1 = left.sum(l.multiply(0.25d));
            var x2 = right.sum(l.multiply(0.25d));

            if (f.apply(x1) > f.apply(mid)) {
                right = mid;
            }
            else if (f.apply(x2) > f.apply(mid)) {
                left = mid;
            }
            else {
                left = x1;
                right = x2;
            }
        }
        return left.sum(right.dif(left));
    }

    public NDimension findMinExtremum(NDimension x0, NDimension h) {
        var count = 0;
        while (count < 1000) {
            count++;
            var x1 = x0.sum(h);
            var x2 = x0.sum(h.multiply(0.5d));
            if (f.apply(x0) < f.apply(x1)) {
                //find
                if (f.apply(x2) < f.apply(x0)) {
                    //System.out.println(String.format("points %s %n%s%n ----", x0, x1));
                    return findMinExtremumBetween(x0, x1);
                } else {
                    x0 = x0.dif(h.multiply(0.5d));
                }
            } else {
                if (f.apply(x2) < f.apply(x1)) {
                    return findMinExtremumBetween(x0, x1);
                } else {
                    x0 = x2;
                }
            }
        }
        return x0;
    }

    public NDimension findMaxExtremum(NDimension x0, NDimension h) {
        var count = 0;
        while (count < 1000) {
            count++;
            var x1 = x0.sum(h);
            var x2 = x0.sum(h.multiply(0.5d));
            if (f.apply(x0) < f.apply(x1)) {
                //find
                if (f.apply(x2) > f.apply(x1)) {
                    return findMaxExtremumBetween(x0, x1);

                } else {
                    x0 = x2;
                }
            } else {
                if (f.apply(x2) > f.apply(x0)) {
                    return findMaxExtremumBetween(x0, x1);

                } else {
                    x0 = x2;
                    h = h.multiply(-1d);
                }
            }
        }
        return x0;
    }

    private static boolean contains(List<NDimension> list, NDimension x, double eps)  {
        return list.stream().anyMatch(i -> i.isAny((x1, x2) -> Math.abs(x2 - x1) < eps, x));
    }
}
