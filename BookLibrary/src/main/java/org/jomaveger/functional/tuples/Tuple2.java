package org.jomaveger.functional.tuples;

import java.util.Objects;
import java.util.function.Function;

public class Tuple2<T, U> {

	public final T _1;
	public final U _2;

	public Tuple2(T t, U u) {
	    this._1 = Objects.requireNonNull(t);
	    this._2 = Objects.requireNonNull(u);
	}
	
	public static <T, U> Tuple2<T,U> from(T t, U u) {
		return new Tuple2<T, U>(t, u);
	}

	@Override
	public String toString() {
		return String.format("(%s,%s)", _1, _2);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o.getClass() == this.getClass()))
			return false;
		else {
			Tuple2 that = (Tuple2) o;
			return _1.equals(that._1) && _2.equals(that._2);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _1.hashCode();
		result = prime * result + _2.hashCode();
		return result;
	}

	public Tuple2<U, T> swap() {
		return new Tuple2<>(_2, _1);
	}

	public static <T> Tuple2<T, T> swapIf(Tuple2<T, T> t, Function<T, Function<T, Boolean>> p) {
		return p.apply(t._1).apply(t._2) ? t.swap() : t;
	}
}
