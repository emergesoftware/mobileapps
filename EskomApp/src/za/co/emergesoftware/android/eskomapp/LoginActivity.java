package za.co.emergesoftware.android.eskomapp;

import za.co.emergesoftware.android.util.NavigationService;
import za.co.emergesoftware.android.animation.AnimationAdapter;
import za.co.emergesoftware.android.animation.AnimationFactory;
import za.co.emergesoftware.android.eskomapp.entity.GenericResult;
import za.co.emergesoftware.android.eskomapp.entity.LoginRequest;
import za.co.emergesoftware.android.eskomapp.entity.User;
import za.co.emergesoftware.android.eskomapp.webservice.AsyncLoginClient;
import za.co.emergesoftware.android.eskomapp.webservice.WebClientEvent;
import za.co.emergesoftware.android.eskomapp.webservice.WebClientEventListener;
import za.co.emergesoftware.android.net.ConnectionManager;
import za.co.emergesoftware.android.ui.dialog.MessageDialog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements View.OnClickListener {
	
	private static final int RESET_PASSWORD_MENU_ITEM = R.id.recoverPasswordMenuItem;
	
	private long startTimeMillis = 0;
	
	private EditText usernameEditText;
	private EditText passwordEditText;
	private Button loginButton;
	private Button registerButton;
	
	private FrameLayout loginProgressLayer;
	private TextView authenticatingMessage;
	private ProgressBar authenticatingProgressBar;
	
	private boolean isAttemptingConnection = false;
	private GenericResult result;
	
	private LoginRequest request;
	public static Context CONTEXT = null;
	public static int loginAttempts = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		// set the context to current
		CONTEXT = this;
		// inflate the UI components
		inflateUIComponents();
	}
	
	/**
	 * Inflates and creates UI elements
	 */
	private void inflateUIComponents() {
		
		// the username edit text
		usernameEditText = (EditText)findViewById(R.id.usernameEditText);
		
		// the password edit text
		passwordEditText = (EditText)findViewById(R.id.passwordEditText);
		
		// the login button
		loginButton = (Button)findViewById(R.id.loginButton);
		loginButton.setOnClickListener(this);
		
		// the register button
		registerButton = (Button)findViewById(R.id.registerButton);
		registerButton.setOnClickListener(this);
		
		// the authenticating frame layer
		loginProgressLayer = (FrameLayout)findViewById(R.id.loginProgressLayer);
		
		// the authenticating message
		authenticatingMessage = (TextView)findViewById(R.id.authenticatingMessage);
		
		// the authenticating progress bar
		authenticatingProgressBar = (ProgressBar)findViewById(R.id.authenticatingProgressBar);
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if (item.getItemId() == RESET_PASSWORD_MENU_ITEM) {
			NavigationService.navigate(this, RecoverPassword.class, true);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	};


	@Override
	public void onBackPressed() {
		if (isAttemptingConnection)
			return;
		
		String message = "Press back button again to exit.";
		
		if (startTimeMillis == 0) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
			startTimeMillis = System.currentTimeMillis();
		}
		
		else if (startTimeMillis > 0) {
			long currentTimeMillis = System.currentTimeMillis();
			long duration = (currentTimeMillis - startTimeMillis);
			
			if (duration < 2000) {
				super.onBackPressed();
			}
			
			else {
				Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
			}
			
			startTimeMillis = System.currentTimeMillis();
		}
	}
	
	/**
	 * Runs the method on button click event 
	 * 
	 */
	private void processLoginEvents() {
		
		String message = null;
		String title = null;
		
		String username = usernameEditText.getText().toString().trim();
		String password = passwordEditText.getText().toString();
		
		if (username == null || username.length() < 8 || 
				username.length() > 18) {
			
			title = "Login details error";
			message = "Username provided has an invalid number of " + 
					  "characters. Try again.";
			
			MessageDialog.show(this, title, message, MessageDialog.TYPE_ERROR_MESSAGE);
			return;
		}
		
		if (password == null || password.length() < 8) {
			
			title = "Login details error";
			message = "Password appears to be too short, try again.";
			
			MessageDialog.show(this, title, message, MessageDialog.TYPE_ERROR_MESSAGE);
			return;
		}
		
		User user = new User(0);
		user.setUsername(username);
		user.setPassword(password);
		
		request = new LoginRequest(user);
		request.setLoginAttempts(loginAttempts);
		
		new AuthenticateUserAsyncTask().execute(request);
	}

	/**
	 * Runs the method on button click event
	 */
	private void navigateToRegistrationActivity() {
		NavigationService.navigate(this, RegisterActivity.class, true);
	}
	
	@Override
	public void onClick(View v) {
		
		final Button source = (Button)v;
		
		if (source != null)
			AnimationFactory.startAnimation(this, source, AnimationFactory.MODERATE_ZOOM_OUT_RESOURCE, 
					new AnimationAdapter() {
				
				@Override
				public void onAnimationEnd(Animation animation) {
					
					super.onAnimationEnd(animation);
					
					if (source == loginButton) {
						processLoginEvents();
					}
					
					if (source == registerButton) {
						navigateToRegistrationActivity();
					}
				}
				
			});	
	}
	
	/**
	 * Connects to the web service and authenticates the 
	 * user's login details
	 * 
	 * @param user
	 * @return
	 */
	private void authenticateUserViaWebService(LoginRequest request) {
		try {
			AsyncLoginClient client = new AsyncLoginClient(this);
			client.addAsyncWebClientEventListener(new WebClientEventListener() {
				
				@Override
				public void onSuccessful(Context ctx, WebClientEvent e) {
					setTopLayerVisibility(false);
					navigateToDashboard();
				}
				
				@Override
				public void onFailure(Context ctx, WebClientEvent e) {
					
					
					
					MessageDialog.show(CONTEXT, "Error Encountered", 
							"A remote server error occured " + 
					        "and could not complete your request " +
							"at the moment, please try later.", 
							MessageDialog.TYPE_ERROR_MESSAGE);
					
					setTopLayerVisibility(false);
				}
			});
			
			client.authenticateUser(request);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Navigates to the dash-board
	 */
	private void navigateToDashboard() {
		NavigationService.navigate(this, DashboardActivity.class, true);
	}
	
	/**
	 * Hides / shows the top layer
	 * and clears the password field
	 * 
	 * @param show
	 */
	private void setTopLayerVisibility(boolean show) {
		if (show) {
			loginButton.setEnabled(false);
			registerButton.setEnabled(false);
			loginProgressLayer.setVisibility(View.VISIBLE);
		}
		
		else {
			loginButton.setEnabled(true);
			registerButton.setEnabled(true);
			loginProgressLayer.setVisibility(View.GONE);
			passwordEditText.setText("");
		}
		
		
	}
	
	/**
	 * Asynchronous task to authenticate the user
	 * 
	 * @author ubuntuvm2
	 *
	 */
	private class AuthenticateUserAsyncTask extends AsyncTask<LoginRequest, Void, GenericResult> {
		
		private ConnectionManager network = null;
		
		@Override
		protected void onPreExecute() {
			isAttemptingConnection = true;
			setTopLayerVisibility(true);
			
			super.onPreExecute();
		}
		
		@Override
		protected GenericResult doInBackground(LoginRequest... requests) {
			
			if (requests == null || requests.length == 0)
				return GenericResult.None;
			
			network = new ConnectionManager();
			if (network.isNetworkConnectionAvailable(CONTEXT)) {
	
				authenticateUserViaWebService(requests[0]);
				return GenericResult.None;
			}
			
			else {
				return GenericResult.NoInternetConnection;
			}
		}
		
		@Override
		protected void onPostExecute(GenericResult result) {
			
			if (result == GenericResult.NoInternetConnection) {
				
				setTopLayerVisibility(false);
				MessageDialog.show(CONTEXT, "Connection Error", 
						"No Internet connection could be established. Please check if your mobile data or " +
						"WiFi settings are properly set and try again.",
						MessageDialog.TYPE_ERROR_MESSAGE);
			}
			
			isAttemptingConnection = false;
			
			super.onPostExecute(result);
		}
	}

}
