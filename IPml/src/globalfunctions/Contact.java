package globalfunctions;
import java.io.IOException;
//import java.io.*;
import java.net.*;

public class Contact {
	
	protected InetAddress ip;
	protected String mac;
	protected String OS;
	protected String Host;
	protected String username;
	
	public Contact(String mac, String OS, String Host, String username, InetAddress ip)
	{
		this.mac = mac;
		this.Host = Host;
		this.OS = OS;
		this.username = username;
		this.ip=ip;
	}
	
	public void SendMessage(String Message) throws SocketException, IOException
	{
		byte[] buf = new byte[256];
		buf = new String("M:"+mac+":"+Message).getBytes();
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
	public String compareablestring()
	{
		return new String(mac+":"+OS+":"+Host+":"+"username"+":"+ip.getHostAddress());
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