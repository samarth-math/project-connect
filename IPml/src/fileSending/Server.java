package fileSending;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import serverclient.MainStart;
import GuiElements.FileTransferPanel;


public class Server implements Runnable{
	
	ServerSocket serverSocket;
	
	FileTransferPanel ftp;
	public Server(int portNumber, FileTransferPanel ftp) {
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
	}
	public void run() {
		
		try{
			Socket socket1 = null;

			System.out.println("Waiting for the connection... ");
			
			socket1 = serverSocket.accept();
			System.out.println("Connection Accepted : socket" + socket1);
			String SaveAs1 = "";
			Receiver.receiveFile(socket1,SaveAs1,ftp);
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
/*	public static void main(String args[])  {
		int portNumber = 6666;
		new Thread(new Server(portNumber)).start();
	}*/
}
	

