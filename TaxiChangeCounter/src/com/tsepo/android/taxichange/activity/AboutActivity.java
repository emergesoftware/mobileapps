package com.tsepo.android.taxichange.activity;


import com.tsepo.android.taxichange.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AboutActivity extends Activity implements OnClickListener{

	private Button sendEmailToDevelopers;
	
	private final static String[] DEVELOPER_EMAIL = 
			{ "shotleftapp@gmail.com" };
	
	private final static String DEFAULT_SUBJECT_LINE = 
			"TAXI CHANGE COUNTER FOR ANDROID: [place your subject here]";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		sendEmailToDevelopers = (Button)findViewById(R.id.sendEmailToDevelopers);
		sendEmailToDevelopers.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		
		if (v == sendEmailToDevelopers) {
			startSendEmailIntentChooser();
		}
		
	}


	/**
	 * Starts an email chooser from Android system
	 * 
	 */

	private void startSendEmailIntentChooser() {
		
		StringBuilder uriParameters = new StringBuilder();
		uriParameters.append("mailto:" + DEVELOPER_EMAIL[0])
					 .append("?subject=" + DEFAULT_SUBJECT_LINE);
		
		Uri uri = Uri.parse(uriParameters.toString());
		
		Intent intentEmail = new Intent(Intent.ACTION_SENDTO);
		
		intentEmail.putExtra(Intent.EXTRA_EMAIL, DEVELOPER_EMAIL);
		intentEmail.setData(uri);
		
		startActivity(Intent.createChooser(intentEmail, "Send email using:"));
		 
	}
}
