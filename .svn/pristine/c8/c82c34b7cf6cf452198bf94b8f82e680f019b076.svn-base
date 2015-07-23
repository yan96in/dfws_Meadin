/** 
* Copyright © 2013 www.veryeast.com 
* 
* Licensed under the Apache License, Version 2.0 (the "License"); 
* you may not use this file except in compliance with the License. 
* You may obtain a copy of the License at 
* 
* http://www.apache.org/licenses/LICENSE-2.0 
* 
* Unless required by applicable law or agreed to in writing, software 
* distributed under the License is distributed on an "AS IS" BASIS, 
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
* See the License for the specific language governing permissions and 
* limitations under the License. 
*/
package com.dfws.shhreader.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dfws.shhreader.entity.MediaInfo;

/**
 * @file： ViewPageAdapter.java
 * @Page： com.dfws.shhreader.ui
 * @description： 
 * @since： 2013-11-25
 * @author： Administrator
 */
public class ViewPageAdapter extends PagerAdapter {

	private Context context;
	List<MediaInfo> mediaInfo;
	private List<ViewPagerItemView> views;
	public  int count;
	
	public ViewPageAdapter(Context context,List<MediaInfo> mediaInfo){
		this.context=context;
		if (mediaInfo!=null) {
			this.mediaInfo=mediaInfo;
			int n=mediaInfo.size();
			ViewPagerItemView view=null;
			views=new ArrayList<ViewPagerItemView>(n);
			for (MediaInfo mediaInfo2 : mediaInfo) {
				if (mediaInfo2!=null) {
					view=new ViewPagerItemView(context);
					view.setData(mediaInfo2.getSrc());
					views.add(view);
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//return count=mediaInfo.size();
		return views.size();
	}
	
	
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}

	public String getItemAlt(int position) {
		return mediaInfo.get(position).getAlt();
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return ((ViewPageAdapter) mediaInfo).getItemPosition(object);
	}
	
	public MediaInfo getItem(int position) {
		return mediaInfo.get(position);
	}


	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return ((ViewPageAdapter) mediaInfo).getPageTitle(position);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#isViewFromObject(android.view.View, java.lang.Object)
	 */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
	//这里进行回收，当我们左右滑动的时候，会把早期的图片回收掉.
		@Override
		public void destroyItem(View container, int position, Object object) {
			ViewPagerItemView itemView=views.get(position);
			itemView.recycle();
			((ViewPager) container).removeView(itemView);
		}

		//这里就是初始化ViewPagerItemView.如果ViewPagerItemView已经存在,
		//重新reload，不存在new一个并且填充数据.
		@Override
		public Object instantiateItem(View container, int position) {	
			ViewPagerItemView itemView=views.get(position);
			itemView.showImage();
				((ViewPager) container).addView(itemView);
			return itemView;
		}

			

}
