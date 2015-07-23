package com.dfws.shhreader.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.dfws.shhreader.activity.ShowImageActivity;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.entity.MediaInfo;
import com.dfws.shhreader.net.utils.ImageLoader;
import com.dfws.shhreader.net.utils.ImageLoader.Callback;
import com.dfws.shhreader.ui.ZoomableImageView;
import com.dfws.shhreader.utils.MD5Utils;
/**
 * the ImagePager adapter
 * @author Eilin.Yang
 *2013-3-20
 */
public class ShowImagePagerAdapter extends PagerAdapter {

	/**TAG*/
	private String TAG="ImagePagerAdapter";
	/**上下文*/
	private Context context;
	/**视图集*/
	private List<ZoomableImageView> imageViews;
	private List<MediaInfo> medias;
	/**视图填充器*/
	private LayoutInflater inflater;
	private int init=0;
	private int destroy=0;
	private ImageLoader loader;
	
	public ShowImagePagerAdapter(Context context,List<ZoomableImageView> imageViews, List<MediaInfo> medias){
		if (imageViews!=null) {
			this.imageViews=imageViews;
			this.medias=medias;
			if(imageViews.size()>0){
				loader=new ImageLoader(context);
			}
		}
		this.context=context;
		this.inflater=LayoutInflater.from(context);
	}
	
	public ZoomableImageView getItem(int position){
		return imageViews.get(position);
	}
	
	@Override
	public int getCount() {
//		Log.i(TAG, "getCount()");
		return imageViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
//		Log.i(TAG, "isViewFromObject(View arg0, Object arg1)");
		return arg0==arg1;
	}
	
	 @Override  
     public int getItemPosition(Object object) {
         int r=POSITION_NONE;
         return r;
     }
	 
	 @Override
	public void setPrimaryItem(View container, int position, Object object) {
		 Log.i(TAG, "setPrimaryItem(View container, int position, Object object)"+"  position="+position);
	}
	 
     @Override  
     public void destroyItem(View arg0, int arg1, Object arg2) {  
    	 Log.i(TAG, "destroyItem(View arg0, int arg1, Object arg2)"+"  arg1="+arg1);
    	 destroy=arg1;
         ((ViewPager) arg0).removeView(imageViews.get(arg1));
     }

     @Override  
     public Object instantiateItem(View arg0, int arg1) {
    	 Log.i(TAG, "instantiateItem(View arg0, int arg1)"+"  arg1="+arg1);
    	 init=arg1;
    	 final ZoomableImageView view=imageViews.get(arg1);
    	 if ("ok".equals((String)view.getTag())) {
        	 view.showImg();
		}else {
			final MediaInfo mInfo=medias.get(arg1);
			if (mInfo!=null) {
				String src=mInfo.getSrc();
				Bitmap bm=loader.loadImage("ShowImagePagerAdapter", src, FrameConfigure.NORMAL_IMG_DRC, MD5Utils.MD5(src), new Callback() {
					
					@Override
					public void imageLoaded(String path, String names, Bitmap bitmap) {
						// TODO Auto-generated method stub
						if (bitmap!=null&&mInfo!=null) {
							ShowImageActivity.loadimg=true;
							ShowImageActivity.resultIntent.putExtra("state", 1);
							view.setImageBitmap(bitmap);
						}
					}
				});
			}
		}
         ((ViewPager) arg0).addView(view);
         imageViews.set(arg1, view);
         return imageViews.get(arg1);
     }  

     @Override  
     public void restoreState(Parcelable arg0, ClassLoader arg1) {  
//    	 Log.i(TAG, "restoreState(Parcelable arg0, ClassLoader arg1)");
     }  

     @Override  
     public Parcelable saveState() {  
//    	 Log.i(TAG, "saveState()");
         return null;  
     }  

     @Override  
     public void startUpdate(View arg0) {  
//    	 Log.i(TAG, "startUpdate(View arg0)");
     }  

     @Override  
     public void finishUpdate(View arg0) {  
//    	 Log.i(TAG, "finishUpdate(View arg0)");
     }
}
