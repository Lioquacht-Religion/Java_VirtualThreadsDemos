package demo.virtualthreads.clientserverexample.local;

import java.io.IOException;

public class VthreadsLocalServerClientTest {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		VthreadsLocalServer server = new VthreadsLocalServer();
		Thread server_thread = Thread.ofPlatform().start(() -> {
				server.start();
		});
		
		VthreadsLocalClient client = new VthreadsLocalClient(server);
		client.start();

		server_thread.join();
	}
}
