package com.leo.test.concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {

    public static void main(String[] args) {
        final CyclicBarrier barrier = new CyclicBarrier(2);  //Two threads can run complete

        new Thread() {
            public void run() {
                try {
                    System.out.println("before await - thread 1");
                    barrier.await();
                    System.out.println("after await - thread 1");
                } catch (BrokenBarrierException|InterruptedException ex) {

                }
            }
        }.start();
    }
}