package globalfunctions;
import java.net.*;

public class IpAddress 
{
	public static long ipToLong(InetAddress ip) 
	{
        byte[] octets = ip.getAddress();
        long result = 0;
        for (byte octet : octets) 
        {
            result <<= 8;
            result |= octet & 0xff;
        }
        return result;
	}
}
