package org.jomaveger.functional.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jomaveger.functional.control.Result;
import org.jomaveger.functional.tuples.Tuple2;

public class ConsoleReader extends AbstractReader {

	protected ConsoleReader(BufferedReader reader) {
		super(reader);
	}

	@Override
	public Result<Tuple2<String, Input>> readString(String message) {
		System.out.print(message + " ");
		return readString();
	}

	@Override
	public Result<Tuple2<Integer, Input>> readInt(String message) {
		System.out.print(message + " ");
		return readInt();
	}

	public static ConsoleReader consoleReader() {
		return new ConsoleReader(new BufferedReader(new InputStreamReader(System.in)));
	}
}
