package com.java8.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SteamsTest {
	public static void main(String[] args) {
		List<Integer> marks = new ArrayList<Integer>();
		marks.add(30);
		marks.add(17);
		marks.add(20);
		marks.add(50);
		marks.add(6);

		long l = marks.stream().filter(m -> m < 30).count();
		System.out.println("count: " + l);

		List<Integer> sl = marks.stream().collect(Collectors.toList());
		System.out.println("list: " + sl);

		List<Integer> sortedList = marks.stream().sorted().collect(Collectors.toList());
		System.out.println("sortedList: " + sortedList);

		// For min and max objects should be sorted
		// min() and max() function in streams works on the basis of position not on
		// value
		// min is the 0th position and max is the last position
		int minVal = marks.stream().min((i1, i2) -> i1.compareTo(i2)).get();
		int maxVal = marks.stream().max((i1, i2) -> i1.compareTo(i2)).get();

		System.out.println("min: " + minVal + ", max: " + maxVal);

		int min = marks.stream().min((i1, i2) -> -i1.compareTo(i2)).get();
		int max = marks.stream().max((i1, i2) -> -i1.compareTo(i2)).get();

		System.out.println("min: " + min + " max: " + max);

		marks.stream().forEach(System.out::println);

		Consumer<Integer> c = i -> {
			System.out.println("Square of i: " + i * i);
		};
		marks.stream().forEach(c);

		Integer[] arr = marks.stream().toArray(Integer[]::new);
		for (Integer i : arr) {
			System.out.println("From Arr: " + i);
		}

		Stream.of(arr).forEach(System.out::println);
	}

}
