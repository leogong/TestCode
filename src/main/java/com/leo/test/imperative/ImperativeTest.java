package com.leo.test.imperative;

/**
 * Created by leo on 15/1/22.
 */
public class ImperativeTest {

    public static void main(String[] args) {
        int i = 0;
//        i = i++;
        // i = ++i;
        // i = i++ + i++ + ++i;
        i = i++ + i++ + ++i + ++i;
        System.out.println(i);
    }
}
