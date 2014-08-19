package za.co.emergesoftware.android.eskomapp.entity;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public interface JSONEntity<E> extends Serializable {
	public abstract String toJSON() throws JSONException;
	public abstract E parseFromJSON(JSONObject json) throws JSONException;
	
}
