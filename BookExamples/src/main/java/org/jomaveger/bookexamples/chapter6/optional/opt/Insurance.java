package org.jomaveger.bookexamples.chapter6.optional.opt;

public class Insurance {
	
	private String name;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name != null ? name : "";
	}
}
