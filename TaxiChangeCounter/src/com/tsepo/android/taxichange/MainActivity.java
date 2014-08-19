package com.tsepo.android.taxichange;

import com.tsepo.android.collection.AllCollections;
import com.tsepo.android.taxichange.activity.AboutActivity;
import com.tsepo.android.taxichange.activity.CollectionActivity;
import com.tsepo.android.ui.dialog.DefaultAlertDialog;
import com.tsepo.android.util.NumericUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

	/** UI elements **/
	private EditText unitPriceEditText;
	private Button startCollectionButton;

	/** Menu Ids **/
	private final static int ABOUT_MENU_ITEM = R.id.aboutApplicationMenuItem;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_unit_cost);
		initialiseComponents();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/** custom methods **/

	/**
	 * Prepares the UI components
	 */
	private void initialiseComponents() {
		
		unitPriceEditText = (EditText)findViewById(R.id.unitPriceEditText);

		startCollectionButton = (Button)findViewById(R.id.startCollectionButton);
		startCollectionButton.setOnClickListener(this);
	}
	
	
	/**
	 * Validates the input unit price
	 * @param input
	 * @return
	 */
	private boolean validateUnitCostInput(String input) {
		
		String message = null;
		String title = "Please rectify first";
		
		if (input.trim().length() == 0) {
			message = "Please enter a cost before you begin.";
			DefaultAlertDialog.showOKDialog(this, title, message);
			return false;
		}
		
		if (NumericUtil.isNaN(input)) {
			message = "You have entered a non-numeric value for cost, please verify.";
			DefaultAlertDialog.showOKDialog(this, title, message);
			return false;
		}
		
		if (Double.parseDouble(input) <= 0) {
			message = "You cannot have R0.00 or lesser as unit cost, try again.";
			DefaultAlertDialog.showOKDialog(this, title, message);
			return false;
		}
		
		return true;
	}

	
	/**
	 * Navigates to the next collections page
	 */
	private void navigateToCollectionsPage() {
		
		Intent intent = new Intent(MainActivity.this, CollectionActivity.class);
		MainActivity.this.startActivity(intent); 
		
	}
	
	/**
	 * Navigates to the About page
	 */
	private void navigateToAboutPage() {
		
		Intent intent = new Intent(MainActivity.this, AboutActivity.class);
		MainActivity.this.startActivity(intent); 
		
	}
	
	/** Event Handlers **/
	@Override
	public void onClick(View view) {
		
		if (view == startCollectionButton) {
			
			String value = unitPriceEditText.getText().toString().trim();
			
			//validate unit cost
			if (validateUnitCostInput(value)) {
				
				//save the unit cost
				double unitCost = Double.parseDouble(value);
				AllCollections.setUnitCost(unitCost);
				
				//navigate to the next page
				navigateToCollectionsPage();
				
			}
		}
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		
			case ABOUT_MENU_ITEM:
				navigateToAboutPage();
				return true;
	
		}
		
		return super.onOptionsItemSelected(item);
	}
	
}
