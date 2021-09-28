package org.jomaveger.structures;

import java.io.Serializable;
import java.util.Objects;
import org.jomaveger.lang.DeepCloneable;
import org.jomaveger.lang.dbc.Contract;

public class LinkedTable<K, V> implements ITable<K, V>, Serializable {

    private IList<TableEntry<K, V>> table;

    public LinkedTable() {
        this.table = new LinkedList<>();
        
        Contract.ensure(isEmpty());
        Contract.invariant(checkInvariant());
    }

    @Override
    public void set(final K key, final V value) {
    	Contract.invariant(checkInvariant());
    	Contract.require(key != null && value != null);
    	int oldSize = size();
    	boolean oldContains = contains(key);
    	int oldKeyListSize = keyList().size();
    	
        boolean insert = true;
        for (Integer i = 0; i < this.table.size(); i++) {
            if (this.table.get(i).getKey().equals(key)) {
                this.table.get(i).setValue(value);
                insert = false;
            }
        }
        if (insert) {
            this.table.addLast(new TableEntry<>(key, value));
        }
        
        Contract.ensure(!isEmpty() && (oldContains || size() == oldSize + 1)
        		&& (!oldContains || oldKeyListSize == keyList().size()));
        Contract.invariant(checkInvariant());
    }

    @Override
    public V get(final K key) {
    	Contract.invariant(checkInvariant());
    	Contract.require(key != null);
    	int oldSize = size();
    	boolean oldContains = contains(key);
    	int oldKeyListSize = keyList().size();
    	
        V result = null;
        for (Integer i = 0; i < this.table.size(); i++) {
            if (this.table.get(i).getKey().equals(key))
                result = this.table.get(i).getValue();
        }
        
        Contract.ensure(size() == oldSize && (oldKeyListSize == keyList().size())
        		&& ((!oldContains && result == null) || (oldContains && result != null)));
        Contract.invariant(checkInvariant());
        return result;
    }

    @Override
    public void remove(final K key) {
    	Contract.invariant(checkInvariant());
    	Contract.require(key != null);
    	int oldSize = size();
    	boolean oldContains = contains(key);
    	int oldKeyListSize = keyList().size();

        for (Integer i = 0; i < this.table.size(); i++) {
            if (this.table.get(i).getKey().equals(key))
                this.table.remove(i);
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
    	
        this.table.clear();
        
        Contract.ensure(isEmpty());
        Contract.invariant(checkInvariant());
    }

    @Override
    public Boolean contains(final K key) {
    	Contract.invariant(checkInvariant());
    	Contract.require(key != null);
    	int oldSize = size();
    	
        Boolean contains = Boolean.FALSE;
        for (Integer i = 0; i < this.table.size(); i++) {
            if (this.table.get(i).getKey().equals(key))
                contains = Boolean.TRUE;
        }
        
        Contract.ensure(size() == oldSize);
        Contract.invariant(checkInvariant());
        return contains;
    }

    @Override
    public Boolean isEmpty() {
    	Contract.invariant(checkInvariant());
    	
        Boolean condition = this.table.isEmpty();
        
        Contract.ensure(condition == (size() == 0));
    	Contract.invariant(checkInvariant());
        return condition;
    }

    @Override
    public Integer size() {
    	Contract.invariant(checkInvariant());
    	
        Integer size = this.table.size();
        
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
            deepCopy = new LinkedTable<>();
        }
        Contract.ensure(deepCopy.equals(this) || deepCopy.isEmpty());
        Contract.invariant(checkInvariant());
        return deepCopy;
    }

    @Override
    public IList<K> keyList() {
    	Contract.invariant(checkInvariant());
    	
        IList<K> list = new LinkedList<K>();
        for (int i = 0; i < this.table.size(); i++) {
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

        LinkedTable<K, V> that = (LinkedTable<K, V>) otherObject;
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