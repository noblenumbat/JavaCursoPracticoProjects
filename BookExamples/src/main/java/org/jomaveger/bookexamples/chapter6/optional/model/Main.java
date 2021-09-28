package org.jomaveger.bookexamples.chapter6.optional.model;

public class Main {

	public String getCarInsuranceName(Person person) {
	    return person.getCar().getInsurance().getName();
	}
}
