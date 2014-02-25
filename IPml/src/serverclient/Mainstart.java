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
		String auth=IpAddress.IdentityMac();
		if (auth==null)
			throw new IOException("Network Problems detected!");
		
		Set <Contact> people = new HashSet <Contact>(); 
        new ListenThread(auth, people).start();
        new ShoutThread(auth).start();
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        
        Iterator <Contact> C = people.iterator();
        while(C.hasNext())
    	{
        	Contact person = C.next();
        	person.printall();
        }

    }
}