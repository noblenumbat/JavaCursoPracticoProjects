package org.jomaveger.structures;

public interface ISet<T> {

    void makeUnitSet(final T elem);

    void clear();

    void add(final T elem);

    Boolean contains(final T elem);

    void remove(final T elem);

    Boolean isSubset(final ISet<T> other);

    void union(final ISet<T> other);

    void intersection(final ISet<T> other);

    void difference(final ISet<T> other);

    Boolean isEmpty();

    Integer cardinal();

    ISet<T> deepCopy();
}
