package org.jomaveger.bookexamples.chapter3;

import java.io.Serializable;

public class Interval<T extends Comparable<T> & Serializable> {

    private T lower;
    private T upper;

    public Interval(final T first, final T second) {
        if (first.compareTo(second) <= 0) {
            this.lower = first;
            this.upper = second;
        } else {
            this.lower = second;
            this.upper = first;
        }
    }

    public T getLower() {
        return this.lower;
    }

    public void setLower(final T lower) {
        this.lower = lower;
    }

    public T getUpper() {
        return this.upper;
    }

    public void setUpper(final T upper) {
        this.upper = upper;
    }
}
