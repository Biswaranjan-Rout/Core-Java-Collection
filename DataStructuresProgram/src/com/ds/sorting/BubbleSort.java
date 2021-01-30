package com.ds.sorting;

public class BubbleSort {

	public static int[] bubbleSort(int[] inputArr) {
		int length = inputArr.length;

		for (int i = 0; i < length - 1; i++) {
			boolean flag = false;
			for (int j = 0; j < length - 1 - i; j++) {
				if (inputArr[j] > inputArr[j + 1]) {
					
					int temp = inputArr[j];
					inputArr[j] = inputArr[j + 1];
					inputArr[j + 1] = temp;

					flag = true;
				}
			}
			if (flag == false)
				break;
		}
		return inputArr;
	}

	public static void main(String[] args) {
		int[] arr = { 16, 4, 5, 8, 15, 3 };

		int[] result = BubbleSort.bubbleSort(arr);
		for (int number : result) {
			System.out.print(number + ", ");
		}
	}

}
