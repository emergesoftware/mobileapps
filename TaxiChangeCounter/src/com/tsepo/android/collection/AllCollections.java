package com.tsepo.android.collection;

import java.util.ArrayList;

import com.tsepo.android.taxichange.events.AmountDueToDriverDeterminedListener;
import com.tsepo.android.widgets.custom.CollectionLineView;

import android.widget.LinearLayout;

public final class AllCollections {
	
	private static ArrayList<AmountDueToDriverDeterminedListener> listeners
	                                  = new ArrayList<AmountDueToDriverDeterminedListener>();
	
	private static double unitCost = 0.0;
	private static double totalDueToDriver = 0.0;
	
	public static final String TOTAL_DUE_TO_DRIVER_TEXT = 
			"TOTAL DUE TO DRIVER: R ";
	
	public static LinearLayout ALL_COLLECTIONS_LINEAR_LAYOUT = null;
	
	/**
	 * Gets the total due to the driver
	 * @return
	 */
	public static double getTotalDueToDriver() {
		return totalDueToDriver;
	}

	/**
	 * Sets the total due to driver
	 * @param totalDueToDriver
	 */
	public static void setTotalDueToDriver(double totalDueToDriver) {
		AllCollections.totalDueToDriver = totalDueToDriver;
	}

	public static void updateTotalDueToDriver() {
		
		LinearLayout layout = ALL_COLLECTIONS_LINEAR_LAYOUT;
		
		if (layout == null)
			return;
		
		int children = layout.getChildCount();
		CollectionLineView line = null;
		totalDueToDriver = 0;
		
		if (children > 0){
			for (int t = 0; t < children; t++) {
				
				if (layout.getChildAt(t) instanceof CollectionLineView) {
					line = (CollectionLineView)layout.getChildAt(t);
					totalDueToDriver += line.getPassengers() * unitCost;
				}
				
			}
		}
		
		notifyAmountDueToDriverDetermined(totalDueToDriver);
		
	}

	public static void addAmountDueToDriverDeterminedListener(AmountDueToDriverDeterminedListener l) {
		if (!listeners.contains(l))
			listeners.add(l);
	}
	
	public static void removeAmountDueToDriverDeterminedListener(AmountDueToDriverDeterminedListener l) {
		if (!listeners.isEmpty() &&
				listeners.contains(l))
			listeners.remove(listeners.indexOf(l));
	}
	
	/**
	 * Triggers an event that amount due to driver is
	 * determined
	 * 
	 * @param totalDueToDriver2
	 */
	private static void notifyAmountDueToDriverDetermined(double total) {
		for (AmountDueToDriverDeterminedListener listener : listeners) {
			listener.amountDetermined(total);
		}
	}

	/**
	 * Sets the unit cost each passenger must pay
	 * in the taxi cab
	 * @param cost
	 */
	public static void setUnitCost(double cost) {
		unitCost = cost;
	}
	
	
	/**
	 * Gets the unit cost each passenger
	 * must pay in the taxi cab
	 * @return
	 */
	public static double getUnitCostPrice() {
		return unitCost;
	}
	
	/**
	 * Determines the change amount due to the customer (passenger)
	 * @param line
	 * @return
	 */
	public static double determineLineChange(double amount, int passengers) {
		double change = 0.00;
		double expectedAmount = 0.00;
		
		expectedAmount = unitCost * passengers;
		change = amount - expectedAmount;
		
		return change;
	}
	
	/**
	 * Resets all static data
	 */
	public static void resetAll() {
		
		unitCost = 0.0;
		totalDueToDriver = 0.0;
		listeners.clear();
		
	}
	
}
