package com.jaca.basics;

public class DemonThreadTest {
	public static void main(String[] args) {
		String tName = Thread.currentThread().getName();
		System.out.println("Main Thread Name: " + tName);
		MyRunnable r = new MyRunnable();
		Thread t = new Thread(r);
		t.start();
		String name = t.getName();
		System.out.println("Child thread name: " + name);
		System.out.println(t.isDaemon());
	}
}

class MyRunnable implements Runnable {
	public void run() {
		for (int i = 0; i < 5; i++) {
			System.out.println("Child Thread");
		}
	}
}
