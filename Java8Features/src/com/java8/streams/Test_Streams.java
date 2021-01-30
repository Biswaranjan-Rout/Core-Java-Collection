package com.java8.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test_Streams {
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(10);
		list.add(20);
		list.add(30);
		list.add(19);
		list.add(17);
		list.add(80);
		System.out.println("List: " + list);

		List<Integer> l1 = list.stream().filter(i -> i % 2 == 0).collect(Collectors.toList());
		System.out.println("Even Numbers: " + l1);

		List<Integer> l2 = list.stream().map(i -> i * 2).collect(Collectors.toList());
		System.out.println("Square: " + l2);

	}
}
