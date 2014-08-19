package com.tsepo.android.ui.dialog;

import java.util.ArrayList;

import com.tsepo.android.collection.AllCollections;
import com.tsepo.android.taxichange.R;
import com.tsepo.android.taxichange.events.EditCollectionEvent;
import com.tsepo.android.taxichange.events.EditCollectionListener;
import com.tsepo.android.util.NumericUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class EditCollectionDialog {
	
	private AlertDialog.Builder dialog;
	
	/** event listeners **/
	private ArrayList<EditCollectionListener> editCollectionListeners;
	
	/** UI elements **/
	private EditText amountBroughtForwardEditText;
	private EditText numberOfPeopleEditText;
	
	/** local variables **/
	private double amount;
	private int passengers;
	
	/** constants **/
	private static final String DIALOG_TITLE = "Edit Collection";
	private static final String POSITIVE_BUTTON_TEXT = "Edit";
	private static final String NEGATIVE_BUTTON_TEXT = "Cancel";
	
	/**
	 * Creates an instance of the edit collection
	 * dialog
	 * 
	 */
	public EditCollectionDialog(double amount, int passengers) {
		this.amount = amount;
		this.passengers = passengers;
		
		dialog = null;
		editCollectionListeners = new ArrayList<EditCollectionListener>();
	}
	
	
	/**
	 * Adds a new instance of the edit collection complete listener
	 * 
	 * @param listener
	 */
	public void addEditCollectionListener(EditCollectionListener listener) {
		
		if (!editCollectionListeners.contains(listener))
			editCollectionListeners.add(listener);
	}

	/**
	 * Triggers an event once the editing of the collection has been completed
	 * 
	 * @param amount
	 * @param passengers
	 */
	private void notifyEditCollectionCompleted(Context context, 
			double amount, int passengers) {
		
		EditCollectionEvent event = new EditCollectionEvent(amount, passengers);
		for (EditCollectionListener listener : editCollectionListeners)
			listener.editCollectionCompleted(context, event);
	}
	
	/**
	 * Shows the dialog for adding new collection
	 * 
	 * @param context
	 */
	public void showDialog(Context context) {
		
		context.setTheme(R.style.AppTheme);
		
		dialog = new AlertDialog.Builder(context);
		dialog.setTitle(DIALOG_TITLE);
		dialog.setIcon(R.drawable.ic_launcher);
		
		View view = getInflatedView(context, null);
		amountBroughtForwardEditText = (EditText)view.findViewById(R.id.amountBroughtForwardEditText);
		amountBroughtForwardEditText.setText(NumericUtil.formatDecimal(amount));
		
		numberOfPeopleEditText = (EditText)view.findViewById(R.id.numberOfPeopleEditText);
		numberOfPeopleEditText.setText(String.valueOf(passengers));
		
		dialog.setView(view);
		
		dialog.setPositiveButton(POSITIVE_BUTTON_TEXT, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (validateInput()) {
					notifyEditCollectionCompleted(null, amount, passengers);
				}
			}
		});
		
		dialog.setNegativeButton(NEGATIVE_BUTTON_TEXT, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//do nothing - aborts the action
			}
		});
		
		dialog.show();
		
	}
	
	/**
	 * Gets the inflated view - specific to this dialog
	 * 
	 * @param context
	 * @param root
	 * @return
	 */
	private View getInflatedView(Context context, ViewGroup root) {
		
		LayoutInflater inflater = (LayoutInflater)context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View view = inflater.inflate(R.layout.dialog_add_new_collection, root);
		
		return view;
		
	}
	
	
	/**
	 * Performs validation on amount and passengers
	 * @return
	 */
	private boolean validateInput() {
		
		String message = null;
		String title = "Please rectify first";
		
		Context context = dialog.getContext();
		
		if (NumericUtil.isNaN(amountBroughtForwardEditText.getText().toString().trim()) ||
				NumericUtil.isNaN(numberOfPeopleEditText.getText().toString().trim())) {
			
			message = "Cannot leave fields empty.";
			DefaultAlertDialog.showOKDialog(context, title, message);
			return false;
		}
		
		amount = Double.parseDouble(amountBroughtForwardEditText.getText().toString().trim());
		passengers = Integer.parseInt(numberOfPeopleEditText.getText().toString().trim());
		
		if (amount < AllCollections.getUnitCostPrice()){
			message = "Amount brought forward must be greater than R " 
						+ NumericUtil.formatDecimal(AllCollections.getUnitCostPrice()) + ".";
			
			DefaultAlertDialog.showOKDialog(context, title, message);
			return false;
		}
		
		if (passengers < 1) {
			message = "Number of passengers must be at least 1.";
			
			DefaultAlertDialog.showOKDialog(context, title, message);
			return false;
		}
		
		double expectedAmount = AllCollections.getUnitCostPrice() * passengers;
		if (amount < expectedAmount) {
			message = "Amount short by R " + NumericUtil.formatDecimal(expectedAmount - amount) + 
					"  for " + passengers + " passengers.";
			
			DefaultAlertDialog.showOKDialog(context, title, message);
			return false;
		}
		
		return true;
	}
}
