package com.dfws.shhreader.slidingmenu.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dfws.shhreader.R;
import com.dfws.shhreader.activity.AppInstance;
import com.dfws.shhreader.activity.DownloadFileService;
import com.dfws.shhreader.activity.MainActivity;
import com.dfws.shhreader.activity.personalcenter.CollectionActivity;
import com.dfws.shhreader.activity.personalcenter.LoginActivity;
import com.dfws.shhreader.activity.personalcenter.LogoutActivity;
import com.dfws.shhreader.activity.personalcenter.MessageActivity;
import com.dfws.shhreader.activity.personalcenter.SetingActivity;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.controllers.NewsController;
import com.dfws.shhreader.controllers.SetsController;
import com.dfws.shhreader.database.sharedpreferences.SetsKeeper;
import com.dfws.shhreader.net.utils.DownloadHander;
import com.dfws.shhreader.ui.dialog.CusDialog;
import com.dfws.shhreader.ui.dialog.CustomerToast;
import com.dfws.shhreader.utils.NetWorkUtils;
import com.dfws.shhreader.utils.StringUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * 右边菜单
 * 
 * @author Eilin.Yang
 * 
 */
public class RightSets extends Fragment {

	/**
	 * 登录账号
	 */
	private LinearLayout linear_login;
	/**
	 * 登录状态
	 */
	public static TextView txt_right_login_status;
	/**
	 * 我的收藏
	 */
	private LinearLayout linear_mytrover;
	/**
	 * 我的消息
	 */
	private LinearLayout linear_mymessage;
	/**
	 * 离线下载
	 */
	private LinearLayout linear_offline_download;
	/**
	 * 阅读模式
	 */
	private LinearLayout linear_reading_model;
	/**
	 * 设置
	 */
	private LinearLayout linear_sets;
	/**
	 * 阅读模式图标
	 */
	public static ImageView iv_right_reading_model;
	/**
	 * 下载相关的
	 */
	public static SeekBar pb_right_download_progress;// 进度条
	public static TextView txt_right_download_progress;// 进度%比
	public static RelativeLayout linear_right_downloading;
	private TextView seting_model;
	// user 图片
	public static ImageView login_image_8_gone, login_image_1_view;
	// 实例化
	private AppInstance appInstance;
	private Context context;
	private LinearLayout scrollview_view;
	public static TextView txt_seting_readmode;
	private ImageView download_cancel;// 取消下载
//	private Intent serviceIntent;// intent
	private SharedPreferences sp = null;// 保存账号密码
	private String userName = null, userPass = null;// 账号和密码
	private SetsController setsController;// 控制器
	private boolean state = true;
	private int strsult;// 接受login
	private CusDialog loaDialog;
	private DownloadHander downloadHander;
	private int load_count=0;
	private int load_getsize=0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = getActivity();
		// 初始化
		appInstance = (AppInstance) context.getApplicationContext();
		setsController = new SetsController(context);
		downloadHander=new DownloadHander(context);
		downloadHander.setLoadListener(loadListener);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.right, null);
		// 初始化各类控件
		linear_sets = (LinearLayout) view.findViewById(R.id.linear_sets);
		linear_reading_model = (LinearLayout) view
				.findViewById(R.id.linear_reading_model);
		linear_offline_download = (LinearLayout) view
				.findViewById(R.id.linear_offline_download);
		linear_mymessage = (LinearLayout) view
				.findViewById(R.id.linear_mymessage);
		linear_mytrover = (LinearLayout) view
				.findViewById(R.id.linear_mytrover);
		linear_login = (LinearLayout) view.findViewById(R.id.linear_login);
		scrollview_view = (LinearLayout) view
				.findViewById(R.id.scrollview_view);
		txt_right_login_status = (TextView) view
				.findViewById(R.id.txt_right_login_status);
		iv_right_reading_model = (ImageView) view
				.findViewById(R.id.iv_right_reading_model);

		// 下载部分
		pb_right_download_progress = (SeekBar) view
				.findViewById(R.id.pb_right_download_progress);
		pb_right_download_progress.setEnabled(false);
		txt_right_download_progress = (TextView) view
				.findViewById(R.id.txt_right_download_progress);
		linear_right_downloading = (RelativeLayout) view
				.findViewById(R.id.linear_right_downloading);
		seting_model = (TextView) view.findViewById(R.id.seting_model);
		txt_seting_readmode = (TextView) view
				.findViewById(R.id.txt_seting_readmode);
		download_cancel = (ImageView) view.findViewById(R.id.download_cancel);

		// user图片
		login_image_8_gone = (ImageView) view.findViewById(R.id.login_image_8);
		login_image_1_view = (ImageView) view.findViewById(R.id.login_image_1);
		addListener();
		return view;
	}

	/**
	 * 添加监听
	 */
	public void addListener() {
		linear_sets.setOnClickListener(setListtenter);
		linear_reading_model.setOnClickListener(setListtenter);
		linear_offline_download.setOnClickListener(setListtenter);
		linear_mymessage.setOnClickListener(setListtenter);
		linear_mytrover.setOnClickListener(setListtenter);
		linear_login.setOnClickListener(setListtenter);

	}
	
	private Handler loadHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0://开始下载文件
				linear_offline_download.setEnabled(false);
				txt_right_download_progress.setTextColor(Color.WHITE);
				pb_right_download_progress.setProgress(0);
				txt_right_download_progress.setText("准备下载中...");
				break;

			case 100://文件下载失败
				pb_right_download_progress.setProgress(0);
				txt_right_download_progress.setText("文件下载失败");
				break;
			case 10://开始下载图片
