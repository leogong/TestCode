package com.leo.test.thread;

/**
 * @说明 变量安全测试
 * @author Leo
 */
public class ThreadLocalTest {
	public static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

	public static void main(String[] args) {
		Runnable accumelatora = new Accumulatort();
		Thread threada = new Thread(accumelatora, "ThreadA");
		Thread threadb = new Thread(accumelatora, "ThreadB");
		Thread threadc = new Thread(accumelatora, "ThreadB");
		threada.start();
		threadb.start();
		threadc.start();
	}
}

class Accumulatort implements Runnable {
	/*
	 * 静态变量
	 */
	private static int local = 0;

	//
	public void run() {
		/*
		 * 静态变量
		 */
		for (int i = 0; i <= 10; i++) {
			local += 1;
			try {
				Thread.sleep(500);
			} catch (Exception e) {
			}
			//
			System.out.println(Thread.currentThread().getName() + "-->" + local);
		}
	}
}

class Accumulatort1 implements Runnable {
	/*
	 * 实例变量
	 */
	int locals = 0;

	//
	public void run() {
		for (int i = 0; i <= 10; i++) {
			locals += 1;
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}
			System.out.println(Thread.currentThread().getName() + "-->" + locals);
		}
	}
}

class Accumulatort2 implements Runnable {
	//
	public void run() {
		/*
		 * 局部变量
		 */
		int locals = 0;
		for (int i = 0; i <= 5; i++) {
			locals += 1;
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}
			System.out.println(Thread.currentThread().getName() + "-->" + locals);
		}
	}
}

class Accumulatort3 implements Runnable {

	public void run() {
		/*
		 * 测试线程安全
		 */
		ThreadLocal<Integer> threadLocal = ThreadLocalTest.threadLocal;
		for (int i = 1; i <= 5; i++) {
			if (threadLocal.get() == null)
				threadLocal.set(new Integer(0));
			int x = threadLocal.get().intValue();
			x += 1;
			threadLocal.set(new Integer(x));
			try {
				Thread.sleep(1000);

			} catch (InterruptedException e) {
			}
			System.out.println(Thread.currentThread().getName() + "-->" + threadLocal.get().intValue());
		}
	}
}