package com.ds.numbersProblem;

public class FactorialOfLargeNumberDemo {

	public static void factorial(int num) {
		int res[] = new int[200];
		res[0] = 1;
		int res_size = 1;

	}

	public static int multiply(int num, int res[], int res_size) {
		int mulRes = 1;
		int carry = 0;
		
		for(int i=0;i<res_size;i++) {
			mulRes = num * res[i] + carry; //Ex: 4 * 6 + 0 = 24
			res[i] = mulRes % 10; //
			carry = mulRes /10;
		}
		
		while(carry != 0) {
			 res[res_size] = carry % 10; 
	         carry = carry / 10; 
	         res_size++; 
		}
		
		return res_size;
	}

	public static void main(String[] args) {

	}

}
