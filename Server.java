import java.io.*;
import java.net.*;

public class Server {
	private int portNumber = 3000;
	
	public static void main(String[] args) {
		Server server = new Server();
		server.go();
	}
	
	public void go() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(portNumber);
			System.out.println("Server has started at port " + serverSocket.getLocalSocketAddress());
			while(true){
				Socket clientSocket = serverSocket.accept();
				ClientJob clientJob = new ClientJob(clientSocket);
				Thread thread = new Thread(clientJob);
				thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // close go
	
	public class ClientJob implements Runnable {
		Socket socket;
		BufferedReader reader;
		
		public ClientJob(Socket clientSocket) {
			try {
				socket = clientSocket;
				InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
				reader = new BufferedReader(isReader);
				System.out.println("Waiting for client's message");
			} catch (Exception e) {e.printStackTrace();}
		}
		
		@Override
		public void run() {
			String message;
			try {
				while((message = reader.readLine())!= null){
					System.out.println("message: " + message);
				}
			} catch (Exception ex) {ex.printStackTrace();}
		}
	} // clost innerClass
}









