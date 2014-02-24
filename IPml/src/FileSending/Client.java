package FileSending;

import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Client {
/*
	public static void receiveFile(Socket socket,String SaveAsPath) throws IOException {
		
		int filesize = 99999999;
		int bytesRead;
		int currentTot = 0;
				
		byte [] bytearray = new byte [filesize];
		byte [] filebytearray = new byte[4];
		
		InputStream is = socket.getInputStream();
		FileOutputStream fos = new FileOutputStream(SaveAsPath);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		bytesRead = is.read(filebytearray, 0, 4);
		System.out.println("Size " + filebytearray[0]);
		
		bytesRead = is.read(bytearray,0,bytearray.length);
		currentTot = bytesRead;
		
		do {
			bytesRead = is.read(bytearray,currentTot,(bytearray.length-currentTot));
			if( bytesRead >=0 ) {
				currentTot += bytesRead;
			}
		} while(bytesRead > -1);
		System.out.println("Receiving File...");
		bos.write(bytearray,0,currentTot);
		bos.flush();
		bos.close();
		socket.close();
		System.out.println("Finished Receiving File...");
	}
	*/
	public static void main(String args[]) throws UnknownHostException, IOException {
		
		Socket socket = new Socket("127.0.0.1",15005);
				
		String FilePath;
		FilePath = "C:\\Users\\rish\\Desktop\\c.txt";
		//Sender.SendFolder(socket, FilePath);
		Path p1 = Paths.get(FilePath);
		Sender.sendFile(socket,p1);
		socket.close();
	}
}
