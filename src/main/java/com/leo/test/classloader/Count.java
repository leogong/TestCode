package com.leo.test.classloader;

/**
 * User: Leo
 * Date: 8/6/13
 * Time: 11:23 AM
 */
public class Count {
    public static int a;


    //java -Dxx.xx.xx=aa Count
    static {
        a = Integer.getInteger("xx.xx.xx");
        System.out.println(a);
    }

    public static void main(String[] args) {
        System.out.println(a + "main");
    }
}
