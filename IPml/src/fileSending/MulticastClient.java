package fileSending;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MulticastClient extends Sender implements Runnable  {
	private DatagramSocket multicastSocket;
	private Path filePath;
	int portNumber;
	String ipAddress;
	
 public MulticastClient(Path filePath,String ip, int pNumber) {
	 this.multicastSocket = null;
	 this.filePath = filePath;
	 this.ipAddress = ip;
	 this.portNumber = pNumber;
 }
 
 public MulticastClient(Path filePath,String ip, int pNumber, String Ip1, String Ip2) {
	 this.multicastSocket = null;
	 this.filePath = filePath;
	 this.ipAddress = ip;
	 this.portNumber = pNumber;
 }
 
	public void run() {
		
		try {
			 multicastSocket = new DatagramSocket();
	    	 byte[] buf = new byte[256];
	    	 String FilePath = "D:\\Articles";
			 filePath = Paths.get(FilePath);
	    	 String header = getHeader(filePath);
	    	 
	    	 //System.out.println("header: " + header);
	    	 
			 buf= new String("F:S:"+header).getBytes();
		     InetAddress address = InetAddress.getByName("255.255.255.255");
		     for(int i=0;i<buf.length;i++) {
		    	 System.out.print((char)buf[i]);
		     }
		     DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 3333);
		     multicastSocket.send(packet);
		     
			/*String FilePath;
			// File Path which is to be sent
			FilePath = "D:\\Articles";
			path = Paths.get(FilePath);
			System.out.println("Calling Multicast...");
			//MulticastClient.multicast(socket, path);
			Sender.send(socket,path);
			socket.close();*/
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[])  {
	
		Path file = null;
		int pNumber = 16000;
		String ip = "172.22.30.20";
		MulticastClient obj = new MulticastClient(file,ip,pNumber);
		(new Thread(obj)).start();
	}

	
    
    public static String getHeader(Path filePath) throws IOException  {
    	String fileName = filePath.getFileName().toString();
		long fSize =  Files.size(filePath);
		long fileSize= fSize;
		int chunkSize = 1024*1024;

		System.out.println("\nFile Size is " + fileSize + " file path " + fileName);
		
		boolean flag=false;
		char pathType= ' ';
		
		flag = isPathValid(filePath.toString());
		if(flag==false) {
			displayError("Path Not Valid");
		}
		flag = isFile(filePath.toString());
		if(flag==true) {
			pathType = 'f';
		}
		if(flag==false) {
			flag = isDirectory(filePath.toString());
			if(flag==true) {
				pathType = 'd';
			}
		}
		System.out.println("Path type " + pathType);
		
		/* File Header is of the format 
		 * [f/d]*[file/directory Size]-[file/directory path][newline character]
		 * f represents a file
		 * d represents a directory
		 * */
				
		// header contains the content of file header which will be sent to receiver
		String header = pathType + "*" + Long.toString(fileSize) + "-" + filePath +  "\n" ;
		byte [] fileHeader  = new byte[header.length()*2];
		// Creation of file header
		fileHeader = header.getBytes("UTF-8");
		
		return header;
		
    }
}