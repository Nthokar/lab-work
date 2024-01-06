package org.example.expression_parser.operations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ExpressionParser {

//    public static void main(String[] args) {
//        String expression = "( ( x1 * x1 ) + ( 3 * ( x2 * x2 ) ) ) + ( ( 2 * x1 ) * x2 )";
//        Point2D point = new Point2D(1d, 1d);
//        String postfix = toPostfix(expression);
//        System.out.println(postfix);
//        var f = calcPostfix(postfix);
//        GradientDescending gd = new GradientDescending();
//        var res = gd.minimize(f, point, null, null, 0.01d, 0.1d);
//        System.out.println(res);
////        Expression exp = parseExpression(expression);
////        System.out.println(exp.apply(10d, 2d));
////        System.out.println(exp.apply(0d, 0d));
////
////        expression = "6 * x1 - 20 + 7 * x2";
////        System.out.println(Arrays.toString(parseToArray(expression)));n
//
//    }

    private static void parseFlat(String expression) {



    }
//x1, x2 - variables
    private static Expression parseExpression(String expressionStr) {
        var leftAndRightSides = expressionStr.split("[+\\-*/]");
        if (leftAndRightSides.length != 2)
            throw new RuntimeException("Not a single operation expression");

        Operator operation = Operator.parse(
                expressionStr.substring(
                        leftAndRightSides[0].length(),
                        expressionStr.length() - leftAndRightSides[1].length())
        );

        leftAndRightSides = Arrays.stream(leftAndRightSides).map(x ->  x.replace(" ", "")).toArray(String[]::new);
        Expression expression = new Expression();
        expression.f = OperationsConcatenation.concatenate(operation);
        boolean isLeftLiter = true;
        boolean isRightLiter = true;
        try {
            expression.leftSide = Double.parseDouble(leftAndRightSides[0]);
        }
        catch (Exception ignored) {
            isLeftLiter = false;
        }
        try {
            expression.rightSide = Double.parseDouble(leftAndRightSides[1]);
        }
        catch (Exception ignored) {
            isRightLiter = false;
        }
        expression.f = OperationsConcatenation.concatenate(
                isLeftLiter ? (x, y) -> expression.leftSide : parseVariable(leftAndRightSides[0]),
                isRightLiter ? (x, y) -> expression.rightSide : parseVariable(leftAndRightSides[1]),
                operation
        );

        return expression;
    }

    static BiFunction<Double, Double, Double> parseVariable(String variableName) {
        variableName = variableName.toLowerCase();
        if (variableName.equals("x")) return OperationsConcatenation.x();
        throw new RuntimeException();
    }

    // without brackets
    static String[] parseToArray(String expression) {
        expression = expression.replace(" ", "");
        List<Integer> operatorsIndexes = new ArrayList<>();
        for (var i = 0; i < expression.length(); i++) {
            Character ch = expression.charAt(i);
            if ("+-*/".contains(ch.toString())) {
                operatorsIndexes.add(i);
            }
        }
        List<String> parts = new ArrayList<>();
        var previous = 0;
        for (var i:operatorsIndexes) {
            parts.add(expression.substring(previous, i));
            parts.add(expression.substring(i, i+1));
            previous = i + 1;
        }
        parts.add(expression.substring(previous));
        return parts.toArray(String[]::new);
    }
    public static String toPostfix(String expr) {
        var array = expr.split(" ");
        StringBuilder postfix = new StringBuilder();
        Stack<Operator> stack = new Stack<>();
        for (int i = 0; i < array.length; i++) {
            String part = array[i];

            //operator
            try {
                var operator = Operator.parse(part);
                if (operator == Operator.LEFT_BRACKET) {
                    stack.push(operator);
                }
                else if (operator == Operator.RIGHT_BRACKET) {
                    while (stack.size() > 0 && stack.peek() != Operator.LEFT_BRACKET) {
                        postfix.append(String.format("%s ", stack.pop().definition));
                    }
                    stack.pop();
                }
                else {
                    while (stack.size() > 0 && stack.peek().order >= operator.order) {
                        postfix.append(String.format("%s ", stack.pop().definition));
                    }
                    stack.push(operator);
                }
            }
            //number or variable
            catch (Exception notOperator) {
                try {
                    parseVariable(part);
                    postfix.append(String.format("%s ", part));

                }
                catch (Exception notVariable) {
                    try {
                        Double.parseDouble(part);
                        postfix.append(String.format("%s ", part));

                    }
                    catch (Exception notDouble) {
                        throw new RuntimeException(notVariable.getMessage());
                    }
                }
            }
        }

        for (var operator:stack) {
            postfix.append(String.format("%s ", operator.definition));
        }
        return postfix.toString();
    }

    public static Function<Double, Double> calcPostfix(String postfix) {
        Stack<BiFunction<Double, Double, Double>> locales = new Stack<>();

        var array = postfix.split(" ");
        for (int i = 0; i < array.length; i++) {
            String part = array[i];
            //operation
            try {
                var operator = Operator.parse(part);

                BiFunction<Double, Double, Double> right = locales.size() > 0 ? locales.pop() : (a, b) -> 0d;
                BiFunction<Double, Double, Double> left = locales.size() > 0 ? locales.pop() : (a, b) -> 0d;

                locales.push(OperationsConcatenation.concatenate(left, right, operator));

            }
            //number or variable
            catch (Exception notOperator) {
                try {
                    var number = Double.parseDouble(part);
                    locales.push((a,b) -> number);
                }
                catch (Exception notDouble) {
                    var variable = parseVariable(part);
                    locales.push(variable);
                }
            }
        }
        var f = locales.pop();
        return (x) -> f.apply(x, null);
    }


//    String configureBrackets(String expression) {
//        StringBuilder sb = new StringBuilder(expression);
//        var maxOrder = Integer.MIN_VALUE;
//        var index = Integer.MAX_VALUE;
//        for (var i = 0; i < sb.length();  i++) {
//            try {
//                Character ch = sb.charAt(i);
//                Operator operator = Operator.parse(ch.toString());
//                if (operator.order > maxOrder) {
//                    maxOrder = operator.order;
//                    index = i;
//                }
//            }
//            catch (Exception exception) { }
//        }
//        for (var i = index; i < sb.length(); i++) {
//
//        }
//    }
}
