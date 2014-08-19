package za.co.emergeapps.metrobusapp;

import za.co.emergeapps.android.anim.AnimationFactory;
import za.co.emergeapps.metrobusapp.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuTileView extends LinearLayout {

	private String titleText;
	private int imageSource;
	
	private ImageView tileIconImageView;
	private TextView titleTitleTextView;
	
	private static Context CONTEXT = null;
	
	/**
	 * Constructor
	 * @param context
	 */
	public MenuTileView(Context context) {
		super(context);
		inflateView(context, null);
	}
	
	/**
	 * Constructor
	 * @param context
	 * @param attrs
	 */
	public MenuTileView(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflateView(context, attrs);
		
	}
	
	/**
	 * Constructor
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public MenuTileView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		inflateView(context, attrs);
	}
	
	/**
	 * Inflates the XML layout resource file
	 * and populates the custom attributes
	 * 
	 * @param context
	 * @param attrs
	 */
	private void inflateView(Context context, AttributeSet attrs) {
		
		CONTEXT = context;
		
		this.titleText = "";
		this.imageSource = 0;
		
		if (attrs != null) {
			
			TypedArray array = context.obtainStyledAttributes(attrs,
			        R.styleable.MenuTileView, 0, 0);
			
			try {
				this.titleText = array.getString(R.styleable.MenuTileView_titleText);
				this.imageSource = array.getResourceId(R.styleable.MenuTileView_imageSource, 0);
				
				array.recycle();

			}
			catch (Exception e) {
				
			}
			
		}
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(
	            Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.compound_view_menu_tile, this, true);
		
		this.setOrientation(LinearLayout.HORIZONTAL);
		
		tileIconImageView = (ImageView)this.findViewById(R.id.menuTileImageView);
		tileIconImageView.setImageResource(imageSource);
		
		titleTitleTextView = (TextView)this.findViewById(R.id.menuTileTextView);
		titleTitleTextView.setText(titleText);
		
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		
	}

	public String getTitleText() {
		return titleText;
	}

	public void setTitleText(String titleText) {
		this.titleText = titleText;
		if (titleText != null)
			this.titleTitleTextView.setText(titleText);
	}

	public int getImageSource() {
		return imageSource;
	}

	public void setImageSource(int imageSource) {
		this.imageSource = imageSource;
		this.tileIconImageView.setImageResource(imageSource);
	}

	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		switch (event.getAction()) {
		
			case MotionEvent.ACTION_DOWN:
				AnimationFactory.startAnimation(CONTEXT, this, AnimationFactory.MODERATE_ZOOM_OUT_RESOURCE, null);
				return true;
		}
		
		return super.onTouchEvent(event);
	}
}
