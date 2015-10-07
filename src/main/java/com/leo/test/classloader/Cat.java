package com.leo.test.classloader;

public class Cat {

    Dog dog = null;

    public Cat() {
        System.out.println("我是cat，被：" + this.getClass().getClassLoader() + "   加载了");
        dog = new Dog();
    }

    public Dog getDog() {
        return dog;
    }

}
