package org.jomaveger.bookexamples.chapter12.cfuture;

import java.util.concurrent.CompletableFuture;

public class CompletableFuture42 {

	public static void main(String[] args) {

		runTask(2);
	}

	private static void runTask(int i) {
		System.out.printf("-- input: %s --%n", i);
		
		CompletableFuture<Integer> before = 
			CompletableFuture.supplyAsync(() -> {
				return i;
			});
		
		CompletableFuture<Integer> after = 
			before.thenCompose(n -> CompletableFuture.supplyAsync(() -> {
				return n * 10;
			}));
		
		after.thenAccept(System.out::println)
			.thenRun(() -> System.out.println("Computation Compose finished"));

		CompletableFuture<Integer> factor2 = 
			CompletableFuture.supplyAsync(() -> {
			return 5;
		});
		
		CompletableFuture<Integer> result =
			before.thenCombine(factor2, (n1, n2) -> n1 * n2);
		
		result.thenAccept(System.out::println)
			.thenRun(() -> System.out.println("Computation Combine finished"));
	}
}
