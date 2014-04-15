/*
 * open license stuff.
 * This is the main file which will start the two threads for server and client.
 * Right now, it only starts the server thread
 * */

package serverclient;
import globalfunctions.Contact;
import java.net.ServerSocket;

import globalfunctions.IpAddress;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import GUIObjects.AppWindow;
import GUIObjects.IPRangeWindow;
import GuiElements.BroadCastFileSend;
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
	public static HashMap <Integer, BroadCastFileSend> broadcastfspanels = new HashMap<Integer, BroadCastFileSend>();
	public static ServerSocket ftpsocket;
	private static ArrayList<String> result;
	
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
			ListenThread L =  new ListenThread(Q);
						
			new Thread(PS).start();
			new Thread(L).start();
			setRanges();
			Shout();
    }
	
	public static void setRanges()
	{
		result = IPRangeWindow.getIPRanges();
	}
	public static void Shout()
	{
		ShoutThread S0 = new ShoutThread();    
		new Thread(S0).start();
		
		if(!result.isEmpty())
		{
			String parts[];
			for(String ipset : result)
			{
				parts = ipset.split("\\|");
				ShoutThread S = new ShoutThread(parts[0],parts[1]);
				new Thread(S).start();
			}
		}
	}
	
}