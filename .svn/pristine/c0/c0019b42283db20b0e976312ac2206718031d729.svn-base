package com.dfws.shhreader.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.dfws.shhreader.activity.AppInstance;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.configures.NetConfigure;
import com.dfws.shhreader.database.sharedpreferences.SetsKeeper;
import com.dfws.shhreader.database.sharedpreferences.VersionAndTokenHandler;
import com.dfws.shhreader.database.tools.UserDatabaseHelper;
import com.dfws.shhreader.entity.UserGEO;
import com.dfws.shhreader.net.utils.HttpTools;
import com.dfws.shhreader.utils.DateTimeUtils;
import com.dfws.shhreader.utils.StringUtils;

/**
 * 
 * <h2>个人中心和设置控制器 <h2>
 * <pre> </pre>
 * @author 东方网升Eilin.Yang
 * @since 2013-10-26
 * @version v1.0
 * @modify ""
 */
public class SetsController extends AbsController {

	/**
	 * 当前上下文
	 */
	private Context context;
	/**
	 * 应用实例
	 */
	private AppInstance appInstance;
	/**
	 * 数据库操作
	 */
	private UserDatabaseHelper dataHelper;
	public SetsController(Context context) {
		this.context=context;
		appInstance=(AppInstance)context.getApplicationContext();
		dataHelper=new UserDatabaseHelper(context);
	}
	
