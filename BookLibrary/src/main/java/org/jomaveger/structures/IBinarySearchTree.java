package org.jomaveger.structures;

import org.jomaveger.lang.dbc.Contract;

public interface IBinarySearchTree<K extends Comparable<K>, V> {

    K min();

    K max();

    Boolean contains(final K key);
        
    V get(final K key);

    void put(final K key, final V value);

    void remove(final K key);

    Boolean isEmpty();

    void makeEmpty();

    Boolean isBalanced();

    Integer height();

    Integer size();

    Integer leaves();
    
    IList<TableEntry<K,V>> preorder();

    IList<TableEntry<K,V>> inorder();
    
    IList<TableEntry<K,V>> postorder();

    IList<TableEntry<K,V>> levelorder();

    IList<TableEntry<K,V>> getOrderedList();
    
    IBinarySearchTree<K, V> deepCopy();

    static <T extends Comparable<T>> IList<T> sort(IList<T> list) {
    	Contract.require(list != null);
    	IBinarySearchTree<T, T> tree = new BinarySearchTree<>();

        while (!list.isEmpty()) {
            T elem = list.getFirst();
            list.removeFirst();
            tree.put(elem, elem);
        }

        IList<TableEntry<T, T>> orderedList = tree.getOrderedList();
        IList<T> resul = new LinkedList<>();

        for (TableEntry<T, T> entry : orderedList) {
            resul.addLast(entry.getKey());
        }
        Contract.ensure(resul.size() >= 0 && (list.isEmpty() || !resul.equals(list)));
        return resul;
    }
}