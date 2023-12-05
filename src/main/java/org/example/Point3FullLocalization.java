package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Point3FullLocalization {
    public Function<NDimension, Double> f;
    public NDimension xMin;
    public NDimension xMax;
    public NDimension h;

    public Point3FullLocalization(Function<NDimension, Double> f, NDimension xMin, NDimension xMax, NDimension h) {
        this.f = f;
        this.h = h;
        this.xMin = xMin;
        this.xMax = xMax;
    }


    public List<ExtremumLocal<NDimension>> findMinLocales() {
        List<ExtremumLocal<NDimension>> extremes = new ArrayList<>();
        var xLeft = xMin;
        var xMid = xMin.sum(h);
        var xRight = xMid.sum(h);
        while (xRight.isAny((x, y) -> x < y, xMax)) {
            if (f.apply(xLeft) >= f.apply(xMid)
                    && f.apply(xRight) >= f.apply(xMid)) {
                extremes.add(new ExtremumLocal<>(xLeft, xRight));
            }
            xLeft = xMid;
            xMid = xLeft.sum(h);
            xRight = xMid.sum(h);
        }
        return extremes;
    }

    public List<ExtremumLocal<NDimension>> findMaxLocales() {
        List<ExtremumLocal<NDimension>> extremes = new ArrayList<>();
        var xLeft = xMin;
        var xMid = xMin.sum(h);
        var xRight = xMid.sum(h);
        while (xRight.isAny((x, y) -> x < y, xMax)) {
            if (f.apply(xLeft) <= f.apply(xMid)
                    && f.apply(xRight) <= f.apply(xMid)) {
                extremes.add(new ExtremumLocal<>(xLeft, xRight));
            }
            xLeft = xMid;
            xMid = xLeft.sum(h);
            xRight = xMid.sum(h);
        }
        return extremes;
    }
}
