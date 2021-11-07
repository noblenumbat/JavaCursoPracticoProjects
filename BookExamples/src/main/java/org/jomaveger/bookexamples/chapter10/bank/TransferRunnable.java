package org.jomaveger.bookexamples.chapter10.bank;

public class TransferRunnable implements Runnable {
	
	private Bank bank;
	private int deLaCuenta;
	private double cantidadMax;
	private int RETARDO = 10;
	
	public TransferRunnable(Bank b, int de, double max) {
		bank = b;
		deLaCuenta = de;
		cantidadMax = max;
	}
	
	@Override
	public void run() {
		try {
			
			while(true) {
				
				int paraLaCuenta = (int) (bank.size() * Math.random());
				double cantidad = cantidadMax * Math.random();
				
				bank.transferencia(deLaCuenta, paraLaCuenta, cantidad);
				
				Thread.sleep((int) (RETARDO * Math.random()));
				
			}
			
		} catch (InterruptedException e) {
			
		}
		
	}

}
