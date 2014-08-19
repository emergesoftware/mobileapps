package za.co.emergesoftware.android.eskomapp.settings;

import za.co.emergesoftware.android.eskomapp.entity.User;

public final class AppSettings {
	
	// the logged in user
	private static User currentUser = null;

	public static User getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(User currentUser) {
		AppSettings.currentUser = currentUser;
	}
	
	
}
