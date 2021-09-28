package org.jomaveger.concurrent.exceptions;

public class DeadlockDetectedException extends RuntimeException {

	public DeadlockDetectedException(String s) {
		super(s);
	}
}
