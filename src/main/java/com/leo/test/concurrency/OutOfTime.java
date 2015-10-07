package com.leo.test.concurrency;

import java.util.Timer;
import java.util.TimerTask;

/**
 * User: Leo
 * Date: 13-10-4
 * Time: 下午5:09
 *
 */
public class OutOfTime {

    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer();
        timer.schedule(new ThrowTask(), 1);
        Thread.sleep(1000);
        timer.schedule(new ThrowTask(), 1);
        Thread.sleep(5 * 1000);

    }

    static class ThrowTask extends TimerTask {

        @Override
        public void run() {
            throw new RuntimeException();
        }
    }
}


