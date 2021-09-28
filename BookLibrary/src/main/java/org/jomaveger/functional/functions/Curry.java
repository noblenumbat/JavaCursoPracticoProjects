package org.jomaveger.functional.functions;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.jomaveger.functional.tuples.Tuple2;
import org.jomaveger.functional.tuples.Tuple3;
import org.jomaveger.functional.tuples.Tuple4;

public interface Curry {
	
	public static <T, U, R> Function<T, Function<U, R>> curry2f(BiFunction<T, U, R> f) {
        return t -> u -> f.apply(t, u);
    }
	
	public static <T, U, V, R> Function<T, Function<U, Function<V, R>>> 
						curry3f(TriFunction<T, U, V, R> f) {
		return t -> u -> v -> f.apply(t, u, v);
	}

	public static <T, U, V, W, R> Function<T, Function<U, Function<V, Function<W, R>>>> 
						curry4f(QuadFunction<T, U, V, W, R> f) {
		return t -> u -> v -> w -> f.apply(t, u, v, w);
	}
	
	public static <A, B, C> Function<A, Function<B, C>> curry2t(Function<Tuple2<A, B>, C> f) {
		return (A a) -> (B b) -> f.apply(Tuple2.from(a, b));
	}
	
	public static <A, B, C, D> Function<A, Function<B, Function<C, D>>> curry3t(Function<Tuple3<A, B, C>, D> f) {
		return a -> b -> c -> f.apply(Tuple3.from(a, b, c));
	}
	
	public static <A, B, C, D, E> Function<A, Function<B, Function<C, Function<D, E>>>> curry4t(Function<Tuple4<A, B, C, D>, E> f) {
		return a -> b -> c -> d -> f.apply(Tuple4.from(a, b, c, d));
	}
}
