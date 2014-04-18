/*
 * This is the global functions file
 */

package globalfunctions;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Enumeration;

import javax.swing.JOptionPane;

import serverclient.MainStart;
import GUIObjects.usernameDialog;

//import org.apache.commons.net.util.*; // Depends on apache commons-net-3.3 library
public class IpAddress 
{
	public static long ipToLong(InetAddress ip) //Converts IP address to a long integer in order to make comparisons
	{
        byte[] octets = ip.getAddress();
        long result = 0;
        for (byte octet : octets) // for a variable octet defined by each element in the array octets
        {
            result <<= 8;//left shift by 8 (due to 8 bytes)
            result |= octet & 0xff;// Or the byte with the octet anded with the number 1 (0xff is hexadecimal for 1)
        }
        return result;
	}
	
	public static InetAddress LongToip(long ip) throws UnknownHostException
	{
		 ByteBuffer buffer = ByteBuffer.allocate(8);
		    buffer.putLong(ip);
		    
		  byte[] octets = new byte[4];
		  
		  for(int i =3; i>=0;  i--)
		  {
			  octets[i] |= ip & 0xff;
			  ip >>>= 8;
		  }
		  
		    return InetAddress.getByAddress(octets);
		
	}
	
	
 //***checks if the specified ip address falls in the specified network address
	
	/*public static boolean iprangeverify(String ipadd, String netadd) throws IllegalArgumentException // Format of netadd : xxx.xxx.xxx.xxx/xx where /xx is network mask
	{
		SubnetUtils utils = new SubnetUtils(netadd);
		return utils.getInfo().isInRange(ipadd);	
	}*/
	
	public static String findmac(NetworkInterface n) throws SocketException // Gives mac address of specific network interface
	{
		byte [] mac = n.getHardwareAddress();
    	StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) 
		{
			sb.append(String.format("%02X", mac[i]));		
		}
		return sb.toString();
	}
	
	public static String IdentityMac() //Returns the Mac address to be used as Identity
	{
		File path = new File(MainStart.rootpath);
		path.mkdirs();
		File authfile = new File(path,"auth.bin");
		String mac=null;
		try{
			if (authfile.exists())
			{
				byte buffer[]= new byte[12];
				FileInputStream fis = new FileInputStream(authfile);
				while(fis.read(buffer)!=-1)
					mac=new String(buffer);
				fis.close();
				return mac;
			}
			else
			{
				
					Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
					while(e.hasMoreElements())
				    {
			            NetworkInterface n=(NetworkInterface) e.nextElement();
			            if (!n.isLoopback() && n.isUp())
			            {
			            	mac = new String(IpAddress.findmac(n));
			            	FileOutputStream fos = new FileOutputStream(authfile);
			    			fos.write(mac.getBytes());
			    			fos.close();
			            	return mac;
			            }       		
				    }
				}
			}
			catch(FileNotFoundException fnfe)
			{
				System.err.println("File not Found");
			}
			catch(SecurityException noperm)
			{
				System.err.println("No permission to read/write to file");
			}
			catch (SocketException soc)
			{
				JOptionPane.showMessageDialog(null,"You're not connected to any network","The Three Musketeers say",JOptionPane.ERROR_MESSAGE);
			}
			catch(IOException ioe)
			{
				System.err.println("Some input/output error occured");
			}
		return mac;
	}	
	
	public static String getUserName()
	{
		String username = null;
		File path = new File(MainStart.rootpath);
		File namefile = new File(path,"username");
		try
		{
				if (namefile.exists())
				{
					byte buffer[]= new byte[20];
					FileInputStream fis = new FileInputStream(namefile);
					while(fis.read(buffer)!=-1)
						username=new String(buffer).trim();
					fis.close();
					return username;
					
				}
				else
				{
					usernameDialog user = new usernameDialog();
					username = user.getusername();
					FileOutputStream fos = new FileOutputStream(namefile);
	    			fos.write(username.getBytes());
	    			fos.close();
					return username;
				}
		}
		catch(FileNotFoundException f)
		{
			return null;
		}
		catch(IOException io)
		{
			return null;
		}
		
	}

	
	/*
	public static String current_Mac_and_IP()// returns ipadd:mac address ###Split returned string using### String [] netinfo = IpAddress.current_Mac_and_IP().split(":"); netinfo[0] = mac and netinfo[1] = ip 
	{
		boolean flag_foundinterface = false;
		String mac = null;
		String ipadd=null;
		try 
    	{
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while(e.hasMoreElements() && flag_foundinterface==false)
		    {
	            NetworkInterface n=(NetworkInterface) e.nextElement();
	            if (!n.isLoopback() && n.isUp())
	            {
	            	mac = new String(IpAddress.findmac(n));
	            	Enumeration<InetAddress> ee = n.getInetAddresses();
	                while(ee.hasMoreElements())
	                {
	                	InetAddress i= (InetAddress) ee.nextElement();
	                	boolean isIPv6 = i instanceof Inet6Address;
	                	if (!isIPv6)
	                	{
	                		flag_foundinterface=true;
	                		ipadd= i.getHostAddress();
	            			return mac+":"+ipadd;
	                	}
	                		
	                }
	            }
		    }
			return "You have reached a part of the code that you were never meant to see. I'd recommend you to restart this app, or try to contact us online";
			}
		catch (SocketException e) 
		{
			return "Error creating socket";
		}
	}	
	*/
}