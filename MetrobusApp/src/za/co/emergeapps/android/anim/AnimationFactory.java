package za.co.emergeapps.android.anim;


import za.co.emergeapps.metrobusapp.R;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;


public final class AnimationFactory {
	
	/** Animation resources **/
	public final static int ZOOM_OUT_ANIMATION_RESOURCE = R.anim.zoom_out;
	public final static int FADE_IN_ANIMATION_RESOURCE = R.anim.fade_in;
	public final static int MODERATE_ZOOM_OUT_RESOURCE = R.anim.moderate_zoom_out;
	
	/** Variables **/
	private static Animation animation = null;
	
	
	/**
	 * Starts an animation from given animation object
	 * on the view
	 * 
	 * @param v
	 * @param animation
	 */
	public static void startAnimation(View view, Animation animation) {
		if (view == null || animation == null)
			return;
		
		view.startAnimation(animation);
	}
	
	/**
	 * Starts animation on the specified view
	 * 
	 * @param context
	 * @param view
	 * @param resourceId
	 * @param listener
	 */
	public static void startAnimation(Context context, View view, int resourceId, AnimationListener listener) {
		if (context == null || view == null)
			return;
		
		animation = createAnimationInstance(context, resourceId, listener);
		view.startAnimation(animation);
		
	}
	
	/**
	 * Creates an instance of the 
	 * AnimationListener object
	 * 
	 * @param context
	 * @param resourceId
	 * @param listener
	 * @return
	 */
	private static Animation createAnimationInstance(Context context, int resourceId, AnimationListener listener) {
		Animation animation = AnimationUtils.loadAnimation(context, resourceId);
		if (listener != null)
			animation.setAnimationListener(listener);
		return animation;
	}
	
	/**
	 * Creates the zoom out animation
	 * 
	 * @param context
	 * @param resourceId
	 * @return
	 */
	public static Animation createZoomOutAnimation(Context context, AnimationListener listener) {
		
		animation = createAnimationInstance(context, ZOOM_OUT_ANIMATION_RESOURCE, listener);
		return animation;
	}
	
	
}
