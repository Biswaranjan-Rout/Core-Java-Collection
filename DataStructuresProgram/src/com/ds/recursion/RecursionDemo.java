package com.ds.recursion;

public class RecursionDemo {

	public static void m1(int n) {
		if (n > 0) {
			//System.out.println("Before fun call: n = " + n);
			m1(n - 1);
			System.out.println(" After fun call: n = " + n);
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Input: 5");
		RecursionDemo.m1(5);
	}

}
