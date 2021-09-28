package org.jomaveger.bookexamples.chapter2;

import static java.lang.Math.PI;

public class Circle extends Shape {

    private Double radio;

    public Circle(final Point2D p, final Double r) {
        super(p);
        this.radio = r;
    }

    public Circle(final Integer x, final Integer y, final Double r) {
        super(x, y);
        this.radio = r;
    }

    public Circle(final Double x, final Double y, final Double r) {
        super(x, y);
        this.radio = r;
    }

    @Override
    public Double getArea() {
        return PI * this.radio * this.radio;
    }
}
