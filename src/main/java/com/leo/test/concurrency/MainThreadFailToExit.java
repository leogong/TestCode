package com.leo.test.concurrency;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * User: Leo Date: 4/22/13 Time: 9:13 PM
 */
public class MainThreadFailToExit {
    private static ScheduledExecutorService cancelExec = new ScheduledThreadPoolExecutor(1);

    public static void main(String[] args) throws Throwable {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
//                    System.out.println("ddddd");
                }
            }
        };

        timedRun(r, 1, TimeUnit.SECONDS);

    }

    public static void timedRun(final Runnable r,
                                long timeout, TimeUnit unit)
            throws Throwable {
        class RethrowableTask implements Runnable {
            private volatile Throwable t;

            public void run() {
                try {
                    r.run();
                } catch (Throwable t) {
                    this.t = t;
                }
            }

            void rethrow() throws Throwable {
                if (t != null)
//                    throw launderThrowable(t);
                    throw t;
            }
        }
//        RethrowableTask task = new RethrowableTask();
        final Thread taskThread = new Thread(r);
        taskThread.start();
        cancelExec.schedule(new Runnable() {
            public void run() {
                taskThread.interrupt();
//                System.exit(0);
            }
        }, timeout, unit);
//        System.out.println(unit.toMillis(timeout));
//        taskThread.join(unit.toMillis(timeout));
//        task.rethrow();
        int i = 0;
        while (taskThread.isAlive() && i < 1) {
            System.out.println("taskThread isAlive " + taskThread.getName());
            i++;
        }
        while (Thread.currentThread().isAlive() && i < 2) {
            System.out.println("currentThread isAlive" + Thread.currentThread().getName());
            i++;
        }
    }
}