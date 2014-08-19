package com.tsepo.android.taxichange.events;

import com.tsepo.android.taxichange.model.Confirmation;

import android.view.View;

public interface DialogConfirmationListener {

	public void confirmationReceived(View source, Confirmation value);
}
