package org.jomaveger.functional.data;

import java.util.function.Function;

public sealed interface LinkedList<T> permits LinkedList.Nil, LinkedList.Cons {

	record Nil<T> () implements LinkedList<T> {
	}

	record Cons<T> (T value, LinkedList<T> next) implements LinkedList<T> {
	}

	static <T> T head(LinkedList<T> list) {
		return execute(list, nil -> {
			throw new IllegalStateException("List is empty");
		}, cons -> cons.value);
	}

	static <T> LinkedList<T> tail(LinkedList<T> list) {
		return execute(list, nil -> {
			throw new IllegalStateException("List is empty");
		}, cons -> cons.next);
	}

	static <T> LinkedList<T> init(LinkedList<T> list) {
		return execute(list, nil -> {
			throw new IllegalStateException("List is empty");
		}, cons -> execute(cons.next, nil -> new Nil<>(), consInner -> new Cons<>(cons.value, init(cons.next))));
	}

	static <T> T last(LinkedList<T> list) {
		return execute(list, nil -> {
			throw new IllegalStateException("List is empty");
		}, cons -> execute(cons.next, nil -> cons.value, consInner -> last(cons.next)));
	}

	static <T> LinkedList<T> addElement(LinkedList<T> list, T element) {
		return execute(list, nil -> new Cons<>(element, new Nil<>()),
				cons -> new Cons<>(cons.value, addElement(cons.next, element)));
	}

	static <T> LinkedList<T> append(LinkedList<T> list1, LinkedList<T> list2) {
		return execute(list1, nil -> list2, cons -> new Cons<>(cons.value, append(cons.next, list2)));
	}

	static <T> int length(LinkedList<T> list) {
		return length(list, 0);
	}

	private static <T> int length(LinkedList<T> list, int counter) {
		return execute(list, nil -> counter, cons -> length(cons.next, counter + 1));
	}

	private static <T, R> R execute(LinkedList<T> list, 
									Function<Nil<T>, R> nilHandler,
									Function<Cons<T>, R> consHandler) {
		return switch (list) {
			case Nil<T> nil -> nilHandler.apply(nil);
			case Cons<T> cons -> consHandler.apply(cons);
			default -> throw new IllegalArgumentException("Unknown type");
		};
	}
}
