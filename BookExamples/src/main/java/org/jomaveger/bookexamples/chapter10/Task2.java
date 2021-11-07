package org.jomaveger.bookexamples.chapter10;

public class Task2 implements Runnable {
	
	public Task2() {
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++)
			System.out.println("Estamos en " + i * 2);
	}
	
	public static void main(String[] args) {
		new Thread(new Task2()).start();
		
		new Thread(() -> {
		    for (int i = 0; i < 10; i++)
				System.out.println("Estamos en " + i + 2);
		}).start();

	}
}
