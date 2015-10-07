package com.leo.test.thread;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * User: Leo
 * Date: 13-10-4
 * Time: 下午5:46
 */
class BrokenPrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;
    private volatile boolean cancelled = false;

    BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!cancelled) {
                queue.put(p = p.nextProbablePrime());
                System.out.println(p);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
    }

    public void cancel() {
        cancelled = true;
    }
}

class Test implements Runnable {
    private volatile boolean needMorePrimes = true;

    void setNeedMorePrimes(boolean needMorePrimes) {
        this.needMorePrimes = needMorePrimes;
    }

    public void run() {
        BlockingQueue<BigInteger> primes = new ArrayBlockingQueue<>(1);
        BrokenPrimeProducer producer = new BrokenPrimeProducer(primes);
        producer.start();
        try {
            while (needMorePrimes)
                try {
                    System.out.println(primes.take());
                } catch (InterruptedException e) {
                    System.out.println("Interrupted");
                }
        } finally {
            producer.cancel();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Test test = new Test();
        new Thread(test).start();
        test.setNeedMorePrimes(false);
    }
}
