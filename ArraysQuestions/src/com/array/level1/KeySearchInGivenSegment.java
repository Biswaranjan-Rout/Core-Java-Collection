package com.array.level1;

public class KeySearchInGivenSegment {

	public static boolean search(int[] arr, int num, int s_size) {
		int end_marker = s_size - 1;
		boolean flag = false;
		int count =0;
		for (int i = 0; i < arr.length; i = i + s_size) {
			System.out.println("i: "+i);
			for (int j = i; j <= end_marker; j++) {
				if (arr[j] == num) {
					flag = true;
					end_marker = end_marker + 3;
					System.out.println("if block --> flag:" + flag + ", arr[" + j + "]=" + arr[j]+ ", end_marker: " + end_marker);
					count =0;
					break;
				}
				flag =false;
				count ++;
				System.out.println("flag: "+flag);
			}
			if(count == s_size) {
				break;
			}
		}
		return flag;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr1 = { 3, 5, 2, 4, 9, 3, 1, 7, 3, 11, 12, 3, 11, 0, 3 };
		int segement_size = 3;
		int num = 3;
		//int num = 11;
		boolean isPresent = search(arr1, num, segement_size);
		System.out.println("Is present: " + isPresent);
	}

}
