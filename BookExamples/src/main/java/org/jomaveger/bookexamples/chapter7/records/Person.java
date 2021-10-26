package org.jomaveger.bookexamples.chapter7.records;

import java.util.Objects;

public record Person(String name, String address) {
	
	public static String UNKNOWN_ADDRESS = "Unknown";
	public static String UNKNOWN_NAME = "Unnamed";
	
	public Person {
        Objects.requireNonNull(name);
        Objects.requireNonNull(address);
    }
	
//	public Person(String name, String address) {
//		Objects.requireNonNull(name);
//        Objects.requireNonNull(address);
//        this.name = name;
//        this.address = address;
//    }
	
	public Person(String name) {
		this(name, UNKNOWN_ADDRESS);
	}

	public static Person unnamed(String address) {
        return new Person(UNKNOWN_NAME, address);
    }
}
