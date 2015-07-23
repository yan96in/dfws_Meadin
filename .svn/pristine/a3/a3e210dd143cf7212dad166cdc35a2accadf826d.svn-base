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
package com.dfws.shhreader.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.dfws.shhreader.R;

/**<h2>popupwindow dialog <h2>
 * <pre> </pre>
 * @author 东方网升Eilin.Yang
 * @since 2013-12-2
 * @version v1.0
 * @modify ""
 */
public class PopuDialog {

	/**
	 * <pre>获取网络状态提示</pre>
	 * @param context 
	 */
	public static PopupWindow getNetStatePopuDialog(final Context context,LayoutInflater inflater){
		
		View view = inflater.inflate(R.layout.layout_netstate_notice,
				null);
		final PopupWindow pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		
		RelativeLayout net_state=(RelativeLayout)view.findViewById(R.id.net_state);
		
		net_state.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.setAction(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
				if (!(context instanceof Activity)) {
					intent.addFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
				}
				context.startActivity(intent);
			}
		});
		
		pop.setFocusable(true);
		pop.setTouchable(true);
		pop.setOutsideTouchable(true);
		pop.setBackgroundDrawable(context.getResources().getDrawable(
				R.drawable.shap_support_pop_bg));
		pop.setAnimationStyle(R.style.pop_anim_style);
		pop.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
					pop.dismiss();
				return false;
			}
		});
		view.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN
						&& keyCode == KeyEvent.KEYCODE_MENU) {
					pop.dismiss();
				}
				return false;
			}
		});
		return pop;
	}
}
