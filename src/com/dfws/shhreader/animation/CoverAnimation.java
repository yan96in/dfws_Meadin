package com.dfws.shhreader.animation;

import android.graphics.Matrix;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
/**
 * 动画
 * @author Administrator
 *
 */
public class CoverAnimation extends Animation {

	private int pointX;
	private int pointY;
	public CoverAnimation(int duration) {
		setDuration(duration);
		setFillAfter(true);
	}

	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		pointX=width/2;
		pointY=height/2;
		setInterpolator(new AccelerateDecelerateInterpolator());
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		// TODO Auto-generated method stub
		super.applyTransformation(interpolatedTime, t);
		final Matrix matrix = t.getMatrix();
		if (interpolatedTime < 0.8) {
			matrix.preScale(1+0.625f*interpolatedTime, 1-interpolatedTime/0.8f+0.01f,pointX,pointY);
		}else{
			matrix.preScale(7.5f*(1-interpolatedTime),0.01f,pointX,pointY);
		}	
	}

	public static TranslateAnimation createAnimation(int toX){
		TranslateAnimation animation=new TranslateAnimation(0, -toX, 0, 0);
		animation.setDuration(3500);
		animation.setInterpolator(new AccelerateDecelerateInterpolator());
		animation.setRepeatCount(0);
		animation.setFillAfter(false);
		return animation;
	}
}
