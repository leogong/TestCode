package com.leo.test.classloader;

public class LoadClassTest {
	static{
		try {
			ClassLoader.getSystemClassLoader().loadClass("com.mycompany.app.ParentTest");
			Class.forName("com.mycompany.app.ParentTest");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		
	}
}
