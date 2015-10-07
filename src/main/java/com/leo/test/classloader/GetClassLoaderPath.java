package com.leo.test.classloader;

public class GetClassLoaderPath {

    static {
        System.out.println(System.getProperty("sun.boot.class.path"));
        System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println(System.getProperty("java.class.path"));
    }

    public static void main(String[] args) {
    }
}
