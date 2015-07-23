/**
 * 
 */
package com.dfws.shhreader.activity.set;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.dfws.shhreader.R;
import com.dfws.shhreader.activity.AppInstance;
import com.dfws.shhreader.activity.personalcenter.BaseActivity;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.controllers.RestsController;
import com.dfws.shhreader.ui.dialog.CustomerToast;
import com.dfws.shhreader.utils.DeviceUtils;
import com.dfws.shhreader.utils.NetWorkUtils;
import com.dfws.shhreader.utils.StringUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * @file： SetFeedback.java
 * @Page： com.dfws.shhreader.activity.set
 * @description：   反馈界面
 * @since： 2013-10-26
 * @author： Administrator
 */
public class SetFeedbackActivity extends BaseActivity {
  private  EditText set_content,set_number;//内容和电话
  private  TextView set_txt_dialog_btn_ok;//提交
  private  RestsController restsController;//控制器
  private  ImageView set_feedback_return;//返回
  private  Context context;//上下文
  private  boolean flg;
   @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
			setContentView(R.layout.night_layout_set_setfeedback);
		}else {
			setContentView(R.layout.layout_set_setfeedback);
		}
	  context=this;
	   restsController=new RestsController(context);
	   initview();
	}
	//初始化数据
	public  void initview(){
		//内容和联系方式
		set_content=(EditText) findViewById(R.id.set_content);
		set_number=(EditText) findViewById(R.id.set_number);
		//提交
		set_txt_dialog_btn_ok=(TextView) findViewById(R.id.set_txt_dialog_btn_ok);	
		set_txt_dialog_btn_ok.setOnClickListener(listener);
		//返回
		set_feedback_return=(ImageView) findViewById(R.id.set_feedback_return);
		set_feedback_return.setOnClickListener(listener);
	}
	//handle 刷新UI
	private  Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
             switch (msg.what) {
			case 0:				
				Toast.makeText(context, "谢谢你宝贵的意见！", Toast.LENGTH_LONG).show();
				finish();
				break;
			case 1:
				Toast.makeText(context, "请输入一个联系方式。", Toast.LENGTH_LONG).show();
			    break;
			default:
				break;
			}
	
		}};
		
    /**
     * 返回和提交的事件处理
     */
	private View.OnClickListener listener=new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listener(v);
			}

			private void listener(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				//提交
				case R.id.set_txt_dialog_btn_ok:
					initDate();
					break;
					//返回
				case R.id.set_feedback_return:
					finish();
					break;
				default:
					break;
				}
			}

			
		};
		/**
		 * 提交请求
		 */
		public void initDate(){
			final String content=set_content.getText().toString();
			final String contact=set_number.getText().toString();
			//判断是否为空
			if (StringUtils.isEmpty(content)) {
				Toast.makeText(context, "请输入你想反馈的内容！", Toast.LENGTH_LONG).show();
				//set_content.setError(this.getResources().getString(R.string.strulst_erro_content));
				return;
			}
			if (content.length()>1000) {
				Toast.makeText(context, "问题和建议不得超过1000字！已输入："+content.length()+"个字。", Toast.LENGTH_LONG).show();
				return;
			}
			if (contact.length()>50) {
				Toast.makeText(context, "邮箱不得超过50字！", Toast.LENGTH_LONG).show();
				return;
			}	
			if (!NetWorkUtils.checkNetWork(context)) {
				CustomerToast.showMessage(context, "请检查网络连接！", false, true);
				return;
			}
			/**
			 * 
			 */
			new Thread(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					 flg=	restsController.feedback(contact, content, DeviceUtils.getMobleInfo(), DeviceUtils.getSystemVersion(), "2.0.1");
						if(flg==true){
							
							handler.sendEmptyMessage(0);
						}else {
							handler.sendEmptyMessage(1);
						}
				}}.start();		
		  }
		
		//判断电话
		 public boolean isMobileNO(String mobiles) {
			 Pattern p = Pattern
			 .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			 Matcher m = p.matcher(mobiles);
			 return m.matches();
			 }
		 
       //判断电话
		 public boolean checkPhone(String phone){
		        Pattern pattern = Pattern.compile("^13/d{9}||15[8,9]/d{8}$");
		        Matcher matcher = pattern.matcher(phone);  
		        if (matcher.matches()) {
		            return true;
		        }
		        return false;
		    }
		//判断邮箱是否合格
		public boolean isEmail(String email) {
				String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
				Pattern p = Pattern.compile(str);
				Matcher m = p.matcher(email);
				return m.matches();
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
