package org.jomaveger.functional.functions;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.jomaveger.functional.tuples.Tuple2;
import org.jomaveger.functional.tuples.Tuple3;
import org.jomaveger.functional.tuples.Tuple4;

public interface Uncurry {

	public static <T, U, R> BiFunction<T, U, R> uncurry2f(Function<T, Function<U, R>> f) {
		return (t, u) -> f.apply(t).apply(u);
	}
	
	public static <T, U, V, R> TriFunction<T, U, V, R> 
								uncurry3f(Function<T, Function<U, Function<V, R>>> f) {
		return (t, u, v) -> f.apply(t).apply(u).apply(v);
	}
	
	public static <T, U, V, W, R> QuadFunction<T, U, V, W, R> 
								uncurry4f(Function<T, Function<U, Function<V, Function<W, R>>>> f) {
		return (t, u, v, w) -> f.apply(t).apply(u).apply(v).apply(w);
	}
	
	public static <A, B, C> Function<Tuple2<A, B>, C> uncurry2t(Function<A, Function<B, C>> f) {
		return (Tuple2<A, B> p) -> f.apply(p._1).apply(p._2);
	}
	
	public static <A, B, C, D> Function<Tuple3<A, B, C>, D> uncurry3t(Function<A, Function<B, Function<C, D>>> f) {
		return (Tuple3<A, B, C> p) -> f.apply(p._1).apply(p._2).apply(p._3);
	}

	public static <A, B, C, D, E> Function<Tuple4<A, B, C, D>, E>
					uncurry4(Function<A, Function<B, Function<C, Function<D, E>>>> f) {
		return (Tuple4<A, B, C, D> p) -> f.apply(p._1)
											.apply(p._2)
											.apply(p._3)
											.apply(p._4);
	}
}
