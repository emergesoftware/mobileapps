package za.co.emergesoftware.android.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;

public final class DeviceProfile {
	
	/**
	 * Gets the device's IP address from the 
	 * very first network interface with Internet connected.
	 * 
	 * Code changed a little:
	 * Source: http://stackoverflow.com/questions/6064510/how-to-get-ip-address-of-the-device
	 * @return
	 */
	public static String getIPAddress() {
		  try {
			  String ip = null;
			  Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			  
			  while (e.hasMoreElements()) {
		            NetworkInterface network = e.nextElement();
		            Enumeration<InetAddress> addresses = network.getInetAddresses();
		            
		            while (addresses.hasMoreElements()) {
		                InetAddress inetAddress = addresses.nextElement();
		                
		                if (!inetAddress.isLoopbackAddress()) {
		                   ip = inetAddress.getHostAddress();
		                   return ip;
		                }
		            }
		        }
		    } catch (SocketException ex) {
		        ex.printStackTrace();
		    }
		    return "NULL";
	}
	
	/**
	 * Gets the device name / model to be 
	 * considered a user agent name.
	 * 
	 * @return
	 */
	public static String getUserAgent() {
		Locale locale = Locale.getDefault();
		return (android.os.Build.MANUFACTURER + 
				" " + android.os.Build.MODEL).
				toUpperCase(locale);
	}
}
