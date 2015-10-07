package com.leo.test.classloader;

public class Test {
	public static void main(String[] args) {
//		URL[] urls = Launcher.getBootstrapClassPath().getURLs();
//		for (URL url : urls) {
//			System.out.println(url);
//		}
//		System.out.println("----------------");
//		System.out.println(System.getProperty("sun.boot.class.path"));
		ClassLoader parent= Test.class.getClassLoader();
		while (parent != null) {
			System.out.println(parent);
			parent = parent.getParent();
		}
	}
}