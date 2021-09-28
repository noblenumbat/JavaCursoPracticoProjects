package org.jomaveger.bookexamples.chapter6;

import java.util.function.Function;

public class Person {
	
	private final Function<Integer, Integer> calcRaiseLambda = currentYear -> calculateSalaryRaise(currentYear);
	private final Function<Integer, Integer> calcRaiseMethRef = this::calculateSalaryRaise;
	
	private static Integer population = 0; 
	
	private final String name;
	private final Integer birthYear;
	private Integer salary;
	
	public Person(String name, Integer birthYear) {
		this.name = name;
		this.birthYear = birthYear;
		Person.population++;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getAge(Integer currentYear) {
		return currentYear - this.birthYear;
	}
	
	public void setSalary(Integer salary) {
		this.salary = salary;
	}
	
	public Integer getSalary() {
		return this.salary;
	}
	
	public Integer calculateSalaryRaise(Integer currentYear) {
		return this.salary * this.getAge(currentYear) / 100;
	}
	
	public void upgradeSalaryWithLambda(Integer currentYear) {
		Integer raise = calcRaiseLambda.apply(currentYear);		
		this.salary += raise;
	}
	
	public void upgradeSalaryWithMethodReference(Integer currentYear) {
		Integer raise = calcRaiseMethRef.apply(currentYear);		
		this.salary += raise;
	}
}
