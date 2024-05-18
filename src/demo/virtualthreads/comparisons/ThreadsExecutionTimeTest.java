package demo.virtualthreads.comparisons;

import java.util.ArrayList;
import java.util.List;

public class ThreadsExecutionTimeTest {
	static final int MAX_THREAD_NUM = 20_000;
	
	public static void main(String[] args) {
		
		System.out.println("vthreads time : " + vthreadsExecTime() 
		+ "ms; pthreads time: " + pthreadsExecTime() + " ms;");
		
	}
	
	static long vthreadsExecTime() {
		long start_time = System.currentTimeMillis();
		
		List<Thread> vthreads = new ArrayList<>(MAX_THREAD_NUM);
		
	    for(int i = 0; i < MAX_THREAD_NUM; i++) {
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
	    	vthreads.add(Thread.ofVirtual().start(task));
		}
	    
	    vthreads.forEach(t -> {
	    	try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    });
		
		return System.currentTimeMillis() - start_time;
	}
	
	static long pthreadsExecTime() {
		long start_time = System.currentTimeMillis();
		
		List<Thread> pthreads = new ArrayList<>(MAX_THREAD_NUM);
		
	    for(int i = 0; i < MAX_THREAD_NUM; i++) {
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
	    	pthreads.add(Thread.ofPlatform().start(task));
		}
	    
	    pthreads.forEach(t -> {
	    	try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    });
		
		return System.currentTimeMillis() - start_time;
	}
	
}
