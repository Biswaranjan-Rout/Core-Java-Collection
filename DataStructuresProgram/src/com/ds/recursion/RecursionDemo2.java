package com.ds.recursion;

public class RecursionDemo2 {

	public static int m1(int n) {
		if (n > 0) {
			System.out.println("n: " + n);
			return m1(n - 1) + n;
		}
		return 0;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int res = RecursionDemo2.m1(5);
		System.out.println("Res: " + res);
		System.out.println("========================");
		int res1 = RecursionDemo2.m1(5);
		System.out.println("Res1: " + res1);
	}
}
