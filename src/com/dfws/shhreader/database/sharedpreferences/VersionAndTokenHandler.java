package com.dfws.shhreader.database.sharedpreferences;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.dfws.shhreader.activity.AppInstance;
import com.dfws.shhreader.database.property.VersionProperty;
/**
 * 该类用于保存数据到SharePreference，并提供读取功能
 * @author Eilin.Yang
 *
 */
public class VersionAndTokenHandler {
	/**
	 * 版本控制数据
	 */
	private static final String VERSION_PREFERENCES_NAME = "version_keeper";
	/**
	 * 主要数据
	 */
	private static final String MAIN_PREFERENCES_NAME = "main_keeper";
	/**
	 * 保存VersionProperty到SharedPreferences
	 * @param context Activity 上下文环境
	 * @param version VersionProperty
	 */
	public static void keepVersionInfo(Context context,VersionProperty version) {
		if (null==version) {
			return;
		}
		SharedPreferences pref = context.getSharedPreferences(VERSION_PREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putInt("id", version.id);
		editor.putString("idstr", version.idstr);
		editor.putString("name", version.name);
		editor.putLong("size", version.size);
		editor.putString("url", version.url);
		editor.putString("path", version.path);
		editor.putString("version", version.version);
		editor.putInt("version_code", version.version_code);
		editor.putString("update_info", version.update_info);
		editor.commit();
	}
	/**
	 * 清空sharepreference 版本信息
	 * @param context
	 */
	public static void clearVersionInfo(Context context){
	    SharedPreferences pref = context.getSharedPreferences(VERSION_PREFERENCES_NAME, Context.MODE_PRIVATE);
	    Editor editor = pref.edit();
	    editor.clear();
	    editor.commit();
	}

	/**
	 * 从SharedPreferences读取版本信息
	 * @param context
	 * @return VersionProperty
	 */
	public static VersionProperty readVersionInfo(Context context){
		VersionProperty property=new VersionProperty();
		SharedPreferences pref = context.getSharedPreferences(VERSION_PREFERENCES_NAME, Context.MODE_PRIVATE);
		property.id=pref.getInt("id", -1);
		property.idstr=pref.getString("idstr", "-1");
		property.name=pref.getString("name", "MeadinReader");
		property.size=pref.getLong("size", 0);
		property.url=pref.getString("url", "");
		property.path=pref.getString("path", "-1");
		property.version=pref.getString("version", "");
		property.version_code=pref.getInt("version_code", 1);
		property.update_info=pref.getString("update_info", "");
		return property;
	}
	
	/**
	 * 保存userToken到SharedPreferences
	 * @param context Activity 上下文环境
	 * @param token userToken
	 */
	public static void keepUserToken(Context context,String token) {
		if (null==token) {
			return;
		}
		SharedPreferences pref = context.getSharedPreferences(MAIN_PREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString((((AppInstance)context.getApplicationContext()).isLogin() ? ((AppInstance)context.getApplicationContext()).getUser().getId():"pass_token"), token);
		editor.commit();
	}
	
	/**
	 * 清空sharePreference 用户信息
	 * @param context
	 */
	public static void clearUserToken(Context context){
	    SharedPreferences pref = context.getSharedPreferences(MAIN_PREFERENCES_NAME, Context.MODE_PRIVATE);
	    Editor editor = pref.edit();
	    editor.clear();
	    editor.commit();
	}

	/**
	 * 从SharedPreferences读取用户信息
	 * @param context
	 * @return UserToken
	 */
	public static String readUserToken(Context context){
		String token="";
		SharedPreferences pref = context.getSharedPreferences(MAIN_PREFERENCES_NAME, Context.MODE_PRIVATE);
		if (((AppInstance)context.getApplicationContext()).isLogin()) {
			token=pref.getString(((AppInstance)context.getApplicationContext()).getUser().getId(), "");
		}else {
			token=pref.getString("pass_token", "");
		}
		return token;
	}
}
