package za.co.emergeapps.metrobusapp;

import za.co.emergeapps.android.util.NavigationService;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class DashboardActivity extends Activity  {

	private MenuTileView timeTableTile;
	private MenuTileView searchTile;
	private MenuTileView locateTile;
	private MenuTileView faresTile;
	private MenuTileView contactTile;
	
	private static Activity context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		context = this;
		inflateLayoutView(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.dashboard, menu);
		return true;
	}
	
	/**
	 * Inflates the layout view
	 * @param context
	 */
	private void inflateLayoutView(Context context) {
		
		// the time table tile
		this.timeTableTile = (MenuTileView)findViewById(R.id.timeTableTile);
		this.timeTableTile.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					NavigationService.navigate(DashboardActivity.context, TimeTableOptionsActivity.class, true);
					return true;
				}
			
				return false;
			}
		});
		// the search tile
		this.searchTile = (MenuTileView)findViewById(R.id.searchTile);
		this.searchTile.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		//the locate tile
		this.locateTile = (MenuTileView)findViewById(R.id.locateTile);
		this.locateTile.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		// the fares tile
		this.faresTile = (MenuTileView)findViewById(R.id.faresTile);
		this.faresTile.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		// the contact tile
		this.contactTile = (MenuTileView)findViewById(R.id.contactTile);
		this.contactTile.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}

}
