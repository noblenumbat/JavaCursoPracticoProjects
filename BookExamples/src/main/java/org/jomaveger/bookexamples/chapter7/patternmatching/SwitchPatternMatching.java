package org.jomaveger.bookexamples.chapter7.patternmatching;

public class SwitchPatternMatching {
	
	public static void main(String[] args) {
		String p = null;
		System.out.println(testStringOrNull(p));
	}

	public static String formatter(Object o) {
	    return switch (o) {
	        case Integer i -> String.format("int %d", i);
	        case Long l    -> String.format("long %d", l);
	        case Double d  -> String.format("double %f", d);
	        case String s  -> String.format("String %s", s);
	        default        -> o.toString();
	    };
	}
	
	public static String formatNonEmptyString(Object o) {
		return switch (o) {
			case String s && s.length() >= 1 -> String.format("String value is %s", s);
			case String s -> String.format("Empty string");
			default -> o.toString();
		};
	}
	
	public static String formatValidString(Object o) {
		return switch (o) {
			case String s && s.length() >= 2 && (s.contains("@") || s.contains("!")) 
					-> String.format("Valid string value is %s", s);
			default -> "Invalid value";
		};
	}
	
	public static String testFooBar(String s) {
		return switch (s) {
			case null -> "Value is null";
			case "Foo", "Bar" -> "Special value";
			default -> "Other value";
		};
	}
	
	public static String testStringOrNull(Object o) {
	    return switch (o) {
	    	case String s -> s.toString();
	        case null, default -> "The rest (including null)";
	    };
	}
	
	public static int coverage(Object o) {
	    return switch (o) {
	        case String s  -> s.length();
	        case Integer i -> i;
	        default -> 0;
	    };
	}
	
	public static String printLength(Object o) {
		return switch (o) {
			case String s -> "String with length: " + s.length();
			case CharSequence cs -> "Sequence with length: " + cs.length();
			default -> "Unknown type";
		};
	}
}
