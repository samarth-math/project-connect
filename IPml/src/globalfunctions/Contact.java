package globalfunctions;
import java.io.IOException;
//import java.io.*;
import java.net.*;

import GUIObjects.ChatWindow;

public class Contact {
	
	private InetAddress ip;
	private String mac;
	private String OS;
	private String Host;
	private String username;
	private ChatWindow cw=null;
	
	public Contact(String mac, String OS, String Host, String username, InetAddress ip)
	{
		this.mac = mac;
		this.Host = Host;
		this.OS = OS;
		this.username = username;
		this.ip=ip;
	}
	private void setWindow(ChatWindow cw)
	{
		this.cw=cw;
	}
	public ChatWindow getWindow()
	{
		if(cw!=null)
			return cw;
		else
		{
			setWindow(new ChatWindow(this));
			return cw;
		}
	}
	public void StartChat()
	{
		setWindow(new ChatWindow(this));
	}
	public void SendMessage(String Message, String senderid) throws SocketException, IOException
	{
		byte[] buf = new byte[256];
		buf = new String("M:"+senderid+":"+Message).getBytes();
		DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, 3333);
		DatagramSocket socket = new DatagramSocket();
		socket.send(packet);
		socket.close();
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