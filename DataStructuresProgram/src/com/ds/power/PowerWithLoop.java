package com.ds.power;

public class PowerWithLoop {

	public static void power(int m, int n) {
		int res = 1;
		for (int i = 1; i <= n; i++) {
			res = res * m;
		}
		System.out.println("Res: " + res);
	}

	public static void main(String[] args) {
		power(2, 6);
	}

}
