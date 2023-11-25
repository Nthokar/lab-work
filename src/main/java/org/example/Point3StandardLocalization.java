package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Point3StandardLocalization {
    public Function<Point3, Double> f;
    public Point3 xMin;
    public Point3 xMax;
    public Point3 h;

    public Point3StandardLocalization(Function<Point3, Double> f, Point3 xMin, Point3 xMax, Point3 h) {
        this.f = f;
        this.h = h;
        this.xMin = xMin;
        this.xMax = xMax;
    }


    public List<ExtremumLocal<Point3>> findMinLocales() {
        List<ExtremumLocal<Point3>> extremes = new ArrayList<>();
        var xLeft = xMin;
        var xMid = xMin.sum(h);
        var xRight = xMid.sum(h);
        while (xRight.x < xMax.x
                || xRight.y < xMax.y
                || xRight.z < xMax.z) {
            if (f.apply(xLeft) >= f.apply(xMid)
                    && f.apply(xRight) >= f.apply(xMid)) {
                extremes.add(new ExtremumLocal<Point3>(xLeft, xRight));
            }
            System.out.println("min loc");
            xLeft = xMid;
            xMid = xLeft.sum(h);
            xRight = xMid.sum(h);
        }
        return extremes;
    }

    public List<ExtremumLocal<Point3>> findMaxLocales() {
        List<ExtremumLocal<Point3>> extremes = new ArrayList<>();
        var xLeft = xMin;
        var xMid = xMin.sum(h);
        var xRight = xMid.sum(h);
        while (xRight.x < xMax.x
                || xRight.y < xMax.y
                || xRight.z < xMax.z) {
            if (f.apply(xLeft) <= f.apply(xMid)
                    && f.apply(xRight) <= f.apply(xMid)) {
                extremes.add(new ExtremumLocal<Point3>(xLeft, xRight));
            }
            xLeft = xMid;
            xMid = xLeft.sum(h);
            xRight = xMid.sum(h);
        }
        return extremes;
    }
}
