/*
 * open license stuff.
 * This is the main file which will start the two threads for server and client.
 * Right now, it only starts the server thread
 * */

package serverclient;
import globalfunctions.IpAddress;

import java.io.*;


public class Mainstart {
	
	public static void main(String[] args) throws IOException 
    {
		String [] netinfo = IpAddress.current_Mac_and_IP().split(":");
        new ListenThread(netinfo[0]).start();
        new ShoutThread(netinfo[0]).start();
    }
}