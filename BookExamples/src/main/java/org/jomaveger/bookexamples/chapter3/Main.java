package org.jomaveger.bookexamples.chapter3;

import java.util.ArrayList;
import java.util.List;

import org.jomaveger.bookexamples.chapter2.Employee;
import org.jomaveger.bookexamples.chapter2.Manager;

public class Main {

	public static <T> T getMiddle(final T... a) {
	    return a[a.length / 2];
	}
	
	public static void main(String[] args) {
		String middle = Main.<String>getMiddle("Hola", "soy", "Jose", "Maria");
		Integer middle2 = Main.<Integer>getMiddle(12, 33, 45);
		System.out.println(middle);
		System.out.println(middle2);
		
		Employee employee = new Employee("Juan", 27000.0, 2016, 4, 16);
		Manager manager = new Manager("Juan", 27000.0, 2016, 4, 16);
		Pair<Employee> pair = new Pair<>();
		pair.setFirst(employee);
		pair.setSecond(manager);
		System.out.println(pair);
		
		List<Pair<?>> pairs = new ArrayList<Pair<?>>();
		pairs.add(new Pair<Integer>(3, 5));
		pairs.add(new Pair<String>("Luke", "Obi-Wan"));
		System.out.println(pairs);
		
		Employee employee1 = new Employee("Juan", 27000.0, 2016, 4, 16);
		Employee employee2 = new Employee("Jaime", 25000.0, 2016, 4, 16);
		Manager manager1 = new Manager("Josema", 40000.0, 2010, 4, 16);
		Manager manager2 = new Manager("Joseluis", 43000.0, 2010, 4, 16);

		List<Employee> list1 = new ArrayList<>();
		list1.add(employee1); list1.add(employee2);
		list1.add(manager1); list1.add(manager2);

		List<Employee> list2 = new ArrayList<>();
		list2.add(employee1); list2.add(employee2);

		List<Manager> list3 = new ArrayList<>();
		list3.add(manager1); list3.add(manager2);

		System.out.println(WildCards.min(list1));
		System.out.println(WildCards.min(list2));
	}
}
