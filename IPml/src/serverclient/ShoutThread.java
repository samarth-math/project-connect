/*
 * This is the client file
 */
package serverclient;
//import globalfunctions.IpAddress;
import java.io.*;
import java.net.*;

public class ShoutThread extends Thread 
{
	protected DatagramSocket socket = new DatagramSocket();
	protected String macadd;
	protected String ipadd;
	
	public ShoutThread(String macadd, String ipadd) throws IOException
	{
		super("ShoutThread");
		// get a datagram socket
		this.macadd=macadd;
		this.ipadd=ipadd;
	}
	
	public void run()
	{  
		
		try
		{
	        // send request
	        byte[] buf = new byte[256];
	        buf= new String("id:"+macadd+System.getProperty("os.name")+InetAddress.getLocalHost().getHostName()).getBytes();
	        InetAddress address = InetAddress.getByName(ipadd);
	        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 2425);
	        socket.send(packet);
	    
	            // get response
	        packet = new DatagramPacket(buf, buf.length);
	        socket.receive(packet);
	
		    // display response
	        String received = new String(packet.getData(), 0, packet.getLength());
	        System.out.println(received);
	        
	        socket.close();
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}	       
    }
}