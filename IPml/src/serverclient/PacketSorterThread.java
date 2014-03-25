package serverclient;

import fileSending.Client;
import globalfunctions.Contact;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

public class PacketSorterThread implements Runnable {
	private BlockingQueue<DatagramPacket> BQ;
	private DatagramSocket socket;
    private String id;
    //protected BlockingQueue <Character> q;
    private String user;
    private byte[] buf;
	
	PacketSorterThread(BlockingQueue<DatagramPacket> BQ)
	{
		this.socket=Mainstart.socket;
		this.BQ = BQ;
        this.id=Mainstart.myid;
        this.user = Mainstart.myusername;
		
	}

	
	@Override
	public void run() 
	{
		Thread.currentThread().setName("Sorting Thread");
		try
		{
			while (true) 
			{
				DatagramPacket p= BQ.take();
				PacketSort(p); 
			}
		}
		catch (InterruptedException ex)
		{
			System.out.print("Received Interrupt in PacketSorter");
		}
		
	}
	public void PacketSort(DatagramPacket packet)
	{
        try{
	
	        String packdetails[] = new String(packet.getData(), 0, packet.getLength()).split(":");//Important part of receiving request. Tool used to parse the request
	        InetAddress address = packet.getAddress();
	        int port = packet.getPort();
	        System.out.println("Packet Sort " + packdetails[0]);
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
	                try 
	                {
	                		socket.send(packet);
					} catch (IOException except)
	                {
	                	System.err.print("Network Problem : Unable to send packets to client!");
	                }
	           	}// end of small if
	        }//end of big if
	        else if(packdetails[0].equals("M"))// implies, Message type packet
	        {/*packdetails[1]=mac of person received from
	           packdetails[2]=message
	           packdetails[3]=threadnumber of sending thread*/
	        	Timestamp t =new Timestamp(new Date().getTime());
	        	//Send Acknowledgment
	        	String PString = new String("A:"+packdetails[3]);
	        	buf = PString.getBytes();
	        	packet = new DatagramPacket(buf, buf.length, address, port);
	        	try 
	        	{
	            		socket.send(packet);
				} catch (IOException except)
	            {
	            	System.err.print("Network Problem : Unable to send packets!");
	            }
	        	ReceiveMessage RM = new ReceiveMessage(packdetails, address, t);
	        	new Thread(RM).start();
	        }
	        else if(packdetails[0].equals("S"))// implies, SendFile  type packet
	        {/*packdetails[1]=mac of person received from
	           packdetails[2]=file header
	           packdetails[3]=threadnumber of sending thread*/
	        	Timestamp t =new Timestamp(new Date().getTime());
	        	//Send Acknowledgment
	        	String PString = new String("A:"+packdetails[3]);
	        	buf = PString.getBytes();
	        	packet = new DatagramPacket(buf, buf.length, address, port);
	        	try 
	        	{
	            		socket.send(packet);
				} catch (IOException except)
	            {
	            	System.err.print("Network Problem : Unable to send packets!");
	            }
	        	ReceiveMessage RM = new ReceiveMessage(packdetails, address, t);
	        	new Thread(RM).start();
	        }
	        else if(packdetails[0].equals("R"))// implies, Message type packet
	        {/*packdetails[1]=mac of person received from
	           packdetails[2]=file path
	          */
	        	System.out.println("I am calling Client :) ");
	        	Client obj = new Client(address.getHostAddress(),6666,packdetails[2]);
					(new Thread(obj)).start();
	        	
	        }
	        else if (packdetails[0].equals("A"))// Catching Acknowledgement
	        {/*packdetails[1]=Thread Number */
	        	BlockingQueue<Character> q = (BlockingQueue<Character>) Mainstart.threadsync.get(packdetails[1]);
	            q.add('y');
		    }
		}//end of try
		catch (UnknownHostException e) 
		{
			System.err.print("Unable to find IP of current machine");
		}
	}
}