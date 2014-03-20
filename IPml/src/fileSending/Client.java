package fileSending;



import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Client implements Runnable {
	
	private Socket socket;
	private Path path;
	int portNumber;
	String ipAddress;
	
	
	public Client()  {
		
	}
	
	public Client(Socket socket,Path filePath,String ip, int pNumber) {
	 this.socket = socket;
	 this.path = filePath;
	 this.ipAddress = ip;
	 this.portNumber = pNumber;
 }
 
	public void run() {
		
		try {
			System.out.println("Initiating connection.. ");
			socket = new Socket(ipAddress,portNumber);
			System.out.println(socket);		
			String FilePath;
			// File Path which is to be sent
			FilePath = "D:\\Movies\\RightHereRightNow.mp4";
			path = Paths.get(FilePath);
			//System.out.println("Calling Multicast...");
			//MulticastClient.multicast(socket, path);
			Sender.send(socket,path);
			socket.close();
			
		} catch(Exception e) {
			System.out.println();
		}
	}
	
	public static void main(String args[])  {
		Socket s = null;
		Path file = null;
		int pNumber = 3333;
		String ip = "127.0.0.1";
		Client obj = new Client(s,file,ip,pNumber);
		(new Thread(obj)).start();
	}
 }
 /*
	public static void main(String args[]) throws UnknownHostException, IOException {
		
		int portNumber = 16000;
		String ipAddress = "127.0.0.1";
		
		Socket socket = new Socket(ipAddress,portNumber);
		System.out.println(socket);		
		String FilePath;
		// File Path which is to be sent
		FilePath = "D:\\programming";
		Path p1 = Paths.get(FilePath);
		System.out.println("Calling Multicast...");
		MulticastClient.multicast(socket, p1);
		//Sender.send(socket,p1);
		socket.close();
	}
 */

