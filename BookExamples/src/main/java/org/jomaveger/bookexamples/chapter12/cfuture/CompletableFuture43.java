package org.jomaveger.bookexamples.chapter12.cfuture;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompletableFuture43 {

	public static void main(String[] args) {

		CompletableFuture<String> future1  
		  = CompletableFuture.supplyAsync(() -> "Hello");
		CompletableFuture<String> future2  
		  = CompletableFuture.supplyAsync(() -> "Beautiful");
		CompletableFuture<String> future3  
		  = CompletableFuture.supplyAsync(() -> "World");
		 
		CompletableFuture<Void> combinedFuture 
		  = CompletableFuture.allOf(future1, future2, future3);
		
		String combined = Stream.of(future1, future2, future3)
				  .map(CompletableFuture::join)
				  .collect(Collectors.joining(" "));
				 
		if ("Hello Beautiful World".equals(combined))
			System.out.println(combined);
		else
			System.out.println("Something went wrong");
	}
}
