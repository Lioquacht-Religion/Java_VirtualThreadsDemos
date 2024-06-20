package demo.virtualthreads.clientserverexample.local;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class VthreadsLocalClient implements LocalClient{
	
	private VthreadsLocalServer server = null;
	public BlockingQueue<String> recvMessageQueue = new LinkedBlockingQueue<String>();
	
	public VthreadsLocalClient() {
	}
	
	public VthreadsLocalClient(VthreadsLocalServer server) {
		this.server = server;
	}
	
	public void setServer(VthreadsLocalServer server) {
		this.server = server;
	}
	
	public void start() { 
	            String userInput;
	            try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));){
					while ((userInput = stdIn.readLine()) != null) {
						boolean success = this.server.sendEchoClientMessage(new ClientMessage(this, userInput));
						if(!success) {
							System.out.println("Server kann keine Anfragen mehr annehmen, versuche es später");
							continue;
						}
					    String recvMssg = this.recvMessageQueue.poll(60, TimeUnit.SECONDS);
					    if (recvMssg != null) {
						    System.out.println("echo: " + recvMssg);
					    }
					    if (userInput.equals("bye")) break;
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					System.out.println("Server hat 60 Sekunden Wartezeit überschritten!");
					e.printStackTrace();
				}
		
		
	}

	@Override
	public boolean recieveMessage(String message) {
		return this.recvMessageQueue.offer(message);
	}
	

}
