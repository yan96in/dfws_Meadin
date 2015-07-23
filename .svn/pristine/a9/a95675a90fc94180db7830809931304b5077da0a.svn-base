/**
 * Copyright © 2013 MeadinReader www.veryeast.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
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

import com.dfws.shhreader.R;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.controllers.NewsController;
import com.dfws.shhreader.entity.Comment;
import com.dfws.shhreader.utils.DateTimeUtils;
import com.dfws.shhreader.utils.StringUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**<h2> <h2>
 * <pre> </pre>
 * @author 东方网升Eilin.Yang
 * @since 2013-11-15
 * @version 
 * @modify ""
 */
public class CommentAdapter extends BaseAdapter {

	private Context mContext;
	/**
	 * 评论操作控制器
	 */
	private NewsController controller;
	/**
	 * 评论集合
	 */
	private List<Comment> comments;
	private LayoutInflater inflater;
	/**
	 * <pre></pre>
	 * @param context 场景
	 * @param comments 评论
	 */
	public CommentAdapter(Context context,List<Comment> comment_list) {
		if (comment_list==null) {
			comments=new ArrayList<Comment>();
		}else {
			comments=comment_list;
		}
		mContext=context;
		inflater=LayoutInflater.from(context);
	}
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return comments.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Comment getItem(int position) {
		// TODO Auto-generated method stub
		return comments.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	/**
	 * <pre>获取评论对象的id</pre>
	 * @param position 对象在集合的位置
	 * @return
	 */
	public long getObjectId(int position) {
		// TODO Auto-generated method stub
		if (position<0||position>getCount()-1) {
			return -1;
		}
		return comments.get(position).getId();
	}
	
	/**
	 * <pre>获取新闻id</pre>
	 * @param position 位置
	 * @return
	 */
	public long getNewsId(int position) {
		// TODO Auto-generated method stub
		if (position<0||position>getCount()-1) {
			return -1;
		}
		return comments.get(position).getNewsId();
	}
	
	/**
	 * <pre>添加评论</pre>
	 * @param comment 单个评论
	 */
	public void addItem(Comment comment) {
		if (comment!=null) {
			comments.add(0,comment);
		}
	}
	
	/**
	 * <pre>添加评论</pre>
	 * @param comment 单个评论
	 */
	public void addItems(List<Comment> list) {
		if (list!=null) {
			comments.addAll(list);
			notifyDataSetChanged();
		}
	}
	
	/**
	 * <pre>修改某条评论</pre>
	 * @param comment 单个评论
	 */
	public void setItem(int position,Comment comment) {
		if (-1<position&&position<getCount()&&comment!=null) {
			comments.set(position,comment);
			notifyDataSetChanged();
		}
	}
	
	/**
	 * <pre>清除评论</pre>
	 * @param comment 单个评论
	 */
	public void clear() {
		if (comments!=null&&getCount()>0) {
			comments.clear();
		}
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewCache viewCache=null;
		if (convertView==null) {
			if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
				convertView=inflater.inflate(R.layout.night_layout_comment_item, null);
			}else {
				convertView=inflater.inflate(R.layout.layout_comment_item, null);
			}
			viewCache=new ViewCache();
			viewCache.txt_comment_item_name=(TextView)convertView.findViewById(R.id.txt_comment_item_name);
			viewCache.txt_comment_item_time=(TextView)convertView.findViewById(R.id.txt_comment_item_time);
			viewCache.txt_comment_item_text=(TextView)convertView.findViewById(R.id.txt_comment_item_text);
			convertView.setTag(viewCache);
		}
		else {
			viewCache=(ViewCache)convertView.getTag();
		}
		Comment comment=comments.get(position);
		if (comment!=null) {
			String time=DateTimeUtils.getPutDate(comment.getCreate_time());
			viewCache.txt_comment_item_name.setText(comment.getUser_name());
			viewCache.txt_comment_item_time.setText(time);
			viewCache.txt_comment_item_text.setText(comment.getText());
		}
		return convertView;
	}
	
	private class ViewCache{
		/**用户*/
		private TextView txt_comment_item_name;
		/**发布时间*/
		private TextView txt_comment_item_time;
		/**内容*/
		private TextView txt_comment_item_text;
	}

}
