package za.co.emergesoftware.android.ui.dialog;

import za.co.emergesoftware.android.eskomapp.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public final class MessageDialog {
	
	private static AlertDialog.Builder dialog = null;
	
	public static final int TYPE_STANDARD_MESSAGE = 1;
	public static final int TYPE_WARNING_MESSAGE = 2;
	public static final int TYPE_ERROR_MESSAGE = 3;
	
	private static final int standardIcon = R.drawable.ic_action_accept;
	private static final int warningIcon = R.drawable.ic_action_warning;
	private static final int errorIcon = R.drawable.ic_action_error;
	
	/**
	 * Shows the alert dialog with the specified title,
	 * message and alert type.
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @param dialogType
	 */
	public static void show(Context context, String title, String message, int dialogType) {
		
		dialog = new AlertDialog.Builder(context);
		dialog.setMessage(message);
		dialog.setTitle(title);
		
		switch (dialogType) {
			case TYPE_STANDARD_MESSAGE:
				dialog.setIcon(standardIcon);
				break;
				
			case TYPE_WARNING_MESSAGE:
				dialog.setIcon(warningIcon);
				break;
				
			case TYPE_ERROR_MESSAGE:
				dialog.setIcon(errorIcon);
				break;
				
			default:
				dialog.setIcon(standardIcon);
		}
		
		dialog.setPositiveButton("Close", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		
		dialog.show();
	}
}
