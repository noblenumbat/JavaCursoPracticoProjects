package org.jomaveger.structures;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import org.jomaveger.lang.DeepCloneable;
import org.jomaveger.lang.dbc.Contract;

public class Array<T> implements Iterable<T>, Serializable {

    private T[] array;

    public Array(final Integer size) {
    	Contract.require(size >= 0);
    	
        array = (T[]) new Object[size];
        
        Contract.ensure(isEmpty() && (length() == size));
        Contract.invariant(checkInvariant());
    }

    public Integer length() {
    	Contract.invariant(checkInvariant());
    	
        Integer result = array.length;
        
        Contract.ensure(result >= 0);
        Contract.invariant(checkInvariant());
        return result;
    }

    public Boolean isEmpty() {
        Boolean isEmpty = true;
        if (this.length() == 0)
            isEmpty = true;
        else {
            Boolean empty = true;
            int i = 0;
            while (i < length() && empty) {
                if (array[i] != null) {
                    isEmpty = false;
                }
                i++;
           }
        }
        return isEmpty;
    }

    public T get(final Integer index) {
    	Contract.invariant(checkInvariant());
    	Contract.require(index >= 0 && index <= (length() - 1));
    	int oldLength = length();
    	
        T elem = array[index];
        
        Contract.ensure(length() == oldLength);
        Contract.invariant(checkInvariant());
        return elem;
    }

    public void set(final T elem, final Integer index) {
    	Contract.invariant(checkInvariant());
    	Contract.require(index >= 0 && index <= (length() - 1));
    	int oldLength = length();
    	
        array[index] = elem;
        
        Contract.ensure(length() == oldLength);
        Contract.invariant(checkInvariant());
    }

    public Array<T> deepCopy() {
    	Contract.invariant(checkInvariant());
        Array<T> deepCopy;
        try {
            deepCopy = DeepCloneable.deepCopy(this);
        } catch (Exception e) {
            deepCopy = new Array<>(this.length());
        }
        Contract.ensure(deepCopy.equals(this) || deepCopy.isEmpty());
        Contract.invariant(checkInvariant());
        return deepCopy;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(this.array);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null
                || this.getClass() != otherObject.getClass())
            return false;

        Array<T> that = (Array<T>) otherObject;

        return Arrays.deepEquals(this.array, that.array);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.array);
    }

    public T[] toNativeArray() {
        return this.array;
    }

    private boolean checkInvariant() {
        return this.array != null && this.array.length >= 0;
    }

    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<T> {

        private Integer index = 0;

        @Override
        public boolean hasNext() {
            return index < Array.this.array.length;
        }

        @Override
        public T next() {
            return Array.this.array[index++];
        }
    }
}