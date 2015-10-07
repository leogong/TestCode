package com.leo.test.thread;

import java.io.UnsupportedEncodingException;

/**
 * User: Leo Date: 4/22/13 Time: 9:13 PM
 */
public class TestUncaughtExceptionHandler {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        System.out.println("enter unCaughtException");
                        e.printStackTrace();

                    }
                });
                throwException();
            }
        }).start();
    }

    public static void throwException() {
        throw new RuntimeException("RunTimeException");
    }


}

