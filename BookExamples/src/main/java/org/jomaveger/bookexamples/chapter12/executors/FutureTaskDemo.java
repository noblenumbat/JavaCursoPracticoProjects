package org.jomaveger.bookexamples.chapter12.executors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureTaskDemo {
     
    public static final long MAX_NUMBER = 3000000000l;
     
    private static final long DIVISOR = 3;
 
    public static void main(String[] args) {
         
        // Ejecucion secuencial
        System.out.println("Starting sequential execution ....");
        long timeStart = System.currentTimeMillis();
        long result = Calculator.calculateNumberOfDivisible(0, MAX_NUMBER, DIVISOR);
        long timeEnd = System.currentTimeMillis();
        long timeNeeded = timeEnd - timeStart;
        System.out.println("Result         : " + result + " calculated in " + timeNeeded + " ms");
         
         
        // Ejecucion concurrente
        System.out.println("Starting parallel execution ....");
        long timeStartFuture = System.currentTimeMillis();
         
        long resultFuture = 0;
         
        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<FutureTask> taskList = new ArrayList<>();
  
        FutureTask<Long> futureTask_1 = new FutureTask(new CallableCalculator(0, MAX_NUMBER / 2, DIVISOR));
        taskList.add(futureTask_1);
        executor.execute(futureTask_1);
  
        FutureTask<Long> futureTask_2 = new FutureTask(new CallableCalculator(MAX_NUMBER / 2 + 1, MAX_NUMBER, DIVISOR));
        taskList.add(futureTask_2);
        executor.execute(futureTask_2);
  
        for (FutureTask<Long> futureTask : taskList) {
            try {
                resultFuture += futureTask.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
 
        awaitTerminationAfterShutdown(executor);
         
        long timeEndFuture = System.currentTimeMillis();
        long timeNeededFuture = timeEndFuture - timeStartFuture;
        System.out.println("Result (Future): " + resultFuture + " calculated in " + timeNeededFuture + " ms");
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
}

class Calculator {

    public static long calculateNumberOfDivisible(long first, long last, 
        long divisor) 
    {
        long amount = 0;

        for (long i = first; i <= last; i++) {
            if (i % divisor == 0) {
                amount++;
            }
        }
        return amount;
    }
}

class CallableCalculator implements Callable<Long> {

    private final long first;
    private final long last;
    private final long divisor;

    public CallableCalculator(long first, long last, long divisor) {
        this.first = first;
        this.last = last;
        this.divisor = divisor;
    }

    @Override
    public Long call() throws Exception {

        return Calculator.calculateNumberOfDivisible(first, last, divisor);
    }
}