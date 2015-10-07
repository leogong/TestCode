package com.leo.test.classloader;

public class Dog {
    public Dog(){
        System.out.println("我是dog,我被："+this.getClass().getClassLoader()+"   加载了");
    }
}



