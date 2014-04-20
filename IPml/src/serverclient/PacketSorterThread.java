package serverclient;

import fileSending.Client;
import globalfunctions.Contact;

import java.awt.Color;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

import GuiElements.BroadCastFileSend;
import GuiElements.FileTransferPanelS;

public class PacketSorterThread implements Runnable {
	private BlockingQueue<DatagramPacket> bq;
	private DatagramSocket socket;
    private String id;
    private byte[] buf;
    private Color colors[]=new Color[8];
	private Random r = new Random();
	
	PacketSorterThread(BlockingQueue<DatagramPacket> bq)
	{
		this.socket=MainStart.socket;
		this.bq = bq;
        this.id=MainStart.myID;
        defineColors();
	}

	
	@Override
	public void run() 
	{
		Thread.currentThread().setName("Sorting Thread");
		try
		{
			while (true) 
			{
				DatagramPacket p= bq.take();
				PacketSort(p); 
			}
		}
		catch (InterruptedException ex)
		{
			System.out.print("Received Interrupt in PacketSorter");
		}
		
	}
	 private void defineColors()
	 {
		 colors[0]= new Color(0,128,255);
		 colors[1]= new Color(76,0,153);
		 colors[2]= new Color(204,0,0);
		 colors[3]= new Color(162,90,126);
		 colors[4]= new Color(102,0,102);
		 colors[5]= new Color(0,153,0);
		 colors[6]= new Color(51,153,255);
		 colors[7]= new Color(102,255,178);
	 }
	public void PacketSort(DatagramPacket packet)
	{
        try{
	
	        String packdetails[] = new String(packet.getData(), 0, packet.getLength()).split("\\|");//Important part of receiving request. Tool used to parse the request
	        InetAddress address = packet.getAddress();
	        int port = packet.getPort();
	        
	        if(packdetails[0].equals("D"))// && !packdetails[2].equals(MainStart.myID))	// if it's a Detection Packet	                
	        {/* packdetails[0] - if Detection Packet
	             * packdetails[1] - if sent by Server or client
	             * packdetails[2] - Mac Address
	             * packdetails[3] - Operating System
	             * packdetails[4] - HostName
	             * packdetails[5] - Username*/
	
	        	//Save Packet
	        	Contact person = new Contact(packdetails[2], packdetails[3], packdetails[4], packdetails[5], address, port,colors[r.nextInt(8)]);
	        	Contact person1 = MainStart.people.get(packdetails[2]);
	        	if (person1==null || !person1.checkChatWindow())
	        	{
	        		MainStart.people.put(packdetails[2], person);
		        	if(MainStart.mainWindow!=null)
		        	{
			        	MainStart.mainWindow.removeFromList(person1);
			        	MainStart.mainWindow.addnewperson(MainStart.people.get(packdetails[2]));
		        	}
	        	}

	        	if (packdetails[1].equals("C"))// If packet came from client, send it a response
	           	{
	            	// figure out response
	                String PString = new String("D|S|"+id+"|"+System.getProperty("os.name")+"|"+InetAddress.getLocalHost().getHostName()+"|"+MainStart.myUserName);
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
	           packdetails[2]=threadnumber of sending thread
	           packdetails[3]=message*/
	        	Timestamp t =new Timestamp(new Date().getTime());
	        	//Send Acknowledgment
	        	String PString = new String("A|"+packdetails[2]);
	        	buf = PString.getBytes();
	        	packet = new DatagramPacket(buf, buf.length, address, port);
	        	try 
	        	{
	            		socket.send(packet);
				} catch (IOException except)
	            {
	            	System.err.print("Network Problem : Unable to send packets!");
	            }
	        	if (packdetails.length>4)
	        	{
	        		for(int i=4;i<packdetails.length;i++)
	        		{
	        			packdetails[3]+= "|"+packdetails[i];
	        		}
	        	}
	        	ReceiveMessage RM = new ReceiveMessage(packdetails, address, t);
	        	new Thread(RM).start();
	        }
	        else if (packdetails[0].equals("BM"))//Broadcast Message
	        {/*packdetails[1]=mac of person received from
	           packdetails[2]=Message*/
	        	if(!packdetails[1].equals(MainStart.myID))
	        	{
		        	Timestamp t =new Timestamp(new Date().getTime());
		        	if (packdetails.length>3)
		        	{
		        		for(int i=3;i<packdetails.length;i++)
		        		{
		        			packdetails[2]+= "|"+packdetails[i];
		        		}
		        	}
		        	ReceiveMessage RM = new ReceiveMessage(packdetails, address, t);
		        	new Thread(RM).start();
	        	}
	        }
	        else if (packdetails[0].equals("BS"))//Broadcast File
	        {//packdetails[1] = id of person received from
	        // packdetails[2] = sender panel id (to extract filepath from)
	        // packdetails[3] = filesize
	        // packdetails[4] = filename
	        	Timestamp t =new Timestamp(new Date().getTime());
	        	if (packdetails.length>5)
	        	{
	        		for(int i=5;i<packdetails.length;i++)
	        		{
	        			packdetails[4]+= "|"+packdetails[i];
	        		}
	        	}
	        	ReceiveMessage RM = new ReceiveMessage(packdetails, address, t);
	        	new Thread(RM).start();
	        	
	        }
	        else if(packdetails[0].equals("S"))// implies, SendFile  type packet
	        {/*packdetails[1]=mac of person received from
	           packdetails[2]=threadnumber of sending thread
	           packdetails[3]=FileTransferPanelS index
	           packdetails[4]=fileSize
	           packdetails[5]=filename*/
	        	System.out.println("File Size " + packdetails[4]);
	        	Timestamp t =new Timestamp(new Date().getTime());
	        	//Send Acknowledgment
	        	String PString = new String("A|"+packdetails[2]);
	        	buf = PString.getBytes();
	        	packet = new DatagramPacket(buf, buf.length, address, port);
	        	try 
	        	{
	            		socket.send(packet);
				} catch (IOException except)
	            {
	            	System.err.print("Network Problem : Unable to send packets!");
	            }
	        	if (packdetails.length>6)
	        	{
	        		for(int i=6;i<packdetails.length;i++)
	        		{
	        			packdetails[5]+= "|"+packdetails[i];
	        		}
	        	}
	        	ReceiveMessage RM = new ReceiveMessage(packdetails, address, t);
	        	new Thread(RM).start();
	        }
	        else if(packdetails[0].equals("R"))// implies, Accepting File type packet
	        {/*packdetails[1]=mac of person received from
		       packdetails[2]=sendPanelId 
	          */
	        	int sendPId = Integer.parseInt(packdetails[2]);
	        	FileTransferPanelS ftps = MainStart.fileSendPanels.get(sendPId);
	        	ftps.onAcceptance();
	        	Path filepath = ftps.getFilePath();
	        	Client obj = new Client(address.getHostAddress(),port,filepath,ftps);
					(new Thread(obj)).start();
	        	
	        }
	        else if(packdetails[0].equals("BR"))// implies, Accepting File type packet
	        {/*packdetails[1]=mac of person received from
		       packdetails[2]=sendPanelId 
	          */
	        	int sendPId = Integer.parseInt(packdetails[2]);
	        	BroadCastFileSend bcfs = MainStart.broadcastfspanels.get(sendPId);
	        	Path filepath = bcfs.getFilePath();
	        	Client obj = new Client(address.getHostAddress(),port,filepath);
					(new Thread(obj)).start();
	        	
	        }
	        else if (packdetails[0].equals("N"))//Reject File type packet
	        {/*packdetails[1]=mac of person received from
			   packdetails[2]=sendPanelId 
	         */
	        	int sendPId = Integer.parseInt(packdetails[2]);
	        	System.out.println("Value of senderpanel in the received packet "+sendPId);
	        	FileTransferPanelS ftps = MainStart.fileSendPanels.remove(sendPId);
	        	if (ftps!=null)
	        		ftps.onReject();
	        }
	        
	        else if (packdetails[0].equals("A"))// Catching Acknowledgement
	        {/*packdetails[1]=Thread Number */
	        	BlockingQueue<Character> q = (BlockingQueue<Character>) MainStart.threadsync.remove(packdetails[1]);
	            q.add('y');
		    }
		}//end of try
		catch (UnknownHostException e) 
		{
			System.err.print("Unable to find IP of current machine");
		}
	}
}