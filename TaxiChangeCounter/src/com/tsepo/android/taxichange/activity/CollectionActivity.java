package com.tsepo.android.taxichange.activity;



import com.tsepo.android.collection.AllCollections;
import com.tsepo.android.taxichange.R;
import com.tsepo.android.taxichange.events.AmountDueToDriverDeterminedListener;
import com.tsepo.android.taxichange.events.CollectionAddedEvent;
import com.tsepo.android.taxichange.events.CollectionAddedListener;
import com.tsepo.android.taxichange.events.DeleteCollectionListener;
import com.tsepo.android.taxichange.events.DialogConfirmationListener;
import com.tsepo.android.taxichange.model.Confirmation;
import com.tsepo.android.ui.dialog.AddNewCollectionDialog;
import com.tsepo.android.ui.dialog.DefaultConfirmDialog;
import com.tsepo.android.util.NumericUtil;
import com.tsepo.android.widgets.custom.CollectionLineView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CollectionActivity extends Activity {

	/** UI Elements **/ 
	private LinearLayout allCollectionsLinearLayout;
	private TextView totalDueToTaxiDriver;
	private ImageView instructionsImage;
	
	/** Dialogs **/
	private AddNewCollectionDialog collectionDialog;

	/** Menu Ids **/
	private final static int ADD_NEW_COLLECTION_MENU_ITEM = R.id.addNewCollectionMenuItem;
	private final static int CLEAR_ALL_COLLECTIONS_MENU_ITEM = R.id.clearAllCollectionsMenuItem;
	private final static int ABOUT_MENU_ITEM = R.id.aboutMenuItem;
	
	/** Variables **/
	private boolean isInstructionsImageRemoved = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_collection);
		
		initialiseComponents();
	}

	
	/**
	 * Creates the reference elements to the inflated XML 
	 * resource file
	 */
	private void initialiseComponents() {
		
		//the linear layout
		allCollectionsLinearLayout = (LinearLayout)findViewById(R.id.allCollectionsLinearLayout);
		AllCollections.ALL_COLLECTIONS_LINEAR_LAYOUT = allCollectionsLinearLayout;
	
		// add a listener to listen to changes to the amount due to 
		// taxi driver
		AllCollections.addAmountDueToDriverDeterminedListener(new AmountDueToDriverDeterminedListener() {
			
			@Override
			public void amountDetermined(double total) {
				totalDueToTaxiDriver.setText(AllCollections.TOTAL_DUE_TO_DRIVER_TEXT + 
						NumericUtil.formatDecimal(total));
			}
		});
		
		//the total due to taxi driver bottom bar
		totalDueToTaxiDriver = (TextView)findViewById(R.id.totalDueToTaxiDriver);
		totalDueToTaxiDriver.setText(AllCollections.TOTAL_DUE_TO_DRIVER_TEXT + 
				NumericUtil.formatDecimal(0.0));
		
		//the instructions image
		instructionsImage = (ImageView)findViewById(R.id.instructionsImage);
		
	}
	
	
	/**
	 * Adds a new collection line widget to the linear layout
	 * 
	 * @param line
	 */
	private void addCollectionLineView(double amount, int passengers, double change) {
		
		CollectionLineView control = new CollectionLineView(this);
		control.setAmount(amount);
		control.setPassengers(passengers);
		control.setChangeDue(change);
		
		control.addDeleteCollectionListener(new DeleteCollectionListener() {
			
			@Override
			public void deleteCollectionCompleted(View source, int index) {
				allCollectionsLinearLayout.removeViewAt(index);
				allCollectionsLinearLayout.invalidate(); 
				
				AllCollections.updateTotalDueToDriver();
			}
		});
		
		allCollectionsLinearLayout.addView(control, 0);
		allCollectionsLinearLayout.invalidate(); 
		
	}
	
	
	/**
	 * Called when the add new collection image is clicked
	 * 
	 */
	private void processAddNewCollectionRequest() {
		
		collectionDialog = new AddNewCollectionDialog();
		collectionDialog.addCollectionAddCompleted(new CollectionAddedListener() {
			
			@Override
			public void onCompleted(Context source, CollectionAddedEvent event) {
				
				double amount = event.getAmount();
				int passengers = event.getPassengers();
				double change = AllCollections.determineLineChange(amount, passengers);
				
				addCollectionLineView(amount, passengers, change);
				AllCollections.updateTotalDueToDriver();
			}
		});
		
		collectionDialog.showDialog(this);
		
		//hide the instructions image if still visible
		setInstructionsImageVisibilityGone();
	}
	
	/**
	 * Hides (removes actually) the instructions image if visible
	 */
	private void setInstructionsImageVisibilityGone() {
		
		if (!isInstructionsImageRemoved) {
		
			int index = ((LinearLayout)instructionsImage.getParent()).indexOfChild(instructionsImage);
			allCollectionsLinearLayout.removeViewAt(index);
			
			allCollectionsLinearLayout.invalidate();
			
			isInstructionsImageRemoved = true;
		}
	}
	
	@Override
	public void onBackPressed() {
		
		String title = "Exit collection";
		String message = "Are you sure you want to quit collection?";
		
		DefaultConfirmDialog dialog = new DefaultConfirmDialog();
		dialog.addConfirmationReceivedListener(new DialogConfirmationListener() {
			
			@Override
			public void confirmationReceived(View source, Confirmation value) {
				
				if (value == Confirmation.Yes)
					invokeBackPressed();
			}
		});
		
		dialog.showConfirmDialog(this, title, message);
		
		return;
		
	}
	
	/**
	 * Invokes the onBackPressed()
	 */
	protected void invokeBackPressed() {
		AllCollections.resetAll();
		super.onBackPressed();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.all_collections_menu, menu);
		return true;
	}

	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int itemId = item.getItemId();
		
		if (itemId == CLEAR_ALL_COLLECTIONS_MENU_ITEM) {
			processClearAllCollections();
			return true;
		}
		
		else if (itemId == ADD_NEW_COLLECTION_MENU_ITEM) {
			processAddNewCollectionRequest();
			return true;
		}
		
		else if (itemId == ABOUT_MENU_ITEM){
			navigateToAboutPage();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Begins process to clear all the 
	 * collections made
	 * 
	 */
	private void processClearAllCollections() {
		String title = "Clear All",
				   message = "Are you sure you want to delete ALL collections?";
			
		DefaultConfirmDialog dialog = new DefaultConfirmDialog();
		dialog.addConfirmationReceivedListener(new DialogConfirmationListener() {
			
			@Override
			public void confirmationReceived(View source, Confirmation value) {
				if (value == Confirmation.Yes) {
					clearAllCollections();
					AllCollections.updateTotalDueToDriver();
				}
			}
		});
		
		dialog.showConfirmDialog(this, title, message);
	}
	
	/**
	 * Clears all collections made
	 */
	private void clearAllCollections() {
		
		if (allCollectionsLinearLayout.getChildCount() == 0)
			return;
		
		allCollectionsLinearLayout.removeAllViews();
		allCollectionsLinearLayout.invalidate();
	
	}
	
	/**
	 * Navigates to the About page
	 */
	private void navigateToAboutPage() {
		
		Intent intent = new Intent(CollectionActivity.this, AboutActivity.class);
		CollectionActivity.this.startActivity(intent); 
		
	}

}
