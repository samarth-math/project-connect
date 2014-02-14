package serverclient;
import java.io.*;
import java.net.*;
import java.util.*;

public class ListenThread extends Thread
{

    protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected Boolean on = true;
    protected Boolean flag_foundinterface = false;

    public ListenThread() throws IOException
    {
    	this("ListenThread");
    }

    public ListenThread(String name) throws IOException
    {
        super(name);
        socket = new DatagramSocket(2425);
    }

    public void run()
    {

        while (on) 
        {
            try
            {
            	Enumeration<NetworkInterface>  e=NetworkInterface.getNetworkInterfaces();
                while(e.hasMoreElements() && flag_foundinterface==false)
                {
                    NetworkInterface n=(NetworkInterface) e.nextElement();
                    if (!n.getDisplayName().equals("lo"))
                    {
                    	flag_foundinterface=true;// To be repositioned
	                    Enumeration<InetAddress> ee = n.getInetAddresses();
	                    while(ee.hasMoreElements())
	                    {
	                    	
	                    	InetAddress i= (InetAddress) ee.nextElement();	                    	
	                    	System.out.println(i.getHostAddress());
	                    }
                    }
                }
                
                byte[] buf = new byte[256];

                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                
                //print request
                String received = new String(packet.getData(), 0, packet.getLength());
                InetAddress addressofperson = packet.getAddress();
                System.out.println("------------start of packet -------------------\n"+ addressofperson.getHostAddress() + " : " + received + "----------------End of Packet-----------------\n");
                
                // figure out response
                String dString = null;
                dString = new Date().toString();
                buf = dString.getBytes();

		// send the response to the client at "address" and "port"
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
            } 
            catch (IOException e)
            {
            	socket.close();
               e.printStackTrace();
            }
        }
    }
}