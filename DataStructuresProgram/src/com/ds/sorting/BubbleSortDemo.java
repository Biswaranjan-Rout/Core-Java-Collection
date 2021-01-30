package com.ds.sorting;

import java.util.Random;

public class BubbleSortDemo {

	static int SIZE = 10000;
	static Random random = new Random(System.currentTimeMillis());
	static int[] input = new int[SIZE];

	public static int[] bubbleSort(int[] arr) {
		for (int i = 0; i < SIZE; i++) {
			// boolean flag = false;
			for (int j = 0; j < SIZE -i- 1; j++) {
				if (input[j] > input[j + 1]) {
					int temp = input[j];
					input[j] = input[j + 1];
					input[j + 1] = temp;
					//flag = true;
				}
			}
			
			// This flag check is increasing the time of execution.
			// if(flag == false) { break; }
			
		}
		return arr;
	}

	public static void main(String[] args) {
		createTestData();
		long start = System.currentTimeMillis();
		bubbleSort(input);
		long end = System.currentTimeMillis();
		System.out.println("Time taken = " + (end - start));
		}

	static void createTestData() {
		for (int i = 0; i < SIZE; i++) {
			input[i] = random.nextInt();
		}
	}
}
