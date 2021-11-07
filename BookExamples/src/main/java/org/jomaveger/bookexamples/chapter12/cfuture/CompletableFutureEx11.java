package org.jomaveger.bookexamples.chapter12.cfuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureEx11 {

	public static void main(String[] args) {

		CompletableFuture.runAsync(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					throw new IllegalStateException(e);
				}
				System.out.println("Message after 5 seconds.");
			}
		}).join();
	}
}
