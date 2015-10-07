package com.leo.test.cpu;

/**
 * User: leo
 * Date: 8/12/13
 * Time: 9:55 AM
 */
public class LoopTest {
    public static void main(String[] args) {
        int[] arr = new int[64 * 1024 * 1024];
        long start = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] *= 3;
        }
//        for (int i = 0; i < arr.length; i += 16) {
//            arr[i] *= 3;
//        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
