package com.leo.test.thread;

/**
 * User: Leo
 * Date: 12-10-8
 * Time: 下午11:59
 */
public class PrintMyStuff {
    public static void main(String[] args){
            Thread t=new Counter1();
        t.start();
        System.out.println("Started");
//        finally{
//
//        }
    }
}

class Counter1 extends Thread implements Runnable{
    public void run(){
        try{
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        System.out.println("Running");
    }
}
