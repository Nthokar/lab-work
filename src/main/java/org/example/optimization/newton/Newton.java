package org.example.optimization.newton;

import org.example.Configuration;
import org.example.optimization.Optimization;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Newton implements Optimization, Runnable {

    final Configuration config;

    public Newton(Configuration config){
        this.config = config;
        validate();
    }


    double findFirstDerivative(double x)
    {
        return (config.f.apply(x + config.eps) - config.f.apply(x - config.eps)) / (2 * config.eps);
    }

    double findSecondDerivative(double x)
    {
        double p1 = (config.f.apply(x + config.eps) - 2 * config.f.apply(x) + config.f.apply(x - config.eps));
        double p2 = (config.eps * config.eps);
        return  p1/p2 ;
    }

    public List<Double> findExtremes() {
        List<Double> extremes = new ArrayList<>();
        for (var i = 0; i < config.iter; i++) {
            double x = Math.random() * (config.maxRandom - config.minRandom) + config.minRandom;
            double x1;
            do {
                x1 = x;
                x = x - findFirstDerivative(x) / findSecondDerivative(x);
            } while (Math.abs(x - x1) > config.eps);
            double fd = (int) (x * 1 / config.eps) / (1 / config.eps);
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
        return extremes;
    }

    @Override
    public void run() {
       var extremes = findExtremes();
        System.out.println(extremes);
    }

    public void validate() {
        if (Objects.isNull(config.f) || Objects.isNull(config.iter) ||
                Objects.isNull(config.minRandom) || Objects.isNull(config.maxRandom) || Objects.isNull(config.eps)) {
            var sb = new StringBuilder();
            if (Objects.isNull(config.f)) sb.append(String.format("%s cannot be null\n", "function"));
            if (Objects.isNull(config.iter)) sb.append(String.format("%s cannot be null\n", "iteration count"));
            if (Objects.isNull(config.minRandom)) sb.append(String.format("%s cannot be null\n", "minRandom"));
            if (Objects.isNull(config.maxRandom)) sb.append(String.format("%s cannot be null\n", "maxRandom"));
            if (Objects.isNull(config.eps)) sb.append(String.format("%s cannot be null\n", "eps"));
            throw new IllegalArgumentException(sb.toString());
        }
    }
}
