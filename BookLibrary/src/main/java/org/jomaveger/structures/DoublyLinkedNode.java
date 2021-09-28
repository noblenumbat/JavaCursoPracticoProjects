package org.jomaveger.structures;

import java.io.Serializable;

public class DoublyLinkedNode<T> implements Serializable {

    private T elem;
    private DoublyLinkedNode<T> next;
    private DoublyLinkedNode<T> previous;

    public DoublyLinkedNode(final T elem,
                            final DoublyLinkedNode<T> next,
                            final DoublyLinkedNode<T> previous) {
        this.elem = elem;
        this.next = next;
        if (this.next != null)
            this.next.previous = this;
        this.previous = previous;
        if (this.previous != null)
            this.previous.next = this;
    }

    public DoublyLinkedNode(final T elem) {
        this(elem,null,null);
    }

    public T getElem() {
        return this.elem;
    }

    public void setElem(final T elem) {
        this.elem = elem;
    }

    public DoublyLinkedNode<T> getNext() {
        return this.next;
    }

    public void setNext(final DoublyLinkedNode<T> next) {
        this.next = next;
    }

    public DoublyLinkedNode<T> getPrevious() {
        return this.previous;
    }

    public void setPrevious(final DoublyLinkedNode<T> previous) {
        this.previous = previous;
    }
}
