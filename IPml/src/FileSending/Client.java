package FileSending;

import java.io.*;
import java.net.*;


public class Client {

	public static void ReceiveFile(Socket socket,String SaveAsPath) throws IOException {
		
		int filesize = 9999999;
		int bytesRead;
		int currentTot = 0;
				
		byte [] bytearray = new byte [filesize];
		InputStream is = socket.getInputStream();
		FileOutputStream fos = new FileOutputStream(SaveAsPath);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		bytesRead = is.read(bytearray,0,bytearray.length);
		currentTot = bytesRead;
		
		do {
			bytesRead = is.read(bytearray,currentTot,(bytearray.length-currentTot));
			if( bytesRead >=0 ) {
				currentTot += bytesRead;
			}
		} while(bytesRead > -1);
		
		bos.write(bytearray,0,currentTot);
		bos.flush();
		bos.close();
		socket.close();
	}
	
	public static void main(String args[]) throws UnknownHostException, IOException {
		
		Socket socket = new Socket("127.0.0.1",15000);
		String SaveAs = "C:\\Users\\rish\\Desktop\\copy.exe";
		Client.ReceiveFile(socket,SaveAs);
		
	}
}
