package com.dfws.shhreader.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
/***
 * the tool of Screen
 * @author Eilin.Yang
 *2013-3-11
 */
public class ScreenUtil extends DeviceUtils{
	/**
	 * dip/dp transform px
	 * @param context activity
	 * @param dpValue dip/dp value
	 * @return px value
	 */
	public static int dip2px(Context context, float dpValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * px  transform  dp/dip
	 * @param context activity
	 * @param pxValue px value
	 * @return dp/dip value
	 */
	public static int px2dip(Context context, float pxValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	    
	/**
	 * get the width of screen
	 * @param activity
	 * @return width in px
	 */
	public static int getScreenWith(Activity activity) {
		int width=0;
		DisplayMetrics metric=new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		width = metric.widthPixels;
		return width;
	}
	
	 /**
	  * 将px值转换为sp值，保证文字大小不变
	  * 
	  * @param pxValue
	  * @param fontScale（DisplayMetrics类中属性scaledDensity）
	  * @return
	  */
	 public static int px2sp(float pxValue, Context context) {
		 float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		 return (int) (pxValue / fontScale + 0.5f);
	 }

	 /**
	  * 将sp值转换为px值，保证文字大小不变
	  * 
	  * @param spValue
	  * @param fontScale（DisplayMetrics类中属性scaledDensity）
	  * @return
	  */
	 public static int sp2px(float spValue, Context context) {
		 float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		 return (int) (spValue * fontScale + 0.5f);
	 }
		
	public static int getStatusBarHeight(Activity activity)
	{
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		return statusBarHeight;
	}
	
	
	/**
     * 
     * @param activity
     * @return > 0 success; <= 0 fail
     */
    public static int getStatusHeight(Activity activity){
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight){
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }
	
	/**
	 * get the height of screen
	 * @param activity
	 * @return height in px
	 */
	public static int getScreenHeight(Activity activity) {
		int height=0;
		DisplayMetrics metric=new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);		
		height = metric.heightPixels;     
		return height;
	}
	
	/**
	 * get the sum of lines with modules
	 * @param activity
	 * @param pageIndex 0：include today topic;>0：normal
	 * @return sum of lines
	 */
	public static int getScreenItemlines(Activity activity,int pageIndex) {
		int h=getScreenHeight(activity);
		int ch=0;
		if (pageIndex==0) {
			ch=h-dip2px(activity, 190);
		}else {
			ch=h-dip2px(activity, 80);
		}
		int lines=ch/(dip2px(activity, 135));
		return lines;
	}
}
