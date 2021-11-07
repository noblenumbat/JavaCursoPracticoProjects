package org.jomaveger.bookexamples.chapter11;

import java.io.Serializable;

public class Person implements Comparable<Person>, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final String FORMAT = "ID: %s, First name: %s, Last name: %s";
	public final int id;
	public final String firstName;
	public final String lastName;

	private Person(int id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public static Person apply(int id, String firstName, String lastName) {
		return new Person(id, firstName, lastName);
	}

	@Override
	public String toString() {
		return String.format(FORMAT, id, firstName, lastName);
	}

	@Override
	public int compareTo(Person o) {
		return Integer.compare(id, o.id);
	}
}