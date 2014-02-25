package globalfunctions;
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
		System.out.print(mac+"\n"+OS+"\n"+Host+"\n"+username+"\n"+ip.getHostAddress());
	}

}