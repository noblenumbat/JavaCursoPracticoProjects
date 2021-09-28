package org.jomaveger.bookexamples.chapter6.optional.opt;

import java.util.Optional;

public class Person {
	
	private Optional<Car> car;

	public Optional<Car> getCar() {
		return car;
	}
	
	public void setCar(Car car) {
		this.car = Optional.ofNullable(car);
	}
}
