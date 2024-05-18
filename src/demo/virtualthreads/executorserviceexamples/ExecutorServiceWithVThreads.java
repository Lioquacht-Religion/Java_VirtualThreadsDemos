package demo.virtualthreads.executorserviceexamples;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceWithVThreads {
	
	public static void main(String[] args) {
		ExecutorService vThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();
		
		vThreadExecutor.execute(
				() -> {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Hello, World from virtual thread!");
				}
		);
		
		Future<Integer> future_value = vThreadExecutor.submit(
				() -> {
					Thread.sleep(1000);
					int t_value = add(1, 2);
					System.out.println("Hello, World from virtual thread with value " + t_value + "!");
					return t_value;
				}
		);
		
		try {
			int value = future_value.get();
			System.out.println("Hello, World from main thread with returned value " + value + "!");
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}	
		
		
		try {
			vThreadExecutor.awaitTermination(10000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
	
	static int add(int x, int y) {
		return x + y;
	}

}
