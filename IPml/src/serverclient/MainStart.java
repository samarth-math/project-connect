/*
 * open license stuff.
 * This is the main file which will start the two threads for server and client.
 * Right now, it only starts the server thread
 * */

package serverclient;
import globalfunctions.Contact;
import globalfunctions.IpAddress;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import GUIObjects.AppWindow;
import GuiElements.FileTransferPanelS;

public class MainStart 
{
	public static HashMap <String,BlockingQueue<Character>> threadsync = new HashMap <String, BlockingQueue<Character>> ();
	public static HashMap <String,Contact> people = new HashMap <String,Contact> ();
	public static String myID;
	public static String myUserName;
	public static DatagramSocket socket;
	public static BlockingQueue<DatagramPacket> Q;
	public static AppWindow mainWindow;
	public static HashMap <Integer, FileTransferPanelS> fileSendPanels = new HashMap <Integer, FileTransferPanelS>();
	
	public static void main(String[] args)
    {
		
		Q = new ArrayBlockingQueue<DatagramPacket>(15);
		try {
			socket = new DatagramSocket(3333);
		} catch (SocketException e) {
			System.err.print("Unable to initiate connection: Port maybe in use already");
			System.exit(0);
		}
		myID=IpAddress.IdentityMac();
		myUserName = IpAddress.getUserName();
		if (myID==null)
			{
				System.err.print("Network Problems detected!");
				System.exit(0);
			}
		java.awt.EventQueue.invokeLater(new Runnable() {
		    public void run() {
		    	mainWindow=new AppWindow();
		    }
		} );

			PacketSorterThread PS = new PacketSorterThread(Q);
			ShoutThread S = new ShoutThread();//"172.22.30.19", "172.22.30.21");
			ListenThread L =  new ListenThread(Q);
						
			new Thread (PS).start();
			new Thread(L).start();
			new Thread(S).start();
		
			try
	        {
	        	Thread.sleep(3000);
	        }
	        catch(Exception E)
	        {
	        	System.out.print("Wokenup");
	        }
    }
}