/*
 * open license stuff.
 * This is the main file which will start the two threads for server and client.
 * Right now, it only starts the server thread
 * */

package serverclient;
import globalfunctions.Contact;
import globalfunctions.IpAddress;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class Mainstart {
	
	public static void main(String[] args) throws IOException 
    {
		String [] netinfo = IpAddress.current_Mac_and_IP().split(":");
		Set <Contact> people = new HashSet<Contact>();
        new ListenThread(netinfo[0], people).start();
        new ShoutThread(netinfo[0]).start();
        Iterator <Contact> C = people.iterator();
        while(C.hasNext())
        {
        	Contact person = C.next();
        	person.printall();
        }
    }
}