package com.dfws.shhreader.activity.set;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dfws.shhreader.R;
import com.dfws.shhreader.adapter.ViewPagerAdapter;
import com.dfws.shhreader.database.sharedpreferences.SetsKeeper;
import com.dfws.shhreader.ui.CustomViewPager;
import com.dfws.shhreader.ui.CustomViewPager.DirectionListener;
import com.umeng.analytics.MobclickAgent;
/**
 * 
 * @file： GuideActivity.java
 * @Page： com.dfws.shhreader.activity
 * @description： 引导界面
 * @since： 2013-11-18
 * @author： Administrator
 */
public class SetGuideActivity extends Activity implements OnPageChangeListener {
	private CustomViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;
	// 底部小点图片
	private ImageView[] dots;
	// 记录当前选中位置
	private int currentIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_guide);
		SetsKeeper.keepLauncherState(this, true);
		// 初始化页面
		initViews();
		// 初始化底部小点
		initDots();
	}

	private void initViews() {
		LayoutInflater inflater = LayoutInflater.from(this);
		views = new ArrayList<View>();
		View view5=inflater.inflate(R.layout.layout_guider_page_1,null);
		// 初始化引导图片列表
		views.add(inflater.inflate(R.layout.layout_guider_page_1, null));
		views.add(inflater.inflate(R.layout.layout_guider_page_2, null));
		views.add(inflater.inflate(R.layout.layout_guider_page_3, null));
		views.add(inflater.inflate(R.layout.layout_guider_page_4, null));
		views.add(view5);
		// 初始化Adapter
		vpAdapter = new ViewPagerAdapter(views);
		
		vp = (CustomViewPager) findViewById(R.id.viewpager);
		vp.setAdapter(vpAdapter);
		// 绑定回调
		vp.setOnPageChangeListener(this);
		view5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		vp.setDirectionListener(new DirectionListener() {
			
			@Override
			public void ontouch() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void direction(boolean left, boolean right) {
				// TODO Auto-generated method stub
				if (currentIndex==4) {
					if (left&&!right) {
						finish();
					}
				}
			}
		});
	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

		dots = new ImageView[views.size()];

		// 循环取得小点图片
		for (int i = 0; i < views.size(); i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);// 都设为灰色
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
	}

	private void setCurrentDot(int position) {
		if (position < 0 || position > views.size() - 1
				|| currentIndex == position) {
			return;
		}

		dots[position].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = position;
	}

	// 当滑动状态改变时调用
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	// 当当前页面被滑动时调用
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	// 当新的页面被选中时调用
	@Override
	public void onPageSelected(int arg0) {
		// 设置底部小点选中状态
		setCurrentDot(arg0);
	}

	@Override
	public void onResume() {
		super.onResume();
		
		/**
		 * 此处调用基本统计代码
		 */
		MobclickAgent.onResume(this);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		/**
		 * 此处调用基本统计代码
		 */
		MobclickAgent.onPause(this);
	}
}