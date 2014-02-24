package FileSending;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Receiver {
public static void receiveFile(Socket socket,String SaveAsPath) throws IOException {
		
		int filesize = 99999999;
		int bytesRead=0;
		int currentTot = 0;
				
		byte [] bytearray = new byte [filesize];
		byte [] header = new byte[10];
		
		InputStream is = socket.getInputStream();
		FileOutputStream fos = new FileOutputStream(SaveAsPath);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		System.out.println(socket.isClosed());
		int current=0;
		do {
			try{
				current = is.read(header);
			}
			catch(Exception e) {
				System.out.println("mil gaya...");
				System.out.println(e.getMessage());
				System.exit(0);
			}
			System.out.println("Read " + current);
			bytesRead += current;
		} while(bytesRead<11 & current!= -1);
		
		for(int i=0;i<10;i++)
			System.out.print(" " +(char) header[i]);
		/*
		//bytesRead=0;
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
		*/
		
		System.out.println("Finished Receiving File...");
	}
}
