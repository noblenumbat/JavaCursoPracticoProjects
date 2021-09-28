package org.jomaveger.functional.control;

import java.util.Objects;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class Memoizer<K, V> {

	private final Map<K, V> cache = new ConcurrentHashMap<>();

	private Memoizer() {
	}

	public static <K, V> Function<K, V> memoize(Function<K, V> function) {
		Objects.requireNonNull(function);
		return new Memoizer<K, V>().doMemoize(function);
	}

	private Function<K, V> doMemoize(Function<K, V> function) {
		return input -> cache.computeIfAbsent(input, function::apply);
	}
}