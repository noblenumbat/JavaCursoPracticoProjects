package org.jomaveger.bookexamples.chapter6;

import org.jomaveger.functional.control.Option;

public class Agent {
	
	private String firstName;
	private String lastName;
	private Integer birthYear;
	private String email;
	private Address address;
	
	public Agent(String firstName, String lastName, Integer birthYear) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthYear = birthYear;
	}
	
	public Agent(String firstName, String lastName, Integer birthYear,
					String email, Address address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthYear = birthYear;
		this.email = email;
		this.address = address;
	}
	
	public Agent(String firstName, String lastName, Integer birthYear,
				String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthYear = birthYear;
		this.email = email;		
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Integer getBirthYear() {
		return birthYear;
	}

	public Option<String> getEmail() {
		return Option.instance(email);
	}

	public Option<Address> getAddress() {
		return Option.instance(address);
	}
}
