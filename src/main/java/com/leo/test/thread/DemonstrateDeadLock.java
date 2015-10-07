package com.leo.test.thread;


import java.util.Random;

/**
 * User: Leo
 * Date: 13-10-15
 * Time: 下午11:14
 * Test DeadLock
 */
public class DemonstrateDeadLock {
    private static final int NUM_THREADS = 20;
    private static final int NUM_ACCOUNTS = 5;
    private static final int NUM_ITERATIONS = 1000000;

    public static void main(String[] args) {
        final Random rnd = new Random();
        final Account[] accounts = new Account[NUM_ACCOUNTS];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account();
        }

        class TransferThread extends Thread {
            public void run() {
                for (int i = 0; i < NUM_ITERATIONS; i++) {
                    Account fromAcct = accounts[rnd.nextInt(NUM_ACCOUNTS)];
                    Account toAcct = accounts[rnd.nextInt(NUM_ACCOUNTS)];
                    transferMoney(fromAcct, toAcct, rnd.nextInt(1000));
                }
            }

            private void transferMoney(Account fromAccount, Account toAccount, int amount) {
                synchronized (fromAccount) {
                    synchronized (toAccount) {
                        if (fromAccount.getBalance() < amount) {
                            throw new RuntimeException("dd");
                        } else {
                            fromAccount.debit(amount);
                            toAccount.credit(amount);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < NUM_THREADS; i++) {
            new TransferThread().start();
        }
    }
}

class Account {
    private int money = 1000000;

    public synchronized void debit(int amount) {
        money = money - amount;
    }

    public synchronized void credit(int amount) {
        money = money + amount;
    }

    public int getBalance() {
        return money;
    }
}
