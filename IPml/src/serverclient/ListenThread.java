/*
 * This is the server thread
 * 
 */
package serverclient;
import java.io.*;
import java.net.*;

public class ListenThread extends Thread
{

    protected DatagramSocket socket = null;
    protected Boolean on = true;
    protected String macadd;
    protected int portnumber;
    

    public ListenThread(String macadd, int portnumber) throws IOException
    {
    	super("ListenThread");
    	this.portnumber=portnumber;
    	socket = new DatagramSocket(portnumber);
        this.macadd=macadd;
    }
    public ListenThread(String macadd) throws IOException
    {
    	super("ListenThread");
    	this.portnumber=3333;
        socket = new DatagramSocket(portnumber);
        this.macadd=macadd;
    }

    public void run()
    {
    	
    	
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
		                System.out.println("-Packet from ListenThread--\n"+ addressofperson.getHostAddress() + " : " + received + "----------------End of Packet-----------------\n");
		                
		                // figure out response
		                String dString = new String("id:"+macadd+System.getProperty("os.name")+InetAddress.getLocalHost().getHostName());
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