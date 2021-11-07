package org.jomaveger.bookexamples.chapter10.bank;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SynchronizedBank3 implements Bank {
	
	private final double[] cuentas;
	private ReadWriteLock cerrojo;
	private Lock cerrojoLectura;
	private Lock cerrojoEscritura;
	private Condition fondosSuficientes;
	
	public SynchronizedBank3(int n, double saldoInicial) {
		cerrojo = new ReentrantReadWriteLock();
		cerrojoLectura = cerrojo.readLock();
		cerrojoEscritura = cerrojo.writeLock();
		fondosSuficientes = cerrojoEscritura.newCondition();
		cuentas = new double[n];
		for(int i = 0; i < cuentas.length; i++)
			cuentas[i] = saldoInicial;
	}
	
	@Override
	public void transferencia(int de, int para, double cantidad) {
		cerrojoEscritura.lock();
		
		try {
			while (cuentas[de] < cantidad) fondosSuficientes.await();
			
			System.out.print(Thread.currentThread());
			
			cuentas[de] = cuentas[de] - cantidad;
			
			System.out.printf(" %10.2f de %d para %d", cantidad, de, para);
			
			cuentas[para] = cuentas[para] + cantidad;
			
			System.out.printf(" Saldo total: %10.2f%n", getSaldoTotal());
			
			fondosSuficientes.signalAll();
			
		} catch (InterruptedException ex) {
			
		} finally {
			cerrojoEscritura.unlock();
		}
	}

	@Override
	public double getSaldoTotal() {
		cerrojoLectura.lock();
		try {
			double sum = 0;
			
			for (double a: cuentas)
				sum += a;
			
			return sum;
		} finally {
			cerrojoLectura.unlock();
		}
	}

	@Override
	public int size() {
		return cuentas.length;
	}
}
