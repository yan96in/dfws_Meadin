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
package com.dfws.shhreader.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.dfws.shhreader.configures.NetConfigure;
import com.dfws.shhreader.entity.Feedback;
import com.dfws.shhreader.entity.Versions;
import com.dfws.shhreader.net.utils.HttpTools;
import com.dfws.shhreader.utils.StringUtils;

import android.content.Context;

/**
 * @file： RestsController.java
 * @Page： com.dfws.shhreader.controllers
 * @description： 意见反馈  更新设备 检查更新 控制器
 * @since： 2013-11-13
 * @author： Administrator
 */
public class RestsController extends AbsController {
	private Context context;
	Feedback  feedback;
	public RestsController(Context context){
		this.context=context;
		feedback=new Feedback();
	}
	/**
	 * 检查 更新
	 * @param device_type
	 * @return  true 可以更新，false  没得新版本
	 */
	public Versions checkVersion(int device_type){
/*		if (!checkNetStattus(context)) {
			return null;
		}*/
		Versions versions=null;
		String strResult="";
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("device_type", device_type+""));
		String request = NetConfigure.common_checkVersion_interface;
		strResult = HttpTools.getJsonString(nv, request);
		if (!StringUtils.isEmpty(strResult)) {
			try {
				JSONObject jObject=new JSONObject(strResult);
				if (!StringUtils.isEmpty(jObject)) {
					if (!jObject.has("code")) {
						versions=new Versions();
						versions.setLatest_version(jObject.getString("latest_version"));
                        versions.setUpdate_log(jObject.getString("update_log"));
                        versions.setApp_url(jObject.getString("app_url"));
					}else {
						NetConfigure.error_code=jObject.getString("code");
						NetConfigure.error_msg=jObject.getString("message");
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return versions;
			}
		}
		return versions;
		
	}
	/**
	 * 
	 * @param contact 联系方式
	 * @param content  意见反馈
	 * @param device_model   设备硬件号
	 * @param device_system   设备系统，
	 * @param app_version   应用版本
	 * @param pass_token    登录用户的票据  （可不填）
	 * @param push_token    设备推送码    （可不填填）
	 * @return
	 */
	public Boolean feedback(
			String contact,
			String content,
			String device_model,
			String device_system,
			String app_version){
		if (!checkNetStattus(context)) {
			return null;
		}
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("contact", contact));
		nv.add(new BasicNameValuePair("content", content));
		nv.add(new BasicNameValuePair("device_model", device_model));
		nv.add(new BasicNameValuePair("device_system", device_system));
		nv.add(new BasicNameValuePair("app_version", app_version));
		String request = NetConfigure.common_feedback_interface;
		String strResult = HttpTools.getJsonString(nv, request);
		if (!StringUtils.isEmpty(strResult)) {
			try {
				JSONObject jObject=new JSONObject(strResult);
				if (!StringUtils.isEmpty(jObject)) {
					if (!jObject.has("code")) {
						String dateTimeString=jObject.getString("create_at");
						if (dateTimeString!=null) {
							return true;
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
		
	}
	

}
