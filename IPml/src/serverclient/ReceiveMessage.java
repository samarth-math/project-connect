package serverclient;

import globalfunctions.Contact;

import java.net.InetAddress;
import java.util.HashMap;

public class ReceiveMessage extends Thread 
{
	String packdetails[] = null;
	InetAddress ip = null;
	HashMap <String, Contact> people = null;
	public ReceiveMessage(String [] packdetails, InetAddress ip, HashMap <String,Contact> people)
	{
		this.packdetails= packdetails;
		this.ip = ip;
		this.people = people;
	}
	
	public void run()
	{
		Contact person = (Contact) people.get(packdetails[1]);
		System.out.print(person.getusername()+": "+packdetails[2]);
	}
}
