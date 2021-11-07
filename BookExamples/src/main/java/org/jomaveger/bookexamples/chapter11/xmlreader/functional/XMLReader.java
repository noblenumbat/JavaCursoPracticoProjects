package org.jomaveger.bookexamples.chapter11.xmlreader.functional;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.apache.log4j.Logger;
import org.jomaveger.functional.control.Result;
import org.jomaveger.functional.data.ImmutableList;
import org.jomaveger.functional.functions.Executable;
import org.jomaveger.functional.tuples.Tuple2;

public final class XMLReader {
	
	private static final Logger log = Logger.getLogger(XMLReader.class);
	
	public static Executable readXmlFile(Supplier<Result<String>> sPath,
            								Supplier<Result<String>> sRootName,
            								Tuple2<String, ImmutableList<String>> format,
            								Consumer<ImmutableList<String>> e) {

		final Result<String> path = sPath.get();
		final Result<String> rRoot = sRootName.get();
		final Result<String> rDoc = path.flatMap(XMLReader::readFile2String);
		final Result<ImmutableList<String>> result = rDoc
				.flatMap(doc -> rRoot
					.flatMap(rootElementName -> readDocument(rootElementName, doc))
						.map(list -> toStringList(list, format)));
		return () -> result.forEachOrThrow(e);
	}

	public static Result<String> readFile2String(String path) {
		try {
			return Result.success(new String(Files.readAllBytes(Paths.get(path))));			
		} catch (IOException e) {
			Result.failure(String.format("IO error while reading file %s", path), e)
					.forEachOrFail(System.out::println)
					.forEach(log::error);
			return Result.failure(String.format("IO error while reading file %s", path), e);
		} catch (Exception e) {
			Result.failure(String.format("Unexpected error while reading file %s", path), e)
					.forEachOrFail(System.out::println)
					.forEach(log::error);
			return Result.failure(String.format("Unexpected error while reading file %s", path), e);
		}
	}
	
	private static Result<ImmutableList<Element>> readDocument(String rootElementName, String stringDoc) {
		final SAXBuilder builder = new SAXBuilder();
		try {
			final Document document = builder.build(new StringReader(stringDoc));
			final Element rootElement = document.getRootElement();
			return Result.success(ImmutableList.from(rootElement.getChildren(rootElementName)));
		} catch (IOException | JDOMException io) {
			Result.failure(String.format("Invalid root element name '%s' or XML data %s", rootElementName, stringDoc), io)
					.forEachOrFail(System.out::println)
					.forEach(log::error);
			return Result.failure(
					String.format("Invalid root element name '%s' or XML data %s",
									rootElementName, stringDoc), io);
		} catch (Exception e) {
			Result.failure(String.format("Unexpected error while reading XML data %s", stringDoc), e)
				.forEachOrFail(System.out::println)
				.forEach(log::error);
			return Result.failure(String.format("Unexpected error while reading XML data %s",
									stringDoc), e);
		}
	}
	
	private static ImmutableList<String> toStringList(ImmutableList<Element> list, Tuple2<String, ImmutableList<String>> format) {
		return list.map(e -> processElement(e, format));
	}

	private static String processElement(Element element, Tuple2<String, ImmutableList<String>> format) {
		String formatString = format._1;
		ImmutableList<String> parameters = format._2.map(element::getChildText);
		return String.format(formatString, parameters.toArray(String.class));
	}
}
