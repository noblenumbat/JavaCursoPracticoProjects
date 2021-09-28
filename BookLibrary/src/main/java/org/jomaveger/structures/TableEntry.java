package org.jomaveger.structures;

import java.io.Serializable;
import java.util.Objects;

public class TableEntry<K, V> implements Serializable {

    private K key;
    private V value;

    public TableEntry(final K key, final V value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(final K key) {
        this.key = key;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public void setValue(final V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.getClass().getName() + "[");
        string.append(this.key + ", " + this.value);
        string.append("]");
        return string.toString();
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null
                || this.getClass() != otherObject.getClass())
            return false;

        TableEntry<K, V> that = (TableEntry<K, V>) otherObject;

        return Objects.equals(this.key, that.key) &&
                Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        Object[] array = new Object[2];
        array[0] = this.key;
        array[1] = this.value;

        return Objects.hash(array);
    }
}