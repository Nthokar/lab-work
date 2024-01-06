package org.example.localization;

import org.example.Configuration;
import org.example.ExtremumLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class StandardLocalization implements Localization {
    public final Configuration config;
    public final Function<Double, Double> f;
    public final Double xMin;
    public final Double xMax;
    public final Double h;

    public StandardLocalization(Configuration config) {
        this.config = config;
        f = config.f;
        xMin = config.minStop;
        xMax = config.maxStop;
        h = config.h;
        if (Objects.isNull(f) || Objects.isNull(h)) {
            var sb = new StringBuilder();
            if (Objects.isNull(f)) sb.append(String.format("%s cannot be null\n", "function"));
            if (Objects.isNull(h)) sb.append(String.format("%s cannot be null\n", "h"));
            throw new IllegalArgumentException(sb.toString());
        }
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
