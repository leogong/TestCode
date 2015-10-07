package com.leo.test.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/*
 * User: Leo
 * Date: 3/27/13
 * Time: 7:08 PM
 * 定时器 schedule():下一次任务的执行时间，等于上一次任务的结束时间+时间间隔
 * scheduleAtFixedRate():下一次任务的执行时间，等于上一次任务的开始时间+时间间隔 当执行任务的时间大于时间间隔时： schedule任务会延后。
 * scheduleAtFixedRate任务不会延后，仍然在时间间隔内执行，存在并发性，要考虑线程同步的问题
 */

public class TimerTest {

    private static int count = 0;

    public static void main(String[] args) {
//        task_1();

//        task_2();

//        task_3();

        task_4();

        // 主线程
        while (true) {
            System.out.println(Calendar.getInstance().get(Calendar.SECOND));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 任务1：第一次延迟3秒后启动任务，后续每隔2秒启动一次任务
    private static void task_1() {
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "定时任务已启动！");

            }
        }, 3000, 2000);
    }

    // 任务2：交互执行，方式1：第一次延迟2秒后启动任务，后续每隔在3秒和6秒之间切换启动任务
    private static void task_2() {
        class MyTimerTask extends TimerTask {

            @Override
            public void run() {
                count = (count + 1) % 2;
                System.out.println(Thread.currentThread().getName() + "定时任务已启动！");

                new Timer().schedule(new MyTimerTask(), 3000 + 3000 * count); // 循环调用
            }
        }

        new Timer().schedule(new MyTimerTask(), 2000); // 2秒后启动定时器
    }

    // 任务3：交互执行，方式2：第一次延迟2秒后启动任务，后续每隔在3秒和6秒之间切换启动任务
    public static void task_3() {
        new Timer().schedule(new MyTimerTaskA(), 300);
    }

    // 任务4：演示scheduleAtFixedRate方法，第一次延迟2秒后启动任务，后续每隔3秒后启动任务，但任务执行时间大于等于6秒
    public static void task_4() {
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                Date date = new Date();
                System.out.println("execute task:"
                        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "定时任务已启动！"
                        + (new Date().getTime() - date.getTime()));
            }
        };

        new Timer().scheduleAtFixedRate(task, 2000, 1000);
//        new Timer().schedule(task, 2000, 3000);
    }
}

// 描述2个任务切换启动
class MyTimerTaskA extends TimerTask {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "任务已启动！");
        new Timer().schedule(new MyTimerTaskB(), 2000);
    }

}

class MyTimerTaskB extends TimerTask {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "任务已启动！");
        new Timer().schedule(new MyTimerTaskA(), 4000);
    }
}
