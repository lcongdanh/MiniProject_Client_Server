import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

	private String host = "localhost";
	private int port = 3000;
	
	public static void main(String[] args) {
		Client client = new Client();
		client.go();
	}
	
	public void go() {
		String input = null;
		
		try {
			Socket clientSocket = new Socket(host, port);
			
			Thread.sleep(1000);
			System.out.println("Send songthing: ");
					
			while(!clientSocket.isClosed()) {
				Scanner scan = new Scanner(System.in);
				if(scan.hasNextLine()){
					input = scan.nextLine();
					PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
					writer.println(input);
				}
			}
		} catch (Exception ex) {ex.printStackTrace();}
		
	}
}
