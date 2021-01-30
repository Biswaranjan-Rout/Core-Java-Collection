package com.jaca.basics;

public class PallindromCheck {

	public static void pallindromStrCheck(String str) {
		if (str == null || str.isEmpty()) {
			System.out.println("Not a valid String");
		} else {
			char[] ch = str.toCharArray();
			StringBuffer sb = new StringBuffer(ch.length);

			for (int i = str.length() - 1; i >= 0; i--) {
				sb.append(ch[i]);
			}
			if (str.equals(sb.toString())) {
				System.out.println("Pallindrom: " + str + " " + sb);
			} else
				System.out.println("Not: " + str + " " + sb);
		}
	}

	public static void pallindromNumberCheck(int num) {
		int pallindrom = num;
		int rev = 0;

		while (pallindrom != 0) {
			int remainder = pallindrom % 10;
			rev = rev * 10 + remainder;
			pallindrom = pallindrom / 10;

		}
		if (num == rev) {
			System.out.println(num + " is pallindrom");
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		pallindromStrCheck("BOB");
		pallindromNumberCheck(121);
	}

}
