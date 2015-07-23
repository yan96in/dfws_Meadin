package com.dfws.shhreader.adapter;

import java.util.ArrayList;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;
import com.dfws.shhreader.slidingmenu.fragment.FigureFragment;
import com.dfws.shhreader.slidingmenu.fragment.ImageFragment;
import com.dfws.shhreader.slidingmenu.fragment.NewsFragment;
import com.dfws.shhreader.slidingmenu.fragment.ReportFragment;
import com.dfws.shhreader.slidingmenu.fragment.TechniqueFragment;
/**
 * 加载MainFragmentAdapter  页面数据源
 * @author Administrator
 *
 */

public class MainFragmentAdapter extends FragmentPagerAdapter{
	private ArrayList<Fragment> fragments;
	private NewsFragment page1;
	private ImageFragment page2;
	private ReportFragment page3;
	private FigureFragment page4;
	private TechniqueFragment page5;
	public MainFragmentAdapter(FragmentManager fm) {
		super(fm);
		fragments=new ArrayList<Fragment>(5);
		page1 = new NewsFragment();
		page2 = new ImageFragment();
		page3 =new ReportFragment();
		page4=new FigureFragment();
		page5=new TechniqueFragment();
		fragments.add(page1);
		fragments.add(page2);
		fragments.add(page3);
		fragments.add(page4);
		fragments.add(page5);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public Fragment getItem(int position) {
		Log.i("MainFragment", "getItem>>position="+position);
		Fragment fragment = null;
		switch (position) {
		case 4:
			fragment=page5;
			break;
		case 3:
			fragment=page4;
			break;
		case 2:
			fragment=page3;
			break;
		case 1:
			fragment=page2;
			break;
		case 0:
		default:
			fragment=page1;
			break;
		}
		return fragment;
	}	

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub		
		Log.i("MainFragment", "setPrimaryItem>> position="+position);
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		Log.i("MainFragment", "instantiateItem>> position="+position);
		return super.instantiateItem(container, position);
	}
	public ArrayList<Fragment> getItemList() {
		return fragments;
	}
	
	@Override
	public int getItemPosition(Object object) {
	    return POSITION_NONE;
	}
}
