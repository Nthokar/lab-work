package org.example.optimization.binary;

import org.example.Configuration;
import org.example.localization.StandardLocalization;
import org.example.optimization.Optimization;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class Binary implements Optimization {
    final Configuration config;
    final Function<Double, Double> f;
    final Double eps;
    final StandardLocalization local;

    public Binary(Configuration config)  {
        this.config  = config;
        this.f = config.f;
        this.local = new StandardLocalization(config);
        this.eps = config.eps;
        if (Objects.isNull(f) || Objects.isNull(eps)) {
            var sb = new StringBuilder();
            if (Objects.isNull(f)) sb.append(String.format("%s cannot be null\n", "function"));
            if (Objects.isNull(eps)) sb.append(String.format("%s cannot be null\n", "eps"));
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public List<Double> findExtremes() {
        List<Double> extremes = new ArrayList<>();
        var minLocales = local.findMinLocales();
        for (var min : minLocales) {
            var extreme = findMinExtremum(min.left, min.right);
            Double fd = (int) (extreme * 1 / eps) / (1 / eps);
            Double p = f.apply(fd);
            double l = -1;
            if (!p.isNaN() && !p.isInfinite()) {
                boolean inRes = false;
                for (var extreme1 : extremes)
                    if (Math.abs(fd - f.apply(extreme1)) < eps) {
                        inRes = true;
                        break;
                    }
                if (!inRes)
                    extremes.add(fd);
            }
        }

        var maxLocales = local.findMaxLocales();
        for (var max : maxLocales) {
            var extreme = findMinExtremum(max.left, max.right);
            double fd = (int) (extreme * 1 / eps) / (1 / eps);
            double p = f.apply(fd);
            double l = -1;
            if (Math.abs(p) != 1d / 0d) {
                boolean inRes = false;
                for (var extreme1 : extremes)
                    if (Math.abs(fd - f.apply(extreme1)) < eps) {
                        inRes = true;
                        break;
                    }
                if (!inRes)
                    extremes.add(fd);
            }
        }
        return extremes;
    }

    public Double findMinExtremum(Double left, Double right) {
        while (Math.abs(left - right) > eps){

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
        while (Math.abs(f.apply(left) - f.apply(right)) > eps){

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
