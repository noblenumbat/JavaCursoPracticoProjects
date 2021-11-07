package org.jomaveger.bookexamples.chapter12.producer_consumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class FileSearcherConsumer implements Runnable {
	
	private final BlockingQueue<File> fileQueue;
    private final File POISON;
    private final String keyword;

    public FileSearcherConsumer(BlockingQueue<File> fileQueue, 
    		File POISON, String keyword)
    {
        this.fileQueue = fileQueue;
        this.POISON = POISON;
        this.keyword = keyword;
    }
    
	@Override
	public void run() {
		try {
			boolean done = false;
			while (!done) {
				File file = fileQueue.take();
				if (file == this.POISON) {
					System.out.println(Thread.currentThread().getName() + " die");
					done = true;
				} else
					search(file, keyword);
			}
		} catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }		
	}

	private void search(File file, String keyword) {
		try (Scanner in = new Scanner(file, "UTF-8")) {
			int lineNumber = 0;
			while (in.hasNextLine()) {
				lineNumber++;
				String line = in.nextLine();
				if (line.contains(keyword))
					System.out.printf("%s:%d:%s%n", file.getPath(), lineNumber, line);	
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found " + file.getAbsolutePath());
		}		
	}
}
