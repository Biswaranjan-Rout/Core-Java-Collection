package com.ds.recursion;

public class TreeRecursion {
	public static void m1(int n) {
		if (n > 0) {
			System.out.println("n: " + n);
			m1(n - 1);
			m1(n - 1);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TreeRecursion.m1(3);

	}
}
