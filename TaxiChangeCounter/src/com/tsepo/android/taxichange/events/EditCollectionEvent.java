package com.tsepo.android.taxichange.events;

public class EditCollectionEvent {
	
	private double edittedAmount;
	private int edittedPassengers;
	
	public EditCollectionEvent(double edittedAmount, int edittedPassengers) {
		this.edittedAmount = edittedAmount;
		this.edittedPassengers = edittedPassengers;
	}
	
	public double getEdittedAmount(){
		return edittedAmount;
	}
	
	public int getEdittedPassengers() {
		return edittedPassengers;
	}
	
}
