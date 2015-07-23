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
package com.dfws.shhreader.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dfws.shhreader.R;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.entity.Messages;
import com.dfws.shhreader.entity.News;
import com.dfws.shhreader.utils.DateTimeUtils;

import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @file： MsgLigstviewAdpaper.java
 * @Page： com.dfws.shhreader.adapter
 * @description：   获取所有推送信息
 * @since： 2013-11-11
 * @author： Administrator
 */
public class MsgLigstviewAdpaper extends BaseAdapter {
	Context context;
	LayoutInflater inflater;
	ListView listview;
	List<Messages> listnews;//新闻
	
	public MsgLigstviewAdpaper(Context  context,ListView listview,List<Messages> listnews){
		if (listnews!=null) {
			Collections.sort(listnews);
			this.listnews=listnews;
		}else {
			this.listnews=new ArrayList<Messages>();
		}
		this.context=context;
		this.listview=listview;
		
		this.inflater=LayoutInflater.from(context);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.listnews.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Messages getItem(int position) {
		// TODO Auto-generated method stub
		//return this.listnews.get(position);
		if (position<0) {
			return listnews.get(0);
		}
		if (position>listnews.size()-1) {
			return listnews.get(listnews.size()-1);
		}
		return listnews.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */

	public void addItem(int position,Messages news) {
		if (position>getCount()||position<0) {
			listnews.add(news);
		}else {
			listnews.add(position, news);
		}
		Collections.sort(listnews);
		notifyDataSetChanged();
	}
	
	public void addItems(int position,List<Messages> list) {
		if (position>getCount()||position<0) {
			listnews.addAll(list);
		}else {
			listnews.addAll(position, list);
		}
		notifyDataSetChanged();
	}
	
	
	
	public int getLastItemObjectId() {
		return listnews.get(listnews.size()-1).getId();
	}
	
	
	
	public void clearItems() {
		if (listnews.size()>0) {
			listnews.clear();
		}
	}
	
	public int getItemObjectId(int position) {
		return listnews.get(position).getId();
	}
	
	public int getItemObjectType(int position) {
		return listnews.get(position).getType();
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewMsg viewMsg=null;
		 if (convertView==null) {
			viewMsg=new ViewMsg();
			if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
				convertView=inflater.inflate(R.layout.night_layout_mssgage, null);
			}else {
				convertView=inflater.inflate(R.layout.layout_mssgage, null);
			}
			
			viewMsg.titlTextView=(TextView) convertView.findViewById(R.id.msg_textview);
		    viewMsg.dateTextView=(TextView) convertView.findViewById(R.id.msg_date);
		    convertView.setTag(viewMsg);
		}else {
			viewMsg=(ViewMsg)convertView.getTag();
		}
		Log.i("msg=====", "几条数据："+listnews.size());
		Messages news=listnews.get(position);
		if (news!=null) {
			String sort_time=news.getSort_time();
			long deffer=DateTimeUtils.getDiffer00(sort_time);
			int flag=getPutDate(deffer);
			if (position==0) {
				if (flag!=-1) {
					viewMsg.dateTextView.setVisibility(View.VISIBLE);
					if (flag==0) {
						viewMsg.dateTextView.setText("今天");
					}
					if (flag==1) {
						viewMsg.dateTextView.setText("昨天");
					}
					if (flag==2) {
						viewMsg.dateTextView.setText("以往");
					}
				}
			}else if(position>0){
				String last_sort_time=listnews.get(position-1).getSort_time();
				long de=DateTimeUtils.getDiffer00(last_sort_time);
				int lastflag=getPutDate(de);
				if (lastflag==flag) {
					viewMsg.dateTextView.setVisibility(View.GONE);
				}else {
					if (flag!=-1) {
						viewMsg.dateTextView.setVisibility(View.VISIBLE);
						if (flag==0) {
							viewMsg.dateTextView.setText("今天");
						}
						if (flag==1) {
							viewMsg.dateTextView.setText("昨天");
						}
						if (flag==2) {
							viewMsg.dateTextView.setText("以往");
						}
					}
				}
			}
			viewMsg.titlTextView.setText(news.getTitle());
		}	
		return convertView;
	}
	/**
	 * 
	 * @param time long值
	 * @return -1没有时间，0：今天，1：昨天，2：以往
	 */
	private int getPutDate(long time){
		if (time<0) {
			return -1;
		}
		long l=time;
		long hours=l/(60*60*1000);
		
		if (hours<24) {
			return 0;
		}else {
			if (hours<48) {
				return 1;
			}else {
				return 2;
			}
		}
	}
	
	private class ViewMsg{
		//日期和标题
		TextView dateTextView,titlTextView;		
	}	
}
