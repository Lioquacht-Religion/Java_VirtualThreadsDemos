package demo.virtualthreads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class VThreadsSynchronisation {
	private static final ReentrantLock lock = new ReentrantLock();
	static Integer curFibonacciCount = 0;
	static Integer lastNumber = 1;
	
	public static void main(String[] args) throws InterruptedException {	
		ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor();
		for(int i = 0; i < 30; i++) {
			exec.execute(() -> {
				lock.lock();
				try {
					int temp = curFibonacciCount + lastNumber;
					System.out.println("Last Number: " + lastNumber + " + Fibonacci Number: " + curFibonacciCount +  " = " + temp);					
					lastNumber = curFibonacciCount;
					curFibonacciCount = temp;
				    Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
				    lock.unlock();
				}
			});			
		}
		Thread.sleep(2000);
		try {
			exec.awaitTermination(10000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
