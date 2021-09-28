package org.jomaveger.bookexamples.chapter6;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

public class PersonTest {

	@Test
	public void testPassingAParameterToATarget() {
		Person person = new Person("Mike", 1977);
		Function<Person, String> getNameLambda = p -> p.getName();
		Function<Person, String> getNameMethRef = Person::getName;
		assertEquals("Mike", getNameLambda.apply(person));
		assertEquals("Mike", getNameMethRef.apply(person));
	}
	
	@Test
	public void testPassingConstructorCalls() {
		BiFunction<String, Integer, Person> createPersonLambda = (name, birthYear) -> new Person(name, birthYear);
		BiFunction<String, Integer, Person> createPersonMethRef = Person::new;
		Person p1 = createPersonLambda.apply("Mike", 1977);
		Person p2 = createPersonMethRef.apply("Anna", 1980);
		assertEquals("Mike", p1.getName());
		assertEquals("Anna", p2.getName());
	}
	
	@Test
	public void testPassingMultipleArgumentsTargetAndArgument() {
		Person person = new Person("Mike", 1977);
		BiFunction<Person, Integer, Integer> getAgeLambda = (p, i) -> p.getAge(i);
		BiFunction<Person, Integer, Integer> getAgeMethRef = Person::getAge;
		assertEquals((Integer)41, getAgeLambda.apply(person, 2018));
		assertEquals((Integer)41, getAgeMethRef.apply(person, 2018));
	}
	
	@Test
	public void testPassingMultipleArguments() {
		Person p1 = new Person("Mike", 1977);
		Person p2 = new Person("Anna", 1980);
		BiFunction<Integer, Integer, Integer> sumLambda = (i, j) -> Integer.sum(i,  j);
		BiFunction<Integer, Integer, Integer> sumMethRef = Integer::sum;
		assertEquals((Integer)79, sumLambda.apply(p1.getAge(2018), p2.getAge(2018)));
		assertEquals((Integer)79, sumMethRef.apply(p1.getAge(2018), p2.getAge(2018)));
	}
	
	@Test
	public void testPassingAParameterAsAnArgumentToAnInstanceMethod() {
		Person p1 = new Person("Mike", 1977);
		Consumer<Integer> setLambda = salary -> p1.setSalary(salary);
		setLambda.accept(27000);		
		assertEquals((Integer)27000, p1.getSalary());
		Consumer<Integer> setMethRef = p1::setSalary;
		setMethRef.accept(30000);
		assertEquals((Integer)30000, p1.getSalary());
	}
	
	@Test
	public void testPassingAParameterAsAnArgumentToAMethodOnThis() {
		Person p1 = new Person("Mike", 1977);		
		p1.setSalary(27000);
		p1.upgradeSalaryWithLambda(2018);
		assertEquals((Integer)38070, p1.getSalary());
		Person p2 = new Person("Mike", 1977);
		p2.setSalary(27000);
		p2.upgradeSalaryWithMethodReference(2018);
		assertEquals((Integer)38070, p2.getSalary());
	}
	
	@Test
	public void testPassingAParameterAsAnArgumentToAStaticMethod() {
		Person p1 = new Person("Mike", 1977);
		Person p2 = new Person("Anna", 1980);
		Function<Integer, Integer> valueOfLambda = i -> Integer.valueOf(i);
		Function<Integer, Integer> valueOfMethRef = Integer::valueOf;
		assertEquals((Integer)3, valueOfLambda.apply(p1.getAge(2018) - p2.getAge(2018)));
		assertEquals((Integer)3, valueOfMethRef.apply(p1.getAge(2018) - p2.getAge(2018)));
	}
}
