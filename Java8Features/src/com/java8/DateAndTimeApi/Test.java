package com.java8.DateAndTimeApi;

import java.time.LocalDate;
import java.time.LocalTime;

public class Test {

	public static void main(String[] args) {
		LocalDate date = LocalDate.now();
		System.out.println("Date: " + date);

		LocalTime time = LocalTime.now();
		System.out.println("Time: " + time);

		int dd = date.getDayOfMonth();
		int mm = date.getMonthValue();
		int yy = date.getYear();
		System.out.println(dd + "-" + mm + "-" + yy);

	}

}
