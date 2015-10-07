package com.leo.test.concurrency;

import java.util.concurrent.CountDownLatch;

/**
 * User: Leo
 * Date: 13-10-1
 * Time: 上午11:11
 */
public class TestHarness {
    public long timeTasks(int nThreads, final Runnable task)
            throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);
        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
            };
            t.start();
        }
        long start = System.currentTimeMillis();
        startGate.countDown();
        endGate.await();
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static void main(String[] args) {
        TestHarness testHarness = new TestHarness();
        try {
            System.out.println(testHarness.timeTasks(3, new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10000);
                        System.out.println(Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
