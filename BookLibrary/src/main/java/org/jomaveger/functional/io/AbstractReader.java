package org.jomaveger.functional.io;

import java.io.BufferedReader;

import org.jomaveger.functional.control.Result;
import org.jomaveger.functional.tuples.Tuple2;

public class AbstractReader implements Input {

	protected final BufferedReader reader;

	protected AbstractReader(BufferedReader reader) {
		this.reader = reader;
	}

	@Override
	public Result<Tuple2<String, Input>> readString() {
		try {
			String s = reader.readLine();
			return s.length() == 0 
					? Result.empty() 
					: Result.success(new Tuple2<>(s, this));
		} catch (Exception e) {
			return Result.failure(e);
		}
	}

	@Override
	public Result<Tuple2<Integer, Input>> readInt() {
		try {
			String s = reader.readLine();
			return s.length() == 0 
					? Result.empty() 
					: Result.success(new Tuple2<>(Integer.parseInt(s), this));
		} catch (Exception e) {
			return Result.failure(e);
		}
	}
}
