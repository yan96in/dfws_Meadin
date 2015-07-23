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
package com.dfws.shhreader.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.TextSize;
import android.widget.LinearLayout;

import com.dfws.shhreader.R;
import com.dfws.shhreader.activity.personalcenter.BaseActivity;
import com.dfws.shhreader.utils.StringUtils;
import com.umeng.analytics.MobclickAgent;

/**<h2> 查看原文<h2>
 * <pre> </pre>
 * @author 东方网升Eilin.Yang
 * @since 2013-11-12
 * @version v1.0 
 * @modify ""
 */
public class NewsResourceWebActivity extends BaseActivity {
	
	private WebView mWebView;
	private WebSettings settings;
	private LinearLayout linear_loading;
	/* (non-Javadoc)
	 * @see com.dfws.shhreader.activity.personalcenter.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		String url=getIntent().getStringExtra("url");
		if (StringUtils.isEmpty(url)) {
			finish();
		}
		setContentView(R.layout.layout_news_resource_web);
		mWebView=(WebView)findViewById(R.id.wv_news_resource);
		settings=mWebView.getSettings();
		settings.setAllowFileAccess(true);//准许访问文件
		settings.setBuiltInZoomControls(true);//取消缩放控件
		settings.setTextSize(TextSize.NORMAL);
		settings.setSupportZoom(true);//取消缩放
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);//显示模式，适应频幕
		settings.setDefaultTextEncodingName("utf-8");//设置文本编码
		settings.setJavaScriptEnabled(true);
		mWebView.setScrollBarStyle(0);
		mWebView.setVerticalScrollBarEnabled(true);
		mWebView.setHorizontalScrollBarEnabled(true);
		mWebView.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				return super.shouldOverrideUrlLoading(view, url);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
			
		});
		mWebView.setWebChromeClient(new WebChromeClient(){

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
			}
			
		});
		mWebView.loadUrl(url);
	}
	
	public void onViewClick(View view) {
		finish();
	}

	@Override
	public void onResume() {
		super.onResume();
		
		/**
		 * 此处调用基本统计代码
		 */
		MobclickAgent.onResume(this);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		/**
		 * 此处调用基本统计代码
		 */
		MobclickAgent.onPause(this);
	}
}
