/*
 * This is the server thread
 * 
 */
package serverclient;
import java.io.*;
import java.net.*;
import java.util.concurrent.BlockingQueue;

public class ListenThread implements Runnable
{
	private DatagramSocket socket;
	private BlockingQueue<DatagramPacket> Q;

    public ListenThread(BlockingQueue<DatagramPacket> Q)
    {
    	this.socket=MainStart.socket;
    	this.Q=Q;
    }

    @Override
    public void run()
    {
    	Thread.currentThread().setName("ListenThread");
    	
        while (true) 
	            {
        			byte[] buf = new byte[1024];
        			DatagramPacket packet = new DatagramPacket(buf, buf.length);
        			// receive request
		            try {
						socket.receive(packet);
						Q.put(packet);
					} catch (IOException except)
	                {
	                	except.getStackTrace();
	                } catch (InterruptedException e) {
						System.err.println("ListenThread Interrupted");
					}
            }//end of while
	}//end of run
}//end of class