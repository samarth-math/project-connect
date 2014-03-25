package fileSending;



import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Client implements Runnable {

	int portNumber;
	String ipAddress;
	
	public Client(String ip, int pNumber) {
	 this.ipAddress = ip;
	 this.portNumber = pNumber;
 }
 
	public void run() {
		
		try {
			Socket socket;
			Path path;
			System.out.println("Initiating connection... " + ipAddress + " " + portNumber);
			socket = new Socket(ipAddress,portNumber);
			System.out.println("Socket is "  + socket);		
			String FilePath;
			// File Path which is to be sent
			FilePath = "D:\\Movies\\RightHereRightNow.mp4";
			OutputStream os=null;
			path = Paths.get(FilePath);
			Sender obj = new Sender(os);
			obj.send(socket,path);
			socket.close();
			
		} catch(Exception e) {
			System.out.println();
		}
	}
	
	public static void main(String args[])  {
		Socket s = null;
		Path file = null;
		int pNumber = 6666;
		String ip = "127.0.0.1";
		Client obj = new Client(ip,pNumber);
		(new Thread(obj)).start();
		ip = "127.0.0.2";
		obj = new Client(ip,pNumber);
		(new Thread(obj)).start();
	}
 }


