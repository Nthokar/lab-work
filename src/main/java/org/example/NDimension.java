package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class NDimension {
    private final List<Double> x;

    public Double get(int index) {
        return x.get(index);
    }

    public NDimension(List<Double> x) {
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
        for (var i = 0; i < x.size(); i++){
            sb.append(String.format("x%s= %s,", i, x.get(i)));
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
        if (this.x.size() != other.x.size()) throw new RuntimeException();

        for (var i = 0; i < x.size(); i++) {
            if (predicate.test(x.get(i), other.x.get(i))) return true;
        }
        return false;
    }
    public boolean isAll(BiPredicate<Double, Double> predicate, NDimension other) {
        if (this.x.size() != other.x.size()) throw new RuntimeException();
        for (var i = 0; i < x.size(); i++) {
            if (!predicate.test(x.get(i), other.x.get(i))) return false;
        }
        return true;
    }
    private NDimension forEach(NDimension a, BiFunction<Double, Double, Double> f) {
        List<Double> nList = new ArrayList<>();
        if (this.x.size() != a.x.size()) throw new RuntimeException();
        for (var i = 0; i < this.x.size(); i++) {
            nList.add(f.apply(this.x.get(i), a.x.get(i)));
        }
        return new NDimension(nList);
    }
    private NDimension forEach(Double a, BiFunction<Double,Double, Double> f) {
        List<Double> nList = new ArrayList<>();
        for (var i = 0; i < this.x.size(); i++) {
            nList.add(f.apply(this.get(i), a));
        }
        return new NDimension(nList);
    }
}

