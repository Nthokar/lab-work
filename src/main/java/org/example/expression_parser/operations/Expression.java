package org.example.expression_parser.operations;

import java.util.Objects;
import java.util.function.BiFunction;

public class Expression {
    int Order;
    public BiFunction<Double, Double, Double> f;

    Double leftSide;
    Double rightSide;

    public Double apply(Double x1, Double x2) {
        return f.apply(
                Objects.isNull(leftSide) ? x1 : leftSide,
                Objects.isNull(rightSide) ? x2 : rightSide);
    }
}
