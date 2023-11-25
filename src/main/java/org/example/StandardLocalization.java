package org.example;

import org.example.optimization.Localization;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class StandardLocalization implements Localization {
    public Function<Double, Double> f;
    public Double xMin;
    public Double xMax;
    public Double h;

    public StandardLocalization(Function<Double, Double> f, Double xMin, Double xMax, Double h) {
        this.f = f;
        this.h = h;
        this.xMin = xMin;
        this.xMax = xMax;
    }


    public List<ExtremumLocal> findLocales(Comparator<Double> comparator) {
        List<ExtremumLocal> extremes = new ArrayList<>();
        Double xLeft = xMin;
        Double xMid = xMin + h;
        Double xRight = xMid + h;
        while (xRight < xMax) {
            if (comparator.compare(f.apply(xLeft), f.apply(xMid)) >= 0
                    && comparator.compare(f.apply(xRight), f.apply(xMid)) >= 0) {
                extremes.add(new ExtremumLocal(xLeft, xRight));
            }
            xLeft = xMid;
            xMid = xLeft + h;
            xRight = xMid + h;
        }
        return extremes;
    }
}
