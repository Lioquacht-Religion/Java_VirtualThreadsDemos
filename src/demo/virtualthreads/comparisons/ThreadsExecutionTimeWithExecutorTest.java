package demo.virtualthreads.comparisons;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadsExecutionTimeWithExecutorTest {
	static final int MAX_THREAD_NUM = 20_000;
	
	public static void main(String[] args) {
		
		long warmup_vthreads = vthreadsExecTime(), warmup_pthreads = pthreadsExecTime();
		long run1_vthreads = vthreadsExecTime(), run1_pthreads = pthreadsExecTime();
		long run2_vthreads = vthreadsExecTime(), run2_pthreads = pthreadsExecTime();
		long run3_vthreads = vthreadsExecTime(), run3_pthreads = pthreadsExecTime();
		
		System.out.println("warmup run // vthreads time : " + warmup_vthreads
		+ "ms; pthreads time: " + warmup_pthreads + " ms;");
		System.out.println("1. run // vthreads time : " + run1_vthreads
		+ "ms; pthreads time: " + run1_pthreads + " ms;");
		System.out.println("2. run // vthreads time : " + run2_vthreads
		+ "ms; pthreads time: " + run2_pthreads + " ms;");
		System.out.println("3. run // vthreads time : " + run3_vthreads
		+ "ms; pthreads time: " + run3_pthreads + " ms;");
		
	}
	
	static long vthreadsExecTime() {
		long start_time = System.currentTimeMillis();

		try (ExecutorService vThreadsExecutor = Executors.newVirtualThreadPerTaskExecutor()) {

			for (int i = 0; i < MAX_THREAD_NUM; i++) {
				String task_text = "vthread task: " + i;
				Runnable task = () -> {
					System.out.println(task_text + " started");
					try {
						Thread.sleep(1_000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					System.out.println(task_text + " ended");
				};
				vThreadsExecutor.execute(task);

			}
			
			vThreadsExecutor.shutdown();
			vThreadsExecutor.awaitTermination(10000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 

		return System.currentTimeMillis() - start_time;
	}
	
	static long pthreadsExecTime() {
		long start_time = System.currentTimeMillis();
		
		try (ExecutorService pThreadsExecutor = Executors.newCachedThreadPool()) {

			for (int i = 0; i < MAX_THREAD_NUM; i++) {
				String task_text = "pthread task: " + i;
				Runnable task = () -> {
					System.out.println(task_text + " started");
					try {
						Thread.sleep(1_000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(task_text + " ended");
				};
				pThreadsExecutor.execute(task);
			}
			pThreadsExecutor.shutdown();
			pThreadsExecutor.awaitTermination(10000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		return System.currentTimeMillis() - start_time;
	}

}
