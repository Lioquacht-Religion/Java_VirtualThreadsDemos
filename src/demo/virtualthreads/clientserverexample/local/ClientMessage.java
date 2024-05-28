package demo.virtualthreads.clientserverexample.local;

public class ClientMessage {
	VthreadsLocalClient client;
	String message;
	
	public ClientMessage (VthreadsLocalClient client, String message){
		this.client = client;
		this.message = message;
	}

}
