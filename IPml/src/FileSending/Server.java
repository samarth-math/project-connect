package FileSending;

import java.io.*;
import java.net.*;


public class Server {


	private static BufferedInputStream bufferedinput;

	public static void SendFile(Socket socket,String FilePath) throws IOException {
		
				
		File transferfile = new File(FilePath);
		byte [] bytearray = new byte [(int) transferfile.length()];
		FileInputStream fin = new FileInputStream(transferfile);
		bufferedinput = new BufferedInputStream(fin);
		bufferedinput.read(bytearray,0,bytearray.length);
		
		OutputStream os =  socket.getOutputStream();
		System.out.println("Sending files...");
		os.write(bytearray,0,bytearray.length);
		os.flush();
		socket.close();
		System.out.println("File Transfer Complete...");
		
	}
	
	public static void main (String args[]) throws IOException {
	
		ServerSocket serverSocket = new ServerSocket(15000);
		Socket socket = serverSocket.accept();
		System.out.println("Connection Accepted : " + socket);
		//String FilePath = "C:\\Users\\rish\\Desktop\\codeblocks.exe";
		String FilePath;
		FilePath = "C:\\Program Files\\CCleaner\\CCleaner64.exe";
		
		Server.SendFile(socket,FilePath);
	}
}

