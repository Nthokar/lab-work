package org.example;

public class ExtremumLocal<T> {
    public final T left;
    public final T right;
    public ExtremumLocal(T left, T right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return String.format("left: %s right: %s", left.toString(), right.toString());
    }
}
