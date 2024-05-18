package demo.virtualthreads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.random.RandomGenerator;

public class VThreadsLimitingConcurrency {
	
	static final Semaphore sem = new Semaphore(10);
	
	public static void main(String[] args) {
		try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
			for (int i = 0; i < 100; i++) {
				final int thread_num = i;
				executor.execute(() -> {
					int rnd_num = getRandomNumber();
					System.out.println("Virtual Thread " + thread_num + " recieved random number: " + rnd_num);
				});
			}
		}
	}
	
	static int getRandomNumber() {
	    try {
			sem.acquire();
			Thread.sleep(1000); //simulate blocking of Network I/O
		    try {
		        return callRandomNumberService();
		    } finally {
		        sem.release();
		    }
		} catch (InterruptedException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	static int callRandomNumberService() {
		return RandomGenerator.getDefault().nextInt();
	}
	

}
