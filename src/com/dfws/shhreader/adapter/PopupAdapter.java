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

import java.util.List;

import com.dfws.shhreader.entity.User;

import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @file： PopupAdapter.java
 * @Page： com.dfws.shhreader.adapter
 * @description： 
 * @since： 2013-11-21
 * @author： Administrator
 */
public class PopupAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    List<User> names;
    

	public PopupAdapter() {
		// TODO Auto-generated method stub

	}
	public PopupAdapter(Context context){
		this.context=context;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return names.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
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
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder=null;
		final User name=names.get(position);
		if (convertView==null) {
			layoutInflater=layoutInflater.from(context);
			//convertView=layoutInflater.inflate(R.layout.layoutlogin_popwindow, null);
		}
		return null;
	}
	  class Holder {  
		 TextView tv;  
		 ImageButton ibtn;               
		  void setId(int position) {  
		     tv.setId(position);  
		     ibtn.setId(position);  
		    }  
		}  
}
