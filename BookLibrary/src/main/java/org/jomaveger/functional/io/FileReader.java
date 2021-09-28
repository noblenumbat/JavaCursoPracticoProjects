package org.jomaveger.functional.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.jomaveger.functional.control.Result;

public class FileReader extends AbstractReader {

	private FileReader(BufferedReader reader) {
		super(reader);
	}

	public static Result<Input> fileReader(String path) {
		try {
			return Result.success(
					new FileReader(
						new BufferedReader(
							new InputStreamReader(
									new FileInputStream(new File(path))))));
		} catch (Exception e) {
			return Result.failure(e);
		}
	}
}