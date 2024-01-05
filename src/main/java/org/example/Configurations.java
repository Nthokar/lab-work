package org.example;

import java.util.function.Function;

public class Configurations {
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

        public Builder setFStr(String fStr) {
            this.fStr = fStr;
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

        public Configurations build() {
            return new Configurations(f, h, eps, iter, minRandom, maxRandom, minStop, maxStop);
        }

        public Function<Double, Double> getF() {
            return this.f;
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

    public Configurations(Function<Double, Double> f, Double h, Double eps, Integer iter, Double minRandom, Double maxRandom, Double minStop, Double maxStop) {
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
