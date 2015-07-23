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
package com.dfws.shhreader.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.dfws.shhreader.activity.AppInstance;
import com.dfws.shhreader.configures.NetConfigure;
import com.dfws.shhreader.net.utils.HttpTools;
import com.dfws.shhreader.utils.StringUtils;

/**<h2>评论操作 <h2>
 * <pre> </pre>
 * @author 东方网升Eilin.Yang
 * @since 2013-11-15
 * @version  v1.0
 * @modify ""
 */
public class CommentController extends AbsController {

	/**
	 * 当前上下文
	 */
	private Context context;
	/**
	 * 应用实例
	 */
	private AppInstance appInstance;
	
	public CommentController(Context context){
		this.context=context;
		appInstance=(AppInstance)context.getApplicationContext();
	}
	
	/**
	 *<pre>从服务器获取新闻评论条数</pre>
	 * @param news_id 新闻id
	 * @return
	 */
	public int getNewsCommentCount(int news_id){
		if (!checkNetStattus(context)) {
			return -1;
		}
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("id", news_id + ""));
		if (!StringUtils.isEmpty(appInstance.pass_token)) {
			nv.add(new BasicNameValuePair("pass_token", appInstance.pass_token));
		}
		int n=0;
		String request = NetConfigure.news_detail_interface;
		String strResult = HttpTools.getJsonString(nv, request);
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject jObject=null;
			try {
				jObject=new JSONObject(strResult);
				if (!StringUtils.isEmpty(jObject)) {
					if (!jObject.has("code")) {
						n=jObject.getInt("comment_count");				
					}else {
						NetConfigure.error_code=jObject.getString("code");
						NetConfigure.error_msg=jObject.getString("message");
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}
		}
		
		return n;
	}
	
}