	/**
	 * 
	 *<pre>登录
	 *返回结果
	 *{
     *"userid": "12345",
     *"username": xxxxxx,
     *"pass_token": xxxxxxxxxxxxxxxxxxxxxxx,
	 *}</pre>
	 * @param user_name 用户名
	 * @param password 密码
	 * @return true：登录成功.false:登录失败
	 */
	public int login(String username,String password) {
		if (!checkNetStattus(context)) {
			return 0;
		}
		if (StringUtils.isEmpty(username)||StringUtils.isEmpty(password)||StringUtils.isEmpty(appInstance.client_id)) {
			return 0;
		}
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("username", username));
		nv.add(new BasicNameValuePair("password", password));
		nv.add(new BasicNameValuePair("push_token", appInstance.client_id));
		nv.add(new BasicNameValuePair("device_type", 2 + ""));
		String request = NetConfigure.user_login_interface;
		String strResult = HttpTools.getJsonString(nv, request);
		if (!StringUtils.isEmpty(strResult)) {
			try {
				JSONObject jObject=new JSONObject(strResult);
				if (!StringUtils.isEmpty(jObject)) {
					if (!jObject.has("code")) {
						String uid=jObject.getString("userid");
						appInstance.user.setId(uid);
		/*				String userName=jObject.getString("username");
						if (!StringUtils.isEmpty(userName)&&userName.contains("@")) {
							userName=userName.substring(0, userName.indexOf("@"));
						}*/
						appInstance.user.setName(jObject.getString("username"));
						appInstance.user.setHasLogin(true);
						appInstance.user.setLogintime(DateTimeUtils.getLongDateTime(true));
						appInstance.user.setGeo(new UserGEO());
						appInstance.user.setIp(StringUtils.getIpAddress());
						String token=jObject.getString("pass_token");
						appInstance.pass_token=token;
						VersionAndTokenHandler.keepUserToken(context, token);
						if (dataHelper.isUserExist(uid)) {
							dataHelper.updateUser(appInstance.user, true);
						}else{
							dataHelper.insertUser(appInstance.user);
						}
						return 1;
					}else{
						String code=jObject.getString("code");
						if("2004".equals(code)){
							return -1;
						}else
							return 0;
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
		}
		return 0;
	}
	
	/**
	 * 
	 *<pre>登出</pre>
	 * @return
	 */
	public boolean logout() {
		if (!checkNetStattus(context)) {
			return false;
		}
		if (StringUtils.isEmpty(appInstance.client_id)) {
			return false;
		}
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("pass_token", appInstance.client_id));
		String request = NetConfigure.user_logout_interface;
		String strResult = HttpTools.getJsonString(nv, request);
		if (!StringUtils.isEmpty(strResult)) {
			try {
				JSONObject jObject=new JSONObject(strResult);
				if (!StringUtils.isEmpty(jObject)) {
					if (!jObject.has("code")) {
						appInstance.user.setId(jObject.getString("userid"));
						appInstance.user.setName(jObject.getString("username"));
						appInstance.user.setHasLogin(false);
						appInstance.user.setLogouttime(DateTimeUtils.getLongDateTime(true));
						appInstance.user.setGeo(new UserGEO());
						appInstance.user.setIp(StringUtils.getIpAddress());
						appInstance.pass_token=null;
						VersionAndTokenHandler.keepUserToken(context, "");
						dataHelper.updateUser(appInstance.user, false);
						return true;
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 
	 *<pre>注册
	 *返回结果
	 *{
     *"userid": "12345",
     *"username": xxxxxx,
     *"pass_token": xxxxxxxxxxxxxxxxxxxxxxx,
	 *}</pre>
	 * @param user_name 用户名
	 * @param password 密码
	 * @return true：注册登录成功.false:注册登录失败
	 */
	public boolean register(String username,String password) {
		if (!checkNetStattus(context)) {
			return false;
		}
		if (StringUtils.isEmpty(username)||StringUtils.isEmpty(password)||StringUtils.isEmpty(appInstance.client_id)) {
			return false;
		}
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("username",username));
		nv.add(new BasicNameValuePair("password",password));
		nv.add(new BasicNameValuePair("push_token",appInstance.client_id));
		nv.add(new BasicNameValuePair("device_type",2+""));
		String request = NetConfigure.user_regist_interface;
		String strResult = HttpTools.getJsonString(nv,request);
		if (!StringUtils.isEmpty(strResult)){
			try {
				JSONObject jObject=new JSONObject(strResult);
				if (!StringUtils.isEmpty(jObject)) {
					if (!jObject.has("code")) {
						String uid=jObject.getString("userid");
						appInstance.user.setId(uid);
					/*	String userName=jObject.getString("username");
						if (!StringUtils.isEmpty(userName)&&userName.contains("@")) {
							userName=userName.substring(0, userName.indexOf("@"));
						}*/
						appInstance.user.setName(jObject.getString("username"));
						appInstance.user.setHasLogin(true);
						appInstance.user.setLogintime(DateTimeUtils.getLongDateTime(true));
						appInstance.user.setGeo(new UserGEO());
						appInstance.user.setIp(StringUtils.getIpAddress());
						String token=jObject.getString("pass_token");
						appInstance.pass_token=token;
						VersionAndTokenHandler.keepUserToken(context, token);
//						if (dataHelper.isUserExist(uid)) {
//							dataHelper.UpdateUser(appInstance.user, true);
//						}else
						dataHelper.insertUser(appInstance.user);
						return true;
					}else{
						NetConfigure.error_code=jObject.getString("code");
						NetConfigure.error_msg=jObject.getString("message");
						return false;
					}					
					//System.out.println("msg===="+strResult);
					//return  true;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("msg===="+strResult);
		}
		return false;
	}
	
	/**
	 * <pre>推送设置</pre>
	 * @param msg_state 新闻推送状态。是否开启新闻推送，true为开启，false为关闭。
	 */
	public void pushSets(boolean msg_state){
		if (!checkNetStattus(context)) {
			return;
		}
		if (StringUtils.isEmpty(appInstance.client_id)) {
			return;
		}
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("device_type", 2+""));
		nv.add(new BasicNameValuePair("push_token", appInstance.client_id));
		nv.add(new BasicNameValuePair("enable", (msg_state?1:0)+""));
		String request = NetConfigure.common_pushSetting_interface;
		String strResult = HttpTools.getJsonString(nv, request);
		if (!StringUtils.isEmpty(strResult)) {
			try {
				JSONObject jObject=new JSONObject(strResult);
				if (!StringUtils.isEmpty(jObject)) {
					if (!jObject.has("code")) {
						boolean state =jObject.getBoolean("enable");
						FrameConfigure.push_state=state;
						SetsKeeper.keepPushState(context, state);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
	}
	
	/**
	 * <pre>推送铃声设置</pre>
	 * @param ring_state true为关闭推送铃声，false为开启推送铃声。
	 */
	public void pushRingSets(boolean ring_state){
		if (!checkNetStattus(context)) {
			return;
		}
		if (StringUtils.isEmpty(appInstance.client_id)) {
			return;
		}
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("device_type", 2+""));
		nv.add(new BasicNameValuePair("push_token", appInstance.client_id));
		nv.add(new BasicNameValuePair("mute", (ring_state?0:1) + ""));
		String request = NetConfigure.common_pushSetting_interface;
		String strResult = HttpTools.getJsonString(nv, request);
		if (!StringUtils.isEmpty(strResult)) {
			try {
				JSONObject jObject=new JSONObject(strResult);
				if (!StringUtils.isEmpty(jObject)) {
					if (!jObject.has("code")) {
						boolean state =jObject.getBoolean("mute");
						FrameConfigure.ring_state=state?false:true;
						SetsKeeper.keepPushRingState(context, FrameConfigure.ring_state);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
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
