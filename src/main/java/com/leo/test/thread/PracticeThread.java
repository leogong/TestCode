package com.leo.test.thread;

public class PracticeThread extends Thread {
    
    @Override
    public void run() {
        PurchasingAgent agent = new PurchasingAgent();
        try {
            agent.purchase();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    
}