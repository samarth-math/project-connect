package globalfunctions;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.swing.SwingUtilities;

import serverclient.MainStart;
import GUIObjects.ChatWindow;
import GuiElements.BroadCastSender;
import LogMaintainence.ChatLogging;
import LogMaintainence.GettingChatLogs;

public class Contact {
	
	
	private InetAddress ip;
	private int port;
	private String mac;
	private String OS;
	private String Host;
	private String username;
	private ChatWindow cw=null;
	private BlockingQueue<String> bq = null;
	
	public Contact(String mac, String OS, String Host, String username, InetAddress ip, int port)
	{
		this.mac = mac;
		this.Host = Host;
		this.OS = OS;
		this.username = username;
		this.ip=ip;
		this.port=port;
	}
	public ChatWindow getWindow()
	{
		final String id = mac;
		if(cw!=null)
			return cw;
		else
		{
	
			try {
				SwingUtilities.invokeAndWait(new Runnable()
				{
					@Override
					public void run()
					{
						cw = new ChatWindow(id);
					}
				});
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
				
			//Populate current Window with Rajat's read thread
			GettingChatLogs.readLog(id);
			setNewBlockingQ();			
			ChatLogging cl = new ChatLogging(id, username, bq);
			new Thread(cl).start();
						
			return cw;
		}
	}
	public void setNewBlockingQ()
	{
		bq=new ArrayBlockingQueue<String>(15);
	}
	public BlockingQueue<String> getBlockingQ()
	{
		return bq;
	}
	public void setWindowNull()
	{
		cw = null;
	}
	public void startChat()
	{
		Thread Chat = new Thread()
		{
			@Override
			public void run()
			{
				cw = getWindow();
			}
		};
		Chat.start();
		
	}
	@Override
	public String toString()
	{
		return username;
	}
	
	
	
	public static void sendToAll(String Message)
	{
		byte[] buf = new byte[1024];
		buf = new String("BM|"+MainStart.myID+"|"+Message).getBytes();
		Timestamp t =new Timestamp(new Date().getTime());
		MainStart.mainWindow.broadcastConsole(new BroadCastSender(Message,  new SimpleDateFormat("HH:mm:ss").format(t)));
		for (String key : MainStart.people.keySet()) {
			Contact person = (Contact) MainStart.people.get(key);
		  
		  	DatagramPacket packet = new DatagramPacket(buf, buf.length, person.getIP() , 3333);
			DatagramSocket socket = MainStart.socket;
	   		try {
				socket.send(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
	}
	public void sendMessage(String Message, String senderid) throws SocketException, IOException
	{
		byte[] buf = new byte[1024];
		buf = new String("M|"+senderid+"|"+Message).getBytes();
		
		DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, port);
		DatagramSocket socket = MainStart.socket;
   		socket.send(packet);
	}
	
	public void sendFile(String header, String senderid) throws SocketException, IOException
	{
		byte[] buf = new byte[1024];
		buf = new String("S|"+senderid+"|"+header).getBytes();
		System.out.println("Send File " + new String(buf,"UTF8"));
		DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, port);
		DatagramSocket socket = MainStart.socket;
   		socket.send(packet);
	}
	public void sendAcceptFile(String senderid, int sendPanelId) throws SocketException, IOException
	{
		byte[] buf = new byte[1024];
		buf = new String("R|"+senderid+"|"+sendPanelId).getBytes();
		DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, port);
		DatagramSocket socket = MainStart.socket;
   		socket.send(packet);
	}
	public void sendRejectFile(String senderid, int sendPanelId) throws SocketException, IOException
	{
		byte[] buf = new byte[1024];
		buf = new String("N|"+senderid+"|"+sendPanelId).getBytes();
		DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, port);
		DatagramSocket socket = MainStart.socket;
   		socket.send(packet);
	}
	public String getId()
	{
		return mac;
	}
	public String getOSName()
	{
		return OS;
	}
	public String getHostName()
	{
		return Host;
	}
	public String getUserName()
	{
		return username;
	}
	public InetAddress getIP()
	{
		return ip;
	}
	public void printall()
	{
		System.out.print(mac+"\n"+OS+"\n"+Host+"\n"+username+"\n"+ip.getHostAddress()+"\n\n\n");
	}
	 @Override
	    public boolean equals(Object someone)//To do a deep comparison
	    {
	        final Contact person = (Contact) someone;
	        if(mac.equals(person.mac))
	            return true;
	        else
	            return false;
	    }
	@Override
	public int hashCode()//to do deep hashing
	{
		return mac.hashCode();
	}
}