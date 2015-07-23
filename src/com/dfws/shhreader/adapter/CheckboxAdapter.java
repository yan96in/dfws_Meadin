package com.dfws.shhreader.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.dfws.shhreader.R;
import com.dfws.shhreader.entity.News;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 
 * @file： CheckboxAdapter.java
 * @Page： com.dfws.shhreader.adapter
 * @description：   chebox 
 * @since： 2013-11-13
 * @author： Administrator
 */
public class CheckboxAdapter extends BaseAdapter {
	
	Context context;
	ArrayList<HashMap<String, Object>> listData;	
	//记录checkbox的状态
	HashMap<Integer, Boolean> state = new HashMap<Integer, Boolean>();		
	ArrayList<News> listnews;//新闻
	ListView listView;
	//构造函数
	public CheckboxAdapter(Context context,	ListView listview,ArrayList<News> listnews) {
		this.context = context;
		this.listnews = listnews;	
		this.listView=listView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	// 重写View
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
	
		LayoutInflater mInflater = LayoutInflater.from(context);
		
		convertView = mInflater.inflate(R.layout.layout_msg_listview, null);
		ViewMsg viewMsg=null;
		viewMsg.msg_item_title = (TextView) convertView.findViewById(R.id.msg_title);
		
		viewMsg.msg_item_delete= (CheckBox) convertView.findViewById(R.id.mes_checkbox);		
		

		News news=listnews.get(position);
		viewMsg.msg_item_title.setText(news.getTitle());
		viewMsg.msg_item_delete.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					state.put(position, isChecked);					
				} else {
					state.remove(position);				
				}
			}
		});
		viewMsg.msg_item_delete.setChecked((state.get(position) == null ? false : true));
		return convertView;
	}
	private class ViewMsg{
		TextView msg_item_title;//标题
		CheckBox msg_item_delete;//复选框
	}
}