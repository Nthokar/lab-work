package org.example.test;

import org.example.Configuration;
import org.example.optimization.binary.Binary;
import org.example.optimization.gold.Gold;
import org.example.optimization.newton.Newton;

import java.util.function.Function;

public class OptimizationTest {
    public static void main(String[] args) {
        testGold();
        testNewton();
        testBinary();
    }

    static void testBinary() {
        System.out.println("\nTESTING BINARY\n");
        {
            Function<Double, Double> f =(a)  -> 1 / a;
            Configuration cfg = new Configuration(f, 0.01d,0.001d, 5, -100d, 100d, -100d, 100d);

            Binary binary = new Binary(cfg);
            var extremes = binary.findExtremes();
            System.out.print("test on 1 / x:\n\t\t");
            System.out.println(extremes.isEmpty() ? "passed" : "failed");
        }
        {
            Function<Double, Double> f =(a)  -> a * a;
            Configuration cfg = new Configuration(f, 0.01d,0.001d, 5, -100d, 100d, -100d, 100d);

            Binary binary = new Binary(cfg);
            var extremes = binary.findExtremes();
            System.out.print("test on x * x:\n\t\t");
            System.out.println(extremes.size() == 1 && extremes.stream().anyMatch(x -> Math.abs(x - 0) < cfg.eps) ? "passed" : "failed");
        }
    }

    static void testNewton() {
        System.out.println("\nTESTING NEWTON\n");
        {
            Function<Double, Double> f =(a)  -> a * a;
            Configuration cfg = new Configuration(f, 0.01d,0.001d, 5, -100d, 100d, -100d, 100d);

            var newton = new Newton(cfg);
            var extremes = newton.findExtremes();
            System.out.print("test on x * x:\n\t\t");
            System.out.println(extremes.size() == 1 && extremes.stream().anyMatch(x -> Math.abs(x - 0) < cfg.eps) ? "passed" : "failed");
        }
        {
            Function<Double, Double> f =(a)  -> 1 / a;
            Configuration cfg = new Configuration(f, 0.01d,0.001d, 5, -100d, 100d, -100d, 100d);

            Newton newton = new Newton(cfg);
            var extremes = newton.findExtremes();
            System.out.print("test on 1 / x:\n\t\t");
            System.out.println(extremes.isEmpty() ? "passed" : "failed");
        }
        {
            Function<Double, Double> f =(a)  -> Math.pow(a, (1/a));
            Configuration cfg = new Configuration(f, 0.01d,0.001d, 10, -1d, 4d, -10d, 10d);

            var binary = new Gold(cfg);
            var extremes = binary.findExtremes();
            System.out.print("test on x^(1/x):\n\t\t");
            System.out.println(extremes.size() == 1 && extremes.stream().anyMatch(x -> Math.abs(x - Math.E) < cfg.eps) ?
                    "passed" : String.format("failed\n expected: %s\tactually:%s", Math.E, extremes));
        }
    }

    static void testGold() {
        System.out.println("\nTESTING GOLD\n");
        {

            Function<Double, Double> f =(a)  -> a * a;
            Configuration cfg = new Configuration(f, 0.01d,0.001d, 5, -100d, 100d, -100d, 100d);

            Gold gold = new Gold(cfg);
            var extremes = gold.findExtremes();
            System.out.print("test on x * x:\n\t\t");
            System.out.println(extremes.size() == 1 && extremes.stream().anyMatch(x -> Math.abs(x - 0) < cfg.eps) ? "passed" : "failed");
        }
        {
            Function<Double, Double> f =(a)  -> 1 / a;
            Configuration cfg = new Configuration(f, 0.01d,0.001d, 5, -100d, 100d, -100d, 100d);

            Gold gold = new Gold(cfg);
            var extremes = gold.findExtremes();
            System.out.print("test on 1 / x:\n\t\t");
            System.out.println(extremes.isEmpty() ? "passed" : "failed");
        }
        {
            Function<Double, Double> f =(a)  -> Math.pow(a, (1/a));
            Configuration cfg = new Configuration(f, 0.01d,0.001d, 5, -1d, 4d, -10d, 10d);

            var binary = new Gold(cfg);
            var extremes = binary.findExtremes();
            System.out.print("test on x^(1/x)x:\n\t\t");
            System.out.println(extremes.size() == 1 && extremes.stream().anyMatch(x -> Math.abs(x - Math.E) < cfg.eps) ?
                    "passed" : String.format("failed\n expected: %s\tactually:%s", Math.E, extremes));
        }
    }
}
