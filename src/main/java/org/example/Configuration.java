package org.example;

import org.example.expression_parser.operations.ExpressionParser;

import java.util.function.Function;

public class Configuration {
    public final Function<Double, Double> f;
    public final Double h;
    public final Double eps;
    public final Integer iter;


    public final Double minRandom, maxRandom;
    public final Double minStop, maxStop;

    public static class Builder {
        Function<Double, Double> f;
        String fStr;
        Double h;
        Double eps;
        Integer iter;


        Double minRandom, maxRandom;
        Double minStop, maxStop;

        public static Builder newDefault() {
            var builder = new Builder();
            builder.h = 0.1;
            builder.eps = 0.001;
            builder.iter = 10;
            builder.minStop = -100_000d;
            builder.maxStop = 100_000d;
            return builder;
        }


        public Builder setFStr(String fStr) {
            this.fStr = fStr;
            var postfix = ExpressionParser.toPostfix(fStr);
            this.f = ExpressionParser.calcPostfix(postfix);
            return this;
        }

        public Builder setH(Double h) {
            this.h = h;
            return this;
        }

        public Builder setEps(Double eps) {
            this.eps = eps;
            return this;
        }

        public Builder setIter(Integer iter) {
            this.iter = iter;
            return this;
        }

        public Builder setMinRandom(Double minRandom) {
            this.minRandom = minRandom;
            return this;
        }

        public Builder setMaxRandom(Double maxRandom) {
            this.maxRandom = maxRandom;
            return this;
        }

        public Builder setMinStop(Double minStop) {
            this.minStop = minStop;
            return this;
        }

        public Builder setMaxStop(Double maxStop) {
            this.maxStop = maxStop;
            return this;
        }

        public Configuration build() {
            return new Configuration(f, h, eps, iter, minRandom, maxRandom, minStop, maxStop);
        }

        public Function<Double, Double> getF() {
            return this.f;
        }
        public String getFStr() {
            return this.fStr;
        }

        public Double getH() {
            return this.h;
        }

        public Double getEps() {
            return this.eps;
        }

        public Integer getIter() {
            return this.iter;
        }

        public Double getMinRandom() {
            return this.minRandom;
        }

        public Double getMaxRandom() {
            return this.maxRandom;
        }

        public Double getMinStop() {
            return this.minStop;
        }

        public Double getMaxStop() {
            return this.maxStop;
        }
    }

    public Configuration(Function<Double, Double> f, Double h, Double eps, Integer iter, Double minRandom, Double maxRandom, Double minStop, Double maxStop) {
        this.f = f;
        this.h = h;
        this.eps = eps;
        this.iter = iter;
        this.minRandom = minRandom;
        this.maxRandom = maxRandom;
        this.minStop = minStop;
        this.maxStop = maxStop;
    }
}
