package org.jomaveger.bookexamples.chapter12.producer_consumer;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class ProducerConsumerSimulator {

	public static void main(String[] args) {
		
		File POISON = new File("");
		String keyword = "volatile";
		int N_PRODUCERS = 1;
        int N_CONSUMERS = Runtime.getRuntime().availableProcessors();
        int N_POISON_PILL_PER_PRODUCER = N_CONSUMERS / N_PRODUCERS;
        int N_POISON_PILL_REMAIN = N_CONSUMERS % N_PRODUCERS;
        
        System.out.println("N_PRODUCERS : " + N_PRODUCERS);
        System.out.println("N_CONSUMERS : " + N_CONSUMERS);
        System.out.println("N_POISON_PILL_PER_PRODUCER : " + N_POISON_PILL_PER_PRODUCER);
        System.out.println("N_POISON_PILL_REMAIN : " + N_POISON_PILL_REMAIN);

        BlockingQueue<File> queue = new ArrayBlockingQueue<>(25);
        
        File root = new File(System.getProperty("java.home") + "/src");
        
        System.out.println(root.getAbsolutePath());

        for (int i = 0; i < N_PRODUCERS - 1; i++) {
            new Thread(new FileExplorerProducer(queue, root,
                    POISON, N_POISON_PILL_PER_PRODUCER)).start();
        }

        for (int i = 0; i < N_CONSUMERS; i++) {
            new Thread(new FileSearcherConsumer(queue, POISON, keyword)).start();
        }
        
        new Thread(new FileExplorerProducer(queue, root, POISON,
                N_POISON_PILL_PER_PRODUCER + N_POISON_PILL_REMAIN)).start();
	}
}
