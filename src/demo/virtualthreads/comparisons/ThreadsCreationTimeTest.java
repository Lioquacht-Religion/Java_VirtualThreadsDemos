package demo.virtualthreads.comparisons;

import java.util.ArrayList;
import java.util.List;

public class ThreadsCreationTimeTest {
	static final int MAX_THREADS_NUM = 20_000;
	public static void main(String[] args) {		
		System.out.println("vthreads time: " + vthreadCreationTime() + "ms; pthreads time: " + pthreadCreationTime() + "ms;");
	}
	
	static long vthreadCreationTime() {
        List<Thread> vthreads = new ArrayList<>(MAX_THREADS_NUM);
		
		long start_time_vthreads = System.currentTimeMillis();
		
		for(int i = 0; i < MAX_THREADS_NUM; i++) {
			String task_text = "virtual thread task: " + i;
			Runnable task = () -> System.out.println(task_text);
			vthreads.add(Thread.ofVirtual().start(task));
		}
		
		long delta_time = System.currentTimeMillis() - start_time_vthreads;
		
		vthreads.forEach( t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		return delta_time;
		
	}
	
	static long pthreadCreationTime() {
        long start_time_pthreads = System.currentTimeMillis();
		
		for(int i = 0; i < MAX_THREADS_NUM; i++) {
			String task_text = "platform thread task: " + i;
			Runnable task = () -> System.out.println(task_text);
			Thread.ofPlatform().start(task);
		}
		
		long delta_time_pthreads = System.currentTimeMillis() - start_time_pthreads;
		return delta_time_pthreads;
	}
	
}
