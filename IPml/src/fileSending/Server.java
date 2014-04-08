package fileSending;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import serverclient.MainStart;
import GuiElements.FileTransferPanel;


public class Server implements Runnable{
	
	private ServerSocket serverSocket;
	private String[] rootPath;
	private String fileSeparator;
	private String SaveAsPath;
	public float i;
	public float max;
	private FileTransferPanel ftp;
	public boolean stop = false;
	public Server(int portNumber, FileTransferPanel ftp,String SaveAsPath) {
		if(MainStart.ftpsocket==null)
		{
			try {
				MainStart.ftpsocket = new ServerSocket(portNumber);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		serverSocket = MainStart.ftpsocket;
		this.ftp = ftp;
		this.SaveAsPath = SaveAsPath;
	}
	public void run() {
		
		try{
			Socket socket1 = null;

			System.out.println("Waiting for the connection... ");
			
			socket1 = serverSocket.accept();
			System.out.println("Connection Accepted : socket" + socket1);
			receiveFile(socket1,SaveAsPath,ftp);
			socket1.close();
						
			//serverSocket.close();
			//MainStart.ftpsocket=null;
		}
		
		catch (Exception e) {
			System.out.println("Error in Server!");
			System.out.println(e.getMessage());
			e.printStackTrace();
		
		}
	}
public float geti(){
	return i;
}
public void receiveFile(Socket socket,String SaveAsPath,FileTransferPanel ftp) throws IOException {
	    
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
		boolean flag_directory = false;
		String SaveAs = SaveAsPath.trim();
		
		System.out.println("Save As "  + SaveAs);
		
		int index = SaveAsPath.lastIndexOf('\\');
		SaveAs = SaveAsPath.substring(0,index);
		
		
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
		//System.out.println("Inside receiver.java");
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
		
		for(int i=0;i<filePath.length();i++) {
			if(filePath.charAt(i)=='\\') {
				fileSeparator = "[\\\\]";
				break;
			}
			if(filePath.charAt(i)=='/') {
				fileSeparator = "[/]";
				break;
			}
		}
		if(entry==false) {
			splitPath(filePath);
			fileName = rootPath[rootPath.length-1];
			entry=true;
		}
		else {
			String f[] = filePath.split(fileSeparator);
			fileName =  f[f.length-1];
		}
		fileName = fileName.trim();
		
		
		fileS.trim();
		fileSize = Long.parseLong(fileS);
		if(!checkPath(filePath)) {
			fileName = relativePath(filePath) ;
		}
		if(flag_directory == true) {
			
			SaveAsPath = SaveAs + fileName;
		}
		System.out.println("Debugging inside Receiver.java SaveAsPath..." + SaveAsPath);
		
		File file = new File(SaveAsPath.trim()) ;
		boolean status = false;
		
		if(pathType == 'd') { 
			flag_directory = true;
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
		//System.out.println("Inside Server Saveaspath " + SaveAs.trim() + "File Name " + fileName);
		//System.out.println("Inside Server Saveaspath " + SaveAsPath);
		FileOutputStream fos = new FileOutputStream(SaveAsPath.trim(),false);
		BufferedOutputStream bos = new BufferedOutputStream(fos); 			
		
		// Contents of file is read and stored in bytearray which will then be written to file location
		bytesRead=0;
		int currentTotal = 0;
		int chunkSize=1024*1024; // chunkSize bytes are read in each step
		long leftBytes = fileSize; // Number of bytes left to be written
		
		max = leftBytes;
		
		byte [] bytearray = new byte [chunkSize];
		ftp.getprogbar().startProgress(max,this);
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
		} while(currentTotal<=fileSize && bytesRead > 0 && !stop);
				
		bos.flush();
		bos.close();
		
		if(stop)
		{
			ftp.showMsg("File Transfer Cancelled");
			//deleteRecursive(SaveAsPath;) code to delete downloaded files so far.
		}
		else
		{
			ftp.showMsg("File Transfer Complete");
		}
		
		System.out.println("Finished Receiving File...");
	}
}

	private void splitPath(String filePath) {
		 rootPath = filePath.split(fileSeparator);
	}
	
	private boolean checkPath (String filePath) {
			
		String splitPath[] = filePath.split(fileSeparator);
		if(rootPath.length==splitPath.length)
			return true;
		else
			return false;
	}
	private String relativePath(String filePath) {

		String relativePath[] = filePath.split(fileSeparator);
		String relativeLocation = "";
		int index = rootPath.length-1;
		while( (index < relativePath.length) ) {
			relativeLocation =  relativeLocation + File.separator + relativePath[index];
			index = index + 1;
		}
			return relativeLocation;
	}

	private boolean deleteRecursive(File path) {
    if (path.isDirectory()) {
        for (File file : path.listFiles()) {
            if (!deleteRecursive(file))
                return false;
        }
    }
    return path.delete();
 }
/*	public static void main(String args[])  {
		int portNumber = 6666;
		new Thread(new Server(portNumber)).start();
	}*/
}
	

