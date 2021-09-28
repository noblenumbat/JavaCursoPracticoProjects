package org.jomaveger.bookexamples.chapter5;

import org.jomaveger.lang.dbc.Contract;
import org.jomaveger.structures.IStack;
import org.jomaveger.structures.LinkedStack;

public class Binary {
	
	public static long binary(int n) {
		Contract.require(n > 0);
		
		long r = 0;
		
		if (n < 2) r = n;
		else r = 10 * binary(n / 2) + (n % 2);
		
		return r;
	}
	
	public static long itBinary(int n) {
		Contract.require(n > 0);
		
		long r = 0;
		int nn = 0;
		IStack<Integer> ns = new LinkedStack<>();
		
		nn = n;
		while (nn >= 2) {
			ns.push(nn);
			nn = nn / 2;
		}
		
		r = nn;
		
		while(!ns.isEmpty()) {
			nn = ns.peek();
			r = 10 * r + nn % 2;
			ns.pop();
		}
		
		return r;
	}
}
