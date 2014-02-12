package client;

import java.io.*;
import java.net.*;

public class QuoteClient extends Thread {
	public static void main(String[] args) throws IOException {

        if (args.length != 1) {
             System.out.println("Usage: java QuoteClient <hostname>");
             return;
        }

            // get a datagram socket
        DatagramSocket socket = new DatagramSocket();

            // send request
        byte[] buf = new byte[256];
        buf = new String ("1:1391423360:SAM-<F07BCB8001D7>:SAM-PC:224395264:BULLSHIT").getBytes();
        
        InetAddress address = InetAddress.getByName(args[0]);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 2425);
        socket.send(packet);
    
            // get response
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

	    // display response
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Quote of the Moment: " + received);
    
        socket.close();
    }
/*
	public QuoteClient()
	{
		try {
			InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			System.err.println("Please check your connection to the local network.");
		}
	}
    public QuoteClient (String startip, String stopip) throws IOException 
    {
    	if (startip==null || stopip==null) {
             System.err.println("Thread called without hosts defined");
             return;
        }

            // get a datagram socket
        DatagramSocket socket = new DatagramSocket();

            // send request
        byte[] buf = new byte[256];
        InetAddress address = InetAddress.getByName(startip);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4447);
        socket.send(packet);
    
            // get response
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

	    // display response
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Response: " + received);
    
        socket.close();
    }
    */
}