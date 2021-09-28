package org.jomaveger.functional.functions;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface QuadFunction<T, U, V, W, R> {

    R apply(T t, U u, V v, W w);

    default <S> QuadFunction<T, U, V, W, S> andThen(Function<? super R, ? extends S> after) {
    	Objects.requireNonNull(after);
        return (t, u, v, w) -> after.apply(apply(t, u, v, w));
    }
}