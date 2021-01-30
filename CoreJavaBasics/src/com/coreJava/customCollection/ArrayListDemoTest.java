package com.coreJava.customCollection;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

class ArrayListDemo<E> extends ArrayList<E> {

	public boolean isDuplicateAllowed;

	public ArrayListDemo(boolean isDuplicateAllowed) {
		this.isDuplicateAllowed = isDuplicateAllowed;
	}

	Set<Object> mySet = new LinkedHashSet<Object>();

	public boolean add(E e) {
		if (isDuplicateAllowed == false && mySet.add(e) == false) {
			throw new IllegalArgumentException("Duplicates are not allowed in this ArrayListDemo.java");
		}
		super.add(e);

		return isDuplicateAllowed;
	}
}

public class ArrayListDemoTest {
	public static void main(String[] args) {
		List<String> cList = new ArrayListDemo<String>(true);
		cList.add("Lucky");
		cList.add("Lucky");
		cList.add("Happy");
		cList.add("Bapi");

		System.out.println(cList);
		
		List<String> myCustomList = new ArrayListDemo<String>(false);
		myCustomList.add("Lucky");
		myCustomList.add("Lucky");
		myCustomList.add("Happy");
		myCustomList.add("Bapi");

		System.out.println(myCustomList);
	}
}
