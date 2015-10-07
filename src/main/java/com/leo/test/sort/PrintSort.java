package com.leo.test.sort;

/**
 * User: Leo
 * Date: 12-10-13
 * Time: 下午5:24
 */
public class PrintSort {
    public static <AnyType extends Comparable<? super AnyType>> void print(AnyType[] sort){
        for(Object a:sort){
            System.out.print(a);
            System.out.print(" ");
        }
    }
}
