package org.jomaveger.lang.ordering;

import java.io.Serializable;
import java.util.Comparator;

public class NaturalOrder<E extends Comparable<E>> 
					implements Comparator<E>, Serializable {

	@Override
	public int compare(E a, E b) {
		return a.compareTo(b);
	}
}
