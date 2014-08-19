package com.tsepo.android.taxichange.events;

public class CollectionAddedEvent {
	
	private double amount;
	private int passengers;
	
	public CollectionAddedEvent(double amount, int passengers) {
		this.amount = amount;
		this.passengers = passengers;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public int getPassengers() {
		return passengers;
	}
	
}
