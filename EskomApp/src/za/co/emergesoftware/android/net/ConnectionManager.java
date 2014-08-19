package za.co.emergesoftware.android.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionManager implements IConnectionManager {

	private ConnectivityManager connection;
	private NetworkInfo networkInfo;
	
	public ConnectionManager() {
		this.connection = null;
		this.networkInfo = null;
	}
	
	/**
	 * Creates an instance of the connectivity manager
	 * 
	 * @param context
	 * @return
	 */
	private ConnectivityManager createConnectivityManagerInstance(Context context) {
		return (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}
	
	@Override
	public boolean isNetworkConnectionAvailable(Context context) {
		
		connection = createConnectivityManagerInstance(context);
		networkInfo = connection.getActiveNetworkInfo();
		
		if (networkInfo != null && networkInfo.isConnectedOrConnecting())
			return true;
		
		return false;
		
	}

	@Override
	public boolean isNetworkWiFiType(Context context) {
		
		connection = createConnectivityManagerInstance(context);
		networkInfo = connection.getActiveNetworkInfo();
	
		if (networkInfo == null)
			return false;
		else
			return (networkInfo.getType() == ConnectivityManager.TYPE_WIFI);
	}

	@Override
	public boolean isNetworkMobileDataType(Context context) {
		
		connection = createConnectivityManagerInstance(context);
		networkInfo = connection.getActiveNetworkInfo();
		
		if (networkInfo == null)
			return false;
		else
			return (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE);
	}

}
