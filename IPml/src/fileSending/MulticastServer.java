package fileSending;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MulticastServer  implements Runnable{
	
	//DatagramSocket multicastSocket;
	//Socket socket = null;
	
	/*public MulticastServer() {
		
		try {
			this.multicastSocket = new multicastSocket(16000);
			//this.socket = serverSocket.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public void run() {
		
		
		try{
			byte [] buf = new byte[30]	;
			String ipAddress="";

			DatagramSocket multicastSocket = new DatagramSocket(3333);
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			multicastSocket.receive(packet);
			ipAddress = multicastSocket.getInetAddress().toString();
			//ipAddress = "127.0.0.1";
			System.out.println("IP address... " + ipAddress);

			boolean flag = false;
		    String filePath="";
		    char fileType;
		    char status;
		    
		    fileType = (char)buf[0];
		    status = (char) buf[2];
		    System.out.println("Status is " + status);
		    
		    if(status=='S') {
		    	
				for(int i=0;i<buf.length;i++) {
					if(buf[i]=='-') {
						flag=true;
						continue;
					}
					if(flag==false) {
						continue;
					}
					else {
						filePath = filePath + (char)buf[i];
					}
				}
							
				String SaveAs = "";
				requestFile(fileType,filePath,ipAddress);
				//Receiver.receiveFile(socket,SaveAs);
				multicastSocket.close();
		    }
		    else if(status=='R'){
		    	System.out.println("You need to receive the file");
		    	Socket s = null;
				Path file = null;
				int pNumber = 3333;
				String ip = ipAddress;
		    	Client obj = new Client(s,file,ip,pNumber);
				(new Thread(obj)).start();
		    }
		}
		
		catch (Exception e) {
			System.out.println("Error in Server!");
			System.out.print(e.getMessage());
		}
	}
	public static void requestFile(char fileType,String filePath, String ipAddress) {
		
		 
    	 byte[] buf = new byte[256];
 
    	 
    	 System.out.println("file path: " + filePath);
    	 
		 buf= new String(fileType+":R-"+filePath).getBytes();
	     InetAddress address = null;
	     
		try {
			address = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	     for(int i=0;i<buf.length;i++) {
	    	 System.out.print((char)buf[i]);
	     }
	     DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 3333);
	     try {
	    	 DatagramSocket multicastSocket = new DatagramSocket();
			 multicastSocket.send(packet);
			 System.out.println("Packet sent ");
			 multicastSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void getPath() {
		
	}
	public static void main(String args[])  {
		
		new Thread(new MulticastServer()).start();
		System.out.println("Thread Started...");
	}
}