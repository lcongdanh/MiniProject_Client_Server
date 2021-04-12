import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable {
	Socket socket;
	Server server;
	BufferedReader reader;
	PrintWriter sendClient;
	
	public ClientHandler(Server server, Socket clientSocket) {
		try {
			this.socket = clientSocket;
			this.server = server;
			InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
			this.reader = new BufferedReader(isReader);
			System.out.println("Waiting for client's message");
		} catch (Exception ex) {ex.printStackTrace();}
	}
	
	public PrintWriter getWriter() {
		return sendClient;
	}
	
	@Override
	public void run() {
		String message;
		try{
			sendClient = new PrintWriter(socket.getOutputStream(), false);
			while(!socket.isClosed()) {
				if((message = reader.readLine()) != null){
					sendEveryone(message);				
				}
			}
			
		}catch(IOException ex) {ex.printStackTrace();}
		
	} // close run method
	
	public void sendEveryone(String message) {
		for(ClientHandler client : server.getClients()){
			PrintWriter clientOut = client.getWriter();
			if(clientOut != null) {
				clientOut.println(message);
				clientOut.flush();			
			}
		}		
	} // close sendEveryone method
}






