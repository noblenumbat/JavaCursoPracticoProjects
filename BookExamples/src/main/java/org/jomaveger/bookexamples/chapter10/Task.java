package org.jomaveger.bookexamples.chapter10;

public class Task extends Thread {
	
	public Task() {
		super();
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++)
			System.out.println("Estamos en " + i);
	}
	
	public static void foo() {
		System.out.println("Hello World!");
	}
	
	public static void main(String[] args) {
		new Task().start();
		Task.foo();
	}
}
