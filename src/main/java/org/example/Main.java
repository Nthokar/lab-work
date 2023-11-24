package org.example;

import org.example.optimization.Binary;

import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Function<Double, Double> f =
                (x) -> Math.pow(x, 6) - 8 * Math.pow(x, 5) + 8 * Math.pow(x, 3) - 10 * Math.pow(x, 2) + x;
        var local = new StandardLocalization(f,-1000.0, 1000.0, 0.0001d);
        var bin = new Binary(f, 1e-15, local);
        System.out.println(bin.findExtremes());
    }
}