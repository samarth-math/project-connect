package FileSending;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Receiver {
public static void receiveFile(Socket socket,String SaveAsPath) throws IOException {
	    
	/*  File Header  is of the format FileSize-FileName
	 *  Here '-' whose ASCII value is 45 has been used as a separator
	 *  Line ends when newline character is read
	 *  ASCII value of new line character is 10
	 * */
	 
	    int minus = 45;
	    int newline = 10;
		long fileSize = 0;
		long bytesRead=0;
		
		byte [] header = new byte[1];
		String fileS = ""; //stores file size
		String fileName = "";
		
		InputStream is = socket.getInputStream();
		
		// do while loop is used to extract the file header		
		int current=0; // current represents number of byte read in each step
		boolean flag = false; // flag will be set when '-' is encountered which is the separator in file header
		do {
			
			current = is.read(header);
			if(flag==true)
				fileName = fileName + (char)header[0]; 
			else if(header[0]==minus)
				flag=true; // flag set when '-' encountered
			else if(header[0]!=newline)
				fileS = fileS + (char) header[0];
			bytesRead += current;
			
		} while(header[0]!=10 && current!= -1); // Loop terminates when newline character is read or end of file is reached
		
		fileS.trim();
		fileSize = Long.parseLong(fileS);
		
		// Creating the path where file will be saved
		SaveAsPath = System.getProperty("user.dir") + File.separator ;
		SaveAsPath = SaveAsPath + fileName.trim();
						
		File file = new File(SaveAsPath);
		if(!file.exists()) // checking if file exists
			file.createNewFile();
		
		FileOutputStream fos = new FileOutputStream(SaveAsPath,false);
		BufferedOutputStream bos = new BufferedOutputStream(fos); 			
		System.out.println("Saving File in " + SaveAsPath);
		
		// Contents of file is read and stored in bytearray which will then be written to file location
		bytesRead=0;
		int currentTotal = 0;
		int chunkSize=1024*1024; // chunkSize bytes are read in each step
		long leftBytes = fileSize; // Number of bytes left to be written
		
		byte [] bytearray = new byte [chunkSize];
		
		System.out.print("FileSize is " + fileSize + " bytes. " + "\nFile Name is " + fileName);
		do {
			if(chunkSize>leftBytes) {
				bytesRead = is.read(bytearray,0,(int)leftBytes);
			}
			else {
				bytesRead = is.read(bytearray,0,chunkSize);
			}
			bos.write(bytearray,0,(int)bytesRead);
			leftBytes = leftBytes - bytesRead;
			
		} while(currentTotal<=fileSize && bytesRead > 0);
				
		bos.flush();
		bos.close();
		socket.close();
		
		System.out.println("Finished Receiving File...");
	}
}
