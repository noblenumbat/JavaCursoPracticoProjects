package org.jomaveger.structures;

import java.io.Serializable;
import java.util.Objects;
import org.jomaveger.lang.DeepCloneable;
import org.jomaveger.lang.dbc.Contract;

public class OpenHashTable<K, V> implements ITable<K, V>, Serializable {

    private Integer m;
    private Integer n;
    private Double maxL;
    private Array<IList<TableEntry<K, V>>> table;

    public OpenHashTable() {
        this(16, 2.5);
    }

    public OpenHashTable(Integer m0, Double maxL) {
        this.maxL = maxL;
        this.m = m0;
        this.table = new Array<>(this.m);
        for (int i = 0; i < this.table.length(); i++) {
            this.table.set(new LinkedList<>(), i);
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

        int i = this.index(key);

        Boolean encontrado = this.contains(key);
        if (encontrado) {
            IList<TableEntry<K, V>> tableEntries = this.table.get(i);
            for (Integer j = 0; j < tableEntries.size(); j++) {
                if (tableEntries.get(j).getKey().equals(key))
                    tableEntries.get(j).setValue(value);
            }
        } else {
            IList<TableEntry<K, V>> tableEntries = this.table.get(i);
            tableEntries.addFirst(new TableEntry<>(key, value));
            this.table.set(tableEntries, i);
            this.n++;
        }
        
        Contract.ensure(!isEmpty() && (oldContains || size() == oldSize + 1)
        		&& (!oldContains || oldKeyListSize == keyList().size()));
        Contract.invariant(checkInvariant());
    }

    private int index(K key) {
        return Math.abs(key.hashCode()) % this.m;
    }

    private void restructure() {
        Array<IList<TableEntry<K, V>>> tmp = this.table;
        this.n = 0;
        this.m = this.m * 2;
        this.table = new Array<>(this.m);
        for (int i = 0; i < tmp.length(); i++) {
            IList<TableEntry<K, V>> tableEntries = tmp.get(i);
            for (int k = 0; k < tableEntries.size(); k++) {
                this.set(tableEntries.get(k).getKey(), tableEntries.get(k).getValue());
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
    	
        int i = this.index(key);

        IList<TableEntry<K, V>> tableEntries = this.table.get(i);
        V result = null;
        for (Integer j = 0; j < tableEntries.size(); j++) {
            if (tableEntries.get(j).getKey().equals(key))
                result = tableEntries.get(j).getValue();
        }
        
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
    	
        int i = this.index(key);

        IList<TableEntry<K, V>> tableEntries = this.table.get(i);

        for (Integer j = 0; j < tableEntries.size(); j++) {
            if (tableEntries.get(j).getKey().equals(key)) {
                tableEntries.remove(j);
                this.n--;
            }
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
            this.table.set(new LinkedList<>(), i);
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
    	
        int i = this.index(key);

        IList<TableEntry<K, V>> tableEntries = this.table.get(i);
        Boolean contains = Boolean.FALSE;
        for (Integer j = 0; j < tableEntries.size(); j++) {
            if (tableEntries.get(j).getKey().equals(key))
                contains = Boolean.TRUE;
        }
        
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
            deepCopy = new OpenHashTable<>();
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
            IList<TableEntry<K, V>> tableEntries = this.table.get(i);
            for (int k = 0; k < tableEntries.size(); k++) {
                list.addLast(tableEntries.get(k).getKey());
            }
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

        OpenHashTable<K, V> that = (OpenHashTable<K, V>) otherObject;
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