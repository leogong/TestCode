package com.leo.test.thread;

/**
 * User: Leo
 * Date: 12-10-9
 * Time: 上午12:10
 */
public class SynchronizedTest {
//    synchronized int i=0;
    synchronized void saySomething(){
        SynchronizedTest synchronizedTest = new SynchronizedTest();
        synchronized (this){}
        synchronized (synchronizedTest){}
//        synchronized (){};

    }
    public void main(String[] args){

    }
}
