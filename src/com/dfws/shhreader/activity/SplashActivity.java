package com.dfws.shhreader.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.dfws.shhreader.R;
import com.umeng.analytics.MobclickAgent;
/**
 * 
 * @file： SplashActivity.java
 * @Page： com.dfws.shhreader.activity
 * @description： 启动画面 
 * @since： 2013-11-18
 * @author： Administrator
 */
public class SplashActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_guide_splash);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				finish();
			}
		}, 4000);
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
