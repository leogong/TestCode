package com.leo.test.thread;


public class PurchasingAgent {

    public PurchasingAgent() {
        System.out.println("Creating a purchasing agent");
    }

    public void purchase() throws InterruptedException {
        Thread t = Thread.currentThread();
        System.out.println("Thread:" + t.getName() + "," + t.getId());
        Store store = Store.getInstance();
        synchronized (store) {
            if (store.getShirtCount() > 0 && store.authorizeCreditCard("1234", 15.00)) {
                Shirt shirt = store.takeShirt();
                System.out.println("The shirt is ours!");
                System.out.println(shirt);
                Thread.sleep(1*1000*10);
            } else {
                System.out.println(t.getName() +" No shirt for you");
            }
        }
    }
}