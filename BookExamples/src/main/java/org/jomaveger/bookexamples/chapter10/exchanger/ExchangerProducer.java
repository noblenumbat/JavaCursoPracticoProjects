package org.jomaveger.bookexamples.chapter10.exchanger;

import java.util.concurrent.Exchanger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExchangerProducer extends Thread {
	
    private Exchanger<List<Integer>> exchanger;
    private List<Integer> buffer = new ArrayList<Integer>();
    private int bufferLimit;
    private Random random = new Random();
    private int currentValue = 0; // para producir valores		    

    public ExchangerProducer(Exchanger<List<Integer>> exchanger,
        int bufferLimit) {
        this.exchanger = exchanger;
        this.bufferLimit = bufferLimit;
    }

    public void run() {  
        while (true) {
            try {
                System.out.println("Productor esta llenando de datos el buffer...");

                int sleepTime = random.nextInt(20) + 1;
                Thread.sleep(sleepTime * 1000);

                // Llena el buffer  
                this.fillBuffer();
                System.out.println("Productor ha llenado:" + buffer);

                // Esperamos al consumidor para intercambiar los datos  
                System.out.println("Productor espera para intercambiar datos...");
                buffer = exchanger.exchange(buffer);
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void fillBuffer() {
        for (int i = 1; i <= bufferLimit; i++) {
            buffer.add(++currentValue);
        }
    }
}
