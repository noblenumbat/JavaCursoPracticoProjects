package org.jomaveger.structures;

public interface IBag<T> {

    void add(final T elem);

    Integer multiplicity(final T elem);
  
    void delete(final T elem);

    void remove(final T elem);
    
    void clear();

    void union(final IBag<T> other);

    void intersection(final IBag<T> other);

    void difference(final IBag<T> other);

    Boolean isEmpty();

    Integer cardinal();

    Integer cardinalDistinct();

    IBag<T> deepCopy();
}