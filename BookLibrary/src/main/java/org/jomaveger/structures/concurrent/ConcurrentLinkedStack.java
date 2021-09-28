package org.jomaveger.structures.concurrent;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.jomaveger.lang.DeepCloneable;
import org.jomaveger.structures.IStack;
import org.jomaveger.structures.LinkedNode;

public class ConcurrentLinkedStack<T> implements IStack<T>, Serializable {

    private AtomicReference<LinkedNode<T>> top;
    private AtomicInteger size;
    
    public ConcurrentLinkedStack() {
        this.top = new AtomicReference<>();
        this.size = new AtomicInteger(0);
    }
    
    @Override
    public void push(T elem) {
        LinkedNode<T> newTop = new LinkedNode<>(elem);
        LinkedNode<T> oldTop;
        do {
            oldTop = top.get();
            newTop.setNext(oldTop);
            size.incrementAndGet();
        } while (!top.compareAndSet(oldTop, newTop));
    }

    @Override
    public void pop() {
        LinkedNode<T> oldTop;
        LinkedNode<T> newTop;
        do {
            oldTop = top.get();
            newTop = oldTop.getNext();
            size.decrementAndGet();
        } while (!top.compareAndSet(oldTop, newTop));
    }

    @Override
    public T peek() {
        return top.get().getElem();
    }

    @Override
    public Boolean isEmpty() {
        return size.get() == 0;
    }

    @Override
    public Integer size() {
        return size.get();
    }

    @Override
    public IStack<T> deepCopy() {
        IStack<T> deepCopy;
        try {
            deepCopy = DeepCloneable.deepCopy(this);
        } catch (Exception e) {
            deepCopy = new ConcurrentLinkedStack<>();
        }
        return deepCopy;
    }
}
