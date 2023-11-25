package org.example.optimization;

import org.example.Point3;
import org.example.Point3StandardLocalization;
import org.example.StandardLocalization;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BinaryPoint3 {
    Double accuracy;
    Function<Point3, Double> f;
    Point3StandardLocalization local;

    public BinaryPoint3(Function<Point3, Double> f, Double accuracy, Point3StandardLocalization local) {
        this.local = local;
        this.accuracy = accuracy;
        this.f = f;
    }

    public List<Point3> findExtremes() {
        List<Point3> extremes = new ArrayList<>();
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

    public Point3 findMinExtremum(Point3 left, Point3 right) {

        while (Math.abs(left.x - right.x) > accuracy
                || Math.abs(left.y - right.y) > accuracy
                || Math.abs(left.z - right.z) > accuracy) {
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

    public Point3 findMaxExtremum(Point3 left, Point3 right) {
        while (Math.abs(f.apply(left) - f.apply(right)) > accuracy){
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
