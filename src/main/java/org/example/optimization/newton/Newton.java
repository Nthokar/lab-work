package org.example.optimization.newton;

import org.example.Configurations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class Newton {

    final Configurations config;
    final Function<Double, Double> f;
    final Double eps;
    final Integer iter;
    final Double minRandom, maxRandom;

    public Newton(Configurations config){
        this.config = config;
        this.f = config.f;
        this.iter = config.iter;
        this.eps = config.eps;
        this.minRandom = config.minRandom;
        this.maxRandom = config.maxRandom;
        if (Objects.isNull(f) || Objects.isNull(iter) ||
                Objects.isNull(minRandom) || Objects.isNull(maxRandom) || Objects.isNull(eps)) {
            var sb = new StringBuilder();
            if (Objects.isNull(f)) sb.append(String.format("%s cannot be null\n", "function"));
            if (Objects.isNull(iter)) sb.append(String.format("%s cannot be null\n", "iteration count"));
            if (Objects.isNull(minRandom)) sb.append(String.format("%s cannot be null\n", "minRandom"));
            if (Objects.isNull(maxRandom)) sb.append(String.format("%s cannot be null\n", "maxRandom"));
            if (Objects.isNull(eps)) sb.append(String.format("%s cannot be null\n", "eps"));
            throw new IllegalArgumentException(sb.toString());
        }
    }


    double findFirstDerivative(double x)
    {
        return (f.apply(x + eps) - f.apply(x - eps)) / (2 * eps);
    }

    double findSecondDerivative(double x)
    {
        double p1 = (f.apply(x + eps) - 2 * f.apply(x) + f.apply(x - eps));
        double p2 = (eps * eps);
        return  p1/p2 ;
    }

    public List<Double> findExtremes() {
        List<Double> extremes = new ArrayList<>();
        for (var i = 0; i < iter; i++) {
            double x = Math.random() * (maxRandom - minRandom) + minRandom;
            double x1;
            do {
                x1 = x;
                x = x - findFirstDerivative(x) / findSecondDerivative(x);
            } while (Math.abs(x - x1) > eps);
            double fd = (int) (x * 1 / eps) / (1 / eps);
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
        return extremes;
    }
}
