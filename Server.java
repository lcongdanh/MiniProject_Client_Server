import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	private int portNumber = 3000;
	private List<ClientHandler> allClients;
	
	public static void main(String[] args) {
		Server server = new Server();
		server.go();
	}
	
	public List<ClientHandler> getClients() {
		return allClients;
	}
	
	public void go() {
		try{
			ServerSocket serverSocket = new ServerSocket(portNumber);
			System.out.println("Server has started on port: " + portNumber);
			acceptClient(serverSocket);
		} catch(IOException ex) {
			System.err.println("Could not listen on port: "+ portNumber);
            System.exit(1);
		}

	} // close go
	
	public void acceptClient(ServerSocket serverSocket){
		allClients = new ArrayList<ClientHandler>();
		while(true){
			try{
				Socket clientSocket = serverSocket.accept();
				System.out.println("accepts : " + clientSocket.getRemoteSocketAddress());
				ClientHandler clientThread = new ClientHandler(this, clientSocket);
				Thread thread = new Thread(clientThread);
				thread.start();
				allClients.add(clientThread);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}









