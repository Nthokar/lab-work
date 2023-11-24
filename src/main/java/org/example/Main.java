package org.example;

public class Main {
    public static void main(String[] args) {
        var l = new Localization();
        l.f = (x) -> x*x;
        l.h = 0.01;
        l.xMin = -100.0;
        l.xMax = 100.0;
        l.findMaxLocales();
        System.out.println(l.extremes);
    }
}