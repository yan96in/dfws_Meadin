package com.dfws.shhreader.activity.personalcenter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.dfws.shhreader.R;
import com.dfws.shhreader.activity.DownloadService;
import com.dfws.shhreader.activity.MainActivity;
import com.dfws.shhreader.activity.set.SetAboutActivity;
import com.dfws.shhreader.activity.set.SetFeedbackActivity;
import com.dfws.shhreader.activity.set.SetGuideActivity;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.controllers.RestsController;
import com.dfws.shhreader.controllers.SetsController;
import com.dfws.shhreader.database.sharedpreferences.SetsKeeper;
import com.dfws.shhreader.entity.Versions;
import com.dfws.shhreader.slidingmenu.fragment.RightSets;
import com.dfws.shhreader.ui.dialog.CusDialog;
import com.dfws.shhreader.ui.dialog.CustomerToast;
import com.dfws.shhreader.utils.FileAccess;
import com.dfws.shhreader.utils.NetWorkUtils;
import com.dfws.shhreader.utils.StringUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMInfoAgent;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SocializeClientListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.exception.SocializeException;

/**
 * @file： SetingActivity.java
 * @Page： com.dfws.shhreader.activity.personalcenter
 * @description： 个人中心设置界面
 * @since： 2013-10-25
 * @author： Administrator
 */
public class SetingActivity extends BaseActivity {

	/**
	 * 返回
	 */
	private ImageView iv_sets_goback;
	/**
	 * 总布局
	 */
	private LinearLayout linear_sets_main;
	/**
	 * 头部
	 */
	private RelativeLayout rela_sets_titlebar;
	/**
	 * 头部标题
	 */
	private TextView txt_sets_title;
	/**
	 * 主要内容
	 */
	private ScrollView scroll_sets_content;
	
	
	/**
	 * 推送设置
	 */
	private LinearLayout linear_push_set;
	private TextView txt_sets_push_set;
	/**新闻*/
	private RelativeLayout rela_sets_news_push;
	private ImageButton tb_sets_news_push;
	private TextView txt_sets_news_push;
	/**铃声*/
	private RelativeLayout rela_sets_push_ring;
	private TextView txt_sets_push_ring;
	private ImageButton tb_sets_push_ring;
	
	/**
	 * 阅读设置
	 */
	private LinearLayout linear_reading_set;
	private TextView txt_sets_read_set;
	/**字体大小*/
	private RelativeLayout rela_sets_font_size;
	private TextView txt_sets_font_size_title;
	private TextView txt_sets_font_size;
	/**图片加载模式*/
	private RelativeLayout rela_sets_img_loading_mode;
	private TextView txt_sets_img_loading_mode_title;
	private TextView txt_sets_img_loading_mode;
	/**夜间模式*/
	private RelativeLayout rela_sets_reading_mode;
	private TextView txt_sets_reading_mode;
	private ToggleButton tb_sets_reading_mode;
	/**清除缓存*/
	private RelativeLayout rela_sets_clear_buffer;
	private TextView txt_sets_clear_buffer_title;
	private TextView txt_sets_clear_buffer;
	
	/**
	 * 绑定第三方账号
	 */
	private LinearLayout linear_binding_accounts;
	private TextView txt_sets_binding_set;
	/**新浪微博*/
	private RelativeLayout rela_sets_binding_sina;
	private TextView txt_sets_sina;
	private ImageView iv_sets_binding_sina;
	/**QQ空间*/
	private RelativeLayout rela_sets_binding_qq;
	private TextView txt_sets_qq;
	private ImageView iv_sets_binding_qq;
	
	/**
	 * 产品信息
	 */
	private LinearLayout linear_product_info;
	private TextView txt_sets_production;
	/**关于*/
	private RelativeLayout rela_sets_about;
	private TextView txt_sets_about;
	/**意见反馈*/
	private RelativeLayout rela_sets_feedback;
	private TextView txt_sets_feedback;
	/**用户引导*/
	private RelativeLayout rela_sets_user_guide;
	private TextView txt_sets_user_guide;
	/**检查更新*/
	private RelativeLayout rela_sets_check_update;
	private TextView txt_sets_check_update;
	
	/**
	 * 清除缓存
	 */
	private LinearLayout linear_dialog_clear_buffer_main;
	private TextView txt_dialog_clear_buffer_title;
	private TextView txt_dialog_clear_buffer_btn_ok;
	private TextView txt_dialog_clear_buffer_btn_cancel;
	
	/**
	 * 字体大小
	 */
	private LinearLayout linear_dialog_font_main;
	private TextView txt_dialog_font_size_title;
	/**超大*/
	private RelativeLayout rela_dialog_font_super;
	private TextView txt_dialog_font_super;
	private ImageView iv_dialog_font_super;
	/**大*/
	private RelativeLayout rela_dialog_font_big;
	private TextView txt_dialog_font_big;
	private ImageView iv_dialog_font_big;
	/**超大*/
	private RelativeLayout rela_dialog_font_mid;
	private TextView txt_dialog_font_mid;
	private ImageView iv_dialog_font_mid;
	/**超大*/
	private RelativeLayout rela_dialog_font_small;
	private TextView txt_dialog_font_small;
	private ImageView iv_dialog_font_small;
	

	/**
	 * 图片加载模式
	 */
	private LinearLayout linear_dialog_img_load_main;
	private TextView txt_dialog_img_load_title;
	/**无图浏览*/
	private RelativeLayout rela_dialog_img_load_null;
	private TextView txt_dialog_img_load_null;
	private TextView txt_dialog_img_load_null_sub;
	private ImageView iv_dialog_img_load_null;
	/**无图浏览*/
	private RelativeLayout rela_dialog_img_load_has;
	private TextView txt_dialog_img_load_has;
	private TextView txt_dialog_img_load_has_sub;
	private ImageView iv_dialog_img_load_has;
	/**无图浏览*/
	private RelativeLayout rela_dialog_img_load_smart;
	private TextView txt_dialog_img_load_smart;
	private TextView txt_dialog_img_load_smart_sub;
	private ImageView iv_dialog_img_load_smart;
	
	
	/**
	 * 检查更新
	 */
	private LinearLayout linear_dialog_update_main;
	private TextView txt_dialog_update_title;
	private TextView txt_dialog_update_content;
	private TextView txt_dialog_update_ok;
	private TextView txt_dialog_update_cancel;
	
	/**清除缓存*/
	private CusDialog dialog_clear;
	/**字体大小*/
	private CusDialog dialog_font;
	/**图片加载模式*/
	private CusDialog dialog_imag;
	/**检查更新*/
	private CusDialog dialog_update;
	
	/**dialog窗口的  view：清除缓存*/
	private View view_clear;
	/**dialog窗口的  view：字体大小*/
	private View view_font;
	/**dialog窗口的  view：图片加载模式*/
	private View view_img;
	/**dialog窗口的  view：检查更新*/
	private View view_update;
	
	private Context context;
	private SetsController controller;
	
	/**夜间模式主背景色*/
	private String night_main_color="#363b41";
	/**夜间模式模块背景色*/
	private String night_model_color="#23262b";
	/**夜间模式文字颜色*/
	private String night_text_color="#ffffff";
	
	/**白天模式主背景色*/
	private String day_main_color="#ecedee";
	/**夜间模式模块背景色*/
	private String day_model_color="#FFFFFF";
	/**夜间模式文字颜色*/
	private String day_text_color="#000000";
	/**
	 * umeng服务
	 */
	private UMSocialService mUMService=null;
	private boolean isInit=false;
//	private boolean isInit_pull=false;
	private boolean isSinaBinding=false;
	private boolean isQQBinding=false;
	/**
	 * 设置中心的控制器
	 */
	RestsController restsController;
	Versions versions;

	private String url;
    private DownloadService.DownloadBinder binder;  
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			int what=msg.what;
			switch (what) {
			case 100://推送设置成功
				
				break;
			case 200://推送铃声设置成功
				
				break;
			case 101://推送设置
				
				CustomerToast.showMessage(context, "推送设置成功！", false, true);
				tb_sets_news_push.setEnabled(true);
				tb_sets_push_ring.setEnabled(true);
				if (FrameConfigure.push_state) {
					tb_sets_news_push.setBackgroundResource(R.drawable.butn_open);
//					rela_sets_push_ring.setVisibility(View.VISIBLE);
				}else {
					tb_sets_news_push.setBackgroundResource(R.drawable.butn_close);
//					rela_sets_push_ring.setVisibility(View.GONE);
				}
				break;
			case 201://推送设置
				
				CustomerToast.showMessage(context, "推送铃声设置成功！", false, true);
				if (FrameConfigure.ring_state) {
					tb_sets_push_ring.setBackgroundResource(R.drawable.butn_open);
				}else {
					tb_sets_push_ring.setBackgroundResource(R.drawable.butn_close);
				}
				tb_sets_news_push.setEnabled(true);
				tb_sets_push_ring.setEnabled(true);
				break;
			case 301://清除缓存完毕
				initCacheSize();
				rela_sets_clear_buffer.setEnabled(true);
				break;
			case 500://显示更新内容	
				String currentVersion=StringUtils.getAppVersionName(context);
				String netVersion=versions.getLatest_version();
				int a=StringUtils.getAppVersionNameInt(context);
				int b=StringUtils.parseArrayToInt(netVersion);
				if (!StringUtils.isEmpty(netVersion)) {
					if (currentVersion.equalsIgnoreCase(netVersion)) {
						CustomerToast.showMessage(context, "暂无新版本，当前已是最新版本!", false, true);
//						dialog_update.dismiss();
					}else if(b>a){
						txt_dialog_update_title.setText(versions.getLatest_version());				
						txt_dialog_update_content.setText(versions.getUpdate_log());
						dialog_update.show();
					}else{
						CustomerToast.showMessage(context, "暂无新版本，当前已是最新版本!", false, true);
					}
				}else {
					CustomerToast.showMessage(context, "获取版本信息失败，请重试！", false, true);
//					dialog_update.dismiss();
				}
				//CustomerToast.showMessage(context, "暂无新版本，当前已是最新版本!", false, true);
				break;
			case 600:
				//没有新版本
				CustomerToast.showMessage(context, "暂无新版本", false, true);
//				dialog_update.dismiss();
				break;
			case 0://出错了
				CustomerToast.showMessage(context, "更新失败", false, true);
				break;
			case 1://这里要刷新  通知栏的里面的进度条
				CustomerToast.showMessage(context, "刷新进度条", false, true);
				break;
			case 2://下载完成，准备安装
				CustomerToast.showMessage(context, "安装", false, true);
				break;
			case 302:
				Toast.makeText(context, "缓存已清除！", Toast.LENGTH_LONG).show();
			default:
				break;
			}
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_set_seting);
		context=this;
		controller=new SetsController(context);
	    restsController=new RestsController(context);
	    versions=new Versions();
	    initviews();
		initlister();
		mUMService=UMServiceFactory.getUMSocialService("com.umeng.share", RequestType.SOCIAL);
	}
	
	/**
	 * <pre>初始化绑定按钮</pre>
	 *
	 */
	private void initBinBtn(){
		isSinaBinding=UMInfoAgent.isOauthed(context, SHARE_MEDIA.SINA);
		isQQBinding=UMInfoAgent.isOauthed(context, SHARE_MEDIA.QZONE);
		if (isSinaBinding) {
			iv_sets_binding_sina.setBackgroundResource(R.drawable.selector_sets_binding_cancel_bg);
		}else {
			iv_sets_binding_sina.setBackgroundResource(R.drawable.selector_sets_binding_bg);
		}
		if (isQQBinding) {
			iv_sets_binding_qq.setBackgroundResource(R.drawable.selector_sets_binding_cancel_bg);
		}else {
			iv_sets_binding_qq.setBackgroundResource(R.drawable.selector_sets_binding_bg);
		}
	}
	
	/**
	 * <pre>初始化推送按钮</pre>
	 *
	 */
	private void initTogBtn(){
//		isInit_pull=true;
		boolean news=SetsKeeper.readPushState(context);
		boolean ring=SetsKeeper.readPushRingState(context);
		FrameConfigure.push_state=news;
		FrameConfigure.ring_state=ring;
		if (news) {
			tb_sets_news_push.setBackgroundResource(R.drawable.butn_open);
//			rela_sets_push_ring.setVisibility(View.VISIBLE);
//			if (ring) {
//				tb_sets_push_ring.setBackgroundResource(R.drawable.butn_open);
//			}else {
//				tb_sets_push_ring.setBackgroundResource(R.drawable.butn_close);
//			}
		}else {
			tb_sets_news_push.setBackgroundResource(R.drawable.butn_close);
//			rela_sets_push_ring.setVisibility(View.GONE);
		}
	}
	
	/**
	 * <pre>设置阅读模式</pre>
	 *
	 */
	private void setReadMode(){
		if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
			tb_sets_reading_mode.setChecked(true);
			nightModel();
		}else {
			tb_sets_reading_mode.setChecked(false);
			dayMode();
		}
	}
	
	/**
	 * <pre>设置阅读模式</pre>
	 *
	 */
	private void initReadMode(){
		isInit=true;
		if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
			tb_sets_reading_mode.setChecked(true);
			nightModel();
		}else {
			tb_sets_reading_mode.setChecked(false);
			dayMode();
		}
	}
	
	/**
	 * <pre>白天模式</pre>
	 *
	 */
	private void dayMode(){
		int main_color=Color.parseColor(day_main_color);
		int model_color=Color.parseColor(day_model_color);
		int text_color=Color.parseColor(day_text_color);
		
		/**
		 * 主要内容
		 */
		scroll_sets_content.setBackgroundColor(main_color);
		/**
		 * 推送设置
		 */
		linear_push_set.setBackgroundColor(model_color);
		txt_sets_push_set.setTextColor(Color.GRAY);
		/**新闻*/
		rela_sets_news_push.setBackgroundResource(R.drawable.selector_sets_item_bg);
		txt_sets_news_push.setTextColor(text_color);
		/**铃声*/
//		rela_sets_push_ring.setBackgroundResource(R.drawable.selector_sets_item_bg);
		txt_sets_push_ring.setTextColor(text_color);
		
		/**
		 * 阅读设置
		 */
		linear_reading_set.setBackgroundColor(model_color);
		txt_sets_read_set.setTextColor(Color.GRAY);
		/**字体大小*/
		rela_sets_font_size.setBackgroundResource(R.drawable.selector_sets_item_bg);
		txt_sets_font_size_title.setTextColor(text_color);
		txt_sets_font_size.setTextColor(text_color);
		/**图片加载模式*/
		rela_sets_img_loading_mode.setBackgroundResource(R.drawable.selector_sets_item_bg);
		txt_sets_img_loading_mode_title.setTextColor(text_color);
		txt_sets_img_loading_mode.setTextColor(text_color);
		/**夜间模式*/
		rela_sets_reading_mode.setBackgroundResource(R.drawable.selector_sets_item_bg);
		txt_sets_reading_mode.setTextColor(text_color);
		/**清除缓存*/
		rela_sets_clear_buffer.setBackgroundResource(R.drawable.selector_sets_item_bg);
		txt_sets_clear_buffer_title.setTextColor(text_color);
		txt_sets_clear_buffer.setTextColor(text_color);
		
		/**
		 * 绑定第三方账号
		 */
		linear_binding_accounts.setBackgroundColor(model_color);
		txt_sets_binding_set.setTextColor(Color.GRAY);
		/**新浪微博*/
		rela_sets_binding_sina.setBackgroundResource(R.drawable.selector_sets_item_bg);
		txt_sets_sina.setTextColor(text_color);
//		private ImageView iv_sets_binding_sina;
		/**QQ空间*/
		rela_sets_binding_qq.setBackgroundResource(R.drawable.selector_sets_item_bg);
		txt_sets_qq.setTextColor(text_color);
//		private ImageView iv_sets_binding_qq;
		
		/**
		 * 产品信息
		 */
		linear_product_info.setBackgroundColor(model_color);
		txt_sets_production.setTextColor(Color.GRAY);
		/**关于*/
		rela_sets_about.setBackgroundResource(R.drawable.selector_sets_item_bg);
		txt_sets_about.setTextColor(text_color);
		/**意见反馈*/
		rela_sets_feedback.setBackgroundResource(R.drawable.selector_sets_item_bg);
		txt_sets_feedback.setTextColor(text_color);
		/**用户引导*/
		rela_sets_user_guide.setBackgroundResource(R.drawable.selector_sets_item_bg);
		txt_sets_user_guide.setTextColor(text_color);
		/**检查更新*/
		rela_sets_check_update.setBackgroundResource(R.drawable.selector_sets_item_bg);
		txt_sets_check_update.setTextColor(text_color);
		
		/**
		 * 清除缓存
		 */
		linear_dialog_clear_buffer_main.setBackgroundColor(model_color);
		txt_dialog_clear_buffer_title.setTextColor(text_color);
		txt_dialog_clear_buffer_btn_cancel.setTextColor(text_color);
		
		/**
		 * 字体大小
		 */
		linear_dialog_font_main.setBackgroundColor(model_color);
		txt_dialog_font_size_title.setTextColor(text_color);
		/**超大*/
		rela_dialog_font_super.setBackgroundResource(R.drawable.selector_sets_item_bg);
		txt_dialog_font_super.setTextColor(text_color);
//		private ImageView iv_dialog_font_super;
		/**大*/
		rela_dialog_font_big.setBackgroundResource(R.drawable.selector_sets_item_bg);
		txt_dialog_font_big.setTextColor(text_color);
//		private ImageView iv_dialog_font_big;
		/**超大*/
		rela_dialog_font_mid.setBackgroundResource(R.drawable.selector_sets_item_bg);
		txt_dialog_font_mid.setTextColor(text_color);
//		private ImageView iv_dialog_font_mid;
		/**超大*/
		rela_dialog_font_small.setBackgroundResource(R.drawable.selector_sets_item_bg);
		txt_dialog_font_small.setTextColor(text_color);
//		private ImageView iv_dialog_font_small;
		

		/**
		 * 图片加载模式
		 */
		linear_dialog_img_load_main.setBackgroundColor(model_color);
		txt_dialog_img_load_title.setTextColor(text_color);
		/**无图浏览*/
		rela_dialog_img_load_null.setBackgroundResource(R.drawable.selector_sets_item_bg);
		txt_dialog_img_load_null.setTextColor(text_color);
		txt_dialog_img_load_null_sub.setTextColor(text_color);
//		private ImageView iv_dialog_img_load_null;
		/**无图浏览*/
		rela_dialog_img_load_has.setBackgroundResource(R.drawable.selector_sets_item_bg);
		txt_dialog_img_load_has.setTextColor(text_color);
		txt_dialog_img_load_has_sub.setTextColor(text_color);
//		private ImageView iv_dialog_img_load_has;
		/**无图浏览*/
		rela_dialog_img_load_smart.setBackgroundResource(R.drawable.selector_sets_item_bg);
		txt_dialog_img_load_smart.setTextColor(text_color);
		txt_dialog_img_load_smart_sub.setTextColor(text_color);
//		private ImageView iv_dialog_img_load_smart;
		
		
		/**
		 * 检查更新
		 */
		linear_dialog_update_main.setBackgroundColor(model_color);
		txt_dialog_update_title.setTextColor(text_color);
		txt_dialog_update_content.setTextColor(text_color);
		txt_dialog_update_cancel.setTextColor(text_color);
	}
	/**
	 * <pre>夜间模式</pre>
	 *
	 */
	private void nightModel(){
		int main_color=Color.parseColor(night_main_color);
		int model_color=Color.parseColor(night_model_color);
		int text_color=Color.parseColor(night_text_color);
		
		/**
		 * 主要内容
		 */
		scroll_sets_content.setBackgroundColor(main_color);
		/**
		 * 推送设置
		 */
		linear_push_set.setBackgroundColor(model_color);
		txt_sets_push_set.setTextColor(Color.WHITE);
		/**新闻*/
		rela_sets_news_push.setBackgroundResource(R.drawable.night_selector_sets_item_bg);
		txt_sets_news_push.setTextColor(text_color);
		/**铃声*/
//		rela_sets_push_ring.setBackgroundResource(R.drawable.night_selector_sets_item_bg);
		txt_sets_push_ring.setTextColor(text_color);
		
		/**
		 * 阅读设置
		 */
		linear_reading_set.setBackgroundColor(model_color);
		txt_sets_read_set.setTextColor(Color.WHITE);
		/**字体大小*/
		rela_sets_font_size.setBackgroundResource(R.drawable.night_selector_sets_item_bg);
		txt_sets_font_size_title.setTextColor(text_color);
		txt_sets_font_size.setTextColor(text_color);
		/**图片加载模式*/
		rela_sets_img_loading_mode.setBackgroundResource(R.drawable.night_selector_sets_item_bg);
		txt_sets_img_loading_mode_title.setTextColor(text_color);
		txt_sets_img_loading_mode.setTextColor(text_color);
		/**夜间模式*/
		rela_sets_reading_mode.setBackgroundResource(R.drawable.night_selector_sets_item_bg);
		txt_sets_reading_mode.setTextColor(text_color);
		/**清除缓存*/
		rela_sets_clear_buffer.setBackgroundResource(R.drawable.night_selector_sets_item_bg);
		txt_sets_clear_buffer_title.setTextColor(text_color);
		txt_sets_clear_buffer.setTextColor(text_color);
		
		/**
		 * 绑定第三方账号
		 */
		linear_binding_accounts.setBackgroundColor(model_color);
		txt_sets_binding_set.setTextColor(Color.WHITE);
		/**新浪微博*/
		rela_sets_binding_sina.setBackgroundResource(R.drawable.night_selector_sets_item_bg);
		txt_sets_sina.setTextColor(text_color);
//		private ImageView iv_sets_binding_sina;
		/**QQ空间*/
		rela_sets_binding_qq.setBackgroundResource(R.drawable.night_selector_sets_item_bg);
		txt_sets_qq.setTextColor(text_color);
//		private ImageView iv_sets_binding_qq;
		
		/**
		 * 产品信息
		 */
		linear_product_info.setBackgroundColor(model_color);
		txt_sets_production.setTextColor(Color.WHITE);
		/**关于*/
		rela_sets_about.setBackgroundResource(R.drawable.night_selector_sets_item_bg);
		txt_sets_about.setTextColor(text_color);
		/**意见反馈*/
		rela_sets_feedback.setBackgroundResource(R.drawable.night_selector_sets_item_bg);
		txt_sets_feedback.setTextColor(text_color);
		/**用户引导*/
		rela_sets_user_guide.setBackgroundResource(R.drawable.night_selector_sets_item_bg);
		txt_sets_user_guide.setTextColor(text_color);
		/**检查更新*/
		rela_sets_check_update.setBackgroundResource(R.drawable.night_selector_sets_item_bg);
		txt_sets_check_update.setTextColor(text_color);
		
		/**
		 * 清除缓存
		 */
		linear_dialog_clear_buffer_main.setBackgroundColor(model_color);
		txt_dialog_clear_buffer_title.setTextColor(text_color);
		txt_dialog_clear_buffer_btn_cancel.setTextColor(text_color);
		
		/**
		 * 字体大小
		 */
		linear_dialog_font_main.setBackgroundColor(model_color);
		txt_dialog_font_size_title.setTextColor(text_color);
		/**超大*/
		rela_dialog_font_super.setBackgroundResource(R.drawable.night_selector_sets_item_bg);
		txt_dialog_font_super.setTextColor(text_color);
//		private ImageView iv_dialog_font_super;
		/**大*/
		rela_dialog_font_big.setBackgroundResource(R.drawable.night_selector_sets_item_bg);
		txt_dialog_font_big.setTextColor(text_color);
//		private ImageView iv_dialog_font_big;
		/**超大*/
		rela_dialog_font_mid.setBackgroundResource(R.drawable.night_selector_sets_item_bg);
		txt_dialog_font_mid.setTextColor(text_color);
//		private ImageView iv_dialog_font_mid;
		/**超大*/
		rela_dialog_font_small.setBackgroundResource(R.drawable.night_selector_sets_item_bg);
		txt_dialog_font_small.setTextColor(text_color);
//		private ImageView iv_dialog_font_small;
		

		/**
		 * 图片加载模式
		 */
		linear_dialog_img_load_main.setBackgroundColor(model_color);
		txt_dialog_img_load_title.setTextColor(text_color);
		/**无图浏览*/
		rela_dialog_img_load_null.setBackgroundResource(R.drawable.night_selector_sets_item_bg);
		txt_dialog_img_load_null.setTextColor(text_color);
		txt_dialog_img_load_null_sub.setTextColor(text_color);
//		private ImageView iv_dialog_img_load_null;
		/**无图浏览*/
		rela_dialog_img_load_has.setBackgroundResource(R.drawable.night_selector_sets_item_bg);
		txt_dialog_img_load_has.setTextColor(text_color);
		txt_dialog_img_load_has_sub.setTextColor(text_color);
//		private ImageView iv_dialog_img_load_has;
		/**无图浏览*/
		rela_dialog_img_load_smart.setBackgroundResource(R.drawable.night_selector_sets_item_bg);
		txt_dialog_img_load_smart.setTextColor(text_color);
		txt_dialog_img_load_smart_sub.setTextColor(text_color);
//		private ImageView iv_dialog_img_load_smart;
		
		
		/**
		 * 检查更新
		 */
		linear_dialog_update_main.setBackgroundColor(model_color);
		txt_dialog_update_title.setTextColor(text_color);
		txt_dialog_update_content.setTextColor(text_color);
		txt_dialog_update_cancel.setTextColor(text_color);
	}
	
	/**
	 * 初始化所有控件
	 */
	public void initviews(){
		
		linear_sets_main=(LinearLayout)findViewById(R.id.linear_sets_main);
		
		iv_sets_goback=(ImageView)findViewById(R.id.iv_sets_goback);
		rela_sets_titlebar=(RelativeLayout)findViewById(R.id.rela_sets_titlebar);
		txt_sets_title=(TextView)findViewById(R.id.txt_sets_title);
		
		scroll_sets_content=(ScrollView)findViewById(R.id.scroll_sets_content);
		
		linear_push_set=(LinearLayout)findViewById(R.id.linear_push_set);
		txt_sets_push_set=(TextView)findViewById(R.id.txt_sets_push_set);
		rela_sets_news_push=(RelativeLayout)findViewById(R.id.rela_sets_news_push);
		tb_sets_news_push=(ImageButton)findViewById(R.id.tb_sets_news_push);
		txt_sets_news_push=(TextView)findViewById(R.id.txt_sets_news_push);
		
		rela_sets_push_ring=(RelativeLayout)findViewById(R.id.rela_sets_push_ring);
		txt_sets_push_ring=(TextView)findViewById(R.id.txt_sets_push_ring);
		tb_sets_push_ring=(ImageButton)findViewById(R.id.tb_sets_push_ring);
		
		linear_reading_set=(LinearLayout)findViewById(R.id.linear_reading_set);
		txt_sets_read_set=(TextView)findViewById(R.id.txt_sets_read_set);
		rela_sets_font_size=(RelativeLayout)findViewById(R.id.rela_sets_font_size);
		txt_sets_font_size_title=(TextView)findViewById(R.id.txt_sets_font_size_title);
		txt_sets_font_size=(TextView)findViewById(R.id.txt_sets_font_size);

		rela_sets_img_loading_mode=(RelativeLayout)findViewById(R.id.rela_sets_img_loading_mode);
		txt_sets_img_loading_mode_title=(TextView)findViewById(R.id.txt_sets_img_loading_mode_title);
		txt_sets_img_loading_mode=(TextView)findViewById(R.id.txt_sets_img_loading_mode);

		rela_sets_reading_mode=(RelativeLayout)findViewById(R.id.rela_sets_reading_mode);
		txt_sets_reading_mode=(TextView)findViewById(R.id.txt_sets_reading_mode);
		tb_sets_reading_mode=(ToggleButton)findViewById(R.id.tb_sets_reading_mode);
		
		rela_sets_clear_buffer=(RelativeLayout)findViewById(R.id.rela_sets_clear_buffer);
		txt_sets_clear_buffer_title=(TextView)findViewById(R.id.txt_sets_clear_buffer_title);
		txt_sets_clear_buffer=(TextView)findViewById(R.id.txt_sets_clear_buffer);
		
		linear_binding_accounts=(LinearLayout)findViewById(R.id.linear_binding_accounts);
		txt_sets_binding_set=(TextView)findViewById(R.id.txt_sets_binding_set);
		rela_sets_binding_sina=(RelativeLayout)findViewById(R.id.rela_sets_binding_sina);
		txt_sets_sina=(TextView)findViewById(R.id.txt_sets_sina);
		iv_sets_binding_sina=(ImageView)findViewById(R.id.iv_sets_binding_sina);

		rela_sets_binding_qq=(RelativeLayout)findViewById(R.id.rela_sets_binding_qq);
		txt_sets_qq=(TextView)findViewById(R.id.txt_sets_qq);
		iv_sets_binding_qq=(ImageView)findViewById(R.id.iv_sets_binding_qq);
		
		linear_product_info=(LinearLayout)findViewById(R.id.linear_product_info);
		txt_sets_production=(TextView)findViewById(R.id.txt_sets_production);
		rela_sets_about=(RelativeLayout)findViewById(R.id.rela_sets_about);
		txt_sets_about=(TextView)findViewById(R.id.txt_sets_about);

		rela_sets_feedback=(RelativeLayout)findViewById(R.id.rela_sets_feedback);
		txt_sets_feedback=(TextView)findViewById(R.id.txt_sets_feedback);

		rela_sets_user_guide=(RelativeLayout)findViewById(R.id.rela_sets_user_guide);
		txt_sets_user_guide=(TextView)findViewById(R.id.txt_sets_user_guide);

		rela_sets_check_update=(RelativeLayout)findViewById(R.id.rela_sets_check_update);
		txt_sets_check_update=(TextView)findViewById(R.id.txt_sets_check_update);
		//CusDialog  
		initDialog_clear();
		initDialog_font();
		initDialog_imag();
		initDialog_update();
	}
	/**
	 * <pre>设置分割线</pre>
	 *
	 */
	private void setLineBackground(){
		int resid=R.drawable.ic_list_line_white;
		if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
			resid=R.drawable.ic_list_line_black;
		}
		findViewById(R.id.line1).setBackgroundResource(resid);
		findViewById(R.id.line2).setBackgroundResource(resid);
		findViewById(R.id.line3).setBackgroundResource(resid);
		findViewById(R.id.line4).setBackgroundResource(resid);
		findViewById(R.id.line5).setBackgroundResource(resid);
		findViewById(R.id.line6).setBackgroundResource(resid);
		findViewById(R.id.line7).setBackgroundResource(resid);
		findViewById(R.id.line8).setBackgroundResource(resid);
	}
	/**
	 * 注册事件
	 */
	public void initlister(){
		iv_sets_goback.setOnClickListener(listeners);
		//滑动按钮
		tb_sets_news_push.setOnClickListener( listener_message);
		tb_sets_push_ring.setOnClickListener( listener_bell);
		tb_sets_reading_mode.setOnCheckedChangeListener( listener_model);
		tb_sets_reading_mode.setOnClickListener(listeners);
		//RelativeLayout
//		rela_sets_news_push.setOnClickListener(listeners);
//		rela_sets_push_ring.setOnClickListener(listeners);
		rela_sets_font_size.setOnClickListener(listeners);
		rela_sets_img_loading_mode.setOnClickListener(listeners);
//		rela_sets_reading_mode.setOnClickListener(listeners);
		rela_sets_clear_buffer.setOnClickListener(listeners);
		rela_sets_binding_sina.setOnClickListener(listeners);
		rela_sets_binding_qq.setOnClickListener(listeners);
		rela_sets_about.setOnClickListener(listeners);
		rela_sets_feedback.setOnClickListener(listeners);
		rela_sets_user_guide.setOnClickListener(listeners);
		rela_sets_check_update.setOnClickListener(listeners);
		
		iv_sets_binding_qq.setOnClickListener(listeners);
		iv_sets_binding_sina.setOnClickListener(listeners);
	}
	
	/**
	 *<pre> 清除缓存 </pre>
	 *
	 */
	private void initDialog_clear(){
		
		dialog_clear=new CusDialog(this);
		view_clear=LayoutInflater.from(this).inflate(R.layout.layout_set_clear_buffer, null);
		linear_dialog_clear_buffer_main=(LinearLayout)view_clear.findViewById(R.id.linear_dialog_clear_buffer_main);
		txt_dialog_clear_buffer_title=(TextView)view_clear.findViewById(R.id.txt_dialog_clear_buffer_title);
		
		txt_dialog_clear_buffer_btn_ok=(TextView)view_clear.findViewById(R.id.txt_dialog_clear_buffer_btn_ok);
		txt_dialog_clear_buffer_btn_cancel=(TextView) view_clear.findViewById(R.id.txt_dialog_clear_buffer_btn_cancel);
		txt_dialog_clear_buffer_btn_ok.setOnClickListener(listeners);
		txt_dialog_clear_buffer_btn_cancel.setOnClickListener(listeners);
		dialog_clear.hideBtn();
		dialog_clear.setMessageView(view_clear);
		dialog_clear.setUpOkBtn(null, null);
		dialog_clear.setUpCancelBtn(null, null);
	}
	/**
	 *  <pre>字体大小</pre>
	 *
	 */
	private void initDialog_font(){
		dialog_font=new CusDialog(this);
		view_font=LayoutInflater.from(this).inflate(R.layout.layout_set_fonts, null);
		linear_dialog_font_main=(LinearLayout)view_font.findViewById(R.id.linear_dialog_font_main);
		txt_dialog_font_size_title=(TextView)view_font.findViewById(R.id.txt_dialog_font_size_title);
		
		rela_dialog_font_super=(RelativeLayout)view_font.findViewById(R.id.rela_dialog_font_super);
		txt_dialog_font_super=(TextView)view_font.findViewById(R.id.txt_dialog_font_super);
		iv_dialog_font_super=(ImageView)view_font.findViewById(R.id.iv_dialog_font_super);
		rela_dialog_font_super.setOnClickListener(listeners);
		
		rela_dialog_font_big=(RelativeLayout)view_font.findViewById(R.id.rela_dialog_font_big);
		txt_dialog_font_big=(TextView)view_font.findViewById(R.id.txt_dialog_font_big);
		iv_dialog_font_big=(ImageView)view_font.findViewById(R.id.iv_dialog_font_big);
		rela_dialog_font_big.setOnClickListener(listeners);
		
		rela_dialog_font_mid=(RelativeLayout)view_font.findViewById(R.id.rela_dialog_font_mid);
		txt_dialog_font_mid=(TextView)view_font.findViewById(R.id.txt_dialog_font_mid);
		iv_dialog_font_mid=(ImageView)view_font.findViewById(R.id.iv_dialog_font_mid);
		rela_dialog_font_mid.setOnClickListener(listeners);
		
		rela_dialog_font_small=(RelativeLayout)view_font.findViewById(R.id.rela_dialog_font_small);
		txt_dialog_font_small=(TextView)view_font.findViewById(R.id.txt_dialog_font_small);
		iv_dialog_font_small=(ImageView)view_font.findViewById(R.id.iv_dialog_font_small);
		rela_dialog_font_small.setOnClickListener(listeners);
		
		dialog_font.hideBtn();
		dialog_font.setMessageView(view_font);
		dialog_font.setUpOkBtn(null, null);
		dialog_font.setUpCancelBtn(null, null);
	}
	/**
	 * <pre>图片加载模式</pre>
	 *
	 */
	private void initDialog_imag(){
		
		dialog_imag=new CusDialog(this);
		view_img=LayoutInflater.from(this).inflate(R.layout.layout_set_image_load, null);
		linear_dialog_img_load_main=(LinearLayout)view_img.findViewById(R.id.linear_dialog_img_load_main);
		txt_dialog_img_load_title=(TextView)view_img.findViewById(R.id.txt_dialog_img_load_title);
		
		rela_dialog_img_load_null=(RelativeLayout)view_img.findViewById(R.id.rela_dialog_img_load_null);
		txt_dialog_img_load_null=(TextView)view_img.findViewById(R.id.txt_dialog_img_load_null);
		txt_dialog_img_load_null_sub=(TextView)view_img.findViewById(R.id.txt_dialog_img_load_null_sub);
		iv_dialog_img_load_null=(ImageView)view_img.findViewById(R.id.iv_dialog_img_load_null);
		rela_dialog_img_load_null.setOnClickListener(listeners);
		
		
		rela_dialog_img_load_has=(RelativeLayout)view_img.findViewById(R.id.rela_dialog_img_load_has);
		txt_dialog_img_load_has=(TextView)view_img.findViewById(R.id.txt_dialog_img_load_has);
		txt_dialog_img_load_has_sub=(TextView)view_img.findViewById(R.id.txt_dialog_img_load_has_sub);
		iv_dialog_img_load_has=(ImageView)view_img.findViewById(R.id.iv_dialog_img_load_has);
		rela_dialog_img_load_has.setOnClickListener(listeners);
		
		rela_dialog_img_load_smart=(RelativeLayout)view_img.findViewById(R.id.rela_dialog_img_load_smart);
		txt_dialog_img_load_smart=(TextView)view_img.findViewById(R.id.txt_dialog_img_load_smart);
		txt_dialog_img_load_smart_sub=(TextView)view_img.findViewById(R.id.txt_dialog_img_load_smart_sub);
		iv_dialog_img_load_smart=(ImageView)view_img.findViewById(R.id.iv_dialog_img_load_smart);
		rela_dialog_img_load_smart.setOnClickListener(listeners);
		
		dialog_imag.hideBtn();
		dialog_imag.setMessageView(view_img);
		dialog_imag.setUpOkBtn(null, null);
		dialog_imag.setUpCancelBtn(null, null);
	}
	/**
	 * <pre>检查更新APK</pre>
	 *
	 */
	private void initDialog_update(){
		dialog_update=new CusDialog(this);
		view_update=LayoutInflater.from(this).inflate(R.layout.layout_set_update, null);
		linear_dialog_update_main=(LinearLayout)view_update.findViewById(R.id.linear_dialog_update_main);
		txt_dialog_update_cancel=(TextView)view_update.findViewById(R.id.txt_dialog_update_cancel);
		txt_dialog_update_ok=(TextView) view_update.findViewById(R.id.txt_dialog_update_ok);
		txt_dialog_update_content=(TextView)view_update.findViewById(R.id.txt_dialog_update_content);
		txt_dialog_update_title=(TextView)view_update.findViewById(R.id.txt_dialog_update_title);
		txt_dialog_update_ok.setOnClickListener(listeners);
		txt_dialog_update_cancel.setOnClickListener(listeners);
		dialog_update.hideBtn();
		dialog_update.setMessageView(view_update);
		dialog_update.setUpOkBtn(null, null);
		dialog_update.setUpCancelBtn(null, null);
	}
	
	/**
	 * <pre>新闻推送设置</pre>
	 * @param isChecked 状态
	 */
	private void setNewsPush(){
		final boolean msg_state=FrameConfigure.push_state?false:true;
		if (NetWorkUtils.checkNetWork(context)) {
			tb_sets_news_push.setEnabled(false);
			tb_sets_push_ring.setEnabled(false);
			new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						controller.pushSets(msg_state);
						sendMessage(101);
					}
			}).start();
		}else {
			CustomerToast.showMessage(context, "请检查网络", false, true);
		}
	}
	
	/**
	 * <pre>推送铃声设置</pre>
	 * @param isChecked 状态
	 */
	private void setNewsPushRing(){
		final boolean Ring_state=FrameConfigure.ring_state?false:true;
		if (NetWorkUtils.checkNetWork(context)) {
			tb_sets_news_push.setEnabled(false);
			tb_sets_push_ring.setEnabled(false);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					controller.pushRingSets(Ring_state);
					sendMessage(201);
				}
			}).start();
		}else {
			CustomerToast.showMessage(context, "请检查网络", false, true);
		}
	}
	
	/**
	 * <pre>设置夜间模式</pre>
	 * @param isChecked 是否开启
	 */
	private void keepReadMode(boolean isChecked){
		if (isChecked) {
			FrameConfigure.reading_type=FrameConfigure.TYPE_NIGHT;
			SetsKeeper.keepReadType(context, FrameConfigure.TYPE_NIGHT);
			RightSets.iv_right_reading_model.setImageResource(R.drawable.ic_readmode_day);
			RightSets.txt_seting_readmode.setText(R.string.frame_right_readmode_day);
			nightModel();
			MainActivity.mainActivity.setReadModeNight();
		}else {
			FrameConfigure.reading_type=FrameConfigure.TYPE_DAY;
			SetsKeeper.keepReadType(context, FrameConfigure.TYPE_DAY);
			RightSets.iv_right_reading_model.setImageResource(R.drawable.ic_readmode_night);
			RightSets.txt_seting_readmode.setText(R.string.frame_right_readmode_night);
			dayMode();
			MainActivity.mainActivity.setReadModeDay();
		}
		setLineBackground();
		MainActivity.mAdapter.notifyDataSetChanged();
	}
	
	//新闻
	private View.OnClickListener  listener_message= new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			MobclickAgent.onEvent(context, "sets_news_push");
			setNewsPush();
		}		
		
	};
     //铃声
	private View.OnClickListener listener_bell=new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			MobclickAgent.onEvent(context, "sets_push_ring");
			setNewsPushRing();
		}		
		
	};
	   //模式切换
    private OnCheckedChangeListener listener_model=new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (!isInit) {
					keepReadMode(isChecked);
				}
			}
		};
	
	/**
	 * 文本事件
	 */
	private View.OnClickListener listeners=new View.OnClickListener() {	
		@Override
		public void onClick(View v) {
			isInit=false;
			switch (v.getId()) {
				case R.id.iv_sets_goback:
				finish();
				break;
				
				case R.id.rela_sets_news_push://新闻推送
					break;
				case R.id.rela_sets_push_ring://推送铃声
					break;
				case R.id.rela_sets_font_size://字体大小
					initFontSize();
					dialog_font.show();
					break;
				case R.id.rela_sets_img_loading_mode://图片加载模式
					initLoadMode();
					dialog_imag.show();
					break;
				case R.id.rela_sets_reading_mode://夜间模式
					
					boolean f3=tb_sets_reading_mode.isChecked();
					if (f3) {
						tb_sets_reading_mode.setChecked(false);
					}else {
						tb_sets_reading_mode.setChecked(true);
					}
					
					break;
				case R.id.rela_sets_clear_buffer://清除缓存
					dialog_clear.show();
					break;
				case R.id.iv_sets_binding_sina:
					bindingSina();
					break;
				case R.id.iv_sets_binding_qq:
					bindingQQ();
					break;
				case R.id.rela_sets_binding_sina://绑定新浪
					bindingSina();
					break;
				case R.id.rela_sets_binding_qq://绑定qq
					bindingQQ();
					break;
				case R.id.rela_sets_about://关于
					Intent intent1=new Intent(context, SetAboutActivity.class);
					startActivity(intent1);
					break;
				case R.id.rela_sets_feedback://意见反馈
					Intent intent2=new Intent(context, SetFeedbackActivity.class);
					startActivity(intent2);
					break;
				case R.id.rela_sets_user_guide://用户引导
					Intent intent3=new Intent(context, SetGuideActivity.class);
					startActivity(intent3);
					break;
				case R.id.rela_sets_check_update://检查更新
					MobclickAgent.onEvent(context, "sets_check_update");
					checkUpdate();
					break;
				case R.id.rela_dialog_font_super:// 字体超大
					MobclickAgent.onEvent(context, "sets_font_super_big");
					txt_sets_font_size.setText("超大");
					SetsKeeper.keepFontSize(context, 3);
					dialog_font.dismiss();
					break;
				case R.id.rela_dialog_font_big://字体大
					MobclickAgent.onEvent(context, "sets_font_big");
					txt_sets_font_size.setText("大");
					SetsKeeper.keepFontSize(context, 2);
					dialog_font.dismiss();
					break;
				case R.id.rela_dialog_font_mid://字体中                                          
					MobclickAgent.onEvent(context, "sets_font_mid");
					txt_sets_font_size.setText("中");
					SetsKeeper.keepFontSize(context, 1);
					dialog_font.dismiss();
					break;
				case R.id.rela_dialog_font_small://字体小
					MobclickAgent.onEvent(context, "sets_font_small");
					txt_sets_font_size.setText("小");
					SetsKeeper.keepFontSize(context, 0);
					dialog_font.dismiss();
					break;
				case R.id.rela_dialog_img_load_has://有图模式
					MobclickAgent.onEvent(context, "sets_img_load_has");
					txt_sets_img_loading_mode.setText("有图浏览");
					FrameConfigure.loading_type=FrameConfigure.TYPE_IMG_ALLOW;
					SetsKeeper.keepLoadType(context, FrameConfigure.TYPE_IMG_ALLOW);
					dialog_imag.dismiss();
					break;
				case R.id.rela_dialog_img_load_null://无图模式
					MobclickAgent.onEvent(context, "sets_img_load_null");
					txt_sets_img_loading_mode.setText("无图浏览");
					FrameConfigure.loading_type=FrameConfigure.TYPE_IMG_NULL;
					SetsKeeper.keepLoadType(context, FrameConfigure.TYPE_IMG_NULL);
					dialog_imag.dismiss();
					break;
				case R.id.rela_dialog_img_load_smart://智能模式
					MobclickAgent.onEvent(context, "sets_img_load_smart");
					txt_sets_img_loading_mode.setText("智能模式");
					FrameConfigure.loading_type=FrameConfigure.TYPE_IMG_SMART;
					SetsKeeper.keepLoadType(context, FrameConfigure.TYPE_IMG_SMART);
					dialog_imag.dismiss();
					break;
				case R.id.txt_dialog_clear_buffer_btn_ok://清除缓存
					rela_sets_clear_buffer.setEnabled(false);
					txt_sets_clear_buffer.setText("正在清除中...");					
					dialog_clear.dismiss();
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub				
							clearBuffer();
							sendMessage(301);
							sendMessage(302);
						}
					}).start();
					break;
				case R.id.txt_dialog_clear_buffer_btn_cancel://取消清除缓存
					dialog_clear.dismiss();
					break;
				case R.id.txt_dialog_update_ok://立即更新
					doUpdate();
					dialog_update.dismiss();
					break;
				case R.id.txt_dialog_update_cancel://取消更新
					dialog_update.dismiss();
					break;
			}						
		}
	};
	
	/**
	 * <pre>绑定新浪</pre>
	 *
	 */
	private void bindingSina(){
		isSinaBinding=UMInfoAgent.isOauthed(context, SHARE_MEDIA.SINA);
		if (isSinaBinding) {
			mUMService.deleteOauth(context, SHARE_MEDIA.SINA, new SocializeClientListener() {
				
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					rela_sets_binding_sina.setEnabled(false);
					iv_sets_binding_sina.setEnabled(false);
				}
				
				@Override
				public void onComplete(int arg0, SocializeEntity arg1) {
					// TODO Auto-generated method stub
					rela_sets_binding_sina.setEnabled(true);
					iv_sets_binding_sina.setEnabled(true);
					if (arg0==200) {
						iv_sets_binding_sina.setBackgroundResource(R.drawable.selector_sets_binding_bg);
					}
				}
			});
		}else{
			mUMService.doOauthVerify(context,
	                   SHARE_MEDIA.SINA,
	                   new UMAuthListener(){

						@Override
						public void onCancel(SHARE_MEDIA arg0) {
							// TODO Auto-generated method stub
							rela_sets_binding_sina.setEnabled(true);
							iv_sets_binding_sina.setEnabled(true);
						}

						@Override
						public void onComplete(Bundle arg0,
								SHARE_MEDIA arg1) {
							// TODO Auto-generated method stub
							rela_sets_binding_sina.setEnabled(true);
							iv_sets_binding_sina.setEnabled(true);
							iv_sets_binding_sina.setBackgroundResource(R.drawable.selector_sets_binding_cancel_bg);
						}

						@Override
						public void onError(
								SocializeException arg0,
								SHARE_MEDIA arg1) {
							// TODO Auto-generated method stub
							rela_sets_binding_sina.setEnabled(true);
							iv_sets_binding_sina.setEnabled(true);
						}

						@Override
						public void onStart(SHARE_MEDIA arg0) {
							// TODO Auto-generated method stub
							rela_sets_binding_sina.setEnabled(false);
							iv_sets_binding_sina.setEnabled(false);
					}
				
			});
		}
	}
	
	/**
	 * <pre>绑定QQ</pre>
	 *
	 */
	private void bindingQQ(){
		isQQBinding=UMInfoAgent.isOauthed(context, SHARE_MEDIA.QZONE);
		if (isQQBinding) {
			mUMService.deleteOauth(context, SHARE_MEDIA.QZONE, new SocializeClientListener() {
				
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					rela_sets_binding_sina.setEnabled(false);
					iv_sets_binding_qq.setEnabled(false);
				}
				
				@Override
				public void onComplete(int arg0, SocializeEntity arg1) {
					// TODO Auto-generated method stub
					rela_sets_binding_qq.setEnabled(true);
					iv_sets_binding_qq.setEnabled(true);
					if (arg0==200) {
						iv_sets_binding_qq.setBackgroundResource(R.drawable.selector_sets_binding_bg);
					}
				}
			});
		}else{
			mUMService.doOauthVerify(context,
	                   SHARE_MEDIA.QZONE,
	                   new UMAuthListener(){

						@Override
						public void onCancel(SHARE_MEDIA arg0) {
							// TODO Auto-generated method stub
							rela_sets_binding_qq.setEnabled(true);
							iv_sets_binding_qq.setEnabled(true);
						}

						@Override
						public void onComplete(Bundle arg0,
								SHARE_MEDIA arg1) {
							// TODO Auto-generated method stub
							rela_sets_binding_qq.setEnabled(true);
							iv_sets_binding_qq.setEnabled(true);
							iv_sets_binding_qq.setBackgroundResource(R.drawable.selector_sets_binding_cancel_bg);
						}

						@Override
						public void onError(
								SocializeException arg0,
								SHARE_MEDIA arg1) {
							// TODO Auto-generated method stub
							rela_sets_binding_sina.setEnabled(true);
							iv_sets_binding_qq.setEnabled(true);
						}

						@Override
						public void onStart(SHARE_MEDIA arg0) {
							// TODO Auto-generated method stub
							rela_sets_binding_qq.setEnabled(false);
							iv_sets_binding_qq.setEnabled(false);
					}
				
			});
		}
	}
	
	/**
	 * <pre>清除缓存</pre>
	 *
	 */
	private void clearBuffer(){
		boolean f=FileAccess.DeleteFile(FrameConfigure.MAIN_FOLDER);
		String dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
		boolean f1=FileAccess.DeleteFile(dir);
	}
	
	/**
	 * <pre>获取缓存大小</pre>
	 *
	 */
	private void initCacheSize(){
		File file=new File(FrameConfigure.MAIN_FOLDER);
		File dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		long size=FileAccess.getDirSize(file);
		long size1=FileAccess.getDirSize(dir)+size;
		String strSize=getString(R.string.sets_clear_buffer_describe)+FileAccess.GetFileSize(size1);
		txt_sets_clear_buffer.setText(strSize);

	}
	
	/**
	 * <pre>初始化字体大小</pre>
	 *
	 */
	private void initFontSize(){
		int size=SetsKeeper.readFontSize(context);
		switch (size) {
		case 3:
			txt_sets_font_size.setText("超大");
			iv_dialog_font_super.setVisibility(View.VISIBLE);
			iv_dialog_font_big.setVisibility(View.GONE);
			iv_dialog_font_mid.setVisibility(View.GONE);
			iv_dialog_font_small.setVisibility(View.GONE);
			break;
		case 2:
			txt_sets_font_size.setText("大");
			iv_dialog_font_super.setVisibility(View.GONE);
			iv_dialog_font_big.setVisibility(View.VISIBLE);
			iv_dialog_font_mid.setVisibility(View.GONE);
			iv_dialog_font_small.setVisibility(View.GONE);
			break;
		case 0:
			txt_sets_font_size.setText("小");
			iv_dialog_font_super.setVisibility(View.GONE);
			iv_dialog_font_big.setVisibility(View.GONE);
			iv_dialog_font_mid.setVisibility(View.GONE);
			iv_dialog_font_small.setVisibility(View.VISIBLE);
			break;
		case 1:
		default:
			txt_sets_font_size.setText("中");
			iv_dialog_font_super.setVisibility(View.GONE);
			iv_dialog_font_big.setVisibility(View.GONE);
			iv_dialog_font_mid.setVisibility(View.VISIBLE);
			iv_dialog_font_small.setVisibility(View.GONE);
			break;
		}
	}
	
	/**
	 * <pre>初始图片加载模式</pre>
	 *
	 */
	private void initLoadMode(){
		int mode=SetsKeeper.readLoadType(context);
		switch (mode) {
		case FrameConfigure.TYPE_IMG_ALLOW:
			txt_sets_img_loading_mode.setText("有图浏览");
			iv_dialog_img_load_has.setVisibility(View.VISIBLE);
			iv_dialog_img_load_null.setVisibility(View.GONE);
			iv_dialog_img_load_smart.setVisibility(View.GONE);
			break;
		case FrameConfigure.TYPE_IMG_NULL:
			txt_sets_img_loading_mode.setText("无图浏览");
			iv_dialog_img_load_has.setVisibility(View.GONE);
			iv_dialog_img_load_null.setVisibility(View.VISIBLE);
			iv_dialog_img_load_smart.setVisibility(View.GONE);
			break;
		default:
			txt_sets_img_loading_mode.setText("智能模式");
			iv_dialog_img_load_has.setVisibility(View.GONE);
			iv_dialog_img_load_null.setVisibility(View.GONE);
			iv_dialog_img_load_smart.setVisibility(View.VISIBLE);
			break;
		}
	}

	/**
	 * <pre>发送消息</pre>
	 * @param what
	 */
	private void sendMessage(int what){
		Message msg=handler.obtainMessage();
		msg.what=what;
		handler.sendMessage(msg);
	}
	
	/**
	 * 立即更新
	 */
	public void doUpdate(){
		url=versions.getApp_url();
		Intent intent = new Intent(this, DownloadService.class);
		intent.putExtra("url", url);
		intent.putExtra("version", versions.getLatest_version());
	    startService(intent);   //如果先调用startService,则在多个服务绑定对象调用unbindService后服务仍不会被销毁   
	    bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}
	//检查更新
	public void checkUpdate(){
		if (!NetWorkUtils.checkNetWork(context)) {
			CustomerToast.showMessage(context, "请检查网络连接！", false, true);
			return;
		}
		new Thread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				versions=restsController.checkVersion(2);
				if (versions!=null) {
					handler.sendEmptyMessage(500);
				}else {
					handler.sendEmptyMessage(600);
				}
			   
			}
		}).start();		
	}
	

    private ServiceConnection conn = new ServiceConnection() 
    {
        @Override  
        public void onServiceConnected(ComponentName name, IBinder service) 
        {
        	binder = (DownloadService.DownloadBinder)service;
        	binder.start();
        }   
        @Override  
        public void onServiceDisconnected(ComponentName name) { 
        	
        }
    };
    
    public void cancel()
    {
    	if(null != binder && !binder.isCancelled())
    	{
    		binder.cancel();
    	}
    }
    
	//关闭服务
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		 super.onDestroy();  
	        if (null != binder)
	        {  
	            unbindService(conn);              
	        }
	        cancel();
	        if(controller!=null)
	        	controller.closeDB();
	}
    
	@Override
	public void onResume() {
		super.onResume();
		
		/**
		 * 此处调用基本统计代码
		 */
		MobclickAgent.onResume(this);
		initReadMode();
		initFontSize();
		initLoadMode();
		initCacheSize();
		initTogBtn();
		initBinBtn();
		setLineBackground();
		isInit=false;
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

	