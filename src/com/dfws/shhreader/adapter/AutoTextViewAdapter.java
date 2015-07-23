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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.dfws.shhreader.R;
import com.dfws.shhreader.entity.User;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

/**
 * @file： AutoTextViewAdapter.java
 * @Page： com.dfws.shhreader.adapter
 * @description：  用于登录清楚数据
 * @since： 2013-11-5
 * @author： Administrator
 */
public class AutoTextViewAdapter extends BaseAdapter implements Filterable {

   public List<User> mList;  
   private Context context; 
   private LayoutInflater inflater;
   Filter mFilter;
   
   
   
 public AutoTextViewAdapter(Context context,List<User> list) {  
     this.context = context;
     if (list!=null&&list.size()>0) {
    	 this. mList = removeDuplicateWithOrder(list);
	}
     else {
    	 this. mList=list;
	}
     this.inflater=LayoutInflater.from(context);
 }  

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList==null ? 0 :mList.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		  return mList == null ? null : mList.get(arg0); 
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public String getUserName(int position) {
		// TODO Auto-generated method stub
		return mList == null ? null : mList.get(position).getName();
	}
	

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		  ViewUser viewMsg=null;
			 if (convertView==null) {
				viewMsg=new ViewUser();
				convertView=inflater.inflate(R.layout.layout_login_item, null);
				viewMsg.userTx=(TextView) convertView.findViewById(R.id.login_user_item);
			    convertView.setTag(viewMsg);
			}else {
				viewMsg=(ViewUser)convertView.getTag();
			}
			User user=mList.get(position);
			if (user!=null) {
				viewMsg.userTx.setText(user.getName());
			}
			return convertView;
	}

	
	private class ViewUser{
		//账号和密码
		TextView userTx,passTx;		
	}	

	  public Filter getFilter() {  
	        if (mFilter == null) {  
	            mFilter = new MyFilter();  
	        }  
	        return mFilter;  
	    }  
	    
	    private class MyFilter extends Filter {  
	    	
	    	@Override  
	    	protected FilterResults performFiltering(CharSequence constraint) {  
	    		FilterResults results = new FilterResults();  
	    		if (mList == null) {  
	    			mList = new ArrayList<User>();  
	    		}  
	    		results.values = mList;  
	    		results.count = mList.size();  
	    		return results;  
	    	}  
	    	
	    	@Override  
	    	protected void publishResults(CharSequence constraint, FilterResults results) {  
	    		if (results.count > 0) {  
	    			notifyDataSetChanged();  
	    		} else {  
	    			notifyDataSetInvalidated();  
	    		}  
	    	}  
	    	
	    }  
	    //去掉重复
	public static List removeDuplicateWithOrder(List list) {
	        Set set = new HashSet();
	        List newList = new ArrayList();
	        for (Iterator iter = list.iterator(); iter.hasNext();) {
	            Object element = iter.next();
	            if (set.add(element))
	                newList.add(element);
	        }
	        return newList;
	    }
}
