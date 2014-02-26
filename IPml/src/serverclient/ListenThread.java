/*
 * This is the server thread
 * 
 */
package serverclient;
import java.io.*;
import java.util.*;
import java.net.*;

import globalfunctions.Contact;

public class ListenThread extends Thread
{

    protected DatagramSocket socket = null;
    protected Boolean on = true;
    protected String macadd;
    protected int portnumber;
    protected Set <Contact> people = null;
    

    public ListenThread(String macadd, Set <Contact>people, int portnumber) throws SocketException
    {
    	super("ListenThread");
    	this.portnumber=portnumber;
    	this.socket = new DatagramSocket(portnumber);
        this.macadd=macadd;
        this.people = people;
    }
    public ListenThread(String macadd, Set <Contact>people) throws SocketException
    {
    	super("ListenThread");
    	this.portnumber=3333;
        this.socket = new DatagramSocket(portnumber);
        this.macadd=macadd;
        this.people = people;
    }

    public void run()
    {
    	
    	
        while (on) 
	            {
        			
        			byte[] buf = new byte[256];
		            try{
				            // receive request
				            DatagramPacket packet = new DatagramPacket(buf, buf.length);
				            socket.receive(packet);
					        
				            String packdetails[] = new String(packet.getData(), 0, packet.getLength()).split(":");//Important part of receiving request. Tool used to parse the request
			                InetAddress address = packet.getAddress();
			                
			                if(packdetails[0].equals("D"))	// if it's a Detection Packet	                
			                {/* packdetails[0] - if Detection Packet
				                 * packdetails[1] - if sent by Server or client
				                 * packdetails[2] - Mac Address
				                 * packdetails[3] - Operating System
				                 * packdetails[4] - HostName
				                 * packdetails[5] - Username*/
	
			                	
			                	//Save Packet
			                	Contact person = new Contact(packdetails[2], packdetails[3], packdetails[4], packdetails[5], address);
			                	people.add(person);
			                	
			                	if (packdetails[1].equals("C"))// If packet came from client, send it a response
			                   	{
				                	// figure out response
				                    String dString = new String("D:S:"+macadd+":"+System.getProperty("os.name")+":"+InetAddress.getLocalHost().getHostName()+":username");
				                    buf = dString.getBytes();		
					                // send the response to the client at "address" and "portnumber"
	
					                packet = new DatagramPacket(buf, buf.length, address, portnumber);
					               
					                socket.send(packet);
			                   	}// end of small if
			                }//end of big if
		            	}//end of try
		            	catch (UnknownHostException e) 
		        		{
		        			System.err.print("Unable to find IP of current machine");
		        		}
		        		catch (IOException except)
		                {
		                	System.err.print("Network Problem : Unable to send packets!");
		                }
	            }//end of while
    	}//end of run
}//end of class