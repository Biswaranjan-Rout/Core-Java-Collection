package com.array.level1;

public class TwoDArrayDemo {

	public static void main(String[] args) {
		int[][] arr = { { 2, 7, 9 }, { 3, 6, 1 }, { 7, 4, 2 } };

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}

		System.out.println();

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j <= i; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println();
		
		for (int i = arr.length-1; i >=0 ; i--) {
			for (int j = 0; j <= i; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println();
		
		for (int i = arr.length-1; i >=0 ; i--) {
			for (int j = arr.length-1; j >=0; j--) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		
		for(int i=0;i< arr.length;i++) {
			for(int j=0;j<arr.length;j++) {
				
			}
			System.out.println();
		}
	}

}
