package org.jomaveger.bookexamples.chapter10;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class BarrierExample extends Thread {

	private CyclicBarrier barrier;
	private int ID;
	private static Random random = new Random();
	
	public BarrierExample(int ID, CyclicBarrier barrier) {
		this.ID = ID;
		this.barrier = barrier;
	}
	
	@Override
	public void run() {
		try {
			
			int workTime = random.nextInt(30) + 1;
			
			System.out.println("Thread #" + ID + " va a trabajar durante " 
			+ workTime + " segundos.");
			
			Thread.sleep(workTime * 1000);
			
			System.out.println("Thread #" + ID + " esta esperando "
					+ "en el punto de encuentro.");
			
			this.barrier.await();
			
			System.out.println("Thread #" + ID + " ha sobrepasado "
					+ "el punto de encuentro.");
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			System.out.println("La barrera esta rota...");
		}
	}
	
	public static void main(String[] args) {
		Runnable barrierAction
			= () -> System.out.println("Estamos todos juntos, ¡Fiesta!");
		
		CyclicBarrier barrier = new CyclicBarrier(3, barrierAction);
		
		for (int i = 1; i <= 3; i++) {
			BarrierExample be = new BarrierExample(i, barrier);
			be.start();
		}
	}
}
