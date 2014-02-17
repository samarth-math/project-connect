package globalfunctions;
import java.net.*;
import org.apache.commons.net.util.*; // Depends on apache commons-net-3.3 library
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
	
	
 //***checks if the specified ip address falls in the specified network address
	
	public static boolean iprangeverify(String ipadd, String netadd)// Format of netadd : xxx.xxx.xxx.xxx/xx where /xx is network mask
	{
		SubnetUtils utils = new SubnetUtils(netadd);
		return utils.getInfo().isInRange(ipadd);	
	}
}
