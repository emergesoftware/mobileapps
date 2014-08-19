package com.tsepo.android.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import com.tsepo.android.collection.AllCollections;
import com.tsepo.android.taxichange.R;
import com.tsepo.android.taxichange.app.settings.AppSettings;
import com.tsepo.android.taxichange.events.CollectionAddedEvent;
import com.tsepo.android.taxichange.events.CollectionAddedListener;
import com.tsepo.android.util.NumericUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class AddNewCollectionDialog {
	
	private AlertDialog.Builder dialog;
	private List<CollectionAddedListener> listeners;
	private CollectionAddedEvent event;
	
	private EditText amountBroughtForwardEditText;
	private EditText numberOfPeopleEditText;
	
	private double amount;
	private int passengers;
	
	private static final String DIALOG_TITLE = "Add New";
	private static final String POSITIVE_BUTTON_TEXT = "Add";
	private static final String NEGATIVE_BUTTON_TEXT = "Cancel";
	
	public AddNewCollectionDialog() {
		dialog = null;
		listeners = new ArrayList<CollectionAddedListener>();
		event = null;
	}
	
	/**
	 * Shows the dialog for adding new collection
	 * 
	 * @param context
	 */
	public void showDialog(Context context) {
		
		context.setTheme(AppSettings.APPLICATION_THEME);
		
		dialog = new AlertDialog.Builder(context);
		
		dialog.setTitle(DIALOG_TITLE);
		dialog.setIcon(R.drawable.ic_launcher);
		
		View view = getInflatedView(context, null);
		amountBroughtForwardEditText = (EditText)view.findViewById(R.id.amountBroughtForwardEditText);
		numberOfPeopleEditText = (EditText)view.findViewById(R.id.numberOfPeopleEditText);
		
		dialog.setView(view);
		
		dialog.setPositiveButton(POSITIVE_BUTTON_TEXT, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (validateInput()) {
					notifyOnCollectionAdded(amount, passengers);
				}
			}
		});
		
		dialog.setNegativeButton(NEGATIVE_BUTTON_TEXT, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//do nothing 
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
		view.setBackgroundColor(Color.TRANSPARENT);
		
		return view;
		
	}
	
	
	/**
	 * Register a new listener for OnNewCollectionAddedListener
	 * 
	 * @param listener
	 */
	public void addCollectionAddCompleted(CollectionAddedListener listener) {
		if (!listeners.contains(listener))
			listeners.add(listener);
	}
	
	/**
	 * Registers a static instance of the linear layout control to be used
	 * to populate the layout with dynamic items
	 * 
	 * @param layout
	 * @param startOver
	 */
	private void notifyOnCollectionAdded(double amount, int passengers) {
		event = new CollectionAddedEvent(amount, passengers);
		for (CollectionAddedListener listener : listeners) {
			listener.onCompleted(dialog.getContext(), event);
		}
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
