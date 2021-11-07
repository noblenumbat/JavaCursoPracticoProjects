package org.jomaveger.bookexamples.chapter10.latch;

import java.util.concurrent.CountDownLatch;

public class LatchTest {
	
    public static void main(String[] args) {
    	
        // Crea un cerrojo de cuenta atras con un contador de 2  
        CountDownLatch latch = new CountDownLatch(2);

        // Crea e inicia el servicio principal  
        LatchMainService ms = new LatchMainService(latch);
        ms.start();

        // Crea e inicia dos servicios auxiliares  
        for (int i = 1; i <= 2; i++) {
            LatchHelperService lhs = new LatchHelperService(i, latch);
            lhs.start();
        }
    }
}