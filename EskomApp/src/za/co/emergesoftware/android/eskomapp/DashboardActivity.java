package za.co.emergesoftware.android.eskomapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class DashboardActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.dashboard, menu);
		return true;
	}

}
