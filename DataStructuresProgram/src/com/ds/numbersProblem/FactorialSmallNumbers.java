package com.ds.numbersProblem;

import java.util.Scanner;

public class FactorialSmallNumbers {

	public static long factorial(int n) {
		if (n == 0 || n == 1) {
			return 1;
		} else {
			long res = 1;
			for (int i = 1; i <= n; i++) {
				res = res * i;
			}
			return res;
		}
	}

	public static void main(String[] args) {
		int input = 0;
		System.out.print("Enter a number: ");
		Scanner sc = new Scanner(System.in);
		input = sc.nextInt();
		sc.close();
		
		long res = factorial(input);
		System.out.println("Result: " + res);
	}
}
