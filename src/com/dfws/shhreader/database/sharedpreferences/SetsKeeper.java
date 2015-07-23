package com.dfws.shhreader.database.sharedpreferences;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.dfws.shhreader.activity.AppInstance;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.database.property.VersionProperty;
import com.dfws.shhreader.utils.DateTimeUtils;
/**
 * 该类用于保存数据到SharePreference，并提供读取功能
 * @author Eilin.Yang
 *
 */
public class SetsKeeper {
	/**
	 * 主要数据
	 */
	private static final String SETS_PREFERENCES_NAME = "sets_keeper";
	
	/**
	 * 保存上一次刷新时间
	 * @param context 上下文环境
	 * @param time 时间。
	 * @param column 栏目。
	 */
	public static void keepRefreshTime(Context context,String time,int column) {
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		switch (column) {
		case 1:
			editor.putString("time_news", time);
			break;

		case 2:
			editor.putString("time_img", time);
			break;
		case 3:
			editor.putString("time_report", time);
			break;

		case 4:
			editor.putString("time_figure", time);
			break;
		case 5:
			editor.putString("time_tec", time);
			break;

		default:
			editor.putString("time_news", time);
			break;
		}
		editor.commit();
	}
	
	/**
	 * 清空刷新时间
	 * @param context
	 */
	public static void clearRefreshTime(Context context){
	    SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
	    Editor editor = pref.edit();
	    editor.remove("time_news");
	    editor.remove("time_img");
	    editor.remove("time_report");
	    editor.remove("time_figure");
	    editor.remove("time_tec");
	    editor.commit();
	}

	/**
	 * 获取是否第一次启动应用
	 * @param context
	 * @param column 栏目,1,2,3,4,5>>新闻，图片，报告，人物，新技术
	 * @return 上一次刷新时间
	 */
	public static String readRefreshTime(Context context,int column){
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		String day=DateTimeUtils.getLongDateTime(true);
		String time="";
		switch (column) {
		case 1:
			time=pref.getString("time_news", day);
			break;

		case 2:
			time=pref.getString("time_img", day);
			break;
		case 3:
			time=pref.getString("time_report", day);
			break;

		case 4:
			time=pref.getString("time_figure", day);
			break;
		case 5:
			time=pref.getString("time_tec", day);
			break;

		default:
			time=pref.getString("time_news", day);
			break;
		}
		return time;
	}
	
	/**
	 * 保存是否第一次启动应用
	 * @param context 上下文环境
	 * @param launcher_state true为启动过，false为未启动。
	 */
	public static void keepLauncherState(Context context,boolean launcher_state) {
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putBoolean("launcher_state", launcher_state);
		editor.commit();
	}
	
	/**
	 * 清空是否第一次启动应用
	 * @param context
	 */
	public static void clearLauncherState(Context context){
	    SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
	    Editor editor = pref.edit();
	    editor.remove("launcher_state");
	    editor.commit();
	}

	/**
	 * 获取是否第一次启动应用
	 * @param context
	 * @return true为启动过，false为未启动。
	 */
	public static boolean readLauncherState(Context context){
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		return pref.getBoolean("launcher_state", false);
	}
	
	/**
	 * 保存推送铃声状态到SharedPreferences
	 * @param context 上下文环境
	 * @param ring_state true为关闭推送铃声，false为开启推送铃声。
	 */
	public static void keepPushRingState(Context context,boolean ring_state) {
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putBoolean("ring_state", ring_state);
		editor.commit();
	}
	
	/**
	 * 清空sharePreference 的推送铃声状态信息
	 * @param context
	 */
	public static void clearPushRingState(Context context){
	    SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
	    Editor editor = pref.edit();
	    editor.remove("ring_state");
	    editor.commit();
	}

	/**
	 * 从SharedPreferences读取推送状态信息
	 * @param context
	 * @return true为关闭推送铃声，false为开启推送铃声。
	 */
	public static boolean readPushRingState(Context context){
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		return pref.getBoolean("ring_state", true);
	}
	
	/**
	 * 保存推送状态到SharedPreferences
	 * @param context 上下文环境
	 * @param push_state 是否开启新闻推送，true为开启，false为关闭。
	 */
	public static void keepPushState(Context context,boolean push_state) {
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putBoolean("push_state", push_state);
		editor.commit();
	}
	
	/**
	 * 清空sharePreference 的推送状态信息
	 * @param context
	 */
	public static void clearPushState(Context context){
	    SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
	    Editor editor = pref.edit();
	    editor.remove("push_state");
	    editor.commit();
	}

	/**
	 * 从SharedPreferences读取推送状态信息
	 * @param context
	 * @return 是否开启新闻推送，true为开启，false为关闭。
	 */
	public static boolean readPushState(Context context){
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		return pref.getBoolean("push_state", true);
	}
	
	/**
	 * 保存字体大小到SharedPreferences
	 * @param context 上下文环境
	 * @param size 字体大小。有0：小，1：中，2：大，3：超大.
	 */
	public static void keepFontSize(Context context,int size) {
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putInt("font_size", size);
		editor.commit();
	}
	
	/**
	 * 清空sharePreference 的字体大小信息
	 * @param context
	 */
	public static void clearFontSize(Context context){
	    SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
	    Editor editor = pref.edit();
	    editor.remove("font_size");
	    editor.commit();
	}

	/**
	 * 从SharedPreferences读取字体大小信息
	 * @param context
	 * @return 有0：小，1：中，2：大，3：超大.
	 */
	public static int readFontSize(Context context){
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		return pref.getInt("font_size", 2);
	}
	
	
	
	/**
	 * 保存阅读模式到SharedPreferences
	 * @param context 上下文环境
	 * @param read_type 阅读模式。有FrameConfigure.TYPE_DAY和FrameConfigure.TYPE_NIGHT两种
	 */
	public static void keepReadType(Context context,int read_type) {
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putInt("read_type", read_type);
		editor.commit();
	}
	
	/**
	 * 清空sharePreference 的阅读模式信息
	 * @param context
	 */
	public static void clearReadType(Context context){
	    SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
	    Editor editor = pref.edit();
	    editor.remove("read_type");
	    editor.commit();
	}

	/**
	 * 从SharedPreferences读取阅读模式信息
	 * @param context
	 * @return 有FrameConfigure.TYPE_DAY和FrameConfigure.TYPE_NIGHT
	 */
	public static int readReadType(Context context){
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		return pref.getInt("read_type", FrameConfigure.TYPE_DAY);
	}
	
	/**
	 * 保存加载模式到SharedPreferences
	 * @param context 上下文环境
	 * @param load_type 阅读模式。有FrameConfigure.TYPE_IMG_NULL、FrameConfigure.TYPE_IMG_ALLOW、FrameConfigure.TYPE_IMG_SMART三种
	 */
	public static void keepLoadType(Context context,int load_type) {
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putInt("load_type", load_type);
		editor.commit();
	}
	
	/**
	 * 清空sharePreference 的加载模式信息
	 * @param context
	 */
	public static void clearLoadType(Context context){
	    SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
	    Editor editor = pref.edit();
	    editor.remove("load_type");
	    editor.commit();
	}

	/**
	 * 从SharedPreferences读取加载模式信息
	 * @param context
	 * @return 有FrameConfigure.TYPE_IMG_NULL、FrameConfigure.TYPE_IMG_ALLOW、FrameConfigure.TYPE_IMG_SMART
	 */
	public static int readLoadType(Context context){
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		return pref.getInt("load_type", FrameConfigure.TYPE_IMG_SMART);
	}
}
