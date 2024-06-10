package demo.virtualthreads.clientserverexample.web;

import java.io.IOException;


public class ThreadsServerClientTest {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		Thread server_thread = Thread.ofPlatform().start(() -> {
			try {
				VthreadsWebServer.main("8080");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		try {
			VthreadsWebClient.main("localhost", "8080");
		} catch (IOException e) {
			e.printStackTrace();
		}

		server_thread.join();

	}
	

}
