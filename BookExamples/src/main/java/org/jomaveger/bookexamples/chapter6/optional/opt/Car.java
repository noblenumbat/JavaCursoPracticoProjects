package org.jomaveger.bookexamples.chapter6.optional.opt;

import java.util.Optional;

public class Car {
	
	private Optional<Insurance> insurance;

	public Optional<Insurance> getInsurance() {
		return insurance;
	}
	
	public void setInsurance(Insurance insurance) {
		this.insurance = Optional.ofNullable(insurance);
	}
}
