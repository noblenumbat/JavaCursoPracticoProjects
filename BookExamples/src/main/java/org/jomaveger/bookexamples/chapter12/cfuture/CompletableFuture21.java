package org.jomaveger.bookexamples.chapter12.cfuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFuture21 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		String result = CompletableFuture.supplyAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return "Hello World";
		}).get();
		
		System.out.println(result);
	}
}
