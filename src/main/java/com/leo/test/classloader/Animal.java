package com.leo.test.classloader;

public class Animal {
    public int a = 2;

    public Animal() {
        System.out.println("我是animal,我被：" + this.getClass().getClassLoader() + "   加载了！");
    }

    public static void main(String[] args) {

    }
}