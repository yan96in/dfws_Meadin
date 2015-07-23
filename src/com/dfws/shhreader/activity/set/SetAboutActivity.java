package com.dfws.shhreader.activity.set;

import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.dfws.shhreader.R;
import com.dfws.shhreader.activity.personalcenter.BaseActivity;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.ui.dialog.CustomerToast;
import com.dfws.shhreader.ui.dialog.ShareCustomDialog;
import com.dfws.shhreader.utils.NetWorkUtils;
import com.dfws.shhreader.utils.StringUtils;
import com.dfws.shhreader.utils.ThirdHelp;
import com.umeng.analytics.MobclickAgent;

/**
 * @file： SetAbout.java
 * @Page： com.dfws.shhreader.activity.set
 * @description： 关于界面
 * @since： 2013-10-26
 * @author： Administrator
 */
public class SetAboutActivity extends BaseActivity {
	/** 返回 */
	private ImageView about_return;
	/** logo */
	private ImageView iv_about_logo;
	/** @微博 */
	private TextView txt_about_weibo;
	/** email */
	private TextView txt_about_email;
	/** 电话 */
	private TextView txt_about_phone;
	/** 版本 */
	private TextView txt_about_version;
	private ShareCustomDialog shareDialog;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (FrameConfigure.reading_type == FrameConfigure.TYPE_NIGHT) {
			setContentView(R.layout.night_layout_set_about);
		} else {
			setContentView(R.layout.layout_set_about);
		}
		context = this;
		initview();
	}

	private void initview() {
		// 返回
		about_return = (ImageView) findViewById(R.id.about_return);
		iv_about_logo = (ImageView) findViewById(R.id.iv_about_logo);
		txt_about_weibo = (TextView) findViewById(R.id.txt_about_weibo);
		txt_about_email = (TextView) findViewById(R.id.txt_about_email);
		txt_about_phone = (TextView) findViewById(R.id.txt_about_phone);
		txt_about_version = (TextView) findViewById(R.id.txt_about_version);
		about_return.setOnClickListener(listener);
		iv_about_logo.setOnClickListener(listener);
		// txt_about_email.setOnClickListener(listener);
		txt_about_phone.setOnClickListener(listener);
		txt_about_weibo.setOnClickListener(listener);
		shareDialog = new ShareCustomDialog(context);
		shareDialog.setOnclickListener(shareListener);
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.about_return:
				finish();
				break;
			case R.id.iv_about_logo:
				shareDialog.show();
				break;
			case R.id.txt_about_email:
				// 必须明确使用mailto前缀来修饰邮件地址,如果使用
				// intent.putExtra(Intent.EXTRA_EMAIL, email)，结果将匹配不到任何应用
				Uri uris = Uri.parse("mailto:news@meadin.com");
				String[] email = { "news@meadin.com" };
				Intent intent = new Intent(Intent.ACTION_SENDTO, uris);
				intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
				intent.putExtra(Intent.EXTRA_SUBJECT, "迈点阅读"); // 主题
				intent.putExtra(Intent.EXTRA_TEXT, "我在使用迈点阅读"); // 正文

				PackageManager pkManager = getPackageManager();
				List<ResolveInfo> activities = pkManager.queryIntentActivities(
						intent, 0);

				if (activities.size() > 1) {
					// Create and start the chooser
					startActivity(Intent.createChooser(intent, "请选择邮件类应用"));

				} else {
					startActivity(intent);
				}
				break;
			case R.id.txt_about_weibo:
				Uri uri = Uri.parse("http://weibo.com/meadinnews");
				startActivity(new Intent(Intent.ACTION_VIEW, uri));
				break;
			case R.id.txt_about_phone:
				Uri urip = Uri.parse("tel:057156029709");
				Intent it = new Intent(Intent.ACTION_DIAL, urip);
				startActivity(it);
				break;
			}
		}
	};

	/**
	 * 分享监听
	 */
	private View.OnClickListener shareListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			shareListener(v);
		}

		/**
		 * 子类 实现每个监听
		 * 
		 * @param v
		 *            代表每个按钮
		 */
		private void shareListener(View v) {

			String content = "我在使用#迈点阅读#移动客户端，感觉不错，快来体验一下吧！下载地址：http://m.meadin.com/android/meadinreading";
			String content_wei = "我在使用迈点阅读，感觉不错，快来体验吧！下载：http://m.meadin.com/android/meadinreading";
			String tag_url = "http://m.meadin.com/android/meadinreading";
			String title = "分享“迈点阅读客户端”";
			switch (v.getId()) {
			case R.id.txt_share_sina:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					break;
				}
				ThirdHelp.share_Sina(context, content, null);

				break;

			case R.id.txt_share_tencent:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					break;
				}
				ThirdHelp.share_Tencent(context, content, null);
				break;

			case R.id.txt_share_qqzone:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					break;
				}
				ThirdHelp.share_Qzone(context, content, null);
				break;

			case R.id.txt_share_weixin:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					break;
				}
				ThirdHelp.share_Weixin(context, tag_url, title, content_wei,
						null, false);
				break;

			case R.id.txt_share_circle:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					break;
				}
				ThirdHelp.share_Weixin(context, tag_url, title, "", null, true);
				break;

			case R.id.txt_share_emile:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					break;
				}
				ThirdHelp.share_Emile(context,"我在使用迈点阅读，感觉不错，快来体验吧！下载地址：http://m.meadin.com/android/meadinreading",null);
				break;

			case R.id.txt_share_sms:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					break;
				}
				ThirdHelp.share_SMS(context, content_wei);
				break;
			case R.id.txt_share__cancel:

				break;
			default:
				break;
			}
			shareDialog.dismiss();
		}
	};

	@Override
	public void onResume() {
		super.onResume();
		String version = StringUtils.getAppVersionName(context);
		txt_about_version.setText(version);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ThirdHelp.onBack(requestCode, resultCode, data);
	}

}
