package org.jomaveger.bookexamples.chapter12.cfuture;

import java.util.concurrent.CompletableFuture;

public class CompletableFuture41 {

	public static void main(String[] args) {

		runTask(2);
		runTask(0);
	}

	private static void runTask(int i) {
		System.out.printf("-- input: %s --%n", i);
		CompletableFuture.supplyAsync(() -> {
			return 16 / i;
		})
		.exceptionally(throwable -> {
            System.out.println("recovering in exceptionally: " + throwable);
            return 1;
        })
		.thenApply(input -> input * 3)
		.thenApply(input -> input - 2)
		.thenAccept(System.out::println)
		.thenRun(() -> System.out.println("Computation finished"));
	}
}
