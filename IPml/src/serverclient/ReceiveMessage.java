package serverclient;

import globalfunctions.Contact;

import java.sql.Timestamp;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import GuiElements.ChatWindowPanelReceiver;
import GuiElements.ChatWindowPanelSender;
import GuiElements.FileTransferPanel;

public class ReceiveMessage implements Runnable 
{
	String packdetails[] = null;
	//InetAddress ip = null;
	HashMap <String, Contact> people = null;
	Timestamp t = null;
	
	public ReceiveMessage(String [] packdetails, InetAddress ip, Timestamp t)
	{
		this.packdetails= packdetails;
		//this.ip = ip;
		this.t=t;
	}
	@Override
	public void run()
	{/*packdetails[1]=mac
        packdetails[2]=threadnumber of sending thread
        packdetails[3]=message/fileheader */
		Thread.currentThread().setName("ReceiveMessageThread");
		Contact person = (Contact) Mainstart.people.get(packdetails[1]);
		if(packdetails[0].equals("M")) {
			ChatWindowPanelReceiver MessagePane = new ChatWindowPanelReceiver(new String(person.getusername()+":"+packdetails[3]), "tsdfhjskdf");
			person.getWindow().chatconsole(MessagePane);
		}
		else if(packdetails[0].equals("S")) {
			String fileP = getFilePath(packdetails[3]);
			System.out.println("Packet is " + packdetails[3] + "FIle path is " + fileP);
			Path filepath = Paths.get(fileP.trim());
			
			
			boolean flag=false;
			FileTransferPanel ftPane = new FileTransferPanel(person,filepath);
			person.getWindow().chatconsole(ftPane);
			String header = packdetails[3];
			String filePath = "";
			System.out.println("Header " + header);
			char fileType = header.charAt(0);
			for(int i=0;i<header.length();i++) {
				if(header.charAt(i)=='-') {
				   flag = true;
				}
				else if(flag==true) {
					filePath = filePath + (char)header.charAt(i);
				}
			}
		
		}
		
	}
	public static void requestFile(char fileType,String filePath, String ipAddress) {
		
		 
   	 byte[] buf = new byte[256];
   	 System.out.println("file path: " + filePath);
   	 
		 buf= new String(fileType+":R-"+filePath).getBytes();
	     InetAddress address = null;
	     
		try {
			address = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	     for(int i=0;i<buf.length;i++) {
	    	 System.out.print((char)buf[i]);
	     }
	     DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 3333);
	     try {
	    	 DatagramSocket multicastSocket = new DatagramSocket();
			 multicastSocket.send(packet);
			 System.out.println("Packet sent ");
			 multicastSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private String getFilePath(String filePath) {
		int i=0;
		while(filePath.charAt(i)!='-') {
			i++;
		}
		i++;
		return filePath.substring(i,filePath.length());
	}
}