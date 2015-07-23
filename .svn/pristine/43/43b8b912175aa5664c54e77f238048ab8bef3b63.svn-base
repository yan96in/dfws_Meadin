package com.dfws.shhreader.ui;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.dfws.shhreader.R;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.utils.BitmapTools;
import com.dfws.shhreader.utils.MD5Utils;
import com.dfws.shhreader.utils.StringUtils;

/**
 * @author frankiewei
 * ����ItemView,�Զ���View.���㸴��.
 */
public class ViewPagerItemView extends FrameLayout {
	//ͼƬ��ImageView.
	private  ZoomableImageView mAlbumImageView;
	//ͼƬ��Bitmap.
	private String url;
	private Bitmap bitmap=null;
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			mAlbumImageView.setImageBitmap(bitmap);
		}
		
	};
	public ViewPagerItemView(Context context){
		super(context);
		setupViews();
	}
	
	public ViewPagerItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupViews();
	}
	
	//��ʼ��View.
	private void setupViews(){
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.layout_image_viewpager_itemview, null);		
		mAlbumImageView = (ZoomableImageView)view.findViewById(R.id.album_imgview); 
		addView(view);
	}

	/**
	 * �����ݣ����ⲿ����.
	 * @param object
	 */
	public void setData(final String url){
		this.url = url;
	}
	
	/**
	 * <pre>显示图片</pre>
	 *
	 */
	public void showImage() {
		if (!StringUtils.isEmpty(url)) {
			String path=FrameConfigure.NORMAL_IMG_DRC+MD5Utils.MD5(url);
			File file=new File(path);
			if (file.exists()) {
				bitmap=BitmapFactory.decodeFile(path);
			}else {
				bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.def_pic_big);
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						bitmap=BitmapTools.loadImage(FrameConfigure.NORMAL_IMG_DRC, url);
						handler.sendEmptyMessage(1);
					}
				}).start();
			}
			mAlbumImageView.setImageBitmap(bitmap);
		}
	}
		
	/**
	 * �����ڴ����.�ⲿ����.
	 */
	public void recycle(){
		mAlbumImageView.setImageBitmap(null);
		if (bitmap==null)
			return;
		this.bitmap.recycle();
		this.bitmap = null;
	}
	
}
