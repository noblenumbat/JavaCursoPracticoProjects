package org.jomaveger.bookexamples.chapter6;

import org.jomaveger.functional.control.Result;

public class Spy {
	
	private final String firstName;
	private final String lastName;
	private final Result<String> email;

	public Spy(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = Result.empty();
	}

	public Spy(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = Result.success(email);
	}

	public Result<String> getEmail() {
		return email;
	}
}