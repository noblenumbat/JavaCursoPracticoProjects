package org.jomaveger.functional.functions;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface TriFunction<T, U, V, R> {
    
	R apply(T a, U b, V c);
	
	default <S> TriFunction<T, U, V, S> andThen(Function<? super R, ? extends S> after) {
		Objects.requireNonNull(after);
        return (t, u, v) -> after.apply(apply(t, u, v));
    }
}
