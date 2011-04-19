/**
 * 
 */
package com.aric.sample.experience;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Dursun KOC
 * 
 */
public class AtomicNumberCounting {
	private final AtomicInteger atomicInteger = new AtomicInteger();

	public void printCurrentValue() {
		System.out.println(atomicInteger);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final AtomicNumberCounting counting = new AtomicNumberCounting();
		final int len = 100;
		Thread[] threads = new Thread[10];
		
		for (int i=0;i<threads.length;i++) {
			threads[i] = new Thread("Thread "+(i+1)){public void run() {for(int i=0;i<len;i++){counting.getAndIncrement();} System.out.println(Thread.currentThread().getName()+" completed.");counting.printCurrentValue();};}; 
		}
		for (Thread thread : threads) {
			thread.start();
		}
	}

	public final int getAndIncrement() {
		for (;;) {
			int current = atomicInteger.get();
			int next = (current + 1);
			if (atomicInteger.compareAndSet(current, next))
				return current;
		}
	}

}
