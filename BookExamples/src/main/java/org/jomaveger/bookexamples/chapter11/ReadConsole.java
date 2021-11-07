package org.jomaveger.bookexamples.chapter11;

import org.jomaveger.functional.control.Result;
import org.jomaveger.functional.data.ImmutableList;
import org.jomaveger.functional.io.ConsoleReader;
import org.jomaveger.functional.io.Input;
import org.jomaveger.functional.tuples.Tuple2;

public class ReadConsole {

	public static void main(String... args) {
		Input input = ConsoleReader.consoleReader();

		ImmutableList<Person> list = ImmutableList.unfold(input, ReadConsole::person);
		list.forEach(System.out::println);
	}

	public static Result<Tuple2<Person, Input>> person(Input input) {
		return input.readInt("Enter ID:")
			.flatMap(id -> id._2.readString("Enter first name:")
				.flatMap(firstName -> firstName._2.readString("Enter last name:")
					.map(lastName -> new Tuple2<>(Person.apply(id._1, firstName._1, lastName._1), 
										lastName._2))));
	}
}
