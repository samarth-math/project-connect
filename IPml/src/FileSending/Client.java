package FileSending;

import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Client {

	public static void main(String args[]) throws UnknownHostException, IOException {
		
		int portNumber = 16000;
		String ipAddress = "127.0.0.1";
		
		Socket socket = new Socket(ipAddress,portNumber);
		System.out.println(socket);		
		String FilePath;
		// File Path which is to be sent
		FilePath = "D:\\test";
		Path p1 = Paths.get(FilePath);
		Sender.send(socket,p1);
		socket.close();
	}
}
