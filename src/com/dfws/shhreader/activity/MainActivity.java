package com.dfws.shhreader.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dfws.shhreader.R;
import com.dfws.shhreader.activity.image.ImageActivitys;
import com.dfws.shhreader.activity.set.SetGuideActivity;
import com.dfws.shhreader.adapter.MainFragmentAdapter;
import com.dfws.shhreader.configures.Configure;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.controllers.RestsController;
import com.dfws.shhreader.database.sharedpreferences.SetsKeeper;
import com.dfws.shhreader.entity.Versions;
import com.dfws.shhreader.slidingmenu.fragment.RightSets;
import com.dfws.shhreader.ui.dialog.PopuDialog;
import com.dfws.shhreader.utils.NetWorkUtils;
import com.dfws.shhreader.utils.ScreenUtil;
import com.dfws.shhreader.utils.StringUtils;
import com.dfws.shhreader.utils.ThirdHelp;
import com.igexin.slavesdk.MessageManager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.analytics.MobclickAgent;
/**
 * <h2>主界面</h2>
 * <pre>主页面，进行主要的界面切换操作</pre>
 * @author Eilin.Yang
 * @version v1.0
 * @since 2013-9-10
 */
@SuppressLint("NewApi")
public class MainActivity extends BaseFrameActivity {

	public static SlidingMenu slidingMenu;
	private RightSets rightSets;
	private Context context;
	public static MainActivity mainActivity;
	private ImageView showRight;
	public static MainFragmentAdapter mAdapter;
	public static ViewPager mPager;
	private ImageButton dialog;
	private ArrayList<Fragment> pagerItemList;
	public static boolean isResume=false;
	/**
	 * 
	 */
	private Action mActionBar;
	/**
	 * 人物
	 */
	private TextView tab_figure;
	/**
	 * 图片
	 */
	private TextView tab_image;
	/**
	 * 报告
	 */
	private TextView tab_report;
	/**
	 * 新技术
	 */
	private TextView tab_technique;
	/**
	 * 新闻
	 */
	private TextView tab_news;
	private View view_tabs;
	/**
	 * tab下标
	 */
	private TextView tab_underline;
	/**
	 * 默认的tab宽度
	 */
	private int tab_width=0;
	/**
	 * 间隔的宽度
	 */
	private int split_width=0;
	private int current_index=0;
	
	private LinearLayout main_tabs;
	private LinearLayout linear_main_tab_items;
	private LinearLayout linear_main_tab_underline;
	private Versions versions;
	private String url;
    private DownloadService.DownloadBinder binder;
    RestsController restsController;
	
