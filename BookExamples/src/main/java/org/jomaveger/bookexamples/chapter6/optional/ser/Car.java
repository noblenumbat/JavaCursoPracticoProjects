package org.jomaveger.bookexamples.chapter6.optional.ser;

import java.io.Serializable;
import java.util.Optional;

public class Car implements Serializable {

	private Insurance insurance;

	public Optional<Insurance> getInsurance() {
		return Optional.ofNullable(insurance);
	}
	
	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}
}
