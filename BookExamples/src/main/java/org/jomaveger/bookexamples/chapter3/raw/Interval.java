package org.jomaveger.bookexamples.chapter3.raw;

public class Interval {

    private Comparable lower;
    private Comparable upper;

    public Interval(final Comparable first, final Comparable second) {
        if (first.compareTo(second) <= 0) {
            this.lower = first;
            this.upper = second;
        } else {
            this.lower = second;
            this.upper = first;
        }
    }

    public Comparable getLower() {
        return this.lower;
    }

    public void setLower(final Comparable lower) {
        this.lower = lower;
    }

    public Comparable getUpper() {
        return this.upper;
    }

    public void setUpper(final Comparable upper) {
        this.upper = upper;
    }
}
