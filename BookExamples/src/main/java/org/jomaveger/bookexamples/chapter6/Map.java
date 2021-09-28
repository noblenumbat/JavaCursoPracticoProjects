package org.jomaveger.bookexamples.chapter6;

import java.util.HashMap;

import org.jomaveger.functional.control.Result;

public class Map<T, U> {
	
	private final java.util.Map<T, U> map = new HashMap<>();

	public static <T, U> Map<T, U> empty() {
		return new Map<>();
	}

	public static <T, U> Map<T, U> add(Map<T, U> m, T t, U u) {
		m.map.put(t, u);
		return m;
	}

	public Result<U> get(final T t) {
		return this.map.containsKey(t) ? Result.success(this.map.get(t)) : Result.empty();
	}

	public Map<T, U> put(T t, U u) {
		return add(this, t, u);
	}

	public Map<T, U> removeKey(T t) {
		this.map.remove(t);
		return this;
	}
}