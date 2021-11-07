package org.jomaveger.bookexamples.chapter10.latch;

import java.util.concurrent.CountDownLatch;
import java.util.Random;

public class LatchHelperService extends Thread {
	
    private int ID;
    private CountDownLatch latch;
    private Random random = new Random();

    public LatchHelperService(int ID, CountDownLatch latch) {
        this.ID = ID;
        this.latch = latch;
    }

    public void run() {
        try {
            int startupTime = random.nextInt(30) + 1;

            System.out.println("Servicio #" + ID + " iniciando en "
                + startupTime + " segundos...");
            Thread.sleep(startupTime * 1000);
            System.out.println("Servicio #" + ID + " ha iniciado...");
        } 
        catch (InterruptedException e) {
            e.printStackTrace();
        } 
        finally {
            // Cuenta atras en el cerrojo para indicar
        	// que se ha iniciado el servicio auxiliar  
            this.latch.countDown();
        }
    }
}