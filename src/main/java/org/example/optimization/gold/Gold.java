package org.example.optimization.gold;

import lombok.NonNull;
import org.example.Configurations;
import org.example.ExtremumLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class Gold {
    Configurations config;
    @NonNull
    Function<Double, Double> f;
    @NonNull
    public Integer iter;
    @NonNull
    Double minRandom, maxRandom;
    @NonNull
    Double minStop, maxStop;
    @NonNull
    Double h, eps;
    public static final double GOLD = 0.618d;

    public Gold(Configurations config) {
        f = config.f;
        iter = config.iter;
        minRandom = config.minRandom;
        maxRandom = config.maxRandom;
        minStop = config.minStop;
        maxStop = config.maxStop;
        h = config.h;
        eps = config.eps;
        if (Objects.isNull(f) || Objects.isNull(iter) ||
                Objects.isNull(minRandom) || Objects.isNull(maxRandom) ||
                Objects.isNull(minStop) || Objects.isNull(maxStop) ||
                Objects.isNull(h) || Objects.isNull(eps)) {
            var sb = new StringBuilder();
            if (Objects.isNull(f)) sb.append(String.format("%s cannot be null\n", "function"));
            if (Objects.isNull(iter)) sb.append(String.format("%s cannot be null\n", "iteration count"));
            if (Objects.isNull(minRandom)) sb.append(String.format("%s cannot be null\n", "minRandom"));
            if (Objects.isNull(maxRandom)) sb.append(String.format("%s cannot be null\n", "maxRandom"));
            if (Objects.isNull(minStop)) sb.append(String.format("%s cannot be null\n", "minStop"));
            if (Objects.isNull(maxStop)) sb.append(String.format("%s cannot be null\n", "maxStop"));
            if (Objects.isNull(h)) sb.append(String.format("%s cannot be null\n", "h"));
            if (Objects.isNull(eps)) sb.append(String.format("%s cannot be null\n", "eps"));
            throw new IllegalArgumentException(sb.toString());
        }
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
            while (Math.abs(b - a) > eps) {
                d = a + GOLD * (b - a);
                c = a + b - d;

                var fa = f.apply(a);
                var fb = f.apply(b);
                var fd = f.apply(d);
                var fc = f.apply(c);

                if (fa > fc && fd > fc) {
                    prev = b;
                    b = d;
                } else if (fc > fd && fb > fd) {
                    prev = a;
                    a = c;
                } else {
                    if (prev > b) {
                        a = b - h;
                        b = prev + h;
                    } else if (prev < a) {
                        b = a + h;
                        a = prev - h;
                    } else {
                        System.out.println("\nerror");
                        System.out.printf("\na=%f, b=%f%n", a, b);
                        isValid = false;
                        break;
                    }
                }
            }
            if (isValid) {
                double fd = (int) (((a + b) / 2) * (1 / eps)) / (1 / eps);
                double p = f.apply(fd);
                double l = -1;
                if (Math.abs(p) != 1d / 0d) {
                    boolean inRes = false;
                    for (var extreme : extremes)
                        if (Math.abs(fd - extreme) < eps) {
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
            while (Math.abs(b - a) > eps) {
                d = a + GOLD * (b - a);
                c = a + b - d;

                var fa = f.apply(a);
                var fb = f.apply(b);
                var fd = f.apply(d);
                var fc = f.apply(c);

                if (fa < fc && fd < fc) {
                    prev = b;
                    b = d;
                } else if (fc < fd && fb < fd) {
                    prev = a;
                    a = c;
                } else {
                    if (prev < b) {
                        a = b - h;
                        b = prev + h;
                    } else if (prev > a) {
                        b = a + h;
                        a = prev - h;
                    } else {
                        System.out.println("\nerror");
                        System.out.printf("\na=%f, b=%f%n", a, b);
                        isValid = false;
                        break;
                    }
                }
            }
            if (isValid) {
                double fd = (int) (((a + b) / 2) * (1 / eps)) / (1 / eps);
                double p = f.apply(fd);
                double l = -1;
                if (Math.abs(p) != 1d / 0d) {
                    boolean inRes = false;
                    for (var extreme : extremes)
                        if (Math.abs(fd - extreme) < eps) {
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
        for (int i = 0; i < iter; i++) {
            //
            // -----o------o----o------
            //      a      b    x
            double a = Math.random() * (maxRandom - minRandom) + minRandom;
            double b = a + h;
            var reversed = 1;
            while (b > minStop && maxStop > b) {
                double d = a + reversed * GOLD * h;
                var fa = f.apply(a);
                var fb = f.apply(b);
                var fd = f.apply(d);
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
                b = a + reversed * h;
            }
        }
        return locales;
    }

    List<ExtremumLocal<Double>> findLocalMax() {
        List<ExtremumLocal<Double>> locales = new ArrayList<>();
        for (int i = 0; i < iter; i++) {
            //
            // -----o------o----o------
            //      a      b    x
            double a = Math.random() * maxRandom + minRandom;
            double b = a + h;
            var reversed = 1;
            while (b > minStop && maxStop > b) {
                double d = a + reversed * GOLD * h;
                var fa = f.apply(a);
                var fb = f.apply(b);
                var fd = f.apply(d);
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
                b = a + reversed * h;
            }
        }
        return locales;
    }
}
