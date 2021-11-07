package org.jomaveger.bookexamples.chapter12.executors;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class FutureDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int number = 20;
        
        ExecutorService threadpool = Executors.newCachedThreadPool();
        Future<Long> futureTask = threadpool.submit(() -> factorial(number));

        while (!futureTask.isDone()) {
            System.out.println("Future is not finished yet...");
        }
        long result = futureTask.get();
        System.out.println("Factorial of " + number + " is: " + result);

        awaitTerminationAfterShutdown(threadpool);
    }
    
    public static void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
    
    public static long factorial(int number) {
        long result = 1; 
        for(int i = number;i > 0;i--) {
            result *= i; 
        } 
        return result; 
    }
}
