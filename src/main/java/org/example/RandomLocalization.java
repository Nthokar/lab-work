package org.example;

import java.util.Random;
import java.util.function.Function;

public class RandomLocalization implements Localization {
    public Function<NDimension, Double> f;
    public NDimension xMin;
    public NDimension xMax;
    public NDimension pointOnLine;
    public NDimension direction;
    public Integer tries;
    private final Random random = new Random(1);

    public RandomLocalization(Function<NDimension, Double> f, NDimension xMin, NDimension xMax, NDimension pointOnLine, NDimension direction, Integer tries) {
        this.f = f;
        this.pointOnLine = pointOnLine;
        this.direction = direction;
        this.xMin = xMin;
        this.xMax = xMax;
        this.tries = tries;
    }

    public RandomLocalization(Function<NDimension, Double> f, NDimension xMin, NDimension xMax, NDimension direction, Integer tries) {
        this.f = f;
        this.pointOnLine = direction.basis();
        this.direction = direction;
        this.xMin = xMin;
        this.xMax = xMax;
        this.tries = tries;
    }
    public NDimension getStart() {
        var multiplayer = 50 - Math.random() * (100);
        var point = direction.multiply(multiplayer).sum(pointOnLine);
        System.out.println(point);
        return point;
    }
}
