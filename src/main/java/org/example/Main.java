package org.example;

import org.example.app.MainMenu;
import org.example.expression_parser.operations.ExpressionParser;
import org.example.localization.RandomLocalization;
import org.example.optimization.binary.Binary;
import org.example.optimization.binary.BinaryNDimension;
import org.example.optimization.gold.Gold;
import org.example.optimization.newton.Newton;

import java.util.function.Function;

public class Main {
    static Double V = 8d;
    public static void main(String[] args) {
        try {
            var builder = Configuration.Builder.newDefault();
            var mainMenu = MainMenu.getMenu(builder);
            mainMenu.run();

//            Function<NDimension, Double> f =
//                    (x) -> x.get(0) * x.get(0) + 8 * x.get(1) * x.get(1) + x.get(2) * x.get(2)
//                            + 3 * x.get(0) * x.get(1) - 8 * x.get(0) * x.get(2) - x.get(1) * x.get(2) + x.get(0)
//                            - 8*x.get(1) + x.get(2);
//            //start point
//            var start = new NDimension(new Double[] { 2d, 1d, 8d });
//            //direction
//            var r = new NDimension(new Double[] { -1d, 2d, 1d });
//            //object,which would localize function f
//            var rl = new RandomLocalization(f,
//                    start.sum(r.multiply(-100d)),
//                    start.sum(r.multiply(100d)),
//                    start,
//                    r,
//                    10);
//            //object, which would calculate mins
//            var binary = new BinaryNDimension(f, 0.01d, rl);
//            System.out.println(binary.findExtremes());

            //testGold();
            //testBinary();
            //testNewton();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void testGold() {
        {
            Function<Double, Double> f =(a)  -> a * a;
            Configuration cfg = new Configuration(f, 0.01d,0.001d, 5, -100d, 100d, -100d, 100d);
            Configuration cfg2 = new Configuration(null, 0.01d,0.001d, 5, -100d, 100d, -100d, 100d);

            Gold gold = new Gold(cfg2);
            var extremes = gold.findExtremes();
            System.out.println(extremes);
        }
        {
            Function<Double, Double> f =(a)  -> Math.pow(a, (1/a));
            Configuration cfg = new Configuration(f, 0.01d,0.0001d, 5, -1d, 4d, -10d, 10d);

            var binary = new Gold(cfg);
            var extremes = binary.findExtremes();
            System.out.print("test on x^(1/x)x:\n\t\t");
            System.out.println(extremes.size() == 1 && extremes.stream().anyMatch(x -> Math.abs(x - Math.E) < cfg.eps) ?
                    "passed" : String.format("failed\n expected: %s\tactually:%s", Math.E, extremes));
        }
    }

    static void task2(){
        Function<NDimension, Double> f = (p) -> p.get(0) * p.get(0) + V * p.get(1) * p.get(1) + p.get(2) * p.get(2) + 3 * p.get(0) * p.get(1) - V * p.get(0) * p.get(2) - p.get(1) * p.get(2) + p.get(0) - 8 * p.get(1)  + p.get(2);
        var start = new NDimension(new Double[]{2d, 1d, 8d});
        var r = new NDimension(new Double[]{-1d, 2d, 1d});
        var local = new RandomLocalization(f, r.multiply(-1000d).sum(start), r.multiply(1000d).sum(start), start, r.multiply(0.5d),20);
        var bin = new BinaryNDimension(f, 0.001d, local);

        var extremes = bin.findExtremes();
        System.out.println(extremes);

    }

    static void testBinary() {
        System.out.println("TESTING BINARY");
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
        {
            Function<Double, Double> f =(a)  -> a * a;
            Configuration cfg = new Configuration(f, 0.01d,0.001d, 5, -100d, 100d, -100d, 100d);

            var newton = new Newton(cfg);
            var extremes = newton.findExtremes();
            System.out.print("test on x * x:\n\t\t");
            System.out.println(extremes.size() == 1 && extremes.stream().anyMatch(x -> Math.abs(x - 0) < cfg.eps) ? "passed" : "failed");
        }
        {
            Function<Double, Double> f =(a)  -> Math.pow(a, (1/a));
            Configuration cfg = new Configuration(f, 0.001d,0.001d, 10, -1d, 4d, -10d, 10d);

            var binary = new Gold(cfg);
            var extremes = binary.findExtremes();
            System.out.print("test on x^(1/x):\n\t\t");
            System.out.println(extremes.size() == 1 && extremes.stream().anyMatch(x -> Math.abs(x - Math.E) < cfg.eps) ?
                    "passed" : String.format("failed\n expected: %s\tactually:%s", Math.E, extremes));
        }
    }

    public static void test() {
        var expr = ExpressionParser.toPostfix("( x * x )");
        System.out.println(expr);
        var f = ExpressionParser.calcPostfix(expr);
        System.out.println(f.apply(1d));
    }
}