package demo.virtualthreads;

import java.util.concurrent.locks.ReentrantLock;

public class VThreadsSynchronisation {
	private static final ReentrantLock lock = new ReentrantLock();
	
	public static void main(String[] args) throws InterruptedException {
		lock.lock();
		try {
		    Thread.sleep(1000);
		} finally {
		    lock.unlock();
		}
	}

}
