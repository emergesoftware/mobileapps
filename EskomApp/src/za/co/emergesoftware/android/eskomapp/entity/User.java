package za.co.emergesoftware.android.eskomapp.entity;

import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class User implements JSONEntity<User> {
	
	private static final long serialVersionUID = 1L;
	
	private int userId;
	private String username;
	private String password;
	private boolean isActive;
	private Date dateAdded;
	private boolean isCompany;
	private boolean isIndividual;
	private Date dateBlocked;
	
	protected User() {
		
		this.userId = 0;
		this.username = null;
		this.password = null;
		this.isActive = true;
		this.isCompany = false;
		this.isIndividual = true;
		this.dateAdded = null;
		this.dateBlocked = null;
	}
	
	public User(int userId) {
		this();
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public boolean isCompany() {
		return isCompany;
	}

	public void setCompany(boolean isCompany) {
		this.isCompany = isCompany;
	}

	public boolean isIndividual() {
		return isIndividual;
	}

	public void setIndividual(boolean isIndividual) {
		this.isIndividual = isIndividual;
	}

	public Date getDateBlocked() {
		return dateBlocked;
	}

	public void setDateBlocked(Date dateBlocked) {
		this.dateBlocked = dateBlocked;
	}

	public int getUserId() {
		return userId;
	}

	@Override
	public String toJSON() throws JSONException {
		
		JSONObject json = new JSONObject();
			
		json.put("username", getUsername());
		json.put("userId", getUserId());
		json.put("isActive", isActive());
		
		long dateAddedTime = 0;
		if (getDateAdded() != null)
			dateAddedTime = getDateAdded().getTime();
		
		json.put("dateAdded", dateAddedTime);
		json.put("isIndividual", isIndividual());
		json.put("isCompany", isCompany());
		
		long dateBlockedTime = 0;
		if (getDateBlocked() != null)
			dateBlockedTime = getDateBlocked().getTime();
		
		json.put("dateBlocked", dateBlockedTime);
			
		return json.toString();
	}

	@Override
	public User parseFromJSON(JSONObject json) throws JSONException {
		if (json == null)
			return null;
		else {
			
			this.userId = json.getInt("userId");
			this.username = json.getString("username");
			this.isActive = json.getBoolean("isActive");
			this.dateAdded = new Date(json.getLong("dateAdded"));
			this.isCompany = json.getBoolean("isCompany");
			this.isIndividual = json.getBoolean("isIndividual");
			this.dateBlocked = new Date(json.getLong("dateBlocked")); 
			
		}
		
		return this;
	}
	
}
