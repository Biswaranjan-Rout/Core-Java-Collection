package com.jaca.basics;

public class FibonacciSeries {

	public static void printFibonacci(int n) {
		int n1 = 0, n2 = 1;

		System.out.print(n1 + " " + n2);

		int sum = 0;
		for (int i = 3; i <= n; ++i) {
			sum = n1 + n2;
			System.out.print(" " + sum);
			n1 = n2;
			n2 = sum;
		}

	}

	public static int printFibonacci2(int n) {
		System.out.println();
		int t0 = 0, t1 = 1;
		int sum = 0;

		if (n <= 1) {
			return n;
		} else {
			for (int i = 2; i <= n; i++) {
				sum = t0 + t1;
				t0 = t1;
				t1 = sum;
			}
			return sum;
		}

	}

	public static void main(String[] args) {
		FibonacciSeries.printFibonacci(10);
		int val = printFibonacci2(10);
		System.out.println(val);
	}

}
