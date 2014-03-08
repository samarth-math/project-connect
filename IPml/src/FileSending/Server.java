package FileSending;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server implements Runnable{
	
	ServerSocket serverSocket = null;
	Socket socket = null;
	
	public Server() {
		
		try {
			this.serverSocket = new ServerSocket(16000);
			//this.socket = serverSocket.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run() {
		
	
		try{
			socket = serverSocket.accept();
			System.out.println("Connection Accepted : " + socket);
			
			String SaveAs = "";
			Receiver.receiveFile(socket,SaveAs);
			socket.close();
			serverSocket.close();
		}
		
		catch (Exception e) {
			System.out.println("Error in Server!");
			System.out.print(e.getMessage());
		}
	}
	public static void main(String args[])  {
		
		new Thread(new Server()).start();
	}
}
	/*
	public static void main (String args[])  {
		ServerSocket serverSocket = null;
		Socket socket = null;
	
		try{
			serverSocket = new ServerSocket(16000);
			socket = serverSocket.accept();
			System.out.println("Connection Accepted : " + socket);
			
			String SaveAs = "";
			Receiver.receiveFile(socket,SaveAs);
			socket.close();
			serverSocket.close();
		}
		
		catch (Exception e) {
			System.out.println("Error in Server!");
			System.out.print(e.getMessage());
		}
	}
	*/

