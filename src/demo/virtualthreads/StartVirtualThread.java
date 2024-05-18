package demo.virtualthreads;

public class StartVirtualThread {
	
	public static void main(String[] args) throws InterruptedException {
		Runnable virtualThreadTask = () -> System.out.println("Hello, World from Virtual Thread!");
		Thread.ofVirtual().start(virtualThreadTask).join();
		
		Runnable platformThreadTask = () -> System.out.println("Hello, World from Platform Thread!");
		Thread.ofPlatform().start(platformThreadTask).join();
	}

}
