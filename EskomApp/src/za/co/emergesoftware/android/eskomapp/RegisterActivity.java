package za.co.emergesoftware.android.eskomapp;

import java.util.ArrayList;

import za.co.emergesoftware.android.net.ConnectionManager;
import za.co.emergesoftware.android.ui.dialog.MessageDialog;
import za.co.emergesoftware.android.util.CellphoneNumberValidator;
import za.co.emergesoftware.android.util.SouthAfricanProvinces;
import za.co.emergesoftware.android.util.EmailAddressValidator;
import za.co.emergesoftware.android.util.SouthAfricanIdentityTool;
import za.co.emergesoftware.android.animation.AnimationAdapter;
import za.co.emergesoftware.android.animation.AnimationFactory;
import za.co.emergesoftware.android.eskomapp.entity.GenericResult;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class RegisterActivity extends Activity implements OnItemSelectedListener,
														  View.OnClickListener{

	private static final int TERMS_AND_CONDITIONS_AREA = 0;
	private static final int PRIVACY_POLICY_AREA = 1;

	private static Context CONTEXT;
	
	private EditText firstNameEditText;
	private EditText lastNameEditText;
	private EditText idNumberEditText;
	private EditText emailAddressEditText;
	private EditText confirmEmailAddressEditText;
	private EditText cellphoneNumberEditText;
	private Spinner provincesSpinner;
	private TextView readTermsAndConditionsTextView;
	private TextView readPrivacyPolicyTextView;
	private Button createAccountButton;
	
	private ArrayList<String> userDetailsErrors = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		CONTEXT = this;
		
		createUIElements();
	}
	
	/**
	 * Inflates and creates UI elements
	 */
	private void createUIElements() {
		
		// the first name edit text
		firstNameEditText = (EditText)findViewById(R.id.firstNameEditText);
		
		// the last name edit text
		lastNameEditText = (EditText)findViewById(R.id.lastNameEditText);
		
		// the SA id number
		idNumberEditText = (EditText)findViewById(R.id.idNumberEditText);
		
		// the email address edit text
		emailAddressEditText = (EditText)findViewById(R.id.emailAddressEditText);
		
		// the confirm email address edit text
		confirmEmailAddressEditText = (EditText)findViewById(R.id.confirmEmailAddressEditText);
		
		// the cellphone number edit text
		cellphoneNumberEditText = (EditText)findViewById(R.id.cellphoneNumberEditText);
		
		// the province spinner
		provincesSpinner = (Spinner)findViewById(R.id.provinceSpinner);
		provincesSpinner.setOnItemSelectedListener(this);
		populateSpinner(provincesSpinner, SouthAfricanProvinces.getProvincesList());
		
		
		// the read terms and conditions text view
		readTermsAndConditionsTextView = (TextView)findViewById(R.id.readTermsAndConditionsTextView);
		setSpannableHyperLinkedArea(TERMS_AND_CONDITIONS_AREA, readTermsAndConditionsTextView);
		
		// the read privacy policy text view
		readPrivacyPolicyTextView = (TextView)findViewById(R.id.readPrivacyPolicyTextView);
		setSpannableHyperLinkedArea(PRIVACY_POLICY_AREA, readPrivacyPolicyTextView);
		
		// the create account button
		createAccountButton = (Button)findViewById(R.id.createAccountButton);
		createAccountButton.setOnClickListener(this);
		
	}
	 
	/**
	 * Populates the spinner with the items in the 
	 * string array resource file
	 * 
	 * @param spinner
	 * @param stringArrayResourceId
	 * @param activityLayoutResourceId
	 * @param spinnerResourceId
	 */
	private void populateSpinner(Spinner spinner, ArrayList<String> list) {
		
		int dropDownResourceFile = android.R.layout.simple_spinner_dropdown_item;
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<String> adapter = new ArrayAdapter<String>
					(this, dropDownResourceFile, list);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(dropDownResourceFile);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
		
	}
	
	/**
	 * Sets the spannable area to hyperlinks
	 * 
	 * @param spannableAreaType
	 * @param textView
	 */
	private void setSpannableHyperLinkedArea(int spannableAreaType, TextView textView) {
			
		SpannableString span = null;
		int resourceId = 0;
		
		StyleSpan style = new StyleSpan(Typeface.BOLD);
		
		switch (spannableAreaType) {
			case TERMS_AND_CONDITIONS_AREA:
				
				resourceId = R.string.read_terms_and_conditions_link_text;
				span = new SpannableString(getResources().getText(resourceId));
				span.setSpan(style, 9, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				
				textView.setText(span);
				
				break;
				
			case PRIVACY_POLICY_AREA:
				
				resourceId = R.string.read_privacy_policy_link_text;
				span = new SpannableString(getResources().getText(resourceId));
				span.setSpan(style, 15, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				
				textView.setText(span);
				
				break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
		
	}

	@Override
	public void onClick(View v) {
		final Button source = (Button)v;
		
		if (source == null)
			return;
		
		AnimationFactory.startAnimation(this, source, AnimationFactory.MODERATE_ZOOM_OUT_RESOURCE, 
			new AnimationAdapter() {
			
			@Override
			public void onAnimationEnd(Animation animation) {
				super.onAnimationEnd(animation);
				
				if (source == createAccountButton) {
					
					if (validateUserDetails()) {
						new RegisterUserAccountWorker().execute(new String[]{"hello"});
					}
					
					// prompt the user for errors found.
					else {
						
						StringBuilder builder = new StringBuilder();
						for (String error : userDetailsErrors) 
							builder.append("- ").append(error).append("\n");
						
						userDetailsErrors = null;
						
						MessageDialog.show(CONTEXT, "Errors Found", 
								builder.toString(), MessageDialog.TYPE_ERROR_MESSAGE);
						
					}
				}
			}
			
		});
		
	}
	
	/**
	 * Validates the details entered by the user for registration
	 * @return
	 */
	private boolean validateUserDetails() {
		
		userDetailsErrors = new ArrayList<String>();
		
		// validate the first name
		String firstName = firstNameEditText.getText().toString().trim();
		if (firstName == null || firstName.length() < 3) {
			userDetailsErrors.add("The First Name provided is too short.");		
		}
	
		// validate the last name
		String lastName = lastNameEditText.getText().toString().trim();
		if (lastName == null || lastName.length() < 3) {
			userDetailsErrors.add("The Last Name provided is too short.");
			
		}
		
		// validate the SA ID number
		String idNumber = idNumberEditText.getText().toString().trim();
		if (idNumber == null || SouthAfricanIdentityTool.isValid(idNumber) == false) {
			userDetailsErrors.add("The Identity Number does not appear authentic.");
		}
		
		// validate the email address
		String emailAddress = emailAddressEditText.getText().toString().trim();
		if (emailAddress != null && emailAddress.length() > 7) {
			if (EmailAddressValidator.isEmailAddressValid(emailAddress) == false)
				userDetailsErrors.add("The Email Address has an invalid format.");
		}
		
		else {
			userDetailsErrors.add("The Email Address provided is too short.");
			
		}
		
		// validate the confirm email address field
		String confirmedEmailAddress = confirmEmailAddressEditText.getText().toString().trim();
		if (confirmedEmailAddress == null || !confirmedEmailAddress.equals(emailAddress)) {
			userDetailsErrors.add("The Confirmed Email Address does not match the Email Address.");
			
		}
		
		//validate the cellphone number
		String cellphoneNumber = "27" + cellphoneNumberEditText.getText().toString().trim();
		try {
			if (cellphoneNumber.length() != CellphoneNumberValidator.VALID_CELL_PHONE_NUMBER_LENGTH 
					|| Double.isNaN(Double.parseDouble(cellphoneNumber)))  {
				userDetailsErrors.add("The Cellphone Number must have 9 digits.");
				
			}
			
			else {
				if (!CellphoneNumberValidator.isCellphoneNumberValid(cellphoneNumber))
					userDetailsErrors.add("The Cellphone Number appears to be invalid.");
			}
		}
		
		catch (NumberFormatException e) {
			userDetailsErrors.add("The Cellphone Number can only contain numbers.");
			
		}
		
		// get the province - no validation required.
		String province = provincesSpinner.getSelectedItem().toString();
		if (province == null || province.trim().length() == 0)
			userDetailsErrors.add("You must selected a province.");
		
		// check if there were errors.
		if (userDetailsErrors.size() > 0)
			return false;
		
		return true;
		
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	private class RegisterUserAccountWorker extends AsyncTask<String, Void, GenericResult> {
		
		@Override
		protected GenericResult doInBackground(String... arg0) {
			
			ConnectionManager network = new ConnectionManager();
			if (!network.isNetworkConnectionAvailable(CONTEXT)) {
				return GenericResult.NoInternetConnection;
			}
			
			return GenericResult.Aborted;
		}
		
		@Override
		protected void onPostExecute(GenericResult result) {
			
			if (result == GenericResult.NoInternetConnection) {
				
				MessageDialog.show(CONTEXT, "Connection Error", 
						"Could not establish an Internet connection, " +
					    "please check your network settings.", 
					MessageDialog.TYPE_ERROR_MESSAGE);
				
			}
			
		
			if (result == GenericResult.Aborted) {
				
				MessageDialog.show(CONTEXT, "Aborted", 
						"Could not register your account at the " +
						"moment, please try later.",  
					MessageDialog.TYPE_ERROR_MESSAGE);
				
			}
			
			super.onPostExecute(result);
		}
		
	}
}
