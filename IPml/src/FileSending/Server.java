package FileSending;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;


public class Server {


	private static BufferedInputStream bufferedinput;
	/*
	public static void SendFile(Socket socket,String FilePath) throws IOException {
		
				
		File transferfile = new File(FilePath);
		byte [] bytearray = new byte [(int) transferfile.length()];
		FileInputStream fin = new FileInputStream(transferfile);
		bufferedinput = new BufferedInputStream(fin);
		bufferedinput.read(bytearray,0,bytearray.length);
		
		OutputStream os =  socket.getOutputStream();
		System.out.println("Sending files..." + transferfile.length());
		os.write((int) transferfile.length());
		os.write(bytearray,0,bytearray.length);
		os.flush();
		//socket.close();
		System.out.println("File Transfer Complete...");
		
	}
	
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
	
	public static void main (String args[]) throws IOException  {
	
		ServerSocket serverSocket = new ServerSocket(15005);
		Socket socket = serverSocket.accept();
		
		System.out.println("Connection Accepted : " + socket);
		String SaveAs = "C:\\Users\\rish\\Desktop\\copy.txt";
		Receiver.receiveFile(socket,SaveAs);
		socket.close();
		serverSocket.close();
	}
}

