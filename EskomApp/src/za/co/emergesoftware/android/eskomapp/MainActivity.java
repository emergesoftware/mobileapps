package za.co.emergesoftware.android.eskomapp;

import za.co.emergesoftware.android.net.ConnectionManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	/** the progress bar **/
	private ProgressBar progressBar;
	private TextView loadingTextView;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        inflateUIComponents();
        
    }
    
    @Override
    protected void onStart() {
    	// check if there is any network connection
        new NetworkStatusAsyncTask().execute(this);
    	super.onStart();
    }
    
    /**
     * Inflates the UI components
     */
    private void inflateUIComponents() {
    	// the progress bar
    	progressBar = (ProgressBar)findViewById(R.id.progressBar);
    	//the loading text view
    	loadingTextView = (TextView)findViewById(R.id.loadingTextView);
    }
    
    /**
     * Navigates to the next activity
     */
    private void navigateToLoginActivity() {
    	Intent intent = new Intent(MainActivity.this, LoginActivity.class);
    	intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
    	this.startActivity(intent);
    	this.finish();
    }
    
    /**
     * Asynchronous task to check if the device is connected 
     * to the Internet.
     * 
     * @author ubuntuvm2
     *
     */
    private class NetworkStatusAsyncTask extends AsyncTask<Context, Void, Boolean> {

    	private ConnectionManager connectionManager;
    	
    	@Override
    	protected void onPreExecute() {
    		
    		loadingTextView.setText("Opening application...");
    		
    		progressBar.setIndeterminate(true);
			progressBar.setVisibility(View.VISIBLE);
    		
    		super.onPreExecute();
    	}
    	
		@Override
		protected Boolean doInBackground(Context... arg0) {
			
			if (arg0.length == 1&& arg0[0] != null) {
				connectionManager = new ConnectionManager();
				return connectionManager.isNetworkConnectionAvailable(arg0[0]);
			}
			
			else
				return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			String message = null;
			
			if (!result){
				message = 
						"No Internet connection was detected. Check your WiFi or " +
						"Mobile data settings and try again.";
				
				progressBar.setIndeterminate(false);
				progressBar.setVisibility(View.INVISIBLE);
				
				loadingTextView.setText(message);

			}
			
			else {
				navigateToLoginActivity();
			}
			
			super.onPostExecute(result);
		}
    	
    }
    
}
