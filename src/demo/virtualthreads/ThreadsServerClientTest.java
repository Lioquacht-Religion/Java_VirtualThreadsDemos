package demo.virtualthreads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ThreadsServerClientTest {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		Thread server_thread = Thread.ofPlatform().start(() -> { 
				try {
					VThreadServer.main("8080");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		});
		
		//Thread client_thread = Thread.ofPlatform().start(() -> { 
			try {
				VThreadClient.main("localhost", "8080");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    //});
		
		//client_thread.join();
		server_thread.join();
		
	}
	
	class VThreadClient{
		
		public static void main(String... args) throws IOException {
			
			if (args.length != 2) {
	            System.err.println(
	                "Usage: java EchoClient <hostname> <port>");
	            System.exit(1);
	        }
	        String hostName = args[0];
	        int portNumber = Integer.parseInt(args[1]);
	        try (
	            Socket echoSocket = new Socket(hostName, portNumber);
	            PrintWriter out =
	                new PrintWriter(echoSocket.getOutputStream(), true);
	            BufferedReader in =
	                new BufferedReader(
	                    new InputStreamReader(echoSocket.getInputStream()));
	        ) {
	            BufferedReader stdIn =
	                new BufferedReader(
	                    new InputStreamReader(System.in));
	            String userInput;
	            while ((userInput = stdIn.readLine()) != null) {
	                out.println(userInput);
	                System.out.println("echo: " + in.readLine());
	                if (userInput.equals("bye")) break;
	            }
	        } catch (UnknownHostException e) {
	            System.err.println("Don't know about host " + hostName);
	            System.exit(1);
	        } catch (IOException e) {
	            System.err.println("Couldn't get I/O for the connection to " +
	                hostName);
	            System.exit(1);
	        } 
		}
	}
	
	class VThreadServer{
		
	    public static void main(String... args) throws IOException {
	         
	        if (args.length != 1) {
	            System.err.println("Usage: java EchoServer <port>");
	            System.exit(1);
	        }
	         
	        int portNumber = Integer.parseInt(args[0]);
	        try (
	            ServerSocket serverSocket =
	                new ServerSocket(Integer.parseInt(args[0]));
	        ) {                
	            while (true) {
	                Socket clientSocket = serverSocket.accept();
	                // Accept incoming connections
	                // Start a service thread
	                Thread.ofVirtual().start(() -> {
	                    try (
	                        PrintWriter out =
	                            new PrintWriter(clientSocket.getOutputStream(), true);
	                        BufferedReader in = new BufferedReader(
	                            new InputStreamReader(clientSocket.getInputStream()));
	                    ) {
	                        String inputLine;
	                        while ((inputLine = in.readLine()) != null) {
	                            System.out.println(inputLine);
	                            out.println(inputLine);
	                        }
	                    
	                    } catch (IOException e) { 
	                        e.printStackTrace();
	                    }
	                });
	            }
	        } catch (IOException e) {
	            System.out.println("Exception caught when trying to listen on port "
	                + portNumber + " or listening for a connection");
	            System.out.println(e.getMessage());
	        }
	    }
		
	}
	

}
