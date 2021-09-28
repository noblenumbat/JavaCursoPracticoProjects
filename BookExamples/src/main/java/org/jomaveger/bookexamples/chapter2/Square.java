package org.jomaveger.bookexamples.chapter2;

public class Square extends Shape {

    private Double side;

    public Square(final Point2D p, final Double side) {
        super(p);
        this.side = side;
    }

    public Square(final Integer x, final Integer y, final Double side) {
        super(x, y);
        this.side = side;
    }

    public Square(final Double x, final Double y, final Double side) {
        super(x, y);
        this.side = side;
    }

    @Override
    public Double getArea() {
        return this.side * this.side;
    }
}
