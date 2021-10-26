package org.jomaveger.bookexamples.chapter7.records;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PersonTest {

	@Test
	public void checkConstructorAndGetterMethods() {
	    String name = "John Doe";
	    String address = "100 Linda Ln.";

	    Person person = new Person(name, address);

	    assertEquals(name, person.name());
	    assertEquals(address, person.address());
	}
	
	@Test
	public void checkEqualsMethod() {
	    String name = "John Doe";
	    String address = "100 Linda Ln.";

	    Person person1 = new Person(name, address);
	    Person person2 = new Person(name, address);

	    assertTrue(person1.equals(person2));
	}
	
	@Test
	public void checkHashCodeMethod() {
	    String name = "John Doe";
	    String address = "100 Linda Ln.";

	    Person person1 = new Person(name, address);
	    Person person2 = new Person(name, address);

	    assertEquals(person1.hashCode(), person2.hashCode());
	}
	
	
	@Test
	public void checkToStringMethod() {
	    String name = "John Doe";
	    String address = "100 Linda Ln.";

	    Person person = new Person(name, address);

	    assertEquals("Person[name=John Doe, address=100 Linda Ln.]", person.toString());
	}
}
