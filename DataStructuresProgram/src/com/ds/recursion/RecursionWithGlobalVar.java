package com.ds.recursion;

public class RecursionWithGlobalVar {

	// int x = 0;
	static int x = 0;

	public static int m1(int n) {
		if (n > 0) {
			x++;
			System.out.println("x: " + x);
			return m1(n - 1) + x;

		}
		return 0;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int res = RecursionWithGlobalVar.m1(5);
		System.out.println("Res: " + res);

		System.out.println("========================");
		int res1 = RecursionWithGlobalVar.m1(5);
		System.out.println("Res1: " + res1);
	}

}
