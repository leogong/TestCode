package com.leo.test.classloader;

public class ConstTest {
    public static void main(String[] args) {
        System.out.println(A.a);// 0.5
    }
}

class A {
    static {
        System.out.println("A 被初始化了");
    }
    public final static Integer a = Integer.valueOf(1 / 2);
}