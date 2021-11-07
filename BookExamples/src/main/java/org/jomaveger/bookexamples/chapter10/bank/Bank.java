package org.jomaveger.bookexamples.chapter10.bank;

public interface Bank {

	void transferencia(int de, int para, double cantidad);
	
	double getSaldoTotal();
	
	int size();
}
