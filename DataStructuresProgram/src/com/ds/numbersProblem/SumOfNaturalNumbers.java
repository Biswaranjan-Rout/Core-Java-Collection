package com.ds.numbersProblem;

public class SumOfNaturalNumbers {

	// Using Recursion - Worst performance O(n) and more space O(n)
	public static int sum1(int n) {
		if (n == 0)
			return 0;

		return sum1(n - 1) + n;
	}

	// Worst performance - O(n)
	public static int sum2(int n) {
		int res = 0;
		for (int i = 1; i <= n; i++) {
			res = res + i;
		}
		return res;
	}

	// Best performance - O(1)
	public static int fastestSum(int n) {
		int res = 0;
		res = n * (n + 1) / 2;
		return res;
	}

	public static void main(String[] args) {

		System.out.println("Sum using recursion: " + sum1(101));
		System.out.println("Sum using loop: " + sum2(101));
		System.out.println("Sum using formula: " + fastestSum(101));

	}
}
