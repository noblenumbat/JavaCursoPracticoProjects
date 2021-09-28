package org.jomaveger.structures;

import java.io.Serializable;

import org.jomaveger.lang.dbc.Contract;

public class UnionFind<T> implements Serializable {
	
	private final ITable<T, Link<T>> elems = new ClosedHashTable<>();
	
	public UnionFind() {
	}

	public UnionFind(IList<? extends T> elems) {
        for (T elem: elems)
            add(elem);
    }
	
	public boolean add(T elem) {
        Contract.require(elem != null);

        if (elems.contains(elem))
            return false;

        elems.set(elem, new Link<T>(elem));
        return true;
    }
	
	public T find(T elem) {
		Contract.require(elems.contains(elem));

        return recFind(elem);
    }

	private T recFind(T elem) {
		Link<T> info = elems.get(elem);

        if (info.getParent().equals(elem))
            return elem;

        info.setParent(recFind(info.getParent()));
        
        return info.getParent();
	}
	
	public void union(T one, T two) {       
        Link<T> oneLink = elems.get(find(one));
        Link<T> twoLink = elems.get(find(two));

        if (oneLink == twoLink) return;

        if (oneLink.getRank() > twoLink.getRank()) {
            
        	twoLink.setParent(oneLink.getParent());
        	
        } else if (oneLink.getRank() < twoLink.getRank()) {
        	
            
            oneLink.setParent(twoLink.getParent());
            
        } else {
            
            twoLink.setParent(oneLink.getParent());
            
            oneLink.setRank(oneLink.getRank() + 1);
        }
    }
}
