package com.coreJava.programs;

import java.util.Date;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MyImmutable mi = MyImmutable.getInstance(10, "SBI Bank", new Date());
		System.out.println("MyImmutable: " + mi);
		
		

	}

}
