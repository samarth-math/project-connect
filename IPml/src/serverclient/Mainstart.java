/*
 * open license stuff.
 * This is the main file which will start the two threads for server and client.
 * Right now, it only starts the server thread
 * 
 * 
 * 
 * 
 * */

package serverclient;
import java.io.*;


public class Mainstart {
	
	public static void main(String[] args) throws IOException 
    {
        new ListenThread().start();
    }

}