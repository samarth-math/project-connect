package FileSending;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class Sender {

private final static int FILE_MAX_SIZE = 2147483647; // This is currently the maximum file size (in bytes) which can be sent

	public static void sendFile(Socket socket,Path filePath) throws IOException {
		
		String fileName = filePath.getClass().toString();
		int fileNameLength = fileName.length();
		long fSize =  Files.size(filePath);
		int fileSize=0;
		
		//Checking if file size fits within Maximum Size allowed.
		if(fSize>FILE_MAX_SIZE) {
			//Call Appropriate Error Handling COde
		}
		else {
			//If size fits type cast to integer
			fileSize = (int) fSize;
		}
		// header contains the content of file header which will be sent to receiver
		String header = Long.toString(fileSize) + "\n" ;
		// Allocation of memory for file header
		byte [] fileHeader  = new byte[12];
		// Creation of file header
		fileHeader = header.getBytes("UTF-8");
		
		File transferfile = new File(filePath.toString());
		byte [] bytearray = new byte [fileSize];
		FileInputStream fin = new FileInputStream(transferfile);
		
		BufferedInputStream bufferedinput = new BufferedInputStream(fin);
		OutputStream os =  socket.getOutputStream();
		
		System.out.print("Sending files.. with file header: ");
		for(int i=0;i<fileHeader.length;i++)
			System.out.print("" +(char) fileHeader[i]);
		
		// buffering content of header
		bufferedinput.read(fileHeader, 0, fileHeader.length);
		// sending file header information to sender
		os.write(fileHeader);
		// buffering file content
		bufferedinput.read(bytearray,0,bytearray.length);		
		// sending file content to sender
		os.write(bytearray,0,bytearray.length);
		
		os.flush();
		//socket.close();
		bufferedinput.close();
		
		System.out.println("File Transfer Complete...");
		
	}
/*	
	public static void SendFolder(final Socket socket, String FilePath) throws IOException  {
		
				Path start = FileSystems.getDefault().getPath(FilePath);
				Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path file,
							BasicFileAttributes attrs) throws IOException {
							System.out.println(file);
				            Server.SendFile(socket, file.toString());		
						return FileVisitResult.CONTINUE;
					}
				});
				socket.close();
	}
	*/
	
}