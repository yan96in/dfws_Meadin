package com.dfws.shhreader.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dfws.shhreader.R;
import com.dfws.shhreader.activity.personalcenter.BaseActivity;
import com.dfws.shhreader.adapter.ShowImagePagerAdapter;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.entity.MediaInfo;
import com.dfws.shhreader.ui.ZoomableImageView;
import com.dfws.shhreader.utils.FileAccess;
import com.dfws.shhreader.utils.MD5Utils;
import com.dfws.shhreader.utils.StringUtils;
import com.umeng.analytics.MobclickAgent;
/**
 * 浏览网页图片
 * @author 东方网升Administrator
 *2013-3-21
 */
public class ShowImageActivity extends BaseActivity {
	
	/**通道*/
	public static final String CHANNEL="img_path";
	/**场景*/
	private Context context;
	/**显示图片路径*/
	private ImageView iv_download;
	/**图片路径*/
	private String image_path;
	/**图片容器*/
	private List<ZoomableImageView> zoomImageViews;
	/**
	 * 图片地址容器
	 */
	public static List<MediaInfo> medias;
	/**viewpager*/
	private ViewPager pager;
	/**适配器*/
	private ShowImagePagerAdapter adapter;
	/**保存图片*/
	private ImageView iv_goback;
	/**图片目录*/
	private File parent;
	/**主视图*/
	private ViewGroup mainView;
	/**当前图片*/
	private int currentPosition=0;
	/**第一个图片*/
	public  static int firstPosition=1;
	/**当前路径*/
	private String currentPath="";
	public static boolean loadimg=false;
	public static Intent resultIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mainView=(ViewGroup)getLayoutInflater().inflate(R.layout.layout_scan_image, null);
		context=this; 
		image_path=getIntent().getStringExtra(CHANNEL);
		File file=new File(image_path);
		if (!file.exists()) {
			finish();
		}
		loadimg=false;
		parent=file.getParentFile();
		initView();
		Log.i("ShowImageActivity", image_path);
		currentPath=image_path;
		initImages();
		adapter=new ShowImagePagerAdapter(context, zoomImageViews,medias);
		pager.setAdapter(adapter);
		pager.setCurrentItem(firstPosition, false);
		resultIntent=new Intent();
		setResult(111,resultIntent);
	}
	
	private void initView(){
		iv_goback=(ImageView)mainView.findViewById(R.id.iv_goback);
		iv_goback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		iv_download=(ImageView)mainView.findViewById(R.id.iv_download);
		iv_download.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveImg();
			}
		});
		pager=(ViewPager)mainView.findViewById(R.id.zimv_scan);
		pager.setOnPageChangeListener(listener);
		setContentView(mainView);
	}
	
	/**
	 * setup image pager
	 * @param parent the images folder
	 * @return	the list of all picture's name
	 */
	private void initImages(){
		if (medias!=null) {
			int n=medias.size();
			if (parent!=null&&n>0) {
				zoomImageViews=new ArrayList<ZoomableImageView>(n);
				for (MediaInfo md : medias) {
					if (md!=null) {
						ZoomableImageView imgv=new ZoomableImageView(context);
						String mPath=md.getPath();
						File mf=new File(mPath);
						if (mf!=null&&mf.exists()) {
							imgv.setImgPath(mPath);
							imgv.setTag("ok");
						}else {
							imgv.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.def_pic_big));
							imgv.setTag("null");
						}
						zoomImageViews.add(imgv);
					}
				}
			}
		}
	}
	
	private OnPageChangeListener listener=new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int arg0) {
			currentPosition=arg0;
			currentPath=adapter.getItem(arg0).getImgPath();
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	/**
	 * save the current image
	 */
	private void saveImg() {
		FileAccess.MakeDir(FrameConfigure.IMG_FAVORITE_DRC);
		FileAccess.copyFileTo(currentPath, FrameConfigure.IMG_FAVORITE_DRC+MD5Utils.MD5(currentPath)+".png");
		Toast.makeText(this, "下载成功", Toast.LENGTH_LONG).show();
		sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
	}
	
	public void onResume() {
		super.onResume();
		
		/**
		 * 此处调用基本统计代码
		 */
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		/**
		 * 此处调用基本统计代码
		 */
		MobclickAgent.onPause(this);
	}
}
