package org.jomaveger.lang.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class ExceptionUtils {

	private ExceptionUtils() {
	}
	
	public static String getStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		return sw.toString();
	}
	
	public static String getExpandedMessage(Throwable throwable) {
		StringBuilder string = new StringBuilder();
		string.append(throwable.getClass().getName() + "[");
		string.append(throwable.getMessage());
		string.append("]");
		string.append("\n");
		string.append(ExceptionUtils.getStackTrace(throwable));
		return string.toString();
	}
	
	public static RuntimeException softenException(Exception e) {
	    return checkednessRemover(e);
	}
	
	private static <T extends Exception> T checkednessRemover(Exception e) throws T {
	    throw (T) e;
	}
}
