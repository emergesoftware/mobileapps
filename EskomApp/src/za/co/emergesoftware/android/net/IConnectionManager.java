package za.co.emergesoftware.android.net;

import android.content.Context;

public interface IConnectionManager {
	
	/**
	 * Determines if there is any network connection
	 * @param context
	 * @return
	 */
	public boolean isNetworkConnectionAvailable(Context context);
	
	/**
	 * Determines if the network type is WI-FI
	 * @param context
	 * @return
	 */
	public boolean isNetworkWiFiType(Context context);
	
	/**
	 * Determines if the network type is MOBILE DATA
	 * @param context
	 * @return
	 */
	public boolean isNetworkMobileDataType(Context context);
	
}