//				linear_offline_download.setEnabled(true);
				pb_right_download_progress.setMax(load_count);
				txt_right_download_progress.setText("准备下载中...");
				break;
			case 11://正在下载图片
				linear_offline_download.setEnabled(true);
				int pr = ((load_getsize * 100) / load_count);
				pb_right_download_progress.setProgress(load_getsize);
				if (pr <= 20) {
					RightSets.txt_right_download_progress.setText("新闻 "
							+ pr + "%");
				} else if (pr <= 40) {
					RightSets.txt_right_download_progress.setText("图片 "
							+ pr + "%");
				} else if (pr <= 60) {
					RightSets.txt_right_download_progress.setText("报告 "
							+ pr + "%");
				} else if (pr <= 80) {
					RightSets.txt_right_download_progress.setText("人物 "
							+ pr + "%");
				} else if (pr <= 100) {
					RightSets.txt_right_download_progress.setText("新技术 "
							+ pr + "%");
				}
				break;
			case 12://取消下载图片
				seting_model.setVisibility(View.VISIBLE);
				linear_right_downloading.setVisibility(View.GONE);
				pb_right_download_progress.setProgress(0);
				Toast.makeText(context, "已取消下载", Toast.LENGTH_LONG).show();
				break;
			case 13://完成下载图片
				state = true;
				seting_model.setVisibility(View.VISIBLE);
				linear_right_downloading.setVisibility(View.GONE);
				pb_right_download_progress.setProgress(load_count);
				Toast.makeText(context, "下载已完成", Toast.LENGTH_LONG).show();
				break;
			case 200://下载图片失败
				linear_offline_download.setEnabled(true);
				pb_right_download_progress.setProgress(0);
				txt_right_download_progress.setText("准备下载中...");
				break;
			case 300://
				pb_right_download_progress.setProgress(0);
				txt_right_download_progress.setText("准备下载中...");
				break;
			}
		}
		
	};
	
	private DownloadHander.LoadListener loadListener=new DownloadHander.LoadListener() {
		
		@Override
		public void loadThumb(int states) {
			// TODO Auto-generated method stub
			Log.i("Offline","loadThumb-------->>state="+states);
			
		}
		
		@Override
		public void loadImage(int states, int count, int getSize) {
			Log.i("Offline","loadImage-------->>state="+states);
			Log.i("Offline","loadImage-------->>count="+count);
			Log.i("Offline","loadImage-------->>getSize="+getSize);
			if (states==0) {
				load_count=count;
				load_getsize=0;
				loadHandler.sendEmptyMessage(10);//开始下载图片
			}
			if (states==1) {
				load_count=count;
				load_getsize=getSize;
				loadHandler.sendEmptyMessage(11);//正在下载图片
			}
			if (states==2) {
				load_count=0;
				load_getsize=0;
				loadHandler.sendEmptyMessage(12);//取消下载图片
			}
			if (states==3) {
				load_count=count;
				load_getsize=getSize;
				loadHandler.sendEmptyMessage(13);//完成下载图片
			}
			if (states==-1) {
				load_count=0;
				load_getsize=0;
				loadHandler.sendEmptyMessage(200);//下载图片失败
			}
		}
		
		@Override
		public void loadFile(int states) {
			Log.i("Offline","loadFile-------->>state="+states);
			if (states==0) {
				loadHandler.sendEmptyMessage(0);//文件下载开始
			}
			if (states==-1) {
				loadHandler.sendEmptyMessage(100);//文件下载失败
			}
		}
	};

	/**
	 * 监听事件
	 */
	private View.OnClickListener setListtenter = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			// 下载
			case R.id.linear_offline_download:
				MobclickAgent.onEvent(context, "offline_download");
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					return;
				}
				if (state) {
					state=false;
					linear_right_downloading.setVisibility(View.VISIBLE);
					seting_model.setVisibility(View.GONE);
					downloadHander.loading();
				}else {
					state = true;
					downloadHander.setLoadingState(false);
					seting_model.setVisibility(View.VISIBLE);
					linear_right_downloading.setVisibility(View.GONE);
					linear_offline_download.setVisibility(View.VISIBLE);
					
				}
				break;
			// 夜间模式
			case R.id.linear_reading_model:
				if (FrameConfigure.reading_type == FrameConfigure.TYPE_NIGHT) {
					MobclickAgent.onEvent(context, "main_readmode_day");
					iv_right_reading_model
							.setImageResource(R.drawable.ic_readmode_night);
					txt_seting_readmode
							.setText(R.string.frame_right_readmode_night);
					SetsKeeper.keepReadType(context, FrameConfigure.TYPE_DAY);
					FrameConfigure.reading_type = FrameConfigure.TYPE_DAY;
					MainActivity.mainActivity.setReadModeDay();
				} else {
					MobclickAgent.onEvent(context, "main_readmode_night");
					iv_right_reading_model
							.setImageResource(R.drawable.ic_readmode_day);
					txt_seting_readmode
							.setText(R.string.frame_right_readmode_day);
					SetsKeeper.keepReadType(context, FrameConfigure.TYPE_NIGHT);
					FrameConfigure.reading_type = FrameConfigure.TYPE_NIGHT;
					MainActivity.mainActivity.setReadModeNight();
				}
				MainActivity.mAdapter.notifyDataSetChanged();
				break;
			// 设置
			case R.id.linear_sets:
				Intent intent_set = new Intent((Activity) context,
						SetingActivity.class);
				startActivity(intent_set);
				break;
			// 我的收藏
			case R.id.linear_mytrover:
				// 判断是否登录，如果没登录就跳到登录界面
				if (!StringUtils.isEmpty(appInstance.pass_token)) {
					Intent intent_model = new Intent(context,
							CollectionActivity.class);
					startActivity(intent_model);
				} else {
					Intent intent = new Intent(context, LoginActivity.class);
					intent.putExtra("from", 1);// 收藏
					startActivity(intent);
				}

				break;
			// 我的信息
			case R.id.linear_mymessage:
				Intent intent_message = new Intent(context,
						MessageActivity.class);
				startActivity(intent_message);
				break;
			// 登录
			case R.id.linear_login:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					return;
				}
				if (StringUtils.isEmpty(appInstance.pass_token)) {
					Intent intent_login = new Intent((Activity) context,
							LoginActivity.class);
					startActivity(intent_login);
				} else {
					AppInstance.isProgramExit=false;
					Intent intent_login = new Intent((Activity) context,
							LogoutActivity.class);
					startActivity(intent_login);
				}
				break;
			// 取消下载
			case R.id.download_cancel:
				
				break;
			}
		}
	};

	// 夜间模式and 白天模式
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (FrameConfigure.reading_type == FrameConfigure.TYPE_NIGHT) {
			iv_right_reading_model.setImageResource(R.drawable.ic_readmode_day);
			txt_seting_readmode.setText(R.string.frame_right_readmode_day);
		} else {
			iv_right_reading_model
					.setImageResource(R.drawable.ic_readmode_night);
			txt_seting_readmode.setText(R.string.frame_right_readmode_night);
		}
		
		if (!NetWorkUtils.checkNetWork(context)) {
			CustomerToast.showMessage(context, "请检查网络连接！", false, true);
			return;
		}
		sp = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		if (appInstance.user.getName() == null|| appInstance.user.getName().equals("")) {
			RightSets.txt_right_login_status.setText("登录账号");
		} else {
			userName = sp.getString("userNmae", userName);
			userPass = sp.getString("userPass", userPass);
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					strsult = setsController.login(userName, userPass);
					handler.sendEmptyMessage(2);

				}
			}).start();
		}
	}

	// 关闭服务
	@Override
	public void onDestroy() {
		super.onDestroy();
//		context.unregisterReceiver(receiver);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		// TODO
		super.onResume();
	}

	// 注册广播
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			int count = bundle.getInt("countsize", 0);
			int getsize = bundle.getInt("getsize", 0);
			txt_right_download_progress.setTextColor(Color.WHITE);
			if (count == 0) {
				pb_right_download_progress.setMax(count);
				pb_right_download_progress.setProgress(0);
				txt_right_download_progress.setText("准备下载中...");
			} else {
				if (getsize == 0) {
					pb_right_download_progress.setMax(count);
					pb_right_download_progress.setProgress(0);
					txt_right_download_progress.setText("准备下载中...");
				} else {
					int pr = ((getsize * 100) / count);
					pb_right_download_progress.setMax(count);
					pb_right_download_progress.setProgress(getsize);
					if (pr <= 20) {
						RightSets.txt_right_download_progress.setText("新闻 "
								+ pr + "%");
					} else if (pr <= 40) {
						RightSets.txt_right_download_progress.setText("图片 "
								+ pr + "%");
					} else if (pr <= 60) {
						RightSets.txt_right_download_progress.setText("报告 "
								+ pr + "%");
					} else if (pr <= 80) {
						RightSets.txt_right_download_progress.setText("人物 "
								+ pr + "%");
					} else if (pr <= 100) {
						RightSets.txt_right_download_progress.setText("新技术 "
								+ pr + "%");
					}
					if (pr == 100) {
//						context.stopService(serviceIntent);
						seting_model.setVisibility(View.VISIBLE);
						linear_right_downloading.setVisibility(View.GONE);
						pb_right_download_progress.setProgress(0);
//						txt_right_download_progress.setText("下载已完成");
						Toast.makeText(context, "下载已完成", Toast.LENGTH_LONG).show();
						state = true;
					}
				}
			}
		}
	};
	
	//刷新UI
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 2:
				if (strsult == 1) {
					MobclickAgent.onEvent(context, "login");
					RightSets.txt_right_login_status.setText(appInstance.user
							.getName());
					//自动登录
					if (appInstance.pass_token == null) {
						RightSets.login_image_1_view.setVisibility(View.VISIBLE);
					} else {
						RightSets.login_image_1_view.setVisibility(View.GONE);
						RightSets.login_image_8_gone.setVisibility(View.VISIBLE);
					}
				} else {
				}
				break;
			}
		}
	};
	
}
