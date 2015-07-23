package com.dfws.shhreader.activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.dfws.shhreader.R;
import com.dfws.shhreader.slidingmenu.fragment.RightSets;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class BaseFrameActivity extends SlidingFragmentActivity {
	/**
	 * 主框架
	 */
	private  SlidingMenu slidingMenu;
	/**
	 * 右边设置
	 */
	private RightSets rightDetail;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(savedInstanceState);
	}
	
	private void initView(Bundle savedInstanceState){
		setBehindContentView(R.layout.behind_frame);	
		slidingMenu=getSlidingMenu();
		slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		slidingMenu.setShadowDrawable(R.drawable.shadowright);
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		slidingMenu.setFadeDegree(0.35f);
		slidingMenu.setMode(SlidingMenu.RIGHT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		slidingMenu.setMenu(R.layout.right_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			rightDetail = new RightSets();
			t.replace(R.id.right_frame, rightDetail);
			t.commit();
		} else {
			rightDetail = (RightSets)this.getSupportFragmentManager().findFragmentById(R.id.right_frame);
		}
	}
		
	/**
	 * 获取右边设置页面
	 * @return fragment 右边设置页面
	 */
	public RightSets getRightSets() {
		return rightDetail;
	}

	/**
	 * 获取主框架
	 * @return SlidingMenu 主框架
	 */
	public SlidingMenu getMainFrame() {
		return slidingMenu;
	}
	
}
