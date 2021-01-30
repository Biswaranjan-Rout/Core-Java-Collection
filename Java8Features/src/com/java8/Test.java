package com.java8;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		FI1 i = (a, b) -> System.out.println("Sum: " + (a + b));
		i.add(10, 20);
		// ===================================================
		// Multithreading code 
		// ===================================================
		//MyRunnable r = new MyRunnable();
		Runnable r = () -> {
			for (int j = 0; j < 5; j++) {
				System.out.println("Child Thread-1");
			}
		};
		
		Thread t = new Thread(r);
		t.start();
		for (int j = 0; j < 5; j++) {
			System.out.println("Parent Thread-1");
			Thread.sleep(100);
		}
	}

}
