package za.co.emergesoftware.android.eskomapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

public class RecoverPassword extends Activity {
	
	private EditText usernameEditText;
	private EditText emailAddressEditText;
	private Button resetPasswordButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recover_password);
		createActivityElements();
	}
	
	private void createActivityElements() {
		
		// the username edit text
		usernameEditText = (EditText)findViewById(R.id.usernameEditText);
		
		// the email address edit text
		emailAddressEditText = (EditText)findViewById(R.id.emailAddressEditText);
		
		// the reset password button
		resetPasswordButton = (Button)findViewById(R.id.resetPasswordButton);
		
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

}
