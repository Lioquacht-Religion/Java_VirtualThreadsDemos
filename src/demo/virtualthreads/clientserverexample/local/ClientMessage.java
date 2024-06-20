package demo.virtualthreads.clientserverexample.local;

public class ClientMessage {
	LocalClient client;
	String message;
	
	public ClientMessage (LocalClient localClient, String message){
		this.client = localClient;
		this.message = message;
	}

}
