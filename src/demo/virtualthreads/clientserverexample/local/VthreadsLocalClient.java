package demo.virtualthreads.clientserverexample.local;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class VthreadsLocalClient {
	
	private VthreadsLocalServer server = null;
	public BlockingQueue<String> recvMessageQueue = new ArrayBlockingQueue<String>(10);
	
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
						this.server.sendEchoClientMessage(new ClientMessage(this, userInput));
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
	

}
