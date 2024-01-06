package org.example.expression_parser.operations;

public enum Operator {
    LEFT_BRACKET(0, "("),
    RIGHT_BRACKET(0, ")"),
    SUMMARY(1, "+"),
    DIFFERENCE(1, "-"),
    MULTIPLY(2, "*"),
    DIVISION(2, "/"),
    EXPONENTIATION(4, "^");
    final Integer order;
    final String definition;

    Operator(int i, String definition) {
        this.order = i;
        this.definition = definition;
    }

    public static Operator parse(String operator) {
        switch (operator) {
            case "+" -> {
                return SUMMARY;
            }
            case "-" -> {
                return DIFFERENCE;
            }
            case "*" -> {
                return MULTIPLY;
            }
            case "/" -> {
                return DIVISION;
            }
            case "^" -> {
                return EXPONENTIATION;
            }
            case "(" -> {
                return LEFT_BRACKET;
            }
            case ")" -> {
                return RIGHT_BRACKET;
            }
        }
        throw new RuntimeException("Undefined operation");
    }
}
