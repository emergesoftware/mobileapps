package com.tsepo.android.taxichange.model;

import java.util.Date;

public class CollectionLine {

	private double amount;
	private int passengers;
	private double change;
	private boolean isSettled;
	private Date timeBroughtForward;
	
	public CollectionLine() {
		init();
	}
	
	private void init() {
		this.amount = 0;
		this.passengers = 0;
		this.change = 0;
		this.isSettled = false;
		this.timeBroughtForward = null;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getPassengers() {
		return passengers;
	}

	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}

	public double getChange() {
		return change;
	}

	public void setChange(double change) {
		this.change = change;
	}

	public boolean isSettled() {
		return isSettled;
	}

	public void setSettled(boolean isSettled) {
		this.isSettled = isSettled;
	}

	public Date getTimeBroughtForward() {
		return timeBroughtForward;
	}

	public void setTimeBroughtForward(Date timeBroughtForward) {
		this.timeBroughtForward = timeBroughtForward;
	}
	
	
}
