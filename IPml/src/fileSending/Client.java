package fileSending;



import globalfunctions.FileTransfer;

import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import GuiElements.FileTransferPanelS;


public class Client implements Runnable {

	private int portNumber;
	private String ipAddress;
	private Path filePath;
	private FileTransferPanelS ftps;
	private long bytesSent;
	
	
	public Client(String ip, int pNumber, Path filePath, FileTransferPanelS ftps) {
	 this.ipAddress = ip;
	 this.portNumber = pNumber;
	 this.filePath = filePath;
	 this.ftps=ftps;
 }
	public Client(String ip, int pNumber, Path filePath) {
		 this.ipAddress = ip;
		 this.portNumber = pNumber;
		 this.filePath = filePath;
	 }
 
	public void run() {
		
		try {
			Socket socket = new Socket(ipAddress,portNumber);		
			OutputStream os=  socket.getOutputStream();
						
			send(socket,filePath, os);
			socket.close();
			
		} catch(Exception e) {
			//Error
		}
	}
	
	public void send(Socket socket,Path filePath, OutputStream os) throws IOException {
		if( FileTransfer.isFile(filePath.toString()) ) {
			sendFile(socket,filePath,os);
		}
			
		else if( FileTransfer.isDirectory(filePath.toString()) ){
			sendFile(socket,filePath,os);
			sendFolder(socket,filePath.toString(),os);
		}
			socket.close();
			os.close();
	}

	public long geti()
	{
		return bytesSent;
	}
	public void sendFile(Socket socket,Path filePath, OutputStream os) throws IOException {
		
		//String fileName = filePath.getFileName().toString().trim();
		File f = new File(filePath.toString());
		long fileSize= f.length();
		int chunkSize = 256*1024;
		
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
		
		if(ftps!=null)
		ftps.getprogbar().startProgress(fileSize, this);
		
		while (leftBytes>0) {
			leftBytes = leftBytes - read;
			if(chunkSize>leftBytes) {
				read = bufferedinput.read(bytearray,0,(int)leftBytes);
			}
			else {
				read=bufferedinput.read(bytearray,0,chunkSize);
			}
			os.write(bytearray,0,read);
			bytesSent=bytesSent+read;
		}
		
		if(ftps!=null)
		ftps.showMsg("File Transfer Complete");
		os.flush();
		bufferedinput.close();
		//don't close the outputstream here
	}

	public  void sendFolder(final Socket socket, String FilePath, OutputStream os) throws IOException  {
		    walk(FilePath,socket, os);
			socket.close();
	}
	
	public static void displayError(String errorMessage)  {
		JOptionPane.showMessageDialog(null,errorMessage,"The Three Musketeers say",JOptionPane.ERROR_MESSAGE);
	}
	
	public void walk( String path, Socket socket, OutputStream os ) throws IOException {

        File root = new File( path );
        File[] list = root.listFiles();

        if (list == null) return;

        for ( File f : list ) {
            if ( f.isDirectory() ) {
                Path p = Paths.get(f.getAbsoluteFile().toString());

            	sendFile(socket, p,os);
                walk( f.getAbsolutePath(), socket, os );
            }
            else {
            	Path p = Paths.get(f.getAbsoluteFile().toString());
            
            	sendFile(socket, p,os);
            }
        }
	}

	/*public static void main(String args[])  {
		Socket s = null;
		Path file = null;
		int pNumber = 6666;
		String ip = "127.0.0.1";
		Client obj = new Client(ip,pNumber);
		(new Thread(obj)).start();
		ip = "127.0.0.2";
		obj = new Client(ip,pNumber);
		(new Thread(obj)).start();
	}*/
	}


