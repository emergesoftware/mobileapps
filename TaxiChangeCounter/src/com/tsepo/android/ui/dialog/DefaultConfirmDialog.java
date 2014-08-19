package com.tsepo.android.ui.dialog;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.tsepo.android.taxichange.R;
import com.tsepo.android.taxichange.events.DialogConfirmationListener;
import com.tsepo.android.taxichange.model.Confirmation;

public class DefaultConfirmDialog {
	
	private AlertDialog.Builder dialog;
	private Confirmation confirmation;
	
	private ArrayList<DialogConfirmationListener> listeners;
	
	/**
	 * Creates an instance of the DefaultConfirmDialog
	 * object
	 * 
	 * @param context
	 */
	public DefaultConfirmDialog() {
		
		listeners = new ArrayList<DialogConfirmationListener>();
		dialog = null;
		
	}
	
	/**
	 * Shows the confirm dialog
	 * 
	 * @param context
	 * @param title
	 * @param message
	 */
	public void showConfirmDialog(Context context, String title, String message) {
		
		dialog = new AlertDialog.Builder(context);
		
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setIcon(R.drawable.ic_launcher);
		
		dialog.setPositiveButton("Yes", new OnClickListener() {

			@Override
			public void onClick(DialogInterface d, int arg1) {
				
				confirmation = Confirmation.Yes;
				notifyConfirmationReceived();
				
			}});
		
		dialog.setNegativeButton("No", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
				confirmation = Confirmation.No;
				notifyConfirmationReceived();
				
			}
		});
		
		dialog.show(); 

	}
	
	/**
	 * Adds an event listener to listen for the
	 * response of the user from the confirm dialog
	 * 
	 * @param l
	 */
	public void addConfirmationReceivedListener(DialogConfirmationListener l) {
		if (!listeners.contains(l))
			listeners.add(l);
	}
	
	/**
	 * Notifies and triggers an event once confirmation has been
	 * received from user
	 * 
	 */
	private void notifyConfirmationReceived() {
		
		if (confirmation == null)
			confirmation = Confirmation.No;
		
		if (!listeners.isEmpty()) {
			
			for (DialogConfirmationListener listener : listeners) {
				listener.confirmationReceived(null, confirmation);
			}
			
		}
	}
}
