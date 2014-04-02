package fileSending;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import GuiElements.FileTransferPanel;

public class Receiver {
	
	private static String[] rootPath;
	private static String fileSeparator;
	public static float i;
	public static float max;

	public static void receiveFile(Socket socket,String SaveAsPath,FileTransferPanel ftp) throws IOException {
	    
		/* File Header is of the format 
		 * [f/d]*[file/directory Size]-[file/directory path][newline character]
		 * f represents a file
		 * d represents a directory
		 * ASCII value of [-] is 45
		 * ASCII value of [newline character] is 10
		 * ASCII value of [*] is 42
		 * */
	    int asterisk = 42;
	    int minus = 45;
	    int newline = 10;
	    int endOfStream = -1;
		boolean entry = false; 
	
		
		fileSeparator = File.separator;
		
		if(fileSeparator.equals("\\"))
			fileSeparator = "[\\\\]";
		else
			fileSeparator = "[/]";
		System.out.println("Inside Receiver.java.. File Separator is " + fileSeparator);
		InputStream is = socket.getInputStream();
		while(true) {
			
		long fileSize = 0;
		long bytesRead=0;
			
		byte [] header = new byte[1];
		String fileS = ""; //stores file size
		String fileName = "";
		String filePath = "";
		
		
		// do while loop is used to extract the file header		
		int current=0; // current represents number of byte read in each step
		boolean flag = false; // flag will be set when '-' is encountered which is the separator in file header
		System.out.println("Inside receiver.java");
		char pathType = ' ';
		do {
			current = is.read(header);
			if(header[0]==asterisk)
				break;
			pathType = (char)header[0];
		} while(current!=endOfStream);
		
		current=0;
		
		do {
			current = is.read(header);
		
			if(flag==true)
				filePath = filePath + (char)header[0]; 
			else if(header[0]==minus && flag==false)
				flag=true; // flag set when '-' encountered
			else if(flag==false && header[0]!=newline)
				fileS = fileS + (char) header[0];
			bytesRead += current;
			
		} while(header[0]!=newline && current!= endOfStream); // Loop terminates when newline character is read or end of file is reached
		
		if(current==endOfStream)
			break;
		
		if(entry==false) {
			splitPath(filePath);
			fileName = rootPath[rootPath.length-1].trim();
			entry=true;
		}
		else {
			
			//String f[] = filePath.split("[\\\\]");
			String f[] = filePath.split(fileSeparator);
			fileName =  f[f.length-1];
		}

		System.out.println("File Name " + fileName + " File Size " + fileSize) ;
		
		fileS.trim();
		fileSize = Long.parseLong(fileS);
		if(!checkPath(filePath)) {
			fileName = relativePath(filePath) ;
		}
		
		
		// Creating the path where file will be saved
		SaveAsPath = System.getProperty("user.dir") + File.separator ;// **************Location Chooser thing
		System.out.println("Debugging inside Receiver.java SaveAsPath..." + SaveAsPath);
		SaveAsPath = SaveAsPath + fileName.trim();
		System.out.println("Debugging inside Receiver.java SaveAsPath..." + SaveAsPath);
		
		File file = new File(SaveAsPath) ;
		boolean status = false;
		
		if(pathType == 'd') { 
			status =  file.mkdir();
			if(!status) {
				System.out.println("Directory Creation failed for directory " + SaveAsPath);
			}
			continue;
		}
		else {
			if(!file.exists()) 
				file.createNewFile();
		}
		
		FileOutputStream fos = new FileOutputStream(SaveAsPath,false);
		BufferedOutputStream bos = new BufferedOutputStream(fos); 			
		
		// Contents of file is read and stored in bytearray which will then be written to file location
		bytesRead=0;
		int currentTotal = 0;
		int chunkSize=1024*1024; // chunkSize bytes are read in each step
		long leftBytes = fileSize; // Number of bytes left to be written
		
		max = leftBytes;
		
		byte [] bytearray = new byte [chunkSize];
		new SwingWorkerProgress(ftp);
		do {
			if(chunkSize>leftBytes) {
				bytesRead = is.read(bytearray,0,(int)leftBytes);
			}
			else {
				bytesRead = is.read(bytearray,0,chunkSize);
			}
			bos.write(bytearray,0,(int)bytesRead);
			leftBytes = leftBytes - bytesRead;
			i = i + bytesRead;			
		} while(currentTotal<=fileSize && bytesRead > 0);
				
		bos.flush();
		bos.close();
		
		System.out.println("Finished Receiving File...");
		}
	}

	private static void splitPath(String filePath) {
		 //rootPath = filePath.split("[\\\\]");
		 rootPath = filePath.split(fileSeparator);
	}
	
	private static boolean checkPath (String filePath) {
		System.out.println("Inside checkPath " + filePath);
	
		//String splitPath[] = filePath.split("[\\\\]");
		String splitPath[] = filePath.split(fileSeparator);
		if(rootPath.length==splitPath.length)
			return true;
		else
			return false;
	}
	private static String relativePath(String filePath) {
		
		//String relativePath[] = filePath.split("[\\\\]");
		String relativePath[] = filePath.split(fileSeparator);
		String relativeLocation = "";
		int index = rootPath.length-1;
		while( (index < relativePath.length) ) {
			relativeLocation =  relativeLocation + File.separator + relativePath[index];
			index = index + 1;
		}
			return relativeLocation;
	}

private static boolean deleteRecursive(File path) {
    if (path.isDirectory()) {
        for (File file : path.listFiles()) {
            if (!deleteRecursive(file))
                return false;
        }
    }
    return path.delete();
 }

}


