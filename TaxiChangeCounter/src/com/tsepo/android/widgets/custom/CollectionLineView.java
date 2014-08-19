package com.tsepo.android.widgets.custom;

import java.util.ArrayList;

import com.tsepo.android.collection.AllCollections;
import com.tsepo.android.taxichange.R;
import com.tsepo.android.taxichange.events.DeleteCollectionListener;
import com.tsepo.android.taxichange.events.DialogConfirmationListener;
import com.tsepo.android.taxichange.events.EditCollectionEvent;
import com.tsepo.android.taxichange.events.EditCollectionListener;
import com.tsepo.android.taxichange.model.Confirmation;
import com.tsepo.android.ui.dialog.DefaultConfirmDialog;
import com.tsepo.android.ui.dialog.EditCollectionDialog;
import com.tsepo.android.util.NumericUtil;
import com.tsepo.android.util.animation.AnimationAdapter;
import com.tsepo.android.util.animation.AnimationFactory;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CollectionLineView extends LinearLayout implements View.OnTouchListener {
	
	/** UI elements **/
	private TextView amountPaidValue;
	private TextView passengersValue;
	private TextView changeDueValue;
	
	private ImageView editCollectionDetails;
	private ImageView deleteCollectionDetails;

	
	/** variables **/
	private double amount;
	private int passengers;
	private double changeDue;
	
	/** event listeners **/
	private ArrayList<DeleteCollectionListener> listeners;
	
	/**
	 * Default constructor (required)
	 * @param context
	 */
	public CollectionLineView(Context context) {
		super(context);
		
		initialiseComponents(context);
		initialiseVariables();
	}
	
	/**
	 * Overridden constructor (required)
	 * @param context
	 * @param attribs
	 */
	public CollectionLineView(Context context, AttributeSet attribs) {
		super(context, attribs);
		
	}
	
	/**
	 * Overridden constructor (required)
	 * @param context
	 * @param attribs
	 * @param defaultStyle
	 */
	public CollectionLineView(Context context, AttributeSet attribs, int defaultStyle) {
		super(context, attribs, defaultStyle);
	}
	
	/**
	 * Initialises the variables
	 */
	private void initialiseVariables(){
		amount = 0;
		changeDue = 0;
		passengers = 0;
		
		listeners = new ArrayList<DeleteCollectionListener>();
	}
	
	/**
	 * Inflates the layout XML resource files and 
	 * gets references to UI elements
	 * 
	 * @param context
	 */
	private void initialiseComponents(Context context) {
		
		// Get the layout inflater service and inflate XML resource
		String inflationService = Context.LAYOUT_INFLATER_SERVICE;
		
		LayoutInflater inflater = (LayoutInflater)getContext()
				.getSystemService(inflationService);
		
		inflater.inflate(R.layout.custom_collection_line_view, this, true);
		
		//Get the resources
		amountPaidValue = (TextView)findViewById(R.id.amountPaidValue);
		passengersValue = (TextView)findViewById(R.id.passengersValue);
		changeDueValue = (TextView)findViewById(R.id.changeDueValue);
		
		editCollectionDetails = (ImageView)findViewById(R.id.editCollectionDetails);
		editCollectionDetails.setOnTouchListener(this);
		
		deleteCollectionDetails = (ImageView)findViewById(R.id.deleteCollectionDetails);
		deleteCollectionDetails.setOnTouchListener(this);
		
		
	}
	
	
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	public boolean onTouch(View element, MotionEvent event) {
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
		
			if (element == editCollectionDetails) {
				
				AnimationFactory.startAnimation(getContext(), 
						editCollectionDetails, AnimationFactory.ZOOM_OUT_ANIMATION_RESOURCE, 
						new AnimationAdapter() {
						
						@Override
						public void onAnimationEnd(Animation animation) {
							prepareEditCollection();
						}
				});
				
				
			}
			
			if (element == deleteCollectionDetails) {
				
				AnimationFactory.startAnimation(getContext(), deleteCollectionDetails, 
						AnimationFactory.ZOOM_OUT_ANIMATION_RESOURCE, 
						new AnimationAdapter() {
					
					@Override
					public void onAnimationEnd(Animation animation) {
						prepareDeleteCollection();
					}
					
				});
			}
		
		}
		return true;
	}
	
	
	
	public void addDeleteCollectionListener(DeleteCollectionListener listener) {
		if (!listeners.contains(listener))
			listeners.add(listener);
	}
	
	private void notifyDeleteCollectionRequested() {
		
		if (this.getParent() instanceof LinearLayout) {
			
			int index = 0;
			LinearLayout layout = (LinearLayout)this.getParent();
			index = layout.indexOfChild(this);
			
			for (DeleteCollectionListener listener : listeners)
				listener.deleteCollectionCompleted(this, index);
		}
		
	}
	
	/**
	 * Prepares the edit collection dialog
	 * 
	 */
	private void prepareEditCollection() {
		
		EditCollectionDialog dialog = 
				new EditCollectionDialog(amount, passengers);
		
		dialog.addEditCollectionListener(new EditCollectionListener() {
			
			@Override
			public void editCollectionCompleted(Context source, EditCollectionEvent event) {
				
				setAmount(event.getEdittedAmount());
				setPassengers(event.getEdittedPassengers());
				
				setChangeDue(AllCollections
						.determineLineChange(event.getEdittedAmount(), event.getEdittedPassengers()));
				
				AllCollections.updateTotalDueToDriver();
				

			}
		});
		
		dialog.showDialog(getContext());
		
	}
	
	/**
	 * Prepares to delete a collection
	 */
	private void prepareDeleteCollection() {
		
		String title = "Delete",
				   message = "Are you sure you want to remove this collection?";
			
		DefaultConfirmDialog dialog = new DefaultConfirmDialog();
		dialog.addConfirmationReceivedListener(new DialogConfirmationListener() {
			
			@Override
			public void confirmationReceived(View source, Confirmation value) {
				
				if (value == Confirmation.Yes)
					notifyDeleteCollectionRequested();
			}
		});
		
		dialog.showConfirmDialog(getContext(), title, message);
		
	}
	
	/**
	 * Sets the amount brought forward
	 * @param amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
		amountPaidValue.setText("R " + NumericUtil.formatDecimal(amount));
	}

	/**
	 * Sets the number of passengers who paid in
	 * this collection
	 * 
	 * @param passengers
	 */
	public void setPassengers(int passengers) {
		this.passengers = passengers;
		passengersValue.setText(String.valueOf(passengers));
	}

	/**
	 * Sets the change due to the passengers
	 * 
	 * @param changeDue
	 */
	public void setChangeDue(double changeDue) {
		this.changeDue = changeDue;
		changeDueValue.setText("R " + NumericUtil.formatDecimal(changeDue));
	}

	public double getAmount() {
		return amount;
	}

	public int getPassengers() {
		return passengers;
	}

	public double getChangeDue() {
		return changeDue;
	}
	
	
}
