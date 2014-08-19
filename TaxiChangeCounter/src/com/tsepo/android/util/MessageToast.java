package com.tsepo.android.util;

import android.content.Context;
import android.widget.Toast;

public final class MessageToast {

	public static void show(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
	
	
}
