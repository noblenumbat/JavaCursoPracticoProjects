package org.jomaveger.bookexamples.chapter10.exchanger;

import java.util.List;
import java.util.concurrent.Exchanger;

public class ExchangerProducerConsumerTest {
	
    public static void main(String[] args) {
    
    	Exchanger<List<Integer>> exchanger = new Exchanger<>();

        // El productor generara 5 numeros enteros cada vez  
        ExchangerProducer producer = new ExchangerProducer(exchanger, 5);
        ExchangerConsumer consumer = new ExchangerConsumer(exchanger);

        producer.start();
        consumer.start();
    }
}
