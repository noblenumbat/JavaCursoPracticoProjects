package org.jomaveger.bookexamples.chapter12.cfuture;

import java.util.concurrent.CompletableFuture;

public class CompletableFuture31 {

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
		.whenComplete((input, exception) -> {
			if (exception != null) {
				System.out.println("exception occurs");
				System.err.println(exception);
			} else {
				System.out.println("no exception, got result: " + input);
			}
		});
	}
}
