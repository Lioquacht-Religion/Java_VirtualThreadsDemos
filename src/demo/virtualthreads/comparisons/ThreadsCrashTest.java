package demo.virtualthreads.comparisons;

public class ThreadsCrashTest {
	
	public static void main(String[] args) {
		int count = 0;
		while(count <= Integer.MAX_VALUE) {
			final String count_str = String.valueOf(count);
		    Runnable task = () -> System.out.println("task started: " + count_str);
		    Thread t = Thread.ofVirtual().start(task);
		    count++;
		}
		/*try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		System.out.println("main thread finished");
	}

}
