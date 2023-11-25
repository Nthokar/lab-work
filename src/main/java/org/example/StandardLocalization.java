package org.example;

import java.util.ArrayList;
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


    public List<ExtremumLocal<Double>> findMinLocales() {
        List<ExtremumLocal<Double>> extremes = new ArrayList<>();
        Double xLeft = xMin;
        Double xMid = xMin + h;
        Double xRight = xMid + h;
        while (xRight < xMax) {
            if (f.apply(xLeft) >= f.apply(xMid)
                    && f.apply(xRight) >= f.apply(xMid)) {
                extremes.add(new ExtremumLocal<Double>(xLeft, xRight));
            }
            xLeft = xMid;
            xMid = xLeft + h;
            xRight = xMid + h;
        }
        return extremes;
    }

    public List<ExtremumLocal<Double>> findMaxLocales() {
        List<ExtremumLocal<Double>> extremes = new ArrayList<>();
        Double xLeft = xMin;
        Double xMid = xMin + h;
        Double xRight = xMid + h;
        while (xRight < xMax) {
            if (f.apply(xLeft) <= f.apply(xMid)
                    && f.apply(xRight) <= f.apply(xMid)) {
                extremes.add(new ExtremumLocal<Double>(xLeft, xRight));
            }
            xLeft = xMid;
            xMid = xLeft + h;
            xRight = xMid + h;
        }
        return extremes;
    }
}
