package com.leo.test.concurrency;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * User: Leo Date: 4/22/13 Time: 9:13 PM
 */
public class InterruptedTest {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new A());
        thread.start();
        thread.interrupt();
    }

}

class A implements Runnable {

    @Override
    public void run() {
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(1);
        try {
            arrayBlockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
//            Thread.currentThread().interrupt();
            System.out.println(Thread.currentThread().isInterrupted());
        }
//        while (!Thread.currentThread().isInterrupted()) {
//
//        }
//        System.out.println("dddd");
    }

    public void throwException() {
        throw new RuntimeException("ddd");
    }
}