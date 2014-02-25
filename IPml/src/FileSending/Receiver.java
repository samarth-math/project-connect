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
	 *  Here '-' whose ASCII value 45 is used as a separator
	 *  Line ends when newline character is read
	 *  ASCII value of new line character is 10
	 * */
	 
	    int minus = 45;
	    int newline = 10;
		int fileSize = 0;
		int bytesRead=0;
		
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
		fileSize = Integer.parseInt(fileS);
		byte [] bytearray = new byte [fileSize];
		
		System.out.println("File Name " + fileName);
		
		// Contents of file is read and stored in bytearray which will then be written to file location
		bytesRead=0;
		int currentTotal = bytesRead;
		System.out.print("FileSize is " + fileSize + " bytes. " + "\nFile Name is " + fileName);
		do {
			bytesRead = is.read(bytearray,currentTotal,(bytearray.length-currentTotal));
			if( bytesRead >=0 ) {
				currentTotal += bytesRead;
			}
		} while(currentTotal<=fileSize && bytesRead > 0);
		
		// Creating the path where file will be saved
		SaveAsPath = System.getProperty("user.dir") + File.separator ;
		SaveAsPath = SaveAsPath + fileName.trim();
		
		File file = new File(SaveAsPath);
		if(!file.exists()) // checking if file exists
			file.createNewFile();
		
		FileOutputStream fos = new FileOutputStream(SaveAsPath,false);
		BufferedOutputStream bos = new BufferedOutputStream(fos);			
		System.out.println("Saving File in " + SaveAsPath);
		
		bos.write(bytearray,0,currentTotal);
		bos.flush();
		bos.close();
		socket.close();
		
		System.out.println("Finished Receiving File...");
	}
}
