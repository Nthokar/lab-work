package org.example.optimization;

import org.example.NDimension;
import org.example.Point3StandardLocalization;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BinaryNDimension {
    Double accuracy;
    Function<NDimension, Double> f;
    Point3StandardLocalization local;

    public BinaryNDimension(Function<NDimension, Double> f, Double accuracy, Point3StandardLocalization local) {
        this.local = local;
        this.accuracy = accuracy;
        this.f = f;
    }

    public List<NDimension> findExtremes() {
        List<NDimension> extremes = new ArrayList<>();
        var minLocales = local.findMinLocales();
        System.out.println(minLocales);
        for(var min:minLocales) {
            extremes.add(findMinExtremum(min.left, min.right));
        }

        var maxLocales = local.findMaxLocales();
        for(var max:maxLocales){
            extremes.add(findMaxExtremum(max.left, max.right));
        }
        return extremes;
    }

    public NDimension findMinExtremum(NDimension left, NDimension right) {

        while (left.isAny((x, y) -> Math.abs(x - y) > accuracy, right)) {
            System.out.println(".");
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

    public NDimension findMaxExtremum(NDimension left, NDimension right) {
        while (left.isAny((x, y) -> Math.abs(x - y) > accuracy, right))  {
            System.out.println("|");
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
}
