package org.jomaveger.structures;

public interface IDequeue<T> {
    
    void addFirst(final T elem);

    void addLast(final T elem);

    T getFirst();

    T getLast();

    void removeFirst();

    void removeLast();

    Boolean contains(final T elem);

    Boolean isEmpty();

    Integer size();

    IDequeue<T> deepCopy();
}
