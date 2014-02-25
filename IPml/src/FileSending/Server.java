package FileSending;


import java.net.ServerSocket;
import java.net.Socket;




public class Server {
	
	public static void main (String args[])  {
		ServerSocket serverSocket = null;
		Socket socket = null;
	
		try{
			serverSocket = new ServerSocket(16000);
			socket = serverSocket.accept();
			System.out.println("Connection Accepted : " + socket);
			
			String SaveAs = "C:\\Users\\rish\\Desktop\\Docs";
			Receiver.receiveFile(socket,SaveAs);
			socket.close();
			serverSocket.close();
		}
		
		catch (Exception e) {
			System.out.println("Error in Server!");
			System.out.print(e.getMessage());
			System.out.print(e.getLocalizedMessage());
		}
	}
}

