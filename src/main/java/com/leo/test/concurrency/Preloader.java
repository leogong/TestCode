package com.leo.test.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * User: Leo
 * Date: 13-10-1
 * Time: 下午2:55
 */
public class Preloader {
    private final FutureTask<String> future =
            new FutureTask<String>(new Callable<String>() {
                public String call() throws Exception {
                    System.out.println("enter call()");
                    Thread.sleep(10000);
                    return "dddd";
                }
            });
    private final Thread thread = new Thread(future);

    public void start() {
        System.out.println("run Thread");
        thread.start();
    }

    public String get()
            throws Exception {
        try {
            System.out.println("waiting for getting");
            return future.get();
        } catch (ExecutionException e) {
            System.out.println("aaaa");
            throw e;
        }
    }

    public static void main(String[] args) throws Exception {
        Preloader preloader = new Preloader();
        preloader.start();
        System.out.println("already ran Thread, then wait");
        System.out.println(preloader.get());
    }
}