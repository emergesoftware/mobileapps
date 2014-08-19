package za.co.emergesoftware.android.eskomapp.entity;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import za.co.emergesoftware.android.util.DeviceProfile;

public class LoginRequest implements JSONEntity<LoginRequest>{
	
	private static final long serialVersionUID = 1L;
	
	private User user;
	private Date requestTime;
	private String ipAddress;
	private String agentName;
	private int attempts;
	
	public LoginRequest(User user) {
		attempts = 0;
		this.user = user;
		agentName = DeviceProfile.getUserAgent();
		ipAddress = DeviceProfile.getIPAddress();
	}
	
	public void setLoginAttempts(int attempts) {
		this.attempts = attempts;
	}
	
	private long getRequestTime() {
		requestTime = new Date();
		return requestTime.getTime();
	}

	@Override
	public String toJSON() throws JSONException {

		JSONObject request = new JSONObject();
		request.put("requestTime", getRequestTime());
		request.put("ipAddress", ipAddress);
		request.put("agentName", agentName);
		request.put("attempts", attempts);
		
		JSONArray array01 = new JSONArray();
		JSONObject userDetail = new JSONObject();
		userDetail.put("username", user.getUsername());
		userDetail.put("password", user.getPassword());
		array01.put(userDetail);
		
		request.put("user", array01);
		
		return request.toString();
	}

	@Override
	public LoginRequest parseFromJSON(JSONObject json) throws JSONException {
		
		return null;
	}
	
	
	
}
