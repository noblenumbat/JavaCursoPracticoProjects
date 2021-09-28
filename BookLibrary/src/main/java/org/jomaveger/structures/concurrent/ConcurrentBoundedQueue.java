package org.jomaveger.structures.concurrent;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import org.jomaveger.lang.DeepCloneable;
import org.jomaveger.structures.IBoundedQueue;

public class ConcurrentBoundedQueue<T> implements IBoundedQueue<T>, Serializable {
    
    private final Semaphore availableItems, availableSpaces;
    private final T[] items; 
    private int putPosition = 0, takePosition = 0;
    private int size = 0;

    public ConcurrentBoundedQueue(int capacity) {
        availableItems = new Semaphore(0);
        availableSpaces = new Semaphore(capacity);
        items = (T[]) new Object[capacity];
    }

    @Override
    public void enqueue(T elem) {
        try {
            availableSpaces.acquire();
            doInsert(elem);
            availableItems.release();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void dequeue() {
        try {
            availableItems.acquire();
            doRemove();
            availableSpaces.release();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public T front() {
        T elem = null;
        try {
            availableItems.acquire();
            elem = doExtract();
            availableItems.release();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        return elem;
    }

    @Override
    public Boolean isEmpty() {
        return availableItems.availablePermits() == 0;
    }
    
    @Override
    public boolean isFull() {
        return availableSpaces.availablePermits() == 0;
    }

    @Override
    public Integer size() {
        return size;
    }

    @Override
    public IBoundedQueue<T> deepCopy() {
        IBoundedQueue<T> deepCopy;
        try {
            deepCopy = DeepCloneable.deepCopy(this);
        } catch (Exception e) {
            deepCopy = new ConcurrentBoundedQueue<>(10);
        }
        return deepCopy;
    }

    private synchronized void doInsert(T elem) {
        int i = putPosition;
        items[i] = elem;
        size++;
        putPosition = (++i == items.length)? 0 : i;
    }

    private synchronized void doRemove() {
        int i = takePosition;
        items[i] = null;
        size--;
        takePosition = (++i == items.length)? 0 : i;
    }

    private synchronized T doExtract() {
        int i = takePosition;
        T x = items[i];
        return x;
    }

    @Override
    public String toString() {
        String toString = "";
        try {
            availableItems.acquire();
            toString = Arrays.deepToString(this.items);
            availableItems.release();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        return toString;
    }

    @Override
    public boolean equals(Object otherObject) {
        boolean equals = false;
        
        try {
            availableItems.acquire();
            
            if (this == otherObject) {
                equals = true;
            }

            if (otherObject == null || this.getClass() != otherObject.getClass()) {
                equals = false;
            }

            ConcurrentBoundedQueue<T> that = (ConcurrentBoundedQueue<T>) otherObject;

            equals = Arrays.deepEquals(this.items, that.items);
            
            availableItems.release();
            
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        
        return equals;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        try {
            availableItems.acquire();
            hashCode = Arrays.deepHashCode(this.items);
            availableItems.release();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        return hashCode;        
    }
}
