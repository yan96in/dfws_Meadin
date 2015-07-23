package com.dfws.shhreader.activity.personalcenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.dfws.shhreader.R;
import com.dfws.shhreader.activity.AppInstance;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.controllers.SetsController;
import com.dfws.shhreader.database.tools.UserDatabaseHelper;
import com.dfws.shhreader.entity.User;
import com.dfws.shhreader.slidingmenu.fragment.RightSets;
import com.dfws.shhreader.ui.dialog.CustomerToast;
import com.umeng.analytics.MobclickAgent;

/**
 * <h2>登出</h2>
 * @author Eilin.Yang
 * @since 2013-10-18
 * @version v1.0
 */
public class LogoutActivity extends BaseActivity {	
	public static TextView login_name;//用户名
	private ImageView login_out_return;//返回
	private TextView login_out_buton;//退出按钮
	private  AppInstance appInstance;//全局实例
	private SetsController setsController;//控制器
	private User user;//用户
	private boolean flg;//返回状态
	private  Context context;//上下文
	private UserDatabaseHelper userDatabaseHelper;//数据库
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
			setContentView(R.layout.night_layout_login_out);
		}else {
			setContentView(R.layout.layout_login_out);
		}
		context=this;
		setsController=new SetsController(context);
		userDatabaseHelper=new UserDatabaseHelper(context);
		appInstance=(AppInstance)getApplicationContext();
		user=appInstance.getUser();
		initview();
	}
	
	public void initview(){		
		login_out_return=(ImageView) findViewById(R.id.login_out_return);
	    login_name=(TextView) findViewById(R.id.login_name);
	    login_out_buton=(TextView) findViewById(R.id.login_out_buton);
	    login_name.setText(user.getName());
        //返回
	    login_out_return.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	    //退出登录
	    login_out_buton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {				
				// TODO Auto-generated method stub
				new Thread( ){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						flg=setsController.logout();
						handler.sendEmptyMessage(0);
					}}.start();		
			}
		});
	}
    //刷新ui
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (flg==true) {
				MobclickAgent.onEvent(context, "logout");
				RightSets.txt_right_login_status.setText("登录账号");
				RightSets.login_image_8_gone.setVisibility(View.GONE);
				RightSets.login_image_1_view.setVisibility(View.VISIBLE);
				CustomerToast.showMessage(context,getString(R.string.strulst_login_out) , true, false);
			    Intent intent=new Intent(context,LoginActivity.class);
				startActivity(intent);
			    finish();
			}
		}
		
	};
	
	//关闭数据库
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		setsController.closeDB();
		userDatabaseHelper.close();
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
