package org.jomaveger.bookexamples.chapter10.exchanger;

import java.util.concurrent.Exchanger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExchangerConsumer extends Thread {
	
    private Exchanger<List<Integer>> exchanger;
    private List<Integer> buffer = new ArrayList<Integer>();
    private Random random = new Random();

    public ExchangerConsumer(Exchanger<List<Integer>> exchanger) {
        this.exchanger = exchanger;
    }

    public void run() {  
        while (true) {
            try {
                // Esperamos al productor para intercambiar los datos 
                System.out.println("Consumidor espera para intercambiar datos...");

                buffer = exchanger.exchange(buffer);
                System.out.println("Consumidor ha recibido:" + buffer);
                System.out.println("Consumidor vacia el buffer...");

                int sleepTime = random.nextInt(20) + 1;
  
                Thread.sleep(sleepTime * 1000);
  
                this.emptyBuffer();
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void emptyBuffer() {
        buffer.clear();
    }
}