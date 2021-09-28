package org.jomaveger.bookexamples.chapter6.optional.ser;

import java.io.Serializable;

public class Insurance implements Serializable {

	private String name;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name != null ? name : "";
	}
}
