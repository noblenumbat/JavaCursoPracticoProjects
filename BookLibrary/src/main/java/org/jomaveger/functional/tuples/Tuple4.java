package org.jomaveger.functional.tuples;

import java.util.Objects;

public final class Tuple4<T, U, V, X> {

	public final T _1;
	public final U _2;
	public final V _3;
	public final X _4;

	public Tuple4(T t, U u, V v, X x) {
		this._1 = Objects.requireNonNull(t);
		this._2 = Objects.requireNonNull(u);
		this._3 = Objects.requireNonNull(v);
		this._4 = Objects.requireNonNull(x);
	}
	
	public static <T, U, V, X> Tuple4<T,U, V, X> from(T t, U u, V v, X x) {
		return new Tuple4<T, U, V, X>(t, u, v, x);
	}

	@Override
	public String toString() {
		return String.format("(%s,%s,%s,%s)", _1, _2, _3, _4);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o.getClass() == this.getClass()))
			return false;
		else {
			Tuple4 that = (Tuple4) o;
			return _1.equals(that._1) && _2.equals(that._2) 
					&& _3.equals(that._3) && _4.equals(that._4);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _1.hashCode();
		result = prime * result + _2.hashCode();
		result = prime * result + _3.hashCode();
		result = prime * result + _4.hashCode();
		return result;
	}
}
