package org.jomaveger.bookexamples.chapter7.patternmatching;

import static org.jomaveger.functional.data.LinkedList.*;

import org.jomaveger.functional.data.LinkedList;

public class LinkedListMain {
	
	public static void main(String[] args) {
		LinkedList<String> emptyList = new LinkedList.Nil<>();
        System.out.println(emptyList);

        LinkedList<String> oneElementList = new LinkedList.Cons<>("Test", new LinkedList.Nil<>());
        System.out.println(oneElementList);

        LinkedList<Integer> list = new LinkedList.Nil<>();
        for (int i = 0; i < 10; i++) {
            list = addElement(list, i);
        }
        System.out.println(list);
        System.out.println(length(list));
        System.out.println(head(list));
        System.out.println(tail(list));
        System.out.println(init(list));
        System.out.println(last(list));
        System.out.println(append(tail(list), init(list)));
        System.out.println(length(append(tail(list), init(list))));
	}
}
