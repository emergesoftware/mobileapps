package com.tsepo.android.ui.dialog;

//import com.tsepo.android.taxichange.R;

import com.tsepo.android.taxichange.R;
import com.tsepo.android.taxichange.app.settings.AppSettings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public final class DefaultAlertDialog {
	
	private static AlertDialog.Builder dialog = null;
	
	public static void showOKDialog(Context context, String title, String message) {
		
		context.setTheme(AppSettings.APPLICATION_THEME);
		
		dialog = new AlertDialog.Builder(context);
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setIcon(R.drawable.ic_launcher);
		
		dialog.setPositiveButton("OK", new OnClickListener() {

			@Override
			public void onClick(DialogInterface d, int arg1) {
				//do nothing for now
			}});
		
		dialog.show();
		
		
	}
	
}
