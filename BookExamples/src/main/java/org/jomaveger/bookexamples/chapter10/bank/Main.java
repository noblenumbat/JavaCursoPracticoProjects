package org.jomaveger.bookexamples.chapter10.bank;

public class Main {
	
	public static final int NUMCUENTAS = 100;
	public static final double SALDO_INICIAL = 1000;

	public static void main(String[] args) {
//		Bank b = new NotSynchronizedBank(NUMCUENTAS, SALDO_INICIAL);
//		Bank b = new SynchronizedBank1(NUMCUENTAS, SALDO_INICIAL);
//		Bank b = new SynchronizedBank2(NUMCUENTAS, SALDO_INICIAL);
//		Bank b = new SynchronizedBank3(NUMCUENTAS, SALDO_INICIAL);
                Bank b = new SynchronizedBank4(NUMCUENTAS, SALDO_INICIAL);
		int i;
		for (i = 0; i < NUMCUENTAS; i++) {
			TransferRunnable r = new TransferRunnable(b, i, SALDO_INICIAL);
			Thread t = new Thread(r);
			t.start();
		}
	}
}
