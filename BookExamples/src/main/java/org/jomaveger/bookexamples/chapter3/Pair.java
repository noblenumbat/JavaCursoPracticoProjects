package org.jomaveger.bookexamples.chapter3;

import java.io.Serializable;
import java.util.Objects;

import org.jomaveger.lang.DeepCloneable;

public class Pair<T> implements Serializable {

    private T first;
    private T second;

    public Pair() {
        this.first = null;
        this.second = null;
    }

    public Pair(final T first, final T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return this.first;
    }

    public T getSecond() {
        return this.second;
    }

    public void setFirst(final T first) {
        this.first = first;
    }

    public void setSecond(final T second) {
        this.second = second;
    }
    
    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null) return false;

        if (this.getClass() != otherObject.getClass()) return false;

        Pair<T> other = (Pair<T>) otherObject;

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
    
    public Pair<T> deepCopy() {
        try {
            return DeepCloneable.deepCopy(this);
        } catch (Exception e) {
            return new Pair<>();
        }
    }
}
