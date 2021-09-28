package org.jomaveger.structures;

import java.io.Serializable;

public class LinkedNode<T> implements Serializable {

    private T elem;
    private LinkedNode<T> next;

    public LinkedNode(final T elem) {
        this.elem = elem;
        this.next = null;
    }

    public LinkedNode(final T elem, final LinkedNode<T> next) {
        this.elem = elem;
        this.next = next;
    }

    public void setElem(final T elem) {
        this.elem = elem;
    }

    public T getElem() {
        return this.elem;
    }

    public void setNext(final LinkedNode<T> next) {
        this.next = next;
    }

    public LinkedNode<T> getNext(){
        return this.next;
    }
}
