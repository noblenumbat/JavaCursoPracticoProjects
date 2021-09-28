package org.jomaveger.structures;

import java.io.Serializable;
import java.util.Objects;
import org.jomaveger.lang.DeepCloneable;
import org.jomaveger.lang.dbc.Contract;

public class ClosedHashTable<K, V> implements ITable<K, V>, Serializable {

    private Integer m;
    private Integer n;
    private Double maxL;
    private Array<TableEntry<K, V>> table;

    public ClosedHashTable() {
        this(16, 0.6);
    }

    public ClosedHashTable(Integer m0, Double maxL) {
        this.maxL = maxL;
        this.m = m0;
        this.table = new Array<>(this.m);
        for (int i = 0; i < this.table.length(); i++) {
            this.table.set(null, i);
        }
        this.n = 0;
        
        Contract.ensure(isEmpty());
        Contract.invariant(checkInvariant());
    }

    @Override
    public void set(K key, V value) {
    	Contract.invariant(checkInvariant());
    	Contract.require(key != null && value != null);
    	int oldSize = size();
    	boolean oldContains = contains(key);
    	int oldKeyListSize = keyList().size();
    	
        if ((1.0 * this.n) / this.m > this.maxL) {
            this.restructure();
        }

        if (this.contains(key)) {
            setNewValue(key, value);
        } else {
            setNewKey(key, value);
            this.n++;
        }
        
        Contract.ensure(!isEmpty() && (oldContains || size() == oldSize + 1)
        		&& (!oldContains || oldKeyListSize == keyList().size()));
        Contract.invariant(checkInvariant());
    }

    private void setNewValue(K key, V value) {
        int index = this.index(key);
        int d = this.step(key);

        while (this.table.get(index) != null &&
                (this.table.get(index).getKey() == null ||
                        !this.table.get(index).getKey().equals(key))) {
            index = (index + d) % this.m;
        }
        this.table.get(index).setValue(value);
    }

    private void setNewKey(K key, V value) {
        int index = this.index(key);
        int d = this.step(key);

        while (this.table.get(index) != null
                && this.table.get(index).getKey() != null) {
            index = (index + d) % this.m;
        }

        if (this.table.get(index) == null) {
            this.table.set(new TableEntry<>(key, value), index);
        } else {
            this.table.get(index).setKey(key);
            this.table.get(index).setValue(value);
        }
    }

    private int index(K key) {
        return Math.abs(key.hashCode()) % this.m;
    }

    private int step(K key) {
        int s = Math.abs(key.hashCode()) / this.m;
        return (s % 2 == 0) ? s + 1 : s;
    }

    private void restructure() {
        this.n = 0;
        this.m = this.m * 2;

        Array<TableEntry<K, V>> tmp = this.table;
        this.table = new Array<>(this.m);
        for (int i = 0; i < this.table.length(); i++) {
            this.table.set(null, i);
        }

        for (int i = 0; i < tmp.length(); i++) {
            TableEntry<K, V> tableEntry = tmp.get(i);
            if (tableEntry != null && tableEntry.getKey() != null) {
                this.set(tableEntry.getKey(), tableEntry.getValue());
            }
        }
    }

    @Override
    public V get(K key) {
    	Contract.invariant(checkInvariant());
    	Contract.require(key != null);
    	int oldSize = size();
    	boolean oldContains = contains(key);
    	int oldKeyListSize = keyList().size();
    	
        int index = this.index(key);
        int d = this.step(key);
        V result = null;
        while (this.table.get(index) != null &&
                (this.table.get(index).getKey() == null ||
                !this.table.get(index).getKey().equals(key))) {
            index = (index + d) % this.m;
        }

        result = (this.table.get(index) == null) ? null : this.table.get(index).getValue();
        
        Contract.ensure(size() == oldSize && (oldKeyListSize == keyList().size())
        		&& ((!oldContains && result == null) || (oldContains && result != null)));
        Contract.invariant(checkInvariant());
        return result;
    }

    @Override
    public void remove(K key) {
    	Contract.invariant(checkInvariant());
    	Contract.require(key != null);
    	int oldSize = size();
    	boolean oldContains = contains(key);
    	int oldKeyListSize = keyList().size();
    	
        int index = this.index(key);
        int d = this.step(key);

        while (this.table.get(index) != null &&
                (this.table.get(index).getKey() == null ||
                        !this.table.get(index).getKey().equals(key))) {
            index = (index + d) % this.m;
        }

        if (this.table.get(index) != null) {
            this.table.get(index).setKey(null);
            this.n--;
        }
        
        Contract.ensure((oldContains || oldKeyListSize == keyList().size())
        		&& (oldContains || size() == oldSize)
        		&& (!oldContains || oldKeyListSize - 1 == keyList().size())
        		&& (!oldContains || size() == oldSize - 1));
        Contract.invariant(checkInvariant());
    }

    @Override
    public void clear() {
    	Contract.invariant(checkInvariant());
    	
        this.table = new Array<>(this.m);
        for (int i = 0; i < this.table.length(); i++) {
            this.table.set(null, i);
        }
        this.n = 0;
        
        Contract.ensure(isEmpty());
        Contract.invariant(checkInvariant());
    }

    @Override
    public Boolean contains(K key) {
    	Contract.invariant(checkInvariant());
    	Contract.require(key != null);
    	int oldSize = size();
    	
        int index = this.index(key);
        int d = this.step(key);

        while (this.table.get(index) != null &&
                (this.table.get(index).getKey() == null ||
                        !this.table.get(index).getKey().equals(key))) {
            index = (index + d) % this.m;
        }

        Boolean contains = (this.table.get(index) == null) ? false : true;
        
        Contract.ensure(size() == oldSize);
        Contract.invariant(checkInvariant());
        return contains;
    }

    @Override
    public Boolean isEmpty() {
    	Contract.invariant(checkInvariant());
    	
        Boolean condition = this.n == 0;
        
        Contract.ensure(condition == (size() == 0));
    	Contract.invariant(checkInvariant());
        return condition;
    }

    @Override
    public Integer size() {
    	Contract.invariant(checkInvariant());
    	
        Integer size = this.n;
        
        Contract.ensure(size >= 0);
        Contract.invariant(checkInvariant());
        return size;
    }

    @Override
    public ITable<K, V> deepCopy() {
    	Contract.invariant(checkInvariant());
        ITable<K, V> deepCopy;
        try {
            deepCopy = DeepCloneable.deepCopy(this);
        } catch (Exception e) {
            deepCopy = new ClosedHashTable<>();
        }
        Contract.ensure(deepCopy.equals(this) || deepCopy.isEmpty());
        Contract.invariant(checkInvariant());
        return deepCopy;
    }

    @Override
    public IList<K> keyList() {
    	Contract.invariant(checkInvariant());
        IList<K> list = new LinkedList<K>();
        for (int i = 0; i < this.table.length(); i++) {
            if (this.table.get(i) != null &&
                    this.table.get(i).getKey() != null)
                list.addLast(this.table.get(i).getKey());
        }
        Contract.ensure(list != null && list.size() >= 0);
        Contract.invariant(checkInvariant());
        return list;
    }

    private boolean checkInvariant() {
        return this.table != null;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null
                || this.getClass() != otherObject.getClass())
            return false;

        ClosedHashTable<K, V> that = (ClosedHashTable<K, V>) otherObject;
        return Objects.equals(this.table, that.table);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.table);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.getClass().getName() + "[");
        string.append(this.table.toString());
        string.append("]");
        return string.toString();
    }
}