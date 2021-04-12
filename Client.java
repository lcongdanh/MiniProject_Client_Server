import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Client {

	private String host = "localhost";
	private int port = 3000;
	
	JTextArea showTextArea;
	JTextField sendTextArea;
	BufferedReader reader;
	PrintWriter writer;
	Socket sock;
	String userName;
	
	public static void main(String[] args) {
		Client client = new Client();
		client.go();
	}
	
	public void go() {
		Scanner getName = new Scanner(System.in);
		System.out.println("Please enter user name: ");
		
		while(userName == null || userName.trim().equals("")) {
			userName = getName.nextLine();
			if(userName.trim().equals("")){
				System.out.println("Invalid name. Please try again: ");
			}
		} // close while loop
		
		makeGUI(userName);
	}
	
	public void makeGUI(String frameName) {
		JFrame frame = new JFrame(frameName);
		JPanel mainPanel = new JPanel();
		
		showTextArea = new JTextArea(20,30);
		showTextArea.setLineWrap(true);
		showTextArea.setWrapStyleWord(true);
		showTextArea.setEditable(false);
		
		JScrollPane scroller = new JScrollPane(showTextArea);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		sendTextArea = new JTextField(20);
		JButton sendButton = new JButton("send");
		sendButton.addActionListener(new SendButtonListener());
		
		mainPanel.add(scroller);
		mainPanel.add(sendTextArea);
		mainPanel.add(sendButton);
		setUpNetWorking();

		Thread readThread = new Thread(new IncomingMessage());
		readThread.start();
		
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(400,500);
		frame.setVisible(true);
	} // close makeGUI method
	
	private void setUpNetWorking(){
		try {
			sock = new Socket(host,port);
			InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(streamReader);
			writer = new PrintWriter(sock.getOutputStream());
			System.out.println("Networking Established");
		} catch(IOException ex) {ex.printStackTrace();}
	} // close setUpNetworking method
	
	
	public class SendButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			try {
				writer.println(userName + " > " + sendTextArea.getText());
				writer.flush();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			sendTextArea.setText("");
			sendTextArea.requestFocus();
		}
	} // close inner class
	
	public class IncomingMessage implements Runnable {
		public void run() {
			String message;
			try {
				while((message = reader.readLine()) != null) {
					showTextArea.append(message + "\n");
				}
			} catch (Exception ex) {ex.printStackTrace();}
		}
	} // close inner Class
} // close outer class


















