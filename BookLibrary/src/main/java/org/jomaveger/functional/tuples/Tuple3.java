package org.jomaveger.functional.tuples;

import java.util.Objects;

public final class Tuple3<T, U, V> {

	public final T _1;
	public final U _2;
	public final V _3;

	public Tuple3(T t, U u, V v) {
		this._1 = Objects.requireNonNull(t);
		this._2 = Objects.requireNonNull(u);
		this._3 = Objects.requireNonNull(v);
	}
	
	public static <T, U, V> Tuple3<T,U, V> from(T t, U u, V v) {
		return new Tuple3<T, U, V>(t, u, v);
	}

	@Override
	public String toString() {
		return String.format("(%s,%s,%s)", _1, _2, _3);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o.getClass() == this.getClass()))
			return false;
		else {
			Tuple3 that = (Tuple3) o;
			return _1.equals(that._1) && _2.equals(that._2) && _3.equals(that._3);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _1.hashCode();
		result = prime * result + _2.hashCode();
		result = prime * result + _3.hashCode();
		return result;
	}
}
