package org.jomaveger.bookexamples.chapter2;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

public class Point2D {

    private Double x;
    private Double y;
    private static Integer numberOfPoints;

    static {
        Point2D.numberOfPoints = 0;
    }

    public Point2D() {
        this(0.0, 0.0);
    }

    public Point2D(final Double x, final Double y) {
        this.x = x;
        this.y = y;
        Point2D.numberOfPoints++;
    }

    public Double getX() {
        return this.x;
    }

    public Double getY() {
        return this.y;
    }

    public static Integer getNumberOfPoints() {
        return Point2D.numberOfPoints;
    }

    public Double getRho() {
        return sqrt(pow(x, 2) + pow(y, 2));
    }

    public Double getTheta() {
        Double angrad = atan2(y, x);
        return toDegrees(angrad);
    }

    public Double getDistance(final Point2D p) {
        return sqrt(pow(x - p.x, 2) + pow(y - p.y, 2));
    }

    public void translate(final Double a, final Double b) {
        x += a;
        y += b;
    }

    public void scale(final Double factor) {
        x *= factor;
        y *= factor;
    }

    public void rotate(final Point2D p, final Double angle) {
        Double angrad = toRadians(angle);

        Double x1 = this.x - p.x;
        Double y1 = this.y - p.y;

        Double x2 = x1 * cos(angrad) - y1 * sin(angrad);
        Double y2 = x1 * sin(angrad) + y1 * cos(angrad);

        this.x = x2 + p.x;
        this.y = y2 + p.y;
    }
}
