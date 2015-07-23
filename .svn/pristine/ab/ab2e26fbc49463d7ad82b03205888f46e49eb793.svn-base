package com.dfws.shhreader.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.dfws.shhreader.R;
import com.dfws.shhreader.activity.personalcenter.BaseActivity;
import com.dfws.shhreader.utils.ThirdHelp;

/**
 * share 分享平台   
 * @author Administrator
 * 本类主要作用：第三方平台集成 
 * @since 2013-10-23
 */
public class ShareActivity extends BaseActivity {

	/**新浪微博*/
	private TextView txt_share_sina;
	/**腾讯微博*/
	private TextView txt_share_tencent;
	/**QQ空间*/
	private TextView txt_share_qqzone;
	/**微信朋友*/
	private TextView txt_share_weixin;
	/**朋友圈*/
	private TextView txt_share_circle;
	/**短信*/
	private TextView txt_share_sms;
	/**邮件*/
	private TextView txt_share_emile;
	/**取消*/
	private TextView txt_share__cancel;
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_dialog_share);
		initview();
	}
	/**
	 * 初始化控件
	 */
	public  void initview(){
		txt_share_tencent=(TextView) findViewById(R.id.txt_share_tencent);
		txt_share_sina=(TextView) findViewById(R.id.txt_share_sina);
		txt_share_qqzone=(TextView) findViewById(R.id.txt_share_qqzone);
		txt_share_weixin=(TextView) findViewById(R.id.txt_share_weixin);
		txt_share_circle=(TextView) findViewById(R.id.txt_share_circle);
		txt_share_sms=(TextView) findViewById(R.id.txt_share_sms);
		txt_share_emile=(TextView) findViewById(R.id.txt_share_emile);
		txt_share__cancel=(TextView) findViewById(R.id.txt_share__cancel);
		txt_share__cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	/**
	 * <pre>设置监听</pre>
	 * @param shareListener
	 */
	public void setOnclickListener(View.OnClickListener shareListener) {
		txt_share__cancel.setOnClickListener(shareListener);
		txt_share_circle.setOnClickListener(shareListener );
		txt_share_emile.setOnClickListener(shareListener);
		txt_share_qqzone.setOnClickListener(shareListener);
		txt_share_sina.setOnClickListener(shareListener);
		txt_share_sms.setOnClickListener(shareListener);
		txt_share_tencent.setOnClickListener(shareListener);	
		txt_share_weixin.setOnClickListener(shareListener);
	}
	
//	/**
//	 * 分享监听
//	 */
//	private View.OnClickListener  shareListener=new View.OnClickListener() {		
//		@Override
//		public void onClick(View v) {
//			shareListener(v);		
//		}
//      /**
//       * 子类  实现每个监听
//       * @param v  代表每个按钮
//       */
//		private void shareListener(View v) {
//			switch (v.getId()) {
//			case R.id.txt_share_sina:
//				ThirdHelp.share_Sina(ShareActivity.this);
//				break;
//
//			case R.id.txt_share_tencent:
//				ThirdHelp.share_QQ_weibo(ShareActivity.this);
//				break;
//
//			case R.id.txt_share_qqzone:
//				ThirdHelp.share_QQ_QZONE(ShareActivity.this);
//				break;
//
//			case R.id.txt_share_weixin:
//				ThirdHelp.share_QQ_WEIXIN(ShareActivity.this);
//				break;
//
//			case R.id.txt_share_circle:
//				ThirdHelp.share_QQ_WEIXIN(ShareActivity.this);
//				break;
//
//			case R.id.txt_share_emile:
//				ThirdHelp.share_Emile(ShareActivity.this);
//				break;
//
//			case R.id.txt_share_sms:
//				ThirdHelp.share_SMS(ShareActivity.this);
//				break;
//			case R.id.txt_share__cancel:
//				finish();
//				break;
//			default:
//				break;
//			}
//			
//		}
//	};

}
