package com.java8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyComparator {
	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(10);
		list.add(20);
		list.add(7);
		list.add(2);

		Comparator<Integer> c = (i1, i2) -> (i1 < i2) ? -1 : (i1 > i2) ? 1 : 0;
		Collections.sort(list, c);
		System.out.println(list);
		list.stream().forEach(System.out::println);
	}
}
