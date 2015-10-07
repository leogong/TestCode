package com.leo.test.thread;

/**
 * User: Leo
 * Date: 13-10-1
 * Time: 上午10:24
 */
public class RecoverInterrupt {
    public static void main(String[] args) {
        try {
            Thread.sleep(110000);
        } catch (InterruptedException e) {
            System.out.println("dddd");
            //恢复被中断的状态
            Thread.currentThread().interrupt();
        }
    }
}
