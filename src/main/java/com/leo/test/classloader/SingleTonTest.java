package com.leo.test.classloader;

public class SingleTonTest {
	public static void main(String[] args) {
		// SingleTon s = SingleTon.getInstance();//invokeStatic
		System.out.println(SingleTon.a);
		System.out.println(SingleTon.b);
	}
}

class SingleTon {
	private static SingleTon singleTon = null;// null
	public static int a;// 0-----1
	public static int b;// 0

	private SingleTon() {
		a++;// 1
		b++;// 1
	}

	static {
		singleTon = getInstance();
		b = 0;
	}

	public static SingleTon getInstance() {
		return singleTon;
	}
}
