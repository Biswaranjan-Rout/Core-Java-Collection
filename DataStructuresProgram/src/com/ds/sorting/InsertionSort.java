package com.ds.sorting;

public class InsertionSort {
	public static int[] insertionSort(int[] arr) {
		System.out.println("length: " + arr.length);

		for (int i = 1; i < arr.length; i++) {
			int temp = arr[i];
			
			//index = location from where we pick the temp value.
			int index = i; 

			while (index > 0 && arr[index - 1] > temp) {
				arr[index] = arr[index - 1];
				index--;
			}
			arr[index] = temp;
		}
		return arr;
	}

	public static void main(String[] args) {
		int[] arr = { 7, 2, 4, 1, 5, 3 };
		int[] result = InsertionSort.insertionSort(arr);
		for (int number : result) {
			System.out.print(number + ", ");
		}
	}
}
