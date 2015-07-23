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
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import com.dfws.shhreader.activity.AppInstance;
import com.dfws.shhreader.configures.NetConfigure;
import com.dfws.shhreader.database.tools.FavoriteDatabaseHelper;
import com.dfws.shhreader.entity.News;
import com.dfws.shhreader.net.utils.HttpTools;
import com.dfws.shhreader.utils.StringUtils;

/**
 * @file： MessageController.java
 * @Page： com.dfws.shhreader.controllers
 * @description：  获取消息，获取，删除收藏  控制器
 * @since： 2013-11-6
 * @author： Administrator
 */
public class CollectionController extends AbsController {
	
	private Context context;//上下文
	private AppInstance appInstance;//应用实例
	private FavoriteDatabaseHelper dataHelper;//数据库操作
	
	public CollectionController(Context context){
		this.context=context;
		appInstance=(AppInstance) context.getApplicationContext();
		dataHelper=new FavoriteDatabaseHelper(context);		
//		dataHelper.open();
	}
	
	/**
	 * 获取收藏列表
	 */
	public ArrayList<News> getCollection(String pass,int page,int size){
		if (!checkNetStattus(context)) {
			return null;
		}
		String strResult="";
		ArrayList<News> newsList = null;
		List<NameValuePair> ln=new ArrayList<NameValuePair>();
		ln.add(new BasicNameValuePair("pass_token", pass));
		ln.add(new BasicNameValuePair("page", page+""));
		ln.add(new BasicNameValuePair("size", size+""));
		String request= NetConfigure.favorites_list_interface;
		strResult = HttpTools.getJsonString(ln, request);
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject  jsonObject=null;
			try {
				jsonObject=new JSONObject(strResult);
				if (!StringUtils.isEmpty(jsonObject)) {
					if (!jsonObject.has("code")) {
    					JSONArray array=jsonObject.getJSONArray("favorites");
    					int n=array.length();
    					newsList=new ArrayList<News>(n);
    					for (int i = 0; i < n; i++) {
                             News news=new News();
    						JSONObject data=array.getJSONObject(i);
    						String time=data.getString("favorited_time");
    						JSONObject newsJson=data.getJSONObject("news");
    						news.setId(newsJson.getInt("id"));
    						news.setIdStr(newsJson.getInt("id")+"");
    					    news.setColumn(newsJson.getInt("column"));
    					    news.setType(newsJson.getInt("type"));
    					    news.setTitle(newsJson.getString("title"));
    					    news.setThumb(newsJson.getString("thumb"));
    					    news.setDigest(newsJson.getString("digest"));
    					    news.setPotime(newsJson.getString("ptime"));
    					    news.setFvtime(time);
    					    newsList.add(news);
						}
					}else {
						NetConfigure.error_code=jsonObject.getString("code");
						NetConfigure.error_msg=jsonObject.getString("message");
					}
				}				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}			
		}
		return newsList;
		
	}
	/**
	 * 收藏
	 */
	public boolean createCollection(int news_id,String favorited_time){
		boolean f=dataHelper.insertFavoriteNews(news_id,favorited_time,1,"");
		if (!checkNetStattus(context)) {
			return (Boolean) null;
		}
		String strResult="";		
		List<NameValuePair> ln=new ArrayList<NameValuePair>();
		ln.add(new BasicNameValuePair("pass_token", appInstance.pass_token));
		ln.add(new BasicNameValuePair("news_id", news_id+""));
		ln.add(new BasicNameValuePair("favorited_time", favorited_time));
		String request= NetConfigure.favorites_create_interface;
		strResult = HttpTools.getJsonString(ln, request);
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject  jsonObject=null;
			try {
				jsonObject=new JSONObject(strResult);
				if (!StringUtils.isEmpty(jsonObject)) {
					if (!jsonObject.has("code")) {
						
					}else {
						NetConfigure.error_code=jsonObject.getString("code");
						NetConfigure.error_msg=jsonObject.getString("message");
					}					
					return  true;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}			
		}
		return f;		
	}
	
	/**
	 * 删除收藏消息
	 */
	public boolean delectCollection(String idString){
		
		boolean f1=dataHelper.removeFavorites(idString);
		if (!checkNetStattus(context)) {
			return f1;
		}
		String strResult="";
		List<NameValuePair> ln=new ArrayList<NameValuePair>();
		ln.add(new BasicNameValuePair("news_ids", idString));
		ln.add(new BasicNameValuePair("pass_token", appInstance.pass_token));
		String request= NetConfigure.favorites_destroy_interface;
		strResult = HttpTools.getJsonString(ln, request);
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject  jsonObject=null;
			try {
				jsonObject=new JSONObject(strResult);
				if (!StringUtils.isEmpty(jsonObject)) {
					if (!jsonObject.has("code")) {
					}else {
						NetConfigure.error_code=jsonObject.getString("code");
						NetConfigure.error_msg=jsonObject.getString("message");
					}					
				}			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return f1;
			}
			
		}
		return f1;
	}
	
	/**
	 * 查询收藏是否存在
	 */
	public boolean queryCollection(String pass,String idString){
		if (!checkNetStattus(context)) {
			return false;
		}
		String strResult="";
		ArrayList<News> newsList = null;
		List<NameValuePair> ln=new ArrayList<NameValuePair>();
		ln.add(new BasicNameValuePair("pass_token", pass));
		ln.add(new BasicNameValuePair("page", 1+""));
		ln.add(new BasicNameValuePair("size", 20+""));
		String request= NetConfigure.favorites_list_interface;
		strResult = HttpTools.getJsonString(ln, request);
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject  jsonObject=null;
			try {
				jsonObject=new JSONObject(strResult);
				if (!StringUtils.isEmpty(jsonObject)) {
					if (!jsonObject.has("code")) {
    					JSONArray array=jsonObject.getJSONArray("favorites");
    					int n=array.length();
    					newsList=new ArrayList<News>(n);
    					for (int i = 0; i < n; i++) {
                             News news=new News();
    						JSONObject data=array.getJSONObject(i);
    						String time=data.getString("favorited_time");
    						JSONObject newsJson=data.getJSONObject("news");
    						news.setId(newsJson.getInt("id"));
    						news.setIdStr(newsJson.getInt("id")+"");
    					    news.setColumn(newsJson.getInt("column"));
    					    news.setType(newsJson.getInt("type"));
    					    news.setTitle(newsJson.getString("title"));
    					    news.setThumb(newsJson.getString("thumb"));
    					    news.setDigest(newsJson.getString("digest"));
    					    news.setPotime(newsJson.getString("ptime"));
    					    news.setFvtime(time);
    					    int id=news.getId();
    					    if (idString==id+"") {
								return false;
							}
    					    return true;
    					   // newsList.add(news);
						}
					}else {
						NetConfigure.error_code=jsonObject.getString("code");
						NetConfigure.error_msg=jsonObject.getString("message");
					}
				}				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}			
		}
		return false;		
	}

	/**
	 * close DB
	 */
	public void closeDB() {
		if (null!=dataHelper) {
			dataHelper.close();
		}
	}
}
