package org.example.optimization.gold;

import org.example.Configuration;
import org.example.ExtremumLocal;
import org.example.optimization.Optimization;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Gold implements Optimization {
    Configuration config;
    public static final double GOLD = 0.618d;

    public Gold(Configuration config) {
        this.config = config;
        validate();
    }

    public List<Double> findExtremes() {
        var extremes = new ArrayList<Double>();
        var mins = findLocalMin();
        var maxs = findLocalMax();
        if (mins.isEmpty() && maxs.isEmpty()) {
            return extremes;
        }
        for (var local : mins) {
            double a = local.left;
            double b = local.right;
            double d = a + GOLD * (b - a);
            double c = a + b - d;

            double prev = 0;
            boolean isValid = true;
            while (Math.abs(b - a) > config.eps) {
                d = a + GOLD * (b - a);
                c = a + b - d;

                var fa = config.f.apply(a);
                var fb = config.f.apply(b);
                var fd = config.f.apply(d);
                var fc = config.f.apply(c);

                if (fa > fc && fd > fc) {
                    prev = b;
                    b = d;
                } else if (fc > fd && fb > fd) {
                    prev = a;
                    a = c;
                } else {
                    if (prev > b) {
                        a = b - config.h;
                        b = prev + config.h;
                    } else if (prev < a) {
                        b = a + config.h;
                        a = prev - config.h;
                    } else {
                        System.out.println("\nerror");
                        System.out.printf("\na=%f, b=%f%n", a, b);
                        isValid = false;
                        break;
                    }
                }
            }
            if (isValid) {
                double fd = (int) (((a + b) / 2) * (1 / config.eps)) / (1 / config.eps);
                double p = config.f.apply(fd);
                double l = -1;
                if (Math.abs(p) != 1d / 0d) {
                    boolean inRes = false;
                    for (var extreme : extremes)
                        if (Math.abs(fd - extreme) < config.eps) {
                            inRes = true;
                            break;
                        }
                    if (!inRes)
                        extremes.add(fd);
                }
            }
        }

        for (var local : maxs) {
            double a = local.left;
            double b = local.right;
            double d = a + GOLD * (b - a);
            double c = a + b - d;

            double prev = 0;
            boolean isValid = true;
            while (Math.abs(b - a) > config.eps) {
                d = a + GOLD * (b - a);
                c = a + b - d;

                var fa = config.f.apply(a);
                var fb = config.f.apply(b);
                var fd = config.f.apply(d);
                var fc = config.f.apply(c);

                if (fa < fc && fd < fc) {
                    prev = b;
                    b = d;
                } else if (fc < fd && fb < fd) {
                    prev = a;
                    a = c;
                } else {
                    if (prev < b) {
                        a = b - config.h;
                        b = prev + config.h;
                    } else if (prev > a) {
                        b = a + config.h;
                        a = prev - config.h;
                    } else {
                        System.out.println("\nerror");
                        System.out.printf("\na=%f, b=%f%n", a, b);
                        isValid = false;
                        break;
                    }
                }
            }
            if (isValid) {
                double fd = (int) (((a + b) / 2) * (1 / config.eps)) / (1 / config.eps);
                double p = config.f.apply(fd);
                double l = -1;
                if (Math.abs(p) != 1d / 0d) {
                    boolean inRes = false;
                    for (var extreme : extremes)
                        if (Math.abs(fd - extreme) < config.eps) {
                            inRes = true;
                            break;
                        }
                    if (!inRes)
                        extremes.add(fd);
                }
            }
        }
        return extremes;
    }
    List<ExtremumLocal<Double>> findLocalMin() {
        List<ExtremumLocal<Double>> locales = new ArrayList<>();
        for (int i = 0; i < config.iter; i++) {
            //
            // -----o------o----o------
            //      a      b    x
            double a = Math.random() * (config.maxRandom - config.minRandom) + config.minRandom;
            double b = a + config.h;
            var reversed = 1;
            while (b > config.minStop && config.maxStop > b) {
                double d = a + reversed * GOLD * config.h;
                var fa = config.f.apply(a);
                var fb = config.f.apply(b);
                var fd = config.f.apply(d);
                if (fa > fd && fb > fd) {
                    if (reversed < 0) {
                        locales.add(new ExtremumLocal<>(b, a));
                        break;
                    } else {
                        locales.add(new ExtremumLocal<>(a, b));
                        break;
                    }
                }
                if (fa < fb && reversed > 0) {
                    reversed = -1;
                }
                a = d;
                b = a + reversed * config.h;
            }
        }
        return locales;
    }

    List<ExtremumLocal<Double>> findLocalMax() {
        List<ExtremumLocal<Double>> locales = new ArrayList<>();
        for (int i = 0; i < config.iter; i++) {
            //
            // -----o------o----o------
            //      a      b    x
            double a = Math.random() * config.maxRandom + config.minRandom;
            double b = a + config.h;
            var reversed = 1;
            while (b > config.minStop && config.maxStop > b) {
                double d = a + reversed * GOLD * config.h;
                var fa = config.f.apply(a);
                var fb = config.f.apply(b);
                var fd = config.f.apply(d);
                if (fa < fd && fb < fd) {
                    if (reversed < 0) {
                        locales.add(new ExtremumLocal<>(b, a));
                        break;
                    } else {
                        locales.add(new ExtremumLocal<>(a, b));
                        break;
                    }
                }
                if (fa > fb && reversed > 0) {
                    reversed = -1;
                }
                a = d;
                b = a + reversed * config.h;
            }
        }
        return locales;
    }
    public void validate() {
        if (Objects.isNull(config.f) || Objects.isNull(config.iter) ||
                Objects.isNull(config.minRandom) || Objects.isNull(config.maxRandom) ||
                Objects.isNull(config.minStop) || Objects.isNull(config.maxStop) ||
                Objects.isNull(config.h) || Objects.isNull(config.eps)) {
            var sb = new StringBuilder();
            if (Objects.isNull(config.f)) sb.append(String.format("%s cannot be null\n", "function"));
            if (Objects.isNull(config.iter)) sb.append(String.format("%s cannot be null\n", "iteration count"));
            if (Objects.isNull(config.minRandom)) sb.append(String.format("%s cannot be null\n", "minRandom"));
            if (Objects.isNull(config.maxRandom)) sb.append(String.format("%s cannot be null\n", "maxRandom"));
            if (Objects.isNull(config.minStop)) sb.append(String.format("%s cannot be null\n", "minStop"));
            if (Objects.isNull(config.maxStop)) sb.append(String.format("%s cannot be null\n", "maxStop"));
            if (Objects.isNull(config.h)) sb.append(String.format("%s cannot be null\n", "h"));
            if (Objects.isNull(config.eps)) sb.append(String.format("%s cannot be null\n", "eps"));
            throw new IllegalArgumentException(sb.toString());
        }
    }
}
