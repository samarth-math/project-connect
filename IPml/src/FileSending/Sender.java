package FileSending;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class Sender {

private final static int FILE_MAX_SIZE = 2147483647; // This is currently the maximum file size (in bytes) which can be sent

	public static void sendFile(Socket socket,Path filePath) throws IOException {
		
		String fileName = filePath.getFileName().toString();
		long fSize =  Files.size(filePath);
		long fileSize=0;
		int chunkSize = 1024*1024;
		
		//If size fits type cast to integer
		fileSize =  fSize;

		// header contains the content of file header which will be sent to receiver
		String header = Long.toString(fileSize) + "-" + fileName +  "\n" ;
		// Allocation of memory for file header
		byte [] fileHeader  = new byte[header.length()*2];
		// Creation of file header
		fileHeader = header.getBytes("UTF-8");
		
		File transferfile = new File(filePath.toString());
		byte [] bytearray = new byte [chunkSize];
		FileInputStream fin = new FileInputStream(transferfile);
		BufferedInputStream bufferedinput = new BufferedInputStream(fin);
		OutputStream os =  socket.getOutputStream();
		
		System.out.print("\nSending file with file header: ");
		for(int i=0;i<fileHeader.length;i++)
			System.out.print("" + (char) fileHeader[i]);
		
		// sending file header information to sender
		os.write(fileHeader);
		
		// sending file content to sender
		int read = 0;
		int count = 1;
		long leftBytes = fileSize; // number of bytes remaining to be written to socket stream
				
		while (leftBytes>0) {
			count++;
			leftBytes = leftBytes - read;
			if(chunkSize>leftBytes) {
				read = bufferedinput.read(bytearray,0,(int)leftBytes);
			}
			else {
				read=bufferedinput.read(bytearray,0,chunkSize);
			}
			os.write(bytearray,0,read);
		}
		System.out.println("\nCount "+count);
		os.flush();
		bufferedinput.close();
		os.close();
		socket.close();
		
		System.out.println("\n File Transfer Complete...");
		
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
