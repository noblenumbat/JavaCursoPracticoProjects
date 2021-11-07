package org.jomaveger.bookexamples.chapter10.bank;

public class SynchronizedBank4 implements Bank {
    
    private final double[] cuentas;

    public SynchronizedBank4(int n, double saldoInicial) {
        cuentas = new double[n];
	for(int i = 0; i < cuentas.length; i++)
            cuentas[i] = saldoInicial;
    }

    @Override
    public synchronized void transferencia(int de, int para, double cantidad) {
        try {
            while (cuentas[de] < cantidad) {
                wait();
            }

            System.out.print(Thread.currentThread());

            cuentas[de] = cuentas[de] - cantidad;

            System.out.printf(" %10.2f de %d para %d", cantidad, de, para);

            cuentas[para] = cuentas[para] + cantidad;

            System.out.printf(" Saldo total: %10.2f%n", getSaldoTotal());

            notifyAll();

        } catch (InterruptedException ex) {
        }
    }

    @Override
    public synchronized double getSaldoTotal() {
        double sum = 0;

        for (double a : cuentas) {
            sum += a;
        }

        return sum;
    }

    @Override
    public int size() {
        return cuentas.length;
    }
}
