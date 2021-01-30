package com.java8.streams;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class ParallalDEmo {

	public static int doubleIt(int n) {
		try {
			Thread.sleep(100);
			System.out.println(Thread.currentThread().getName() + " with n = " + n);

		} catch (InterruptedException ignore) {
			// TODO: handle exception
		}
		return n * 2;
	}

	public static void main(String[] args) {
		List<Integer> ints = Arrays.asList(3, 5, 8, 6, 7);
		int total = 0;

		for (Integer i : ints) {
			total += i;
		}

		total = IntStream.of(3, 1, 4, 7, 43, 7, 9, 2)
						 .sum();

		System.out.println("Total: " + total);

		Instant before = Instant.now();

		total = IntStream.of(3, 1, 4, 7, 43, 7, 9, 2)
						//.parallel()
						.map(ParallalDEmo::doubleIt)
						.sum();

		Instant after = Instant.now();

		Duration duration = Duration.between(before, after);
		System.out.println("Total of doubles: " + total);
		System.out.println("Time: " + duration.toMillis() + "ms");

	}

}
