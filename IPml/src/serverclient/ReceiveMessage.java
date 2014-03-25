package serverclient;

import globalfunctions.Contact;

import java.sql.Timestamp;
import java.net.InetAddress;
import java.util.HashMap;

import GuiElements.ChatWindowPanelReceiver;
import GuiElements.ChatWindowPanelSender;

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
        packdetails[2]=message
        packdetails[3]=threadnumber of sending thread*/
		Thread.currentThread().setName("ReceiveMessageThread");
		Contact person = (Contact) Mainstart.people.get(packdetails[1]);
		ChatWindowPanelReceiver MessagePane = new ChatWindowPanelReceiver(new String(person.getusername()+":"+packdetails[2]), "tsdfhjskdf");
		person.getWindow().chatconsole(MessagePane);
	}
}