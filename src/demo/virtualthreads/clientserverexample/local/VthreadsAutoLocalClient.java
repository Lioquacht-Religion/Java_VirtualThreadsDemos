package demo.virtualthreads.clientserverexample.local;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class VthreadsAutoLocalClient implements LocalClient{
	
	private VthreadsLocalServer server = null;
	public BlockingQueue<String> recvMessageQueue = new ArrayBlockingQueue<String>(10);
	
	public VthreadsAutoLocalClient() {
	}
	
	public VthreadsAutoLocalClient(VthreadsLocalServer server) {
		this.server = server;
	}
	
	public void setServer(VthreadsLocalServer server) {
		this.server = server;
	}
	
	public void start() {

		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			boolean success = this.server.sendEchoClientMessage(new ClientMessage(this, "hello from AutoClient!"));
			if (!success) {
				System.out.println("Server kann keine Anfragen mehr annehmen, versuche es später");
				continue;
			}
			String recvMssg = "";
			try {
				recvMssg = this.recvMessageQueue.poll(60, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (recvMssg != null) {
				System.out.println("echo: " + recvMssg);
			}
		}

	}

	@Override
	public boolean recieveMessage(String message) {
		return this.recvMessageQueue.offer(message);
	}
}
