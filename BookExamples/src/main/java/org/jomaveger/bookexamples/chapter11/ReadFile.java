package org.jomaveger.bookexamples.chapter11;

import java.io.IOException;

import org.jomaveger.functional.control.Result;
import org.jomaveger.functional.data.ImmutableList;
import org.jomaveger.functional.io.FileReader;
import org.jomaveger.functional.io.Input;
import org.jomaveger.functional.tuples.Tuple2;
import org.jomaveger.util.IResourcesLoader;

public class ReadFile {

	public static void main(String... args) throws IOException {
		Result<Input> rInput = FileReader.fileReader(IResourcesLoader.getResourceFileAbsolutePath("data.txt"));

		Result<ImmutableList<Person>> rList = rInput.map(input -> ImmutableList.unfold(input, ReadFile::person));
		rList.forEachOrFail(list -> list.forEach(System.out::println));
	}

	public static Result<Tuple2<Person, Input>> person(Input input) {
		return input.readInt("Enter ID:")
			.flatMap(id -> id._2.readString("Enter first name:")
				.flatMap(firstName -> firstName._2.readString("Enter last name:")
					.map(lastName -> new Tuple2<>(Person.apply(id._1, firstName._1, lastName._1), 
										lastName._2))));
	}

}
