package demo.virtualthreads.clientserverexample.local;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VthreadsLocalServer {
	public BlockingQueue<ClientMessage> recvMessageQueue = new ArrayBlockingQueue<>(10);
	
	public VthreadsLocalServer() {
	}
	
	public void sendEchoClientMessage(ClientMessage mssg) {
		this.recvMessageQueue.add(mssg);
	}
	
	public void start() {
		ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor();
		while(true) {
			try {
				ClientMessage mssg = this.recvMessageQueue.poll(60, TimeUnit.SECONDS);
				exec.execute(() -> {
					mssg.client.recvMessageQueue.add("ServerEcho: " + mssg.message);
				});
				if (mssg.message.equals("bye")) break;
			} catch (InterruptedException e) {
				System.out.println("60 Sekunden Inputwartezeit überschritten!");
				break;
			}
		}
	}

	


}
