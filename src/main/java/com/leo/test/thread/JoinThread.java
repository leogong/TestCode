package com.leo.test.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: Leo
 * Date: 13-10-31
 * Time: 下午2:25
 */

public class JoinThread extends Thread {
    //    public static volatile int n = 0;
    public static CheesyCounter n = new CheesyCounter();

//    public static AtomicInteger n = new AtomicInteger(0);
//    public static synchronized void inc() {
//        n++;
//    }

    public void run() {
        for (int i = 0; i < 10; i++)
            try {
                n.increment();
//                n.incrementAndGet();
//                inc();
                sleep(3);  //  为了使运行结果更随机，延迟3毫秒


            } catch (Exception e) {
            }
//        System.out.println(n);
    }

    public static void main(String[] args) throws Exception {

        Thread threads[] = new Thread[100];
        for (int i = 0; i < threads.length; i++)
            //  建立100个线程
            threads[i] = new JoinThread();
        for (int i = 0; i < threads.length; i++)
            //  运行刚才建立的100个线程
            threads[i].start();
        for (int i = 0; i < threads.length; i++)
            //  100个线程都执行完后继续
            threads[i].join();
        System.out.println(" n= " + JoinThread.n);
    }
}

class CheesyCounter {
    // Employs the cheap read-write lock trick
    // All mutative operations MUST be done with the 'this' lock held
    private volatile int value;

    public int getValue() {
        return value;
    }

    public synchronized int increment() {
        return value++;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
