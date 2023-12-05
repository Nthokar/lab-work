package org.example;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class NDimension {
    private final Double[] x;

    public Double get(int index) {
        return x[index];
    }

    public NDimension(Double[] x) {
        this.x = x;
    }
    public NDimension sum(NDimension p) {
        return forEach(p, (x, y) -> x + y);
    }

    public NDimension dif(NDimension p) {
        return forEach(p, (x, y) -> x - y);
    }

    public NDimension multiply(Double a) {
        return forEach(a, (x, y) -> x * y);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("#.####");
        for (var i = 0; i < x.length; i++){
            sb.append(String.format("x%s= %s,", i, decimalFormat.format(x[i])));
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
    public boolean isAny(Predicate<Double> predicate) {
        for (var xi:x){
            if (predicate.test(xi)) return true;
        }
        return false;
    }
    public boolean isAll(Predicate<Double> predicate) {
        for (var xi:x){
            if (!predicate.test(xi)) return false;
        }
        return true;
    }

    public boolean isAny(BiPredicate<Double, Double> predicate, NDimension other) {
        if (this.x.length != other.x.length) throw new RuntimeException();

        for (var i = 0; i < x.length; i++) {
            if (predicate.test(x[i], other.x[i])) return true;
        }
        return false;
    }
    public boolean isAll(BiPredicate<Double, Double> predicate, NDimension other) {
        if (this.x.length != other.x.length) throw new RuntimeException();
        for (var i = 0; i < x.length; i++) {
            if (!predicate.test(x[i], other.x[i])) return false;
        }
        return true;
    }
    private NDimension forEach(NDimension a, BiFunction<Double, Double, Double> f) {
        Double[] nList = new Double[a.order()];
        if (this.x.length != a.x.length) throw new RuntimeException();
        for (var i = 0; i < this.x.length; i++) {
            nList[i] = f.apply(this.x[i], a.x[i]);
        }
        return new NDimension(nList);
    }
    private NDimension forEach(Double a, BiFunction<Double,Double, Double> f) {
        Double[] nList = new Double[order()];
        for (var i = 0; i < this.x.length; i++) {
            nList[i] = f.apply(this.get(i), a);
        }
        return new NDimension(nList);
    }

    public Integer order () {
        return x.length;
    }
    public NDimension basis() {
        var x = new Double[order()];
        Arrays.fill(x, 0);
        return new NDimension(x);
    }

    /** WARNING: Only if points on the same line
     *
     */
    public Integer compare(NDimension x, NDimension lineVector, NDimension linePoint) {
        var i1 = this.forEach(linePoint, (i, j) -> i - j);
        var i2 = x.forEach(linePoint, (i, j) -> i - j);

        var r1 = 0;
        for (var i = 0; i < lineVector.order(); i++) {
            if (lineVector.get(i) != 0) {
                r1 = i;
                break;
            }
        }
        var a1 = i1.get(r1)/lineVector.get(r1);
        var a2 = i2.get(r1)/lineVector.get(r1);
        return Double.compare(a1, a2);
    }
}

