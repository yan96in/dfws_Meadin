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
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

import com.dfws.shhreader.R;
import com.dfws.shhreader.activity.personalcenter.CollectionActivity;
import com.dfws.shhreader.entity.News;

/**
 * @file： ListviewAdapter.java
 * @Page： com.dfws.shhreader.adapter
 * @description： 收藏  适配器  
 * @since： 2013-11-7
 * @author： Administrator
 */
public class MsgListviewAdapter extends BaseAdapter {

	Context context;
	private LayoutInflater inflater;
	ListView listview;
	boolean [] itemStatus;
	ArrayList<News> listnews;//新闻

	private boolean status=false;
	
	public MsgListviewAdapter(Context  context,ListView listview,ArrayList<News> listnews){
		if (listnews!=null) {
			this.listnews=listnews;
		}else {
			this.listnews=new ArrayList<News>();
		}
		itemStatus=new boolean[this.listnews.size()];
		this.context=context;
		this.listview=listview;
		this.inflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return this.listnews.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.listnews.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void addItem(int position,News news) {
		if (position>getCount()||position<0) {
			listnews.add(news);
		}else {
			listnews.add(position, news);
		}
		notifyDataSetChanged();
	}
	
	public void toggostatus(boolean status){
		this.status=status;
		notifyDataSetChanged();
	}
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewMsg viewMsg=null;
		if (convertView==null) {
			viewMsg=new ViewMsg();
			convertView=inflater.inflate(R.layout.layout_msg_listview, null);
			viewMsg.msg_item_title=(TextView) convertView.findViewById(R.id.msg_title);
		    viewMsg.msg_item_delete=(CheckBox) convertView.findViewById(R.id.mes_checkbox);
		    convertView.setTag(viewMsg);
		}else {
			viewMsg=(ViewMsg)convertView.getTag();
		}
		Log.i("msg", "size----"+listnews.size());
		News news=listnews.get(position);
		viewMsg.msg_item_delete.setVisibility(View.GONE);
		if (status) {
			viewMsg.msg_item_delete.setVisibility(View.VISIBLE);
		}
		final CheckBox cbx=viewMsg.msg_item_delete;
		if (news!=null) {
           viewMsg.msg_item_title.setText(news.getTitle());
           viewMsg.msg_item_delete.setOnCheckedChangeListener(new CheckBoxListener(position));
		   switchs(position, cbx);
			Log.i("msg", "news---"+news.toString());
			convertView.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					switchs(position,cbx);
				}
			});
		}
		return convertView;
	}
	  /**
	   * 获取chebox状态
	   * 
	   * @param position
	   * @param msg_item_delete
	   */
	private void switchs(int position,CheckBox msg_item_delete) {
		 if (itemStatus[position]==true) {
			    msg_item_delete.setChecked(true);
			}else {
				msg_item_delete.setChecked(false);
			}
	}
	/**
	 * 改变选择状态
	 * @param position
	 */
    public  void state(int position){
    	if(itemStatus[position] == true){
			itemStatus[position] = false;
		}else{
			itemStatus[position] = true;
		}
		this.notifyDataSetChanged();	
    }

     /**
      * 
      * 获取选中行
      * @return
      */
    public int [] getSelectIetmIndexes(){
    	if (itemStatus==null || itemStatus.length==0) {
			return new int[0];
		}else {
			int size=itemStatus.length;
			int counter=0;
			for (int i = 0; i < size; i++) {
				if (itemStatus[i]==true) 
					++counter;				
			}
			int[] selectIndexs=new int[counter];
			int index=0;
			for (int i = 0; i < size; i++) {
				selectIndexs[index++]=i;
			}
			return selectIndexs;
		}   	
    }
    //gengxin 
    public void updateView(ArrayList<News> listnews){
    	if (listnews!=null&&listnews.size()>0) {
			this.listnews=listnews;
			itemStatus=new boolean[this.listnews.size()];
		}
    }
    /**
     * check box 
     * 
     */
    class CheckBoxListener  implements OnCheckedChangeListener{
      int position;
		/* (non-Javadoc)
		 * @see android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged(android.widget.CompoundButton, boolean)
		 */
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			//xxxx
			if (isChecked) {
				itemStatus[position]=true;
			}else {
				itemStatus[position]=false;
			}
		}
	private  CheckBoxListener(int position) {
		// TODO Auto-generated method stub
          this.position=position;
	}
    	
    }
	
	private class ViewMsg{
		TextView msg_item_title;//标题
		CheckBox msg_item_delete;//复选框
	}
	
}
