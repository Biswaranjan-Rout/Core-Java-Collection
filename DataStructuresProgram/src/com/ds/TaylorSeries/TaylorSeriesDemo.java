package com.ds.TaylorSeries;

public class TaylorSeriesDemo {

	public static double e(int x, int n) {
		double res = 1;
		if (x > 0 && n > 0) {
			for (; n > 0; n--) {
				res = 1 + (x * res) / n;
			}
		}

		return res;
	}

	public static void main(String[] args) {
		double val = e(1, 10);
		System.out.println("Result: " + val);
	}
}

// Taylor Series
// 1 + (x/1!) + (x^2/2!) +(x^3/3!) +...... + n
//The above equation is converted to 
//1+(x/1)[1+(x/2)[1+(x/3)[1+(x/4)]]]