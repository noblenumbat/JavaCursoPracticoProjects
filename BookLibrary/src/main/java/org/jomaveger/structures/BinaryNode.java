package org.jomaveger.structures;

import java.io.Serializable;

public class BinaryNode<T> implements Serializable {

    private T elem;
    private BinaryNode<T> left;
    private BinaryNode<T> right;

    public BinaryNode() {
        this(null, null, null);
    }

    public BinaryNode(final T elem,
                        final BinaryNode<T> left,
                        final BinaryNode<T> right) {
        this.elem = elem;
        this.left = left;
        this.right = right;
    }

    public T getElem() {
        return this.elem;
    }

    public void setElem(T elem) {
        this.elem = elem;
    }

    public BinaryNode<T> getLeft() {
        return this.left;
    }

    public void setLeft(BinaryNode<T> left) {
       this.left = left;
    }

    public BinaryNode<T> getRight() {
       return this.right;
    }

    public void setRight(BinaryNode<T> right) {
        this.right = right;
    }
}