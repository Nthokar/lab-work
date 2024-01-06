package org.example.expression_parser.operations;

import java.util.function.BiFunction;

public class OperationsConcatenation {
    static BiFunction<Double, Double, Double> multiply(
            BiFunction<Double, Double, Double> f1,
            BiFunction<Double, Double, Double> f2
    ) {
        return (x, y) -> f1.apply(x, y) * f2.apply(x, y);
    }

    static BiFunction<Double, Double, Double> multiply() {
        return (x1, x2)  -> x1 * x2;
    }

    static BiFunction<Double, Double, Double> division(
            BiFunction<Double, Double, Double> f1,
            BiFunction<Double, Double, Double> f2
    ) {
        return (x, y) -> f1.apply(x, y) / f2.apply(x, y);
    }

    static BiFunction<Double, Double, Double> division() {
        return (x1, x2)  -> x1 / x2;
    }

    static BiFunction<Double, Double, Double> exponentiation(
            BiFunction<Double, Double, Double> f1,
            BiFunction<Double, Double, Double> f2
    ) {
        return (x, y) ->Math.pow(f1.apply(x, y), f2.apply(x, y));
    }

    static BiFunction<Double, Double, Double> exponentiation() {
        return (x1, x2)  -> Math.pow(x1, x2);
    }

    static BiFunction<Double, Double, Double> sum(
            BiFunction<Double, Double, Double> f1,
            BiFunction<Double, Double, Double> f2
    ) {
        return (x, y) -> f1.apply(x, y) + f2.apply(x, y);
    }

    static BiFunction<Double, Double, Double> sum() {
        return (x1, x2)  -> x1 + x2;
    }
    static BiFunction<Double, Double, Double> difference(
            BiFunction<Double, Double, Double> f1,
            BiFunction<Double, Double, Double> f2
    ) {
        return (x, y) -> f1.apply(x, y) - f2.apply(x, y);
    }

    static BiFunction<Double, Double, Double> difference() {
        return (x1, x2)  -> x1 - x2;
    }

    public static BiFunction<Double, Double, Double> x() {
        return (x1, x2)  -> x1;
    }

    public static BiFunction<Double, Double, Double> x2() {
        return (x1, x2)  -> x2;
    }

    public static BiFunction<Double, Double, Double> concatenate(
            BiFunction<Double, Double, Double> f1,
            BiFunction<Double, Double, Double> f2,
            Operator operation)  {
        switch (operation) {
            case SUMMARY -> {
                return sum(f1, f2);
            }
            case DIVISION -> {
                return division(f1, f2);
            }
            case MULTIPLY -> {
                return multiply(f1, f2);
            }
            case DIFFERENCE -> {
                return difference(f1, f2);
            }
            case EXPONENTIATION -> {
                return exponentiation(f1, f2);
            }
        }
        throw new RuntimeException();
    }

    public static BiFunction<Double, Double, Double> concatenate(
            Operator operation)  {
        switch (operation) {
            case SUMMARY -> {
                return sum();
            }
            case DIVISION -> {
                return division();
            }
            case MULTIPLY -> {
                return multiply();
            }
            case DIFFERENCE -> {
                return difference();
            }
            case EXPONENTIATION -> {
                return exponentiation();
            }
        }
        throw new RuntimeException();
    }
}
