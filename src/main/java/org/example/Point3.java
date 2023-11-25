package org.example;

public class Point3 {
    public final Double x;
    public final Double y;
    public final Double z;

    public Point3(Double x, Double y, Double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Point3 sum(Point3 p) {
        return new Point3(x + p.x, y + p.y, z + p.z);
    }

    public Point3 dif(Point3 p) {
        return new Point3(x - p.x, y - p.y, z - p.z);
    }

    public Point3 multiply(Double a) {
        return new Point3(x * a, y * a, z * a);
    }

    @Override
    public String toString() {
        return String.format("x=%s y=%s z=%s", x, y, z);
    }
}

