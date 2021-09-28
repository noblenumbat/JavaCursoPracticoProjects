package org.jomaveger.functional.io;

import org.jomaveger.functional.control.Result;
import org.jomaveger.functional.tuples.Tuple2;

public interface Input {

	Result<Tuple2<String, Input>> readString();

	Result<Tuple2<Integer, Input>> readInt();

	default Result<Tuple2<String, Input>> readString(String message) {
		return readString();
	}

	default Result<Tuple2<Integer, Input>> readInt(String message) {
		return readInt();
	}
}
