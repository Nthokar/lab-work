package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Localization {
    public record extremumLocal(Double left, Double right) {}
    public Function<Double, Double> f;
    public Double xMin;
    public Double xMax;
    public Double h;
    public List<extremumLocal> extremes;
    public void findMinLocales() {
        extremes = new ArrayList<>();
        Double xLeft = xMin;
        Double xMid = xMin + h;
        Double xRight = xMid + h;
        var percent = (xMax - xMin) / 100;
        while (xRight < xMax) {
            if (f.apply(xLeft) >= f.apply(xMid)
                    && f.apply(xRight) >= f.apply(xMid)) {
                extremes.add(new extremumLocal(xLeft, xRight));
            }
            System.out.println(xLeft/percent);
            xLeft = xMid;
            xMid = xLeft + h;
            xRight = xMid + h;
        }
    }

    public void findMaxLocales() {
        extremes = new ArrayList<>();
        Double xLeft = xMin;
        Double xMid = xMin + h;
        Double xRight = xMid + h;
        var percent = (xMax - xMin) / 100;
        while (xRight < xMax) {
            if (f.apply(xLeft) <= f.apply(xMid)
                    && f.apply(xRight) <= f.apply(xMid)) {
                extremes.add(new extremumLocal(xLeft, xRight));
            }
            System.out.println(xLeft/percent);
            xLeft = xMid;
            xMid = xLeft + h;
            xRight = xMid + h;
        }
    }
}
