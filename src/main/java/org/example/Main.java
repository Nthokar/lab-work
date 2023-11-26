package org.example;

import org.example.optimization.BinaryNDimension;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static Double V = 8d;
    public static void main(String[] args) {
//        Function<Double, Double> f =
//                (x) -> Math.pow(x, 6) - 8 * Math.pow(x, 5) + 8 * Math.pow(x, 3) - 10 * Math.pow(x, 2) + x;
//        var local = new StandardLocalization(f,-1000.0, 1000.0, 0.01d);
//        var bin = new Binary(f, 1e-15, local);
//        System.out.println(bin.findExtremes());

        Function<NDimension, Double> f = (p) -> p.get(0) * p.get(0) + V * p.get(1) * p.get(1) + p.get(2) * p.get(2) + 3 * p.get(0) * p.get(1) - V * p.get(0) * p.get(2) - p.get(1) * p.get(2) + p.get(0) - 8 * p.get(1)  + p.get(2);
        var start = new NDimension(Stream.of(2d, 1d, V)
                .collect(Collectors.toCollection(
                        ArrayList::new)));

        var r = new NDimension(Stream.of(-1d, 2d, 1d)
                .collect(Collectors.toCollection(
                        ArrayList::new)));
        var local = new Point3StandardLocalization(f, r.multiply(-1000d).sum(start), r.multiply(1000d).sum(start), r.multiply(0.5d));
        var bin = new BinaryNDimension(f, 0.001d, local);
        System.out.println(f.apply(start));
        System.out.println(f.apply(start.dif(r)));
        System.out.println(bin.findExtremes());
    }
}