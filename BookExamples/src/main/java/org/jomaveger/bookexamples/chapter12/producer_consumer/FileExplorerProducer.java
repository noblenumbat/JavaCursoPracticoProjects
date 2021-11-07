package org.jomaveger.bookexamples.chapter12.producer_consumer;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class FileExplorerProducer implements Runnable {

	private final BlockingQueue<File> fileQueue;
    private final File file;
    private final File POISON;
    private final int N_POISON_PILL_PER_PRODUCER;

	public FileExplorerProducer(BlockingQueue<File> fileQueue,
            File file, File POISON, int n_POISON_PILL_PER_PRODUCER) 
	{
		this.fileQueue = fileQueue;
		this.file = file;
		this.POISON = POISON;
		this.N_POISON_PILL_PER_PRODUCER = n_POISON_PILL_PER_PRODUCER;
	}

	public void run() {
		try {
			enumerate(file);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			System.out.println(Thread.currentThread().getName()
				+ " - FileExplorerProducer is done, poison all the consumers!");
			
            for (int i = 0; i < N_POISON_PILL_PER_PRODUCER; i++) {
                System.out.println(Thread.currentThread().getName() 
                	+ " - puts poison pill!");
                try {
					this.fileQueue.put(this.POISON);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
            }
		}
	}

	private void enumerate(File directory) throws InterruptedException {
		File[] files = directory.listFiles();
		for (File file: files) {
			if (file.isDirectory())
				enumerate(file);
			else
				fileQueue.put(file);
		}
	}
}
