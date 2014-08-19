package za.co.emergesoftware.android.util;

import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;

public final class NavigationService {
	
	/**
	 * Starts navigation to the next Intent (Activity)
	 * 
	 */
	public static void navigate(Activity context, Class<?> newIntent, 
			boolean retainInStack) {
		
		Intent intent = new Intent(context, newIntent);
		
		if (retainInStack) {
			intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
		}
		else {
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		}
		
		context.startActivity(intent);
	}
	
	/**
	 * Starts navigation to the next Intent (Activity)
	 *
	 */
	public static void navigate(Activity context, Class<?> newIntent,
			boolean retainInStack, Map<String, Object> parameterMap) {
		
		Intent intent = new Intent(context, newIntent);
		
		if (retainInStack) {
			intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
		}
		
		else {
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		}
		
		if (parameterMap != null) {
			
			Set<String> keys = parameterMap.keySet();
			
			for (String key : keys) {
				
				Object value = parameterMap.get(key);
				if (value == null)
					continue;
				
				if (value instanceof String)
					intent.putExtra(key, (String)value);
				
				else if (value instanceof Integer)
					intent.putExtra(key, (Integer)value);
				
				else if (value instanceof Double)
					intent.putExtra(key, (Double)value);
				
				else if (value instanceof Character)
					intent.putExtra(key, (Character)value);
				
				else if (value instanceof Long)
					intent.putExtra(key, (Long)value);
				
				else if (value instanceof Boolean)
					intent.putExtra(key, (Boolean)value);
				
			}
		}
		
		context.startActivity(intent);
	}
	
}
