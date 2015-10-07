package com.leo.test.thread;

/**
 * User: Leo
 * Date: 13-10-3
 * Time: 下午3:55
 */

public class NoVisible {
    private  static Boolean ready = false;
    private  static int number;

    private static class ReaderThread extends Thread {
        public ReaderThread(String name) {
            super(name);
        }
        public void run() {
            while (!ready) {
                System.out.println( this.getName() + " while " + ready + " " + number);
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread("A").start();
        new ReaderThread("B").start();
        System.out.println("started thread");
        number = 42;
        System.out.println("set number successful");
        ready = true;
        System.out.println("set ready successful");
    }
}
