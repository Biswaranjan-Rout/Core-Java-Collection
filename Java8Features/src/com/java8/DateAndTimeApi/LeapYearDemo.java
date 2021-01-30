package com.java8.DateAndTimeApi;

import java.time.Year;

public class LeapYearDemo {
	public static void main(String[] args) {
		int input = 2020;
		Year yr = Year.of(input);

		if (yr.isLeap())
			System.out.println(yr + " is Leap year");
		else
			System.out.println("Not");
	}
}
