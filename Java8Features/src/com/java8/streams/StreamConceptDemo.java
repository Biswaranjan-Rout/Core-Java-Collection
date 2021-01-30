package com.java8.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StreamConceptDemo {
	public static void main(String[] args) {
		Stream<Integer> s = Stream.of(10, 100, 1990, 324, 12442);
		s.forEach(System.out::println);

		System.out.println("---------------------------------");

		Integer[] arr = { 54, 3, 5, 7, 98, 13 };
		Stream.of(arr).forEach(System.out::println);

		System.out.println("---------------------------------");
		System.out.println("Is Parallal: "+s.isParallel());
		System.out.println("---------------------------------");
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(10); list.add(140); list.add(50); list.add(89); list.add(30);
		
		//converting stream to array
		Integer[] iArr = list.stream().toArray(Integer[] :: new);
		
		for(Integer i : iArr) {
			System.out.print(i+" ");
		}
	}
}
