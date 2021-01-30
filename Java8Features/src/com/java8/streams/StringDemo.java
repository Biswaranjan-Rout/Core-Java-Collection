package com.java8.streams;

public class StringDemo {
	public static void main(String[] args) {
		
		String s = "APP07118-1944";
		String s1 = "APP07118";
		
		String[] arr = s.split("-");
		System.out.println("length of Arr: "+arr.length);
		
		for(int i=0;i<arr.length;i++) {
			System.out.println(arr[i]);
		}
	}
}
