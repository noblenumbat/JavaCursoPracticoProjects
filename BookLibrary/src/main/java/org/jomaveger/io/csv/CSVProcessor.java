package org.jomaveger.io.csv;

public interface CSVProcessor<T> {

	T process(T inData);
}
