/*
 * open license stuff.
 * This is the main file which will start the two threads for server and client.
 * Right now, it only starts the server thread
 * */

package serverclient;
import globalfunctions.Contact;
import globalfunctions.IpAddress;

import java.awt.EventQueue;
import java.net.SocketException;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

import Interface.RunInterface;

//import GUIObjects.ChatWindow;


public class Mainstart 
{
	public static HashMap <String,BlockingQueue<Character>> threadsync = new HashMap <String, BlockingQueue<Character>> ();
	public static HashMap <String,Contact> people = new HashMap <String,Contact> ();

	public static String myid=IpAddress.IdentityMac();

	public static void main(String[] args)
    {
		String auth=IpAddress.IdentityMac();
		if (auth==null)
			{
				System.err.print("Network Problems detected!");
				System.exit(0);
			}


		try
		{
			ListenThread L =  new ListenThread(auth, "User");
			ShoutThread S = new ShoutThread(auth, "Shasak");
			new Thread(L).start();
			new Thread(S).start();
			try
	        {
	        	Thread.sleep(6000);
	        }
	        catch(Exception E)
	        {
	        	System.out.print("Wokenup");
	        }

		   for (String key : people.keySet()) {
	            Contact value = (Contact) people.get(key);
	            value.StartChat();
	        }

			/*this is shasak testing something------
			RunInterface R = new RunInterface();
			new Thread(R).start();
			//--------------------------------------*/

			/*final Contact person = (Contact) people.get("78E400ACD134");
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						person.StartChat();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});*/
	   //     SendMessage SM = new SendMessage(person, "This is the message I'm sending to you!!!");
	     //   new Thread(SM).start();


		}
	    catch(SocketException ex)
		{
			System.err.print("Unable to initiate connection: Port maybe in use already");
			System.exit(0);
		}
    }
}