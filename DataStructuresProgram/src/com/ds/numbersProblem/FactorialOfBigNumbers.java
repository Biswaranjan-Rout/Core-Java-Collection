package com.ds.numbersProblem;

import java.math.BigInteger;

public class FactorialOfBigNumbers {

	public static void factorial(int n) {

		BigInteger factorial = BigInteger.ONE;

		if (n < 0)
			System.out.println("Can't find factorial of negative numbers.");
		else if (n <= 1)
			System.out.printf("%d! = %d ", n,1);
		else {
			for (int counter = n; counter >= 2; counter--) {
				factorial = factorial.multiply(BigInteger.valueOf(counter));
			}
			System.out.printf("%d! = %d " ,n, factorial);
		}
	}

	public static void main(String[] args) {
		factorial(50);
		System.out.println();
		factorial(100);
	}
}
