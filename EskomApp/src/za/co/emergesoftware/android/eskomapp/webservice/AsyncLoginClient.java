package za.co.emergesoftware.android.eskomapp.webservice;

import android.content.Context;
import za.co.emergesoftware.android.eskomapp.entity.LoginRequest;

public class AsyncLoginClient extends HttpWebClient {
		
	public static final String RELATIVE_PATH = "account/authenticate";
	
	private AsyncLoginClient(){
		super();
	}
	
	public AsyncLoginClient(Context context) {
		this();
		AsyncLoginClient.context = context;
	}
	
	/**
	 * Authenticates a user
	 *  
	 * @param user
	 */
	public void authenticateUser(LoginRequest request) {
		try {
			setRequestBody(request.toJSON()); 
			connect(POST, getAbsoluteUrl(RELATIVE_PATH));
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
