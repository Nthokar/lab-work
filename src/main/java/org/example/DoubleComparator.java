package org.example;

import java.util.Comparator;

public class DoubleComparator implements Comparator<Double> {
    @Override
    public int compare(Double o1, Double o2) {
        if (o1 > o2) return 1;
        if (o1 < o2) return -1;
        else return 0;
    }
}
