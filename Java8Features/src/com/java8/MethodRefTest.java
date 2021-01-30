package com.java8;

public class MethodRefTest {

	// Method reference will check only method argument.
	// So we can change return type, modifier, method name, access modifier.
	public static void cal(int y, int k) {
		System.out.println("Sum: " + (y + k));
	}

	public void calculator(int y, int k) {
		System.out.println("Sum: " + (y + k));
	}

	public static void main(String[] args) {
		
		// I_MethodRefDemo is the functional interface we have written
		
		I_MethodRefDemo fi = (int i, int j) -> System.out.println("Sum: " + (i + j));
		fi.add(10, 20);

		I_MethodRefDemo fi2 = MethodRefTest::cal;
		fi2.add(100, 200);

		MethodRefTest mrt = new MethodRefTest();
		I_MethodRefDemo fi3 = mrt::calculator;
		fi3.add(200, 300);
	}
}
