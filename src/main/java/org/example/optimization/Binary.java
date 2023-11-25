package org.example.optimization;

import org.example.DoubleComparator;
import org.example.StandardLocalization;

import java.util.ArrayList;
import java.util.Comparator;
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
        var minLocales = local.findLocales(new DoubleComparator());
        for(var min:minLocales) {
            extremes.add(findExtremum(min.left(), min.right(), new DoubleComparator()));
        }

        var maxLocales = local.findLocales(new DoubleComparator().reversed());
        for(var max:maxLocales){
            extremes.add(findExtremum(max.left(), max.right(), new DoubleComparator().reversed()));
        }
        return extremes;

    }
    public Double findExtremum(Double left, Double right, Comparator<Double> comparator) {
        var df =  Math.abs(f.apply(left) - f.apply(right));
        while (comparator.compare(df, accuracy) > 0) {
            double l = Math.abs(right - left);
            double mid = left + (l / 2);
            double x1 = left + (l / 4);
            double x2 = right - (l / 4);

            if (comparator.compare(f.apply(mid), f.apply(x1))  >  0) {
                right = mid;
            }
            else if (comparator.compare(f.apply(mid), f.apply(x2)) > 0) {
                left = mid;
            }
            else {
                left = x1;
                right = x2;
            }
        }
        return left + (right - left);
    }       }
