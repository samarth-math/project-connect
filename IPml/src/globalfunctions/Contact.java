package globalfunctions;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import serverclient.MainStart;
import GUIObjects.ChatWindow;

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
		{try {
			java.awt.EventQueue.invokeAndWait(new Runnable() {
			    public void run() {
			    	cw = new ChatWindow(id);
			    }
			} );
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			
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
		this.cw = getWindow();
		//setWindow(new ChatWindow(this));
		
	}
	public static void sendToAll(String Message, String senderid)
	{
		byte[] buf = new byte[1024];
		buf = new String("M|"+senderid+"|"+Message).getBytes();
		
		InetAddress ipall=null;
		try {
			ipall = InetAddress.getByName("255.255.255.255");
		} catch (UnknownHostException e1) {
			// Unable to broadcast - figure out course of action
			e1.printStackTrace();
		}
		DatagramPacket packet = new DatagramPacket(buf, buf.length, ipall, 3333);
		DatagramSocket socket = MainStart.socket;
   		try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public void sendAcceptFile(String filePath, String senderid, int sendPanelId) throws SocketException, IOException
	{
		byte[] buf = new byte[1024];
		buf = new String("R|"+senderid+"|"+sendPanelId+"|"+filePath).getBytes();
		DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, port);
		DatagramSocket socket = MainStart.socket;
   		socket.send(packet);
	}
	public void sendRejectFile(String filePath, String senderid, int sendPanelId) throws SocketException, IOException
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