package org.jomaveger.structures;

public interface ITable<K, V> {
    
    void set(final K key, final V value);

    V get(final K key);

    void remove(final K key);

    void clear();

    Boolean contains(final K key);

    Boolean isEmpty();

    Integer size();

    ITable<K, V> deepCopy();
    
    IList<K> keyList();
}
