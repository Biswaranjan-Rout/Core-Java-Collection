package com.ds.sorting;

public class InsertionsortDemo {
	public static int[] insertionSort(int[] arr) {
		System.out.println("length: " + arr.length);

		for (int i = 0; i < arr.length; i++) {
			int temp = arr[i];
			int position = i;
			while (i >= 0 && arr[position - 1] > temp) {
				arr[position] = arr[position - 1];
				position = position - 1;
			}
			arr[position] = temp;
			
		}
		return arr;
	}

	public static void main(String[] args) {
		int[] arr = { 7, 2, 4, 1, 5, 3 };
		int[] result = InsertionSort.insertionSort(arr);
		for (int number : result) {
			System.out.print(number + " ");
		}
	}
}
