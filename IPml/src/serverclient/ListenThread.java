/*
 * This is the server thread
 * 
 */
package serverclient;
import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

import globalfunctions.Contact;

public class ListenThread implements Runnable
{
	private DatagramSocket socket;
    protected Boolean on = true;
    protected String id;
    protected BlockingQueue <Character> q;
    protected String user;
    

    public ListenThread(String macadd, String user)
    {
    	this.socket=socket;
        this.id=macadd;
        this.user = user;
    }

    @Override
    public void run()
    {
    	Thread.currentThread().setName("ListenThread");
    	
        while (on) 
	            {
        			
        			byte[] buf = new byte[256];
		            try{
				            // receive request
				            DatagramPacket packet = new DatagramPacket(buf, buf.length);
				            socket.receive(packet);
					        
				            String packdetails[] = new String(packet.getData(), 0, packet.getLength()).split(":");//Important part of receiving request. Tool used to parse the request
			                InetAddress address = packet.getAddress();
			                int port = packet.getPort();
			                
			                if(packdetails[0].equals("D"))	// if it's a Detection Packet	                
			                {/* packdetails[0] - if Detection Packet
				                 * packdetails[1] - if sent by Server or client
				                 * packdetails[2] - Mac Address
				                 * packdetails[3] - Operating System
				                 * packdetails[4] - HostName
				                 * packdetails[5] - Username*/
	
			                	
			                	//Save Packet
			                	Contact person = new Contact(packdetails[2], packdetails[3], packdetails[4], packdetails[5], address, port);
			                	Mainstart.people.put(packdetails[2],person);
			                	
			                	if (packdetails[1].equals("C"))// If packet came from client, send it a response
			                   	{
				                	// figure out response
				                    String PString = new String("D:S:"+id+":"+System.getProperty("os.name")+":"+InetAddress.getLocalHost().getHostName()+":"+user);
				                    buf = PString.getBytes();		
					                // send the response to the client at "address" and "port"
					                packet = new DatagramPacket(buf, buf.length, address, port);
					                socket.send(packet);
			                   	}// end of small if
			                }//end of big if
			                else if(packdetails[0].equals("M"))// implies, Message type packet
			                {/*packdetails[1]=mac of person received from
			                   packdetails[2]=message
			                   packdetails[3]=threadnumber of sending thread*/
			                	Timestamp t =new Timestamp(new Date().getTime());
			                	//Send Acknowledgement
			                	String PString = new String("A:"+packdetails[3]);
			                	buf = PString.getBytes();
			                	packet = new DatagramPacket(buf, buf.length, address, port);
			                	socket.send(packet);
			                	ReceiveMessage RM = new ReceiveMessage(packdetails, address, t);
			                	new Thread(RM).start();
			                }
			                else// Catching Acknowledgement
			                {/*packdetails[1]=Thread Number */
			                	BlockingQueue<Character> q = (BlockingQueue<Character>) Mainstart.threadsync.get(packdetails[1]);
				                q.add('y');
				    	    }
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