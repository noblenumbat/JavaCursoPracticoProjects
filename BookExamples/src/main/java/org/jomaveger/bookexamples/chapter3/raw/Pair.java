package org.jomaveger.bookexamples.chapter3.raw;

import java.util.Objects;

public class Pair {

    private Object first;
    private Object second;

    public Pair() {
        this.first = null;
        this.second = null;
    }

    public Pair(final Object first, final Object second) {
        this.first = first;
        this.second = second;
    }

    public Object getFirst() {
        return this.first;
    }

    public Object getSecond() {
        return this.second;
    }

    public void setFirst(final Object first) {
        this.first = first;
    }

    public void setSecond(final Object second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null) return false;

        if (this.getClass() != otherObject.getClass()) return false;

        Pair other = (Pair) otherObject;

        return Objects.equals(this.first, other.first) &&
                Objects.equals(this.second, other.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.first, this.second);
    }

    @Override
    public String toString() {
        return getClass().getName() + "[first=" + this.first + ", second=" + this.second + "]";
    }
}