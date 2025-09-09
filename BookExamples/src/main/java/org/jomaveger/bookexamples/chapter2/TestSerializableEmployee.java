package org.jomaveger.bookexamples.chapter2;

import java.io.*;
import org.jomaveger.lang.DeepCloneable;

public class TestSerializableEmployee {
	public static void main (String[] args) throws IOException, ClassNotFoundException {
		Employee empleado1 = new Employee("Jonathan",450000.0, 14, 11, 2013);
		
		// Usando deep copy por serialización
		Employee empleado2 = DeepCloneable.deepCopy(empleado1);
		
		System.out.println("Empleado1: " + empleado1);
		System.out.println("Empleado2: " + empleado2);
		
		System.out.println("");
		
		System.out.println("Despues de subir el sueldo en un 30% a Empleado 2");
		
		empleado2.subirSueldo(30.0);
		
		System.out.println("");
		
		System.out.println("Empleado 2: " + empleado2);
	}
}