package com.jaca.basics;

import java.math.BigInteger;

public class FactorialOfNumber {
	
	public static void factorial(int n) {
		BigInteger factorial = BigInteger.ONE;
		for(int num = n; num >= 2; num-- ) {
			factorial = factorial.multiply(BigInteger.valueOf(num));
		}
		System.out.println("fac "+factorial);
	}
	
	public static void factorial2(int n) {
		
		BigInteger factorial = BigInteger.ONE;
		
		for(int num =n; num >=2;num--) {
			
		}
	}

	public static void main(String[] args) {
		
	}

}