	private PopupWindow pop;
//	private int from=-1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		int from=getIntent().getIntExtra("from", -1);
		if (from==0) {
			int type=getIntent().getIntExtra("type", -1);
			int newsid=getIntent().getIntExtra("news_id", -1);
			Intent intentmsg=null;
			if (type==2) {
				intentmsg=new Intent(this, ImageActivitys.class);
			}else {
				intentmsg=new Intent(this, NewsDetailActivity.class);
				intentmsg.putExtra("type", type);
			}
			intentmsg.putExtra("news_id", newsid);
			intentmsg.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intentmsg);
		}else{
			boolean l_state=SetsKeeper.readLauncherState(this);
			if (!l_state) {
				Intent splash=new Intent(this, SetGuideActivity.class);
				splash.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(splash);
			}else {
				Intent splash=new Intent(this, SplashActivity.class);
				splash.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(splash);
			}
		}
		if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
			setContentView(R.layout.night_view_pager);
		}else {
			setContentView(R.layout.view_pager);
		}
		mainActivity=this;
		//注册各推服务
		MessageManager.getInstance().initialize(this.getApplicationContext());
		context=this;
		slidingMenu=getMainFrame();
		rightSets=getRightSets();
		initView();
		ThirdHelp.registerWEIXIN(this);//注册微信，和友盟
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mReceiver, mFilter);
		restsController=new RestsController(context);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		/**
		 * 此处调用基本统计代码
		 */
		MobclickAgent.onResume(this);
		isResume=true;
		tab_underline.getLayoutParams().width=((Configure.screenWidth-ScreenUtil.dip2px(context, 4))/5-ScreenUtil.dip2px(context, 4));
		checkUpdate();
	}
	
	private void initView(){
		linear_main_tab_underline=(LinearLayout)findViewById(R.id.linear_main_tab_underline);
		linear_main_tab_items=(LinearLayout)findViewById(R.id.linear_main_tab_items);
		main_tabs=(LinearLayout)findViewById(R.id.main_tabs);
		tab_figure=(TextView)findViewById(R.id.txt_tab_figure);
		tab_image=(TextView)findViewById(R.id.txt_tab_image);
		tab_report=(TextView)findViewById(R.id.txt_tab_report);
		tab_technique=(TextView)findViewById(R.id.txt_tab_technique);
		tab_news=(TextView)findViewById(R.id.txt_tab_news);
		tab_underline=(TextView)findViewById(R.id.txt_tab_underline);
		tab_figure.setOnClickListener(new TabListener(3));
		tab_image.setOnClickListener(new TabListener(1));
		tab_news.setOnClickListener(new TabListener(0));
		tab_report.setOnClickListener(new TabListener(2));
		tab_technique.setOnClickListener(new TabListener(4));
		showRight = (ImageView) findViewById(R.id.showRight);
		pop=PopuDialog.getNetStatePopuDialog(context,getLayoutInflater());
		mPager = (ViewPager) findViewById(R.id.pager);
		mAdapter =new MainFragmentAdapter(getSupportFragmentManager());
		mPager.setAdapter(mAdapter);
		mPager.setCurrentItem(0);
		mPager.setOffscreenPageLimit(1);
		pagerItemList=mAdapter.getItemList();
		mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				
				if (position==pagerItemList.size()-1) {
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				}else {
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
				}
				initTabUnderline();
				Animation animation=null;
				float fx=0;
				float tx=0;
				int width=0;//underline宽度
				switch (position) {
				case 0:
					if (current_index==1) {
						fx=tab_width+split_width;
						tx=0;
						animation=new TranslateAnimation(fx, tx, 0, 0);
					}else if (current_index==2) {
						fx=(tab_width+split_width)*2;
						tx=0;
						animation=new TranslateAnimation(fx, tx, 0, 0);
					}else if (current_index==3) {
						fx=(tab_width+split_width)*3;
						tx=0;
						animation=new TranslateAnimation(fx, tx, 0, 0);
					}else if (current_index==4) {
						fx=(tab_width+split_width)*4;
						tx=0;
						animation=new TranslateAnimation(fx, tx, 0, 0);
					}
					width=tab_news.getWidth();
					break;
				case 1:
					if (current_index==0) {
						fx=0;
						tx=tab_width+split_width;
						animation=new TranslateAnimation(fx, tx, 0, 0);
					}else if (current_index==2) {
						fx=(tab_width+split_width)*2;
						tx=tab_width+split_width;
						animation=new TranslateAnimation(fx, tx, 0, 0);
					}else if (current_index==3) {
						fx=(tab_width+split_width)*3;
						tx=tab_width+split_width;
						animation=new TranslateAnimation(fx, tx, 0, 0);
					}else if (current_index==4) {
						fx=(tab_width+split_width)*4;
						tx=tab_width+split_width;
						animation=new TranslateAnimation(fx, tx, 0, 0);
					}
					width=tab_news.getWidth();
					break;
				case 2:
					if (current_index==0) {
						fx=0;
						tx=(tab_width+split_width)*2;
						animation=new TranslateAnimation(fx, tx, 0, 0);
					}else if (current_index==1) {
						fx=tab_width+split_width;
						tx=(tab_width+split_width)*2;
						animation=new TranslateAnimation(fx, tx, 0, 0);
					}else if (current_index==3) {
						fx=(tab_width+split_width)*3;
						tx=(tab_width+split_width)*2;
						animation=new TranslateAnimation(fx, tx, 0, 0);
					}else if (current_index==4) {
						fx=(tab_width+split_width)*4;
						tx=(tab_width+split_width)*2;
						animation=new TranslateAnimation(fx, tx, 0, 0);
					}
					width=tab_news.getWidth();
					break;
				case 3:
					if (current_index==0) {
						fx=0;
						tx=(tab_width+split_width)*3;
						animation=new TranslateAnimation(fx, tx, 0, 0);
					}else if (current_index==1) {
						fx=tab_width+split_width;
						tx=(tab_width+split_width)*3;
						animation=new TranslateAnimation(fx, tx, 0, 0);
					}else if (current_index==2) {
						fx=(tab_width+split_width)*2;
						tx=(tab_width+split_width)*3;
						animation=new TranslateAnimation(fx, tx, 0, 0);
					}else if (current_index==4) {
						fx=(tab_width+split_width)*4;
						tx=(tab_width+split_width)*3;
						animation=new TranslateAnimation(fx, tx, 0, 0);
					}
					width=tab_news.getWidth();
					break;
				case 4:
					if (current_index==0) {
						fx=0;
						tx=(tab_width+split_width)*4;
						animation=new TranslateAnimation(fx, tx, 0, 0);
					}else if (current_index==1) {
						fx=tab_width+split_width;
						tx=(tab_width+split_width)*4;
						animation=new TranslateAnimation(fx, tx, 0, 0);
					}else if (current_index==2) {
						fx=(tab_width+split_width)*2;
						tx=(tab_width+split_width)*4;
						animation=new TranslateAnimation(fx, tx, 0, 0);
					}else if (current_index==3) {
						fx=(tab_width+split_width)*3;
						tx=(tab_width+split_width)*4;
						animation=new TranslateAnimation(fx, tx, 0, 0);
					}
					width=tab_technique.getWidth();
					break;
				default:
					break;
				}
				tab_underline.getLayoutParams().width=width;
				current_index = position;
			    animation.setFillAfter(true);
				animation.setDuration(100);
				tab_underline.startAnimation(animation);
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}

			@Override
			public void onPageScrollStateChanged(int position) {

				

			}
		});
		showRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!slidingMenu.isMenuShowing()) {
					slidingMenu.showMenu(false);
				}else {
					slidingMenu.showContent(true);
				}
			}
		});
	}
	/**
	 * 初始化underline数据
	 */
	private void initTabUnderline(){
		tab_width=tab_news.getWidth();
		split_width=ScreenUtil.dip2px(context, 1);
		tab_underline.setWidth(tab_width);
	}
	
	/**
	 * 是否是第一页
	 * @return
	 */
	public boolean isFirst() {
		if (mPager.getCurrentItem() == 0)
			return true;
		else
			return false;
	}

	/**
	 * 是否最后一页
	 * @return
	 */
	public boolean isEnd() {
		if (mPager.getCurrentItem() == pagerItemList.size() - 1)
			return true;
		else
			return false;
	}
	
	/**
	 * tabs监听器
	 * @author Eilin.Yang
	 * @since 2013-10-16
	 */
	private class TabListener implements OnClickListener {
		private int index=0;
		public TabListener(int index) {
			this.index=index;
		}
		
		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index, true);
		}
	};
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isResume=false;
		unregisterReceiver(mReceiver);
		 if (null != binder)
	        {  
	            unbindService(conn);              
	        }
	        cancel();
	}
	/**
	 * <pre> 夜间模式</pre>
	 *
	 */
	public void setReadModeNight(){
		int mColor=Color.parseColor("#23262b");
		main_tabs.setBackgroundColor(mColor);
		linear_main_tab_items.setBackgroundColor(mColor);
		tab_news.setBackgroundResource(R.drawable.night_tab_indicator_bg);
		tab_news.setTextColor(Color.WHITE);
		tab_image.setBackgroundResource(R.drawable.night_tab_indicator_bg);
		tab_image.setTextColor(Color.WHITE);
		tab_report.setBackgroundResource(R.drawable.night_tab_indicator_bg);
		tab_report.setTextColor(Color.WHITE);
		tab_figure.setBackgroundResource(R.drawable.night_tab_indicator_bg);
		tab_figure.setTextColor(Color.WHITE);
		tab_technique.setBackgroundResource(R.drawable.night_tab_indicator_bg);
		tab_technique.setTextColor(Color.WHITE);
		linear_main_tab_underline.setBackgroundColor(mColor);
		mPager.setBackgroundColor(Color.parseColor("#363b41"));
	}
	/**
	 * <pre>白天模式</pre>
	 *
	 */
	public void setReadModeDay(){
		int mColor=Color.parseColor("#ecedee");
		main_tabs.setBackgroundColor(mColor);
		linear_main_tab_items.setBackgroundColor(mColor);
		tab_news.setBackgroundResource(R.drawable.tab_indicator_bg);
		tab_news.setTextColor(Color.BLACK);
		tab_image.setBackgroundResource(R.drawable.tab_indicator_bg);
		tab_image.setTextColor(Color.BLACK);
		tab_report.setBackgroundResource(R.drawable.tab_indicator_bg);
		tab_report.setTextColor(Color.BLACK);
		tab_figure.setBackgroundResource(R.drawable.tab_indicator_bg);
		tab_figure.setTextColor(Color.BLACK);
		tab_technique.setBackgroundResource(R.drawable.tab_indicator_bg);
		tab_technique.setTextColor(Color.BLACK);
		linear_main_tab_underline.setBackgroundColor(mColor);
		mPager.setBackgroundColor(0);
	}

	public void onPause() {
		super.onPause();
		/**
		 * 此处调用基本统计代码
		 */
		MobclickAgent.onPause(this);
	}
	
	private ConnectivityManager connectivityManager;
	private NetworkInfo info;
	public BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				info = connectivityManager.getActiveNetworkInfo();
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (info != null && info.isAvailable()) {
							if (pop!=null&&pop.isShowing()) {
								pop.dismiss();
							}
						} else {
							if (pop!=null&&!pop.isShowing()) {
								pop.showAsDropDown(main_tabs);
							}
						}
					}
				}, 160);
			}
		}
	};
	
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
			return;
		}
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				versions=restsController.checkVersion(2);
				if (versions!=null) {
					handler.sendEmptyMessage(101);
				}
			}
		}).start();
	}
	// APK update	
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if (msg.what==101) {
				String currentVersion=StringUtils.getAppVersionName(context);
				String netVersion=versions.getLatest_version();
				if (!StringUtils.isEmpty(netVersion)) {
					int a=StringUtils.getAppVersionNameInt(context);
					int b=StringUtils.parseArrayToInt(netVersion);
					if (b>a) {
						new AlertDialog.Builder(context).setTitle("发现新版本"+versions.getLatest_version())
						.setMessage(versions.getUpdate_log())
						.setIcon(R.drawable.ic_launcher).setNegativeButton("立即更新", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								doUpdate();
								Log.i("UPDATES", "立即更新");
							}
						})
						.setPositiveButton("暂不更新", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								Log.i("UPDATES", "暂不更新");
							}
						}).create().show();
					}
				}
			}
		}
		
	};
	

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
}
