package org.example.optimization;

import org.example.StandardLocalization;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Binary {
    Double accuracy;
    Function<Double, Double> f;
    StandardLocalization local;

    public Binary(Function<Double, Double> f, Double accuracy, StandardLocalization local) {
        this.local = local;
        this.accuracy = accuracy;
        this.f = f;
    }

    public List<Double> findExtremes() {
        List<Double> extremes = new ArrayList<>();
        var minLocales = local.findMinLocales();
        for(var min:minLocales) {
            extremes.add(findMinExtremum(min.left(), min.right()));
        }

        var maxLocales = local.findMaxLocales();
        for(var max:maxLocales){
            extremes.add(findMaxExtremum(max.left(), max.right()));
        }
        return extremes;
    }

    public Double findMinExtremum(Double left, Double right) {
        while (Math.abs(f.apply(left) - f.apply(right)) > accuracy){

            double l = Math.abs(right - left);
            double mid = left + (l / 2);
            double x1 = left + (l / 4);
            double x2 = right - (l / 4);

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
        return left + (right - left);
    }

    public Double findMaxExtremum(Double left, Double right) {
        while (Math.abs(f.apply(left) - f.apply(right)) > accuracy){

            double l = Math.abs(right - left);
            double mid = left + (l / 2);
            double x1 = left + (l / 4);
            double x2 = right - (l / 4);

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
        return left + (right - left);
    }
}
