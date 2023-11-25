package org.example;

import org.example.optimization.Binary;
import org.example.optimization.BinaryPoint3;

import java.util.function.Function;

public class Main {
    static Double V = 8d;
    public static void main(String[] args) {
//        Function<Double, Double> f =
//                (x) -> Math.pow(x, 6) - 8 * Math.pow(x, 5) + 8 * Math.pow(x, 3) - 10 * Math.pow(x, 2) + x;
//        var local = new StandardLocalization(f,-1000.0, 1000.0, 0.01d);
//        var bin = new Binary(f, 1e-15, local);
//        System.out.println(bin.findExtremes());

        Function<Point3, Double> f = (p) -> p.x*p.x + V * p.y * p.y + p.z * p.z + 3 * p.x * p.y - V * p.x * p.z - p.y * p.z + p.x - 8 * p.y  + p.z;
        var start = new Point3(2d, 1d, V);
        var r = new Point3(-1d, 2d, 1d);
        var local = new Point3StandardLocalization(f, r.multiply(-1000d).sum(start), r.multiply(1000d).sum(start), r.multiply(0.5d));
        var bin = new BinaryPoint3(f, 0.001d, local);
        System.out.println(f.apply(start));
        System.out.println(f.apply(start.dif(r)));
        System.out.println(bin.findExtremes());
    }
}