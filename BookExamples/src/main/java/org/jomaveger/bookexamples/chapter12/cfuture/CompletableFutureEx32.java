package org.jomaveger.bookexamples.chapter12.cfuture;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureEx32 {

	public static void main(String[] args) {
		
		runTask(2);
		runTask(0);
	}
	
	private static void runTask(int i) {
		System.out.printf("-- input: %s --%n", i);
		CompletableFuture.supplyAsync(() -> {
			return 16 / i;
		})
		.handle((input, exception) -> {
			if (exception != null) {
				System.out.println("exception occurs");
				System.err.println(exception);
				return 1;
			} else {
				System.out.println("no exception, got result: " + input);
				System.out.println("increasing input by 2: " + (input + 2));
	            return input + 2;
			}
		});
	}
}
