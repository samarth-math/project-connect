/*
 * This is the server thread
 */
package serverclient;
import globalfunctions.IpAddress;

import java.io.*;
import java.net.*;
//import java.util.*;

public class ListenThread extends Thread
{

    protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected Boolean on = true;
    

    public ListenThread() throws IOException
    {
    	this("ListenThread");
    }

    public ListenThread(String name) throws IOException
    {
        super(name);
        socket = new DatagramSocket(2425);
    }

    public void run()
    {
    	
    	String [] netinfo = IpAddress.current_Mac_and_IP().split(":");
        while (on) 
	            {
	                try
	                {        
		                byte[] buf = new byte[256];
		
		                // receive request
		                DatagramPacket packet = new DatagramPacket(buf, buf.length);
		                socket.receive(packet);
		                
		                //print request
		                String received = new String(packet.getData(), 0, packet.getLength());
		                InetAddress addressofperson = packet.getAddress();
		                System.out.println("------------start of packet -------------------\n"+ addressofperson.getHostAddress() + " : " + received + "----------------End of Packet-----------------\n");
		                
		                // figure out response
		                String dString = new String("id:"+netinfo[0]+System.getProperty("os.name")+InetAddress.getLocalHost().getHostName());
		                buf = dString.getBytes();
		                //System.out.println(dString+"\n"+buf.length);
		
		                // send the response to the client at "address" and "port"
		                InetAddress address = packet.getAddress();
		                int port = packet.getPort();
		                packet = new DatagramPacket(buf, buf.length, address, port);
		                socket.send(packet);
		            } 
		            catch (IOException except)
		            {
		            	
		            	System.err.print("Given port already in use by another application 2");
		            }
	            }
    }
}