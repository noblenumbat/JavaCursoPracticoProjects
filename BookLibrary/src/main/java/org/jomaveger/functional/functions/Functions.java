package org.jomaveger.functional.functions;

import java.util.function.Function;

public interface Functions {
		
	public static <T, U, V> Function<Function<U, V>, Function<Function<T, U>, Function<T, V>>> compose() {
	    return (Function<U, V> f) -> (Function<T, U> g) -> (T x) -> f.apply(g.apply(x));
	}
	
	public static <T, U, V> Function<Function<T, U>, Function<Function<U, V>, Function<T, V>>> andThen() {
	    return (Function<T, U> f) -> (Function<U, V> g) -> (T z) -> g.apply(f.apply(z));
	}
}
