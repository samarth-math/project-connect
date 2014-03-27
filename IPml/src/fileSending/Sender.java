package fileSending;

import globalfunctions.FileTransfer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;



public class Sender  {
	
	 OutputStream os;
	
	public Sender(OutputStream os) {
		this.os = os;
	}
	public Sender() {
		
	}
	public void send(Socket socket,Path filePath) throws IOException {
		if( FileTransfer.isFile(filePath.toString()) )
			sendFile(socket,filePath);
		else{
			sendFile(socket,filePath);
			sendFolder(socket,filePath.toString());
		}
			System.out.println("Closing the output stream");
			socket.close();
			os.close();
	}

	public void sendFile(Socket socket,Path filePath) throws IOException {
		
		String fileName = filePath.getFileName().toString();
		long fSize =  Files.size(filePath);
		long fileSize= fSize;
		int chunkSize = 1024*1024;

		System.out.println("Inside Server.java... File Size is " + fileSize + " file Name " + fileName + "FIle path " + filePath.toString());
		
		boolean flag=false;
		char pathType= ' ';
		
		flag = FileTransfer.isPathValid(filePath.toString());
		if(flag==false) {
			displayError("Path Not Valid");
		}
		flag = FileTransfer.isFile(filePath.toString());
		if(flag==true) {
			pathType = 'f';
		}
		if(flag==false) {
			flag = FileTransfer.isDirectory(filePath.toString());
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
		
		for(int i=0;i<fileHeader.length;i++)
			System.out.print("" + (char) fileHeader[i]);
		
		os =  socket.getOutputStream();
		if(pathType=='d') {
			os.write(fileHeader);
			os.flush();
	
			return;
		}
		File transferfile = new File(filePath.toString());
		byte [] bytearray = new byte [chunkSize];
		FileInputStream fin = new FileInputStream(transferfile);
		BufferedInputStream bufferedinput = new BufferedInputStream(fin);
		
		// sending file header information to sender
		os.write(fileHeader);
		
		// sending file content to sender
		int read = 0;
		long leftBytes = fileSize; // number of bytes remaining to be written to socket stream
				
		while (leftBytes>0) {
			leftBytes = leftBytes - read;
			if(chunkSize>leftBytes) {
				read = bufferedinput.read(bytearray,0,(int)leftBytes);
			}
			else {
				read=bufferedinput.read(bytearray,0,chunkSize);
			}
			os.write(bytearray,0,read);
		}
	
		os.flush();
		bufferedinput.close();
		os.close();
		
		System.out.println("File Transfer Complete...");
	}

	public  void sendFolder(final Socket socket, String FilePath) throws IOException  {
		 FileWalker fw = new FileWalker();
	        fw.walk(FilePath,socket);
			socket.close();
	}
	

	public static void displayError(String errorMessage)  {
		System.err.println(errorMessage);
		System.exit(1);
	}
}
