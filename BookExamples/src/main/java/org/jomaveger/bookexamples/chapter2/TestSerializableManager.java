package org.jomaveger.bookexamples.chapter2;

import java.io.*;
import org.jomaveger.lang.DeepCloneable;

public class TestSerializableManager{
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Manager empleado3 = new Manager("Thalia",450000.0, 14, 11, 2013);
		Manager empleado4 = DeepCloneable.deepCopy(empleado3);
		
		System.out.println("empleado3=" + empleado3);
		System.out.println("empleado4=" + empleado4);
		
		empleado3.setIncentivo(70000.0);
		
		System.out.println("");
		
		System.out.println("Despues de dar un incentivo a empleado3");
		
		System.out.println("");
		
		System.out.println("empleado3= " + empleado3.getSueldo());
		System.out.println("empleado4= " + empleado4.getSueldo());
		
		
	}
}