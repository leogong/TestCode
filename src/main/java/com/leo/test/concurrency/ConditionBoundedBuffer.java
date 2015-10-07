package com.leo.test.concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by leo on 12/10/13.
 *
 */
public class ConditionBoundedBuffer<T> {
    protected final Lock lock = new ReentrantLock();
    //条件谓词: notFull (count < items.length)
    private final Condition notFull = lock.newCondition();
    // 条件谓词: notEmpty (count > 0)
    private final Condition notEmpty = lock.newCondition();

    private final T[] items = (T[]) new Object[10];

    private int tail, head, count;

    //阻塞并直到notFull
    public void put(T x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
                items[tail] = x;
                if (++tail == items.length) {
                    tail = 0;
                }
                ++count;
                notEmpty.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    //阻塞并直到：notEmpty
    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            T x = items[head];
            if (++head == items.length) {
                head = 0;
            }
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

}
