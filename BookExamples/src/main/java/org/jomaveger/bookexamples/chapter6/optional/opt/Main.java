package org.jomaveger.bookexamples.chapter6.optional.opt;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
	
	public String getCarInsuranceName(Optional<Person> person) {
	    return person.flatMap(Person::getCar)
	                .flatMap(Car::getInsurance)
	                .map(Insurance::getName)
	                .orElse("Unknown");
	}
	
	public Set<String> getCarInsuranceNamesV1(List<Person> persons) {
		return persons.stream()
						.map(Person::getCar)
						.map(optCar -> optCar.flatMap(Car::getInsurance))
						.map(optIns -> optIns.map(Insurance::getName))
						.filter(Optional::isPresent)
						.map(Optional::get)
						.collect(Collectors.toSet());
	}
	
	public Set<String> getCarInsuranceNamesV2(List<Person> persons) {
		return persons.stream()
						.map(Person::getCar)
						.map(optCar -> optCar.flatMap(Car::getInsurance))
						.map(optIns -> optIns.map(Insurance::getName))
						.flatMap(Optional::stream)
						.collect(Collectors.toSet());
	}
}
