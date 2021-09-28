package org.jomaveger.bookexamples.chapter6.optional.ser;

import java.io.Serializable;
import java.util.Optional;

public class Person implements Serializable {

	private Car car;

	public Optional<Car> getCar() {
		return Optional.ofNullable(car);
	}
	
	public void setCar(Car car) {
		this.car = car;
	}
}
