package org.jomaveger.bookexamples.chapter11.properties.functional;

import org.apache.log4j.Logger;
import org.jomaveger.bookexamples.chapter11.Person;
import org.jomaveger.functional.control.Result;

public class PropertyReaderTest {
	
	private static final Logger log = Logger.getLogger(PropertyReaderTest.class);

	public static void main(String[] args) throws Exception {
		
		PropertyReader preader = PropertyReader.filePropertyReader("config.properties");
//		PropertyReader1 preader = new PropertyReader1("config.properties");
		
//		preader.getProperty("host").forEachOrFail(System.out::println).forEach(log::error);
//		preader.getProperty("port").forEachOrFail(System.out::println).forEach(log::error);
//		preader.getProperty("name").forEachOrFail(System.out::println).forEach(log::error);
//		preader.getProperty("temp").forEachOrFail(System.out::println).forEach(log::error);
//		preader.getProperty("price").forEachOrFail(System.out::println).forEach(log::error);
//		preader.getProperty("id").forEachOrFail(System.out::println).forEach(log::error);
//		preader.getProperty("firstName").forEachOrFail(System.out::println).forEach(log::error);
//		preader.getProperty("lastName").forEachOrFail(System.out::println).forEach(log::error);
//		preader.getProperty("type").forEachOrFail(System.out::println).forEach(log::error);
//		preader.getProperty("range").forEachOrFail(System.out::println).forEach(log::error);
		
		
		Result<Person> person = preader.getProperty("id").map(Integer::parseInt)
				.flatMap(id -> preader.getProperty("firstName").flatMap(firstName -> preader
						.getProperty("lastName").map(lastName -> Person.apply(id, firstName, lastName))));
		person.forEach(log::info);
		
	}
}
