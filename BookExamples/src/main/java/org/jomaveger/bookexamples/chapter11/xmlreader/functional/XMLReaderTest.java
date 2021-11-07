package org.jomaveger.bookexamples.chapter11.xmlreader.functional;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jomaveger.functional.control.Result;
import org.jomaveger.functional.data.ImmutableList;
import org.jomaveger.functional.functions.Executable;
import org.jomaveger.functional.tuples.Tuple2;
import org.jomaveger.util.IResourcesLoader;

public class XMLReaderTest {
	
	private static final Logger log = Logger.getLogger(XMLReaderTest.class);

	private final static Tuple2<String, ImmutableList<String>> format = new Tuple2<>(
			"First Name : %s\n" + "\tLast Name : %s\n" + "\tEmail : %s\n" + "\tSalary : %s",
			ImmutableList.of("firstname", "lastname", "email", "salary"));

	public static void main(String... args) {
		Executable program = XMLReader.readXmlFile(XMLReaderTest::getXmlFilePath, 
				XMLReaderTest::getRootElementName, format,
				XMLReaderTest::processList);
		program.exec();
	}

	private static Result<String> getXmlFilePath() {
		try {
			return Result.of(IResourcesLoader.getResourceFileAbsolutePath("file.xml"));
		} catch (IOException ex) {
			Result.failure(String.format("IO error while reading file %s", "file.xml"), ex)
			.forEachOrFail(System.out::println)
			.forEach(log::error);
			return Result.failure(String.format("IO error while reading file %s", "file.xml"), ex);
		}
	}

	private static Result<String> getRootElementName() {
		return Result.of("staff");
	}

	private static <T> void processList(ImmutableList<T> list) {
		list.forEach(log::info);
	}
}
