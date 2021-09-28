package org.jomaveger.functional.tuples;

import java.util.Objects;

public final class Tuple1<T> {

	public final T _1;
	
	public Tuple1(T t) {
		this._1 = Objects.requireNonNull(t);		
	}
	
	public static <T> Tuple1<T> from(T t) {
		return new Tuple1<T>(t);
	}

	@Override
	public String toString() {
		return String.format("(%s)", _1);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o.getClass() == this.getClass()))
			return false;
		else {
			Tuple1 that = (Tuple1) o;
			return _1.equals(that._1);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _1.hashCode();		
		return result;
	}
}
