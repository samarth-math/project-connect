package serverclient;

import globalfunctions.Contact;
import java.sql.Timestamp;
import java.net.InetAddress;
import java.util.HashMap;

public class ReceiveMessage implements Runnable 
{
	String packdetails[] = null;
	InetAddress ip = null;
	HashMap <String, Contact> people = null;
	Timestamp t = null;
	
	public ReceiveMessage(String [] packdetails, InetAddress ip, Timestamp t)
	{
		this.packdetails= packdetails;
		this.ip = ip;
		this.t=t;
	}
	@Override
	public void run()
	{
		Thread.currentThread().setName("ReceiveMessageThread");
		Contact person = (Contact) Mainstart.people.get(packdetails[1]);
		System.out.println(String.format("%1$TD %1$TT", t)+person.getusername()+": "+packdetails[2]);
	}
}
