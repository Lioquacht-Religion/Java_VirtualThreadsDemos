package demo.virtualthreads.clientserverexample.local;

import java.io.IOException;

public class VthreadsLocalServerClientTest {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		VthreadsLocalServer server = new VthreadsLocalServer();
		Thread server_thread = Thread.ofPlatform().start(() -> {
				server.start();
		});
		
		VthreadsAutoLocalClient autoClient = new VthreadsAutoLocalClient(server);
		Thread.ofPlatform().start(() -> {autoClient.start();});
		VthreadsAutoLocalClient autoClient2 = new VthreadsAutoLocalClient(server);
		Thread.ofPlatform().start(() -> {autoClient2.start();});
		
		VthreadsLocalClient client = new VthreadsLocalClient(server);
		client.start();

		server_thread.join();
	}
}
