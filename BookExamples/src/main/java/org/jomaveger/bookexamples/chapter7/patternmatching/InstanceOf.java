package org.jomaveger.bookexamples.chapter7.patternmatching;

public class InstanceOf {
	
	private String s = "initial value";

	public void outputValueInUppercase(Object obj) {
		if (obj instanceof String) {               
			String str = (String) obj;            
			System.out.println(str.toUpperCase());  
		}
	}
	
	public void outputValueInUppercase2(Object obj) {
		if (obj instanceof String str) {
			System.out.println(str.toUpperCase());
		}
	}
	
    public void outputValueInUppercase3(Object obj) {
        if (obj instanceof String s) {
            System.out.println(s.toUpperCase()); // refers to pattern var
        } else {
            System.out.println(s.toLowerCase()); // refers to field s
        }
    }
    
    public static String formatter(Object o) {
	    String formatted = "unknown";
	    if (o instanceof Integer i) {
	        formatted = String.format("int %d", i);
	    } else if (o instanceof Long l) {
	        formatted = String.format("long %d", l);
	    } else if (o instanceof Double d) {
	        formatted = String.format("double %f", d);
	    } else if (o instanceof String s) {
	        formatted = String.format("String %s", s);
	    }
	    return formatted;
	}
}
