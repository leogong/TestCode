package com.leo.test.classloader;

public class ParentTest {

	public static void main(String[] args) {
		System.out.println("child");
	}

	static {
		System.out.println(Parent.a);
	}

}

class Parent extends SuperParent {
	public static Integer a = 1;
	static {
		System.out.println("parent");
	}
}

class SuperParent {
	static {
		System.out.println("Superparent");
	}
	public static Integer a = 1;
}
