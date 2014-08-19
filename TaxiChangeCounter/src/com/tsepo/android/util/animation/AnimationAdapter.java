package com.tsepo.android.util.animation;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public abstract class AnimationAdapter implements AnimationListener {
	
	public AnimationAdapter() {}
	
	@Override
	public void onAnimationStart(Animation animation) {}
	
	@Override
	public void onAnimationRepeat(Animation animation) {}
	
	@Override
	public void onAnimationEnd(Animation animation) {}
	
}
