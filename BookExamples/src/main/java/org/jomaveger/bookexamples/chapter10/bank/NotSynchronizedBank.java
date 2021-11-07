package org.jomaveger.bookexamples.chapter10.bank;

public class NotSynchronizedBank implements Bank {
	
	private final double[] cuentas;

	public NotSynchronizedBank(int n, double saldoInicial) {
		cuentas = new double[n];
		for(int i = 0; i < cuentas.length; i++)
			cuentas[i] = saldoInicial;
	}
	
	public void transferencia(int de, int para, double cantidad) {
		if (cuentas[de] < cantidad) return;
		
		System.out.print(Thread.currentThread());
		
		cuentas[de] = cuentas[de] - cantidad;
		
		System.out.printf(" %10.2f de %d para %d", cantidad, de, para);
		
		cuentas[para] = cuentas[para] + cantidad;
		
		System.out.printf(" Saldo total: %10.2f%n", getSaldoTotal());
	}
	
	public double getSaldoTotal() {
		double sum = 0;
		
		for (double a: cuentas)
			sum += a;
		
		return sum;
	}
	
	public int size() {
		return cuentas.length;
	}
}
