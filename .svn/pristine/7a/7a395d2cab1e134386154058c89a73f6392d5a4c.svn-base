package com.dfws.shhreader.activity;

import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.database.property.ConfigureProperty;
import com.dfws.shhreader.database.sharedpreferences.SetsKeeper;
import com.dfws.shhreader.database.sharedpreferences.VersionAndTokenHandler;
import com.dfws.shhreader.entity.User;
import com.dfws.shhreader.slidingmenu.fragment.RightSets;
import com.dfws.shhreader.utils.MD5Utils;
import com.dfws.shhreader.utils.StringUtils;

import android.app.Application;
import android.widget.Toast;
/**
 * <h2>应用实例</h2>
 * <pre>应用相关信息</pre>
 * @author Eilin.Yang
 * @since 2013-10-21
 * @version v1.0
 */
public class AppInstance extends Application {

	private static AppInstance instance=null;
	/**
	 * 当前用户
	 */
	public User user;
	/**
	 * 用户票据
	 */
	public String pass_token;
	/**
	 * 推送票据
	 */
	public String Push_token;
	/**
	 * 登录是否有效
	 */
	public boolean isVialid;
	public static String client_id="";
	/***
	 * 应用启动状态，是否为第一次启动
	 */
	public static boolean launcher_state=false;
	
	
	public AppInstance(){
		
	}
	/**
	 * 获取应用实例
	 * @return
	 */
	public synchronized AppInstance getInstance() {
		if (null==instance) {
			instance=(AppInstance)getApplicationContext();
		}
		return instance;
	}
	@Override
	public void onCreate() {	
		super.onCreate();
		user=new User();
		FrameConfigure.loading_type=SetsKeeper.readLoadType(this);
		FrameConfigure.reading_type=SetsKeeper.readReadType(this);
		FrameConfigure.push_state=SetsKeeper.readPushState(this);
		FrameConfigure.ring_state=SetsKeeper.readPushRingState(this);
		Push_token=MD5Utils.MD5(StringUtils.getIMEI(this));
		launcher_state=SetsKeeper.readLauncherState(this);
	}
	
	
		// 程序退出标记
	public static boolean isProgramExit = false;

	public void setExit(boolean exit) {

		isProgramExit = exit;

	}

	public boolean isExit() {

		return isProgramExit;

	}
	/**
	 * 是否登录
	 * @return
	 */
	public boolean isLogin() {
		return user!=null&&user.isHasLogin()&&!StringUtils.isEmpty(pass_token);
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getPass_token() {
		return pass_token;
	}
	public void setPass_token(String pass_token) {
		this.pass_token = pass_token;
	}
	public String getPush_token() {
		return Push_token;
	}
	public void setPush_token(String push_token) {
		Push_token = push_token;
	}		
}
