package za.co.emergesoftware.android.eskomapp.webservice;

import android.content.Context;

public interface WebClientEventListener {
	public void onSuccessful(Context ctx, WebClientEvent e);
	public void onFailure(Context ctx, WebClientEvent e);
}
