package com.jaca.basics;

public class SwitchCaseDemo {

	public static void m1(int x) {

		switch (x) {
		case 10:
			System.out.println("10");
		case 11:
			System.out.println("11");
			break;
		default:
			System.out.println("default");
		}

	}

	public static void main(String[] args) {
		m1(10);
		System.out.println("===================");
		m1(20);
	}
}
