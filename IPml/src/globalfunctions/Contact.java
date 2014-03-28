package globalfunctions;
import java.io.IOException;
import java.net.*;

import serverclient.Mainstart;
import GUIObjects.ChatWindow;

public class Contact {
	private InetAddress ip;
	private int port;
	private String mac;
	private String OS;
	private String Host;
	private String username;
	private ChatWindow cw=null;
	
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
		{java.awt.EventQueue.invokeLater(new Runnable() {
		    public void run() {
		    	cw = new ChatWindow(id);
		    }
		} );
			
			return cw;
		}
	}
	public void setWindowNull()
	{
		cw = null;
	}
	public void StartChat()
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
		DatagramSocket socket = Mainstart.socket;
   		try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void SendMessage(String Message, String senderid) throws SocketException, IOException
	{
		byte[] buf = new byte[1024];
		buf = new String("M|"+senderid+"|"+Message).getBytes();
		
		DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, port);
		DatagramSocket socket = Mainstart.socket;
   		socket.send(packet);
	}
	
	public void SendFile(String header, String senderid) throws SocketException, IOException
	{
		byte[] buf = new byte[1024];
		buf = new String("S|"+senderid+"|"+header).getBytes();
		System.out.println("Send File " + new String(buf,"UTF8"));
		DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, port);
		DatagramSocket socket = Mainstart.socket;
   		socket.send(packet);
	}
	public void SendReceiveFile(String filePath, String senderid) throws SocketException, IOException
	{
		byte[] buf = new byte[1024];
		buf = new String("R|"+senderid+"|"+filePath).getBytes();
		DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, port);
		DatagramSocket socket = Mainstart.socket;
   		socket.send(packet);
	}
	public String getid()
	{
		return mac;
	}
	public String getOSname()
	{
		return OS;
	}
	public String getHostname()
	{
		return Host;
	}
	public String getusername()
	{
		return username;
	}
	public InetAddress getip()
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