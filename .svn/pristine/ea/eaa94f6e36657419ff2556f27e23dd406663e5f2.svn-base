package com.dfws.shhreader.activity.personalcenter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.dfws.shhreader.R;
import com.dfws.shhreader.activity.AppInstance;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.configures.NetConfigure;
import com.dfws.shhreader.controllers.SetsController;
import com.dfws.shhreader.entity.User;
import com.dfws.shhreader.slidingmenu.fragment.RightSets;
import com.dfws.shhreader.ui.dialog.CustomerToast;
import com.dfws.shhreader.utils.NetWorkUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * <h2>注册</h2>
 * @author Eilin.Yang
 * @since 2013-10-18
 * @version v1.0
 */
public class RegisterActivity extends BaseActivity {
   private EditText reg_userid_etxt,reg_password_etxt;//文本框
   private TextView reg_loginbtn; //注册
   private TextView reg_regist_iv;//切换到登录
   private  SetsController setsController;//控制器
   private ImageView reg_return;//返回
   private Context context;//上下文
   private boolean strsult;//返回 信息
   private  AppInstance appInstance;//应用实例
   private SharedPreferences sp = null;//保存账号密码
   private String userName;
   private String userPass;
   private User user;
 
    //刷新UI
   @SuppressLint("HandlerLeak")
private Handler handler =new Handler(){

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case 0:
			if (!NetWorkUtils.checkNetWork(context)) {
				CustomerToast.showMessage(context, "请检查网络连接！", false, true);
				return;
			}
			if (strsult==true) {
				MobclickAgent.onEvent(context, "register");
				RightSets.txt_right_login_status.setText(appInstance.user.getName());
				//LogoutActivity.login_name.setText(appInstance.user.getName());
				//RightSets.txt_right_login_status.setText(user.getName());
				RightSets.login_image_8_gone.setVisibility(0);
				RightSets.login_image_1_view.setVisibility(8);
				Editor editor = sp.edit();  
                editor.putString("userNmae", userName);  
                editor.putString("userPass",userPass);  
                editor.commit();
				CustomerToast.showMessage(context,getString(R.string.strulst_loging),true,false);
				finish();
			/*	Intent intent = new Intent(RegisterActivity.this, RightSets.class);    
				startActivity(intent);*/
				/*AppInstance mApp = (AppInstance)getApplication(); 				 
				mApp.setExit(true); 
				AppInstance.isProgramExit=false;
				finish(); */
			}else {
				CustomerToast.showMessage(context,getString(R.string.strulst_reg),true,false);
			}
			break;
		case 2:
			CustomerToast.showMessage(context,getString(R.string.strulst_reg_1),true,false);
			break;
		case 3:
			CustomerToast.showMessage(context,getString(R.string.strulst_reg_2),true,false);
			break;
		case 5:
			CustomerToast.showMessage(context,getString(R.string.strulst_reg_email),true,false);
			break;
		case 6:
			CustomerToast.showMessage(context,"该邮箱已被注册过",true,false);
			break;
			
		default:
			break;
		}
	  }	   
   };
   
	@Override				
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
			setContentView(R.layout.night_layout_registered);
		}else {
			setContentView(R.layout.layout_registered);
		}
	    context=this.getApplicationContext();
	    appInstance=(AppInstance) getApplicationContext();
	    setsController=new SetsController(context);
	    sp=this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
	    user=new User();
	    initviews();
	    
	    
	}
	
	//初始化
	public void initviews(){
		reg_loginbtn=(TextView) findViewById(R.id.reg_loginbtn);
		reg_regist_iv=(TextView) findViewById(R.id.reg_regist_iv);
		reg_userid_etxt=(EditText) findViewById(R.id.reg_userid_etxt);
		reg_password_etxt=(EditText) findViewById(R.id.reg_password_etxt);
        reg_return=(ImageView) findViewById(R.id.reg_return);
		
        reg_return.setOnClickListener(listeners);
		reg_loginbtn.setOnClickListener(listeners);		
		reg_regist_iv.setOnClickListener(listeners);
	}
	//监听器
	private View.OnClickListener listeners=new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			listeners(v);			
		}
		private void listeners(View v) {
			switch (v.getId()) {
			//注册
			case R.id.reg_loginbtn:
				doRegsion();	
				break;
		   //登录
			case R.id.reg_regist_iv:
				doLogin();
				break;
		    //返回
			case R.id.reg_return:
				finish();
				break;
			default:
				break;
			}
			
		}
	};
	//注册
	public void doRegsion(){
		userName =reg_userid_etxt.getText().toString();
		userPass=reg_password_etxt.getText().toString();		
		//判断注册
		if (userName==null||userName.trim().equals("")) {
			  handler.sendEmptyMessage(2);
			  return;
			}
		if(!isEmail(userName)){
			handler.sendEmptyMessage(5);
			return;
		}
		if (userPass==null||userPass.trim().equals("")) {
			handler.sendEmptyMessage(3);
			return;
		}
		final String number=2001+"";
	    new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				strsult=setsController.register(userName, userPass);	
				
		/*		if(!NetConfigure.error_code.equals(number)){
					handler.sendEmptyMessage(6);
				}*/
				handler.sendEmptyMessage(0);
			}	    	
	    }.start();
	}
	
	//登录
	public void doLogin(){
		Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);    
		startActivity(intent);
		//finish();
	}
	//判断邮箱是否合格
	public boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
		}
	/* (non-Javadoc)
		 * @see android.app.Activity#onDestroy()
		 */
		@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			setsController.closeDB();
		
		}
		
		public void onResume() {
			super.onResume();
			
			/**
			 * 此处调用基本统计代码
			 */
			MobclickAgent.onResume(this);
		}

		public void onPause() {
			super.onPause();
			/**
			 * 此处调用基本统计代码
			 */
			MobclickAgent.onPause(this);
		}
	
}
