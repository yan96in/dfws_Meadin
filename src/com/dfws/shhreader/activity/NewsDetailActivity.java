/**
 * Copyright © 2013 www.veryeast.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package com.dfws.shhreader.activity;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.GpsStatus.NmeaListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.dfws.shhreader.R;
import com.dfws.shhreader.activity.personalcenter.BaseActivity;
import com.dfws.shhreader.activity.personalcenter.LoginActivity;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.configures.NewsConfigure;
import com.dfws.shhreader.configures.Configure;
import com.dfws.shhreader.controllers.CollectionController;
import com.dfws.shhreader.controllers.NewsController;
import com.dfws.shhreader.database.sharedpreferences.SetsKeeper;
import com.dfws.shhreader.database.tools.FavoriteDatabaseHelper;
import com.dfws.shhreader.entity.MediaInfo;
import com.dfws.shhreader.entity.News;
import com.dfws.shhreader.net.utils.DetailImageLoader;
import com.dfws.shhreader.net.utils.DetailImageLoader.Callback;
import com.dfws.shhreader.slidingmenu.fragment.BaseFragment;
import com.dfws.shhreader.slidingmenu.fragment.FigureFragment;
import com.dfws.shhreader.slidingmenu.fragment.TechniqueFragment;
import com.dfws.shhreader.slidingmenu.fragment.FigureFragment.FigureLoadNextListener;
import com.dfws.shhreader.slidingmenu.fragment.NewsFragment;
import com.dfws.shhreader.slidingmenu.fragment.NewsFragment.NewsLoadNextListener;
import com.dfws.shhreader.slidingmenu.fragment.ReportFragment;
import com.dfws.shhreader.slidingmenu.fragment.ReportFragment.ReportLoadNextListener;
import com.dfws.shhreader.slidingmenu.fragment.RightSets;
import com.dfws.shhreader.slidingmenu.fragment.TechniqueFragment.TechniqueLoadNextListener;
import com.dfws.shhreader.ui.dialog.CustomerToast;
import com.dfws.shhreader.ui.dialog.PopuDialog;
import com.dfws.shhreader.ui.dialog.ShareCustomDialog;
import com.dfws.shhreader.utils.BitmapTools;
import com.dfws.shhreader.utils.DateTimeUtils;
import com.dfws.shhreader.utils.MD5Utils;
import com.dfws.shhreader.utils.NetWorkUtils;
import com.dfws.shhreader.utils.ScreenUtil;
import com.dfws.shhreader.utils.StringUtils;
import com.dfws.shhreader.utils.ThirdHelp;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.DirectionListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.umeng.analytics.MobclickAgent;

/**<h2>新闻详细页 <h2>
 * <pre> 普通新闻详细页</pre>
 * @author 东方网升Eilin.Yang
 * @since 2013-10-28
 * @version v1.0
 * @modify ""
 */
public class NewsDetailActivity extends BaseActivity {
	
	private Context context;
	/**可拖拽的scrollview*/
	private PullToRefreshScrollView scrollView;
	/**scrollview*/
	private ScrollView mScrollView;
	/** 详细内容*/
	private WebView mWebView;
	/**评论*/
	private RelativeLayout relative_news_detail_comment;
	/**评论*/
	private ImageView iv_news_detail_comment;
	/**评论通知 */
	private TextView txt_news_detail_notice;
	/**分享*/
	private ImageView iv_news_detail_share;
	/**分享 */
	private ImageView iv_news_detail_favorite;
	/**更多 */
	private ImageView iv_news_detail_more;
	/**返回 */
	private ImageView iv_news_detail_back;
	
	/**当前新闻*/
	private News mNews;
	/** 当前图片集*/
	private List<MediaInfo> medias;
	/**数据控制器*/
	private NewsController controller;
	/** 收藏管理*/
	private CollectionController msgController;
	/**线程池*/
	private ExecutorService executorService;
	/**线程池默认大小*/
	private int THREAD_POOL_SIZE=6;
	/** 设置参数 */
	private WebSettings settings;
	/** 当前位置*/
	private int current_position=0;
	/** 当前新闻id*/
	private int id_current=1;
	/**上一篇标题 */
	private String title_up;
	/** 上一篇id*/
	private int id_up;
	/** 下一篇标题*/
	private String title_down;
	/**下一篇id*/
	private int id_down;
	/** 加载模式 */
	private int load_type=BaseFragment.FIRST_LOAD;
	/** 默认字体大小 */
	private int textSize=1;
	/** 从哪个页面跳转来。1：新闻，3：报告，4：人物，5：新技术 */
	private int from=-1;
	/** 字体小 */
	private TextView fontSize_small;
	/** 字体中 */
	private TextView fontSize_mid;
	/** 字体大 */
	private TextView fontSize_big;
	/** 字体超大*/
	private TextView fontSize_super_big;
	/** 阅读模式*/
	private ToggleButton tb_readMode;
	/** 图片加载工具 */
	private DetailImageLoader imageLoader;
	/** 更多 */
	private LinearLayout linear_more;
	/** 更多是否显示*/
	private boolean ismore=false;
	/** 底部菜单*/
	private RelativeLayout rela_menu;
	/** 收藏新闻用*/
	private FavoriteDatabaseHelper databaseHelper;
	/** 是否收藏 */
	private boolean isFavorited;
	private AppInstance appInstance;
	/** 评论总数 */
	private int comment_count=0;
	/** 分享*/
	private ShareCustomDialog shareDialog;
	
	private RelativeLayout rela_news_detail_main;
	private RelativeLayout rela_news_detail_font_size;
	private TextView txt_news_detail_font_size;
	private LinearLayout linear_news_detail_font_size;
	private RelativeLayout rela_news_detail_read_mode;
	private TextView txt_news_detail_read_mode;
	private LinearLayout tab_bottom;
	/**网络状态提醒*/
	private PopupWindow pop;
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			case BaseFragment.FIRST_LOAD://首次加载
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						mWebView.loadUrl("javascript:inits()");
					}
				}, 1000);				
				break;
			case BaseFragment.REFRESH_PULL://上一页
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						mWebView.loadUrl("javascript:inits()");
					}
				}, 1000);
				scrollView.onRefreshComplete();
				scrollToFirst();
				break;
			case BaseFragment.PAGE_NEXT_LOAD://下一页
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						mWebView.loadUrl("javascript:inits()");
					}
				}, 1000);
				scrollView.onRefreshComplete();
				scrollToFirst();
				break;
			case BaseFragment.FIRST_REQUEST_ERROR://首次加载错误
				if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
					mWebView.loadUrl("file:///android_asset/night_weberror.html");
				}else {
					mWebView.loadUrl("file:///android_asset/weberror.html");
				}
				break;
			case BaseFragment.REFRESH_REQUEST_ERROR://上一篇错误
				++current_position;
				scrollView.onRefreshComplete();
				if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
					mWebView.loadUrl("file:///android_asset/night_weberror.html");
				}else {
					mWebView.loadUrl("file:///android_asset/weberror.html");
				}
				break;
			case BaseFragment.PAGE_NEXT_REQUEST_ERROR://下一篇错误
				--current_position;
				scrollView.onRefreshComplete();
				if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
					mWebView.loadUrl("file:///android_asset/night_weberror.html");
				}else {
					mWebView.loadUrl("file:///android_asset/weberror.html");
				}
				break;
				
			case 101://加载图片成功
				MediaInfo md=(MediaInfo)msg.obj;
				if (md!=null) {
					String id=md.getId();
					String path="file://"+md.getPath();
					mWebView.loadUrl("javascript:showImage(\""+id+"\",\""+path+"\")");
				}
				break;
				
			case 200://
				
				break;
			case 201://收藏成功
				iv_news_detail_favorite.setEnabled(true);
				isFavorited=true;
				mNews.setCollected(true);
				CustomerToast.showMessage(context, "已添加到\"我的收藏\"！", false, true);
				break;
			case 401://通知加载成功
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						mWebView.loadUrl("javascript:inits()");
					}
				}, 1000);
				break;

			case 402://通知加载失败
				if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
					mWebView.loadUrl("file:///android_asset/night_weberror.html");
				}else {
					mWebView.loadUrl("file:///android_asset/weberror.html");
				}
				break;
			case 501://
				iv_news_detail_favorite.setEnabled(true);
				isFavorited=false;
				mNews.setCollected(false);
				CustomerToast.showMessage(context, "已取消收藏！", false, true);
				break;
			case 1001:
				CustomerToast.showMessage(context, "请检查网络连接！", false, false);
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		from=getIntent().getIntExtra("from", -1);
		if (from==-1) {
			finish();
		}
		setContentView(R.layout.layout_news_detail_web);
		context=this;
		controller=new NewsController(context);
		executorService=Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		appInstance=(AppInstance)getApplicationContext();
		initView();
		setupWebView();
		initReadMode();
		imageLoader=new DetailImageLoader(context);
		databaseHelper=new FavoriteDatabaseHelper(context);
		initClickImgLoad();
		msgController=new CollectionController(context);
		switch (from) {
		case 0:
			current_position=0;
			id_current=getIntent().getIntExtra("news_id", -1);
			break;
		case 1:
			current_position=NewsFragment.current_position;
			break;
		case 3:
			current_position=ReportFragment.current_position;
			break;
		case 4:
			current_position=FigureFragment.current_position;
			break;
		case 5:
			current_position=TechniqueFragment.current_position;
			break;
		
		}
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mReceiver, mFilter);
		load_type=BaseFragment.FIRST_LOAD;
		executorService.submit(new LoadThread());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		/**
		 * 此处调用基本统计代码
		 */
		MobclickAgent.onResume(this);
	}
	
	/**
	 * <pre>滚动到其实位置</pre>
	 *
	 */
	private void scrollToFirst(){
		if (scrollView!=null){
			mWebView.setFocusable(false);
			mWebView.setFocusableInTouchMode(false);
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					scrollView.scrollBy(0, 1);
				}
			}, 160);
		}
	}
	
	/**
	 * <pre>初始化通知</pre>
	 *
	 */
	private void initNotice(){
		if (mNews==null||null==txt_news_detail_notice) {
			return;
		}
		if (mNews.getComment_count()>0) {
			txt_news_detail_notice.setVisibility(View.VISIBLE);
			txt_news_detail_notice.setText(mNews.getComment_count()+"");
		}else {
			txt_news_detail_notice.setVisibility(View.GONE);
			txt_news_detail_notice.setText("");
		}
	}
	
	/**
	 * <pre>初始化图片加载工具</pre>
	 *
	 */
	private void initClickImgLoad(){
		if (imageLoader!=null) {
			imageLoader.start();
		}
	}
	
	/**
	 * <pre>初始化图片加载工具</pre>
	 *
	 */
	private void initImgLoad(){
		if (medias!=null&&medias.size()>0) {
			if (FrameConfigure.loading_type==FrameConfigure.TYPE_IMG_SMART) {
				if (NetWorkUtils.isWifi(context)) {
					loadImages();
				}
			}else {
				if (FrameConfigure.loading_type==FrameConfigure.TYPE_IMG_ALLOW) {
					if (NetWorkUtils.checkNetWork(context)) {
						loadImages();
					}
				}
			}
		}
	}
	
	
	/**
	 * 初始化控件
	 */
	private void initView() {
		// TODO Auto-generated method stub
		rela_news_detail_main=(RelativeLayout)findViewById(R.id.rela_news_detail_main);
		scrollView=(PullToRefreshScrollView)findViewById(R.id.pull_scrollview);
		scrollView.setMode(Mode.BOTH);
		mWebView=(WebView)findViewById(R.id.wv_news_detail_content);
		rela_menu=(RelativeLayout)findViewById(R.id.rela_menu);
		tab_bottom=(LinearLayout)findViewById(R.id.tab_bottom);
		initMenuMore();
		iv_news_detail_back=(ImageView)findViewById(R.id.iv_news_detail_back);
		iv_news_detail_back.setOnClickListener(listener);
		iv_news_detail_comment=(ImageView)findViewById(R.id.iv_news_detail_comment);
		iv_news_detail_comment.setOnClickListener(listener);
		relative_news_detail_comment=(RelativeLayout)findViewById(R.id.relative_news_detail_comment);
		relative_news_detail_comment.setOnClickListener(listener);
		txt_news_detail_notice=(TextView)findViewById(R.id.txt_news_detail_notice);
		txt_news_detail_notice.setOnClickListener(listener);
		iv_news_detail_share=(ImageView)findViewById(R.id.iv_news_detail_share);
		iv_news_detail_share.setOnClickListener(listener);
		iv_news_detail_favorite=(ImageView)findViewById(R.id.iv_news_detail_favorite);
		iv_news_detail_favorite.setOnClickListener(listener);
		iv_news_detail_more=(ImageView)findViewById(R.id.iv_news_detail_more);
		iv_news_detail_more.setOnClickListener(listener);
		initShareDialog();
		scrollView.setDirectionListener(new DirectionListener() {

			@Override
			public void direction(boolean left, boolean right,float def) {
				Log.i("Move", "direction||left/right/def/width:"+left+"/"+right+"/"+def+"/"+Configure.screenWidth);
				if (left&&!right&&def>(0.3*Configure.screenWidth)) {
					if(!NetWorkUtils.checkNetWork(context)){
						CustomerToast.showMessage(context, "网络连接已经断开！", false,true);
						return;
					}
					if(null==mNews)
						return;
					Intent intent=new Intent(context, CommentFromRightActivity.class);
					intent.putExtra("newsid", id_current);
					startActivity(intent);
					overridePendingTransition(R.anim.sliding_in_right,R.anim.sliding_out_right);
				}else if (!left&&right&&def>(0.3*Configure.screenWidth)) {
					finish();
				}
			}

			@Override
			public void touchng() {
				if(View.VISIBLE==linear_more.getVisibility()){
					linear_more.setVisibility(View.GONE);
				}
			}
			
		});
		
		scrollView.setRefreshLabelTextSize(14);
		scrollView.setRefreshLabelColor(Color.GRAY);
		scrollView.setOnRefreshListener(new com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2<ScrollView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				
					if (from==0) {
						scrollView.onRefreshComplete();
						return;
					}
					if (current_position==0) {
						scrollView.onRefreshComplete();
						return;
					}
					load_type=BaseFragment.REFRESH_PULL;
					--current_position;
					executorService.submit(new LoadThread());
					mWebView.loadUrl("file:///android_asset/newspage.html");
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				
					if (from==0) {
						scrollView.onRefreshComplete();
						return;
					}
					if (from==1) {
						if(NewsFragment.adapter.getLastItemObjectId()==id_current) {
							if(!NewsConfigure.list_news_has_more){
								scrollView.onRefreshComplete();
								CustomerToast.showMessage(context, "没有数据了", false,true);
								return;
							}
							scrollView.setFooterTitle("正在加载下一页...");
							scrollView.setRefreshingLabel("正在加载下一页...");
							NewsFragment.loadNextPage(new NewsLoadNextListener(){
	
								@Override
								public void loadFinish() {
									load_type=BaseFragment.PAGE_NEXT_LOAD;
									++current_position;
									executorService.submit(new LoadThread());
									mWebView.loadUrl("file:///android_asset/newspage.html");
								}
	
								@Override
								public void loadEorror() {
									scrollView.onRefreshComplete();
									if (!NetWorkUtils.checkNetWork(context)) {
										CustomerToast.showMessage(context, "请检查网络！", false,true);
									}
								}
								
							});
						}else {
							load_type=BaseFragment.PAGE_NEXT_LOAD;
							++current_position;
							executorService.submit(new LoadThread());
							mWebView.loadUrl("file:///android_asset/newspage.html");
						}
					}
					if (from==3) {
						if(ReportFragment.adapter.getLastItemObjectId()==id_current) {
							if(!NewsConfigure.list_report_has_more){
								scrollView.onRefreshComplete();
								CustomerToast.showMessage(context, "没有数据了", false,true);
								return;
							}
							scrollView.setFooterTitle("正在加载下一页...");
							scrollView.setRefreshingLabel("正在加载下一页...");
							ReportFragment.loadNextPage(new ReportLoadNextListener(){
	
								@Override
								public void loadFinish() {
									load_type=BaseFragment.PAGE_NEXT_LOAD;
									++current_position;
									executorService.submit(new LoadThread());
									mWebView.loadUrl("file:///android_asset/newspage.html");
								}
	
								@Override
								public void loadEorror() {
									scrollView.onRefreshComplete();
									if (!NetWorkUtils.checkNetWork(context)) {
										CustomerToast.showMessage(context, "请检查网络！", false,true);
									}
								}
								
							});
						}else {
							load_type=BaseFragment.PAGE_NEXT_LOAD;
							++current_position;
							executorService.submit(new LoadThread());
							mWebView.loadUrl("file:///android_asset/newspage.html");
						}
					}
					if (from==4) {
						if(FigureFragment.adapter.getLastItemObjectId()==id_current) {
							if(!NewsConfigure.list_figure_has_more){
								scrollView.onRefreshComplete();
								CustomerToast.showMessage(context, "没有数据了", false,true);
								return;
							}
							scrollView.setFooterTitle("正在加载下一页...");
							scrollView.setRefreshingLabel("正在加载下一页...");
							FigureFragment.loadNextPage(new FigureLoadNextListener(){
	
								@Override
								public void loadFinish() {
									load_type=BaseFragment.PAGE_NEXT_LOAD;
									++current_position;
									executorService.submit(new LoadThread());
									mWebView.loadUrl("file:///android_asset/newspage.html");
								}
	
								@Override
								public void loadEorror() {
									scrollView.onRefreshComplete();
									if (!NetWorkUtils.checkNetWork(context)) {
										CustomerToast.showMessage(context, "请检查网络！", false,true);
									}
								}
								
							});
						}else {
							load_type=BaseFragment.PAGE_NEXT_LOAD;
							++current_position;
							executorService.submit(new LoadThread());
							mWebView.loadUrl("file:///android_asset/newspage.html");
						}
					}
					if (from==5) {
						if(TechniqueFragment.adapter.getLastItemObjectId()==id_current) {
							if(!NewsConfigure.list_technique_has_more){
								scrollView.onRefreshComplete();
								CustomerToast.showMessage(context, "没有数据了", false,true);
								return;
							}
							scrollView.setFooterTitle("正在加载下一页...");
							scrollView.setRefreshingLabel("正在加载下一页...");
							TechniqueFragment.loadNextPage(new TechniqueLoadNextListener(){
	
								@Override
								public void loadFinish() {
									load_type=BaseFragment.PAGE_NEXT_LOAD;
									++current_position;
									executorService.submit(new LoadThread());
									mWebView.loadUrl("file:///android_asset/newspage.html");
								}
	
								@Override
								public void loadEorror() {
									scrollView.onRefreshComplete();
									if (!NetWorkUtils.checkNetWork(context)) {
										CustomerToast.showMessage(context, "请检查网络！", false,true);
									}
								}
								
							});
						}else {
							load_type=BaseFragment.PAGE_NEXT_LOAD;
							++current_position;
							executorService.submit(new LoadThread());
							mWebView.loadUrl("file:///android_asset/newspage.html");
						}
					}
			}
		});
		final int w=ScreenUtil.px2dip(context, 20);
		scrollView.setOnPullEventListener(new OnPullEventListener<ScrollView>() {

			@Override
			public void onPullEvent(PullToRefreshBase<ScrollView> refreshView,
					State state, Mode direction) {
				if (from==0) {
					scrollView.onRefreshComplete();
					return;
				}
				if (direction==Mode.PULL_FROM_START) {
					scrollView.setHeaderTitle(title_up);
					scrollView.setRefreshingLabel(title_up);
				}else {
					scrollView.setFooterTitle(title_down);
					scrollView.setRefreshingLabel(title_down);
				}
			}
		});
		if(StringUtils.getApiVersion()>8)
		scrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
		mScrollView=scrollView.getRefreshableView();
		pop=PopuDialog.getNetStatePopuDialog(context,getLayoutInflater());
	}
	
	/**
	 * <pre>初始化</pre>
	 *
	 */
	private void initShareDialog(){
		shareDialog=new ShareCustomDialog(context);
		shareDialog.setOnclickListener(shareListener);
	}
	
	private void initMenuMore(){
		linear_more=(LinearLayout)findViewById(R.id.linear_more);
		rela_news_detail_font_size=(RelativeLayout)findViewById(R.id.rela_news_detail_font_size);
		txt_news_detail_font_size=(TextView)findViewById(R.id.txt_news_detail_font_size);
		linear_news_detail_font_size=(LinearLayout)findViewById(R.id.linear_news_detail_font_size);
		rela_news_detail_read_mode=(RelativeLayout)findViewById(R.id.rela_news_detail_read_mode);
		txt_news_detail_read_mode=(TextView)findViewById(R.id.txt_news_detail_read_mode);
		
		fontSize_big=(TextView)findViewById(R.id.txt_font_size_big);
		fontSize_big.setOnClickListener(listener);
		fontSize_mid=(TextView)findViewById(R.id.txt_font_size_mid);
		fontSize_mid.setOnClickListener(listener);
		fontSize_small=(TextView)findViewById(R.id.txt_font_size_samll);
		fontSize_small.setOnClickListener(listener);
		fontSize_super_big=(TextView)findViewById(R.id.txt_font_size_super_big);
		fontSize_super_big.setOnClickListener(listener);
		tb_readMode=(ToggleButton)findViewById(R.id.btn_night_mode);
		tb_readMode.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					MobclickAgent.onEvent(context, "news_detail_readmode_night");
					int color=Color.parseColor("#363b41");
					FrameConfigure.reading_type=FrameConfigure.TYPE_NIGHT;
					mWebView.loadUrl("javascript:setReadMode(0)");
					scrollView.setBackgroundColor(Color.parseColor("#24272c"));
					mScrollView.setBackgroundColor(Color.parseColor("#24272c"));
					mWebView.setBackgroundColor(Color.parseColor("#24272c"));
					rela_news_detail_main.setBackgroundColor(Color.parseColor("#24272c"));
					tab_bottom.setBackgroundColor(color);
					rela_menu.setBackgroundColor(Color.BLACK);
					linear_more.setBackgroundColor(color);
					rela_news_detail_font_size.setBackgroundColor(color);
					txt_news_detail_font_size.setBackgroundColor(color);
					txt_news_detail_font_size.setTextColor(Color.WHITE);
					linear_news_detail_font_size.setBackgroundColor(Color.BLACK);
					fontSize_big.setBackgroundColor(color);
					fontSize_mid.setBackgroundColor(color);
					fontSize_small.setBackgroundColor(color);
					fontSize_super_big.setBackgroundColor(color);
					fontSize_big.setTextColor(Color.WHITE);
					fontSize_mid.setTextColor(Color.WHITE);
					fontSize_small.setTextColor(Color.WHITE);
					fontSize_super_big.setTextColor(Color.WHITE);
					rela_news_detail_read_mode.setBackgroundColor(color);
					txt_news_detail_read_mode.setBackgroundColor(color);
					txt_news_detail_read_mode.setTextColor(Color.WHITE);
					
					RightSets.iv_right_reading_model.setImageResource(R.drawable.ic_readmode_day);
					RightSets.txt_seting_readmode.setText(R.string.frame_right_readmode_day);
					MainActivity.mainActivity.setReadModeNight();
				}else{
					MobclickAgent.onEvent(context, "news_detail_readmode_day");
					int color=Color.parseColor("#ecedee");
					FrameConfigure.reading_type=FrameConfigure.TYPE_DAY;
					mWebView.loadUrl("javascript:setReadMode(1)");
					mWebView.setBackgroundColor(Color.parseColor("#f4f4f4"));
					scrollView.setBackgroundColor(Color.parseColor("#f4f4f4"));
					mScrollView.setBackgroundColor(Color.parseColor("#f4f4f4"));
					rela_news_detail_main.setBackgroundColor(Color.parseColor("#f0f0f0"));
					tab_bottom.setBackgroundColor(color);
					rela_menu.setBackgroundColor(color);
					linear_more.setBackgroundColor(color);
					rela_news_detail_font_size.setBackgroundColor(color);
					txt_news_detail_font_size.setBackgroundColor(color);
					txt_news_detail_font_size.setTextColor(Color.BLACK);
					linear_news_detail_font_size.setBackgroundColor(Color.parseColor("#DCDCDC"));
					fontSize_big.setBackgroundColor(color);
					fontSize_mid.setBackgroundColor(color);
					fontSize_small.setBackgroundColor(color);
					fontSize_super_big.setBackgroundColor(color);
					fontSize_big.setTextColor(Color.BLACK);
					fontSize_mid.setTextColor(Color.BLACK);
					fontSize_small.setTextColor(Color.BLACK);
					fontSize_super_big.setTextColor(Color.BLACK);
					rela_news_detail_read_mode.setBackgroundColor(color);
					txt_news_detail_read_mode.setBackgroundColor(color);
					txt_news_detail_read_mode.setTextColor(Color.BLACK);
					
					RightSets.iv_right_reading_model.setImageResource(R.drawable.ic_readmode_night);
					RightSets.txt_seting_readmode.setText(R.string.frame_right_readmode_night);
					MainActivity.mainActivity.setReadModeDay();
				}
				textSize=SetsKeeper.readFontSize(context);
				int color_red=Color.parseColor("#ff0000");
				switch (textSize) {
				case 0:
					fontSize_small.setTextColor(color_red);
					break;

				case 1:
					fontSize_mid.setTextColor(color_red);
					break;
				case 2:
					fontSize_big.setTextColor(color_red);
					break;
				case 3:
					fontSize_super_big.setTextColor(color_red);
					break;
				default:
					fontSize_mid.setTextColor(color_red);
					break;
				}
				SetsKeeper.keepReadType(context, FrameConfigure.reading_type);
				MainActivity.mAdapter.notifyDataSetChanged();
			}
		});
		textSize=SetsKeeper.readFontSize(context);
		int color_red=Color.parseColor("#ff0000");
		switch (textSize) {
		case 0:
			fontSize_small.setTextColor(color_red);
			break;

		case 1:
			fontSize_mid.setTextColor(color_red);
			break;
		case 2:
			fontSize_big.setTextColor(color_red);
			break;
		case 3:
			fontSize_super_big.setTextColor(color_red);
			break;
		default:
			fontSize_mid.setTextColor(color_red);
			break;
		}
	}
	
	/**
	 * <pre>初始化阅读模式</pre>
	 *
	 */
	private void initReadMode(){
		int dn=SetsKeeper.readReadType(context);
		if (dn==0) {
			int color=Color.parseColor("#363b41");
			tb_readMode.setChecked(true);
			mWebView.setBackgroundColor(Color.parseColor("#24272c"));
			scrollView.setBackgroundColor(Color.parseColor("#24272c"));
			mScrollView.setBackgroundColor(Color.parseColor("#24272c"));
			rela_news_detail_main.setBackgroundColor(Color.parseColor("#24272c"));
			tab_bottom.setBackgroundColor(color);
			rela_menu.setBackgroundColor(Color.BLACK);
			linear_more.setBackgroundColor(color);
			rela_news_detail_font_size.setBackgroundColor(color);
			txt_news_detail_font_size.setBackgroundColor(color);
			txt_news_detail_font_size.setTextColor(Color.WHITE);
			linear_news_detail_font_size.setBackgroundColor(Color.BLACK);
			fontSize_big.setBackgroundColor(color);
			fontSize_mid.setBackgroundColor(color);
			fontSize_small.setBackgroundColor(color);
			fontSize_super_big.setBackgroundColor(color);
			fontSize_big.setTextColor(Color.WHITE);
			fontSize_mid.setTextColor(Color.WHITE);
			fontSize_small.setTextColor(Color.WHITE);
			fontSize_super_big.setTextColor(Color.WHITE);
			rela_news_detail_read_mode.setBackgroundColor(color);
			txt_news_detail_read_mode.setBackgroundColor(color);
			txt_news_detail_read_mode.setTextColor(Color.WHITE);
			iv_news_detail_back.setBackgroundResource(R.drawable.night_selector_news_detail_goback_bg);
		}else {
			int color=Color.parseColor("#ecedee");
			tb_readMode.setChecked(false);
			mWebView.setBackgroundColor(Color.parseColor("#f4f4f4"));
			scrollView.setBackgroundColor(Color.parseColor("#f4f4f4"));
			mScrollView.setBackgroundColor(Color.parseColor("#f4f4f4"));
			rela_news_detail_main.setBackgroundColor(Color.parseColor("#f0f0f0"));
			tab_bottom.setBackgroundColor(color);
			rela_menu.setBackgroundColor(color);
			linear_more.setBackgroundColor(color);
			rela_news_detail_font_size.setBackgroundColor(color);
			txt_news_detail_font_size.setBackgroundColor(color);
			txt_news_detail_font_size.setTextColor(Color.BLACK);
			linear_news_detail_font_size.setBackgroundColor(Color.parseColor("#DCDCDC"));
			fontSize_big.setBackgroundColor(color);
			fontSize_mid.setBackgroundColor(color);
			fontSize_small.setBackgroundColor(color);
			fontSize_super_big.setBackgroundColor(color);
			fontSize_big.setTextColor(Color.BLACK);
			fontSize_mid.setTextColor(Color.BLACK);
			fontSize_small.setTextColor(Color.BLACK);
			fontSize_super_big.setTextColor(Color.BLACK);
			rela_news_detail_read_mode.setBackgroundColor(color);
			txt_news_detail_read_mode.setBackgroundColor(color);
			txt_news_detail_read_mode.setTextColor(Color.BLACK);
			iv_news_detail_back.setBackgroundResource(R.drawable.selector_news_detail_goback_bg);
		}
		textSize=SetsKeeper.readFontSize(context);
		int color_red=Color.parseColor("#ff0000");
		switch (textSize) {
		case 0:
			fontSize_small.setTextColor(color_red);
			break;

		case 1:
			fontSize_mid.setTextColor(color_red);
			break;
		case 2:
			fontSize_big.setTextColor(color_red);
			break;
		case 3:
			fontSize_super_big.setTextColor(color_red);
			break;
		default:
			fontSize_mid.setTextColor(color_red);
			break;
		}
	}
	
	/**
	 * <pre>初始化webview</pre>
	 *
	 */
	private void setupWebView(){
		settings=mWebView.getSettings();
		settings.setAllowFileAccess(true);//准许访问文件
		settings.setBuiltInZoomControls(false);//取消缩放控件
		settings.setTextSize(TextSize.NORMAL);
		settings.setSupportZoom(false);//取消缩放
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);//显示模式，适应频幕
		settings.setDefaultTextEncodingName("utf-8");//设置文本编码
		settings.setJavaScriptEnabled(true);
		mWebView.setScrollBarStyle(0);
		mWebView.setBackgroundColor(0);
		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setHorizontalScrollBarEnabled(false);
		mWebView.addJavascriptInterface(new NewsBean(this), "newsbean");
		mWebView.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				mWebView.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				scrollToFirst();
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
			
		});
		mWebView.setWebChromeClient(new WebChromeClient(){

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
			}
			
		});
		mWebView.loadUrl("file:///android_asset/newspage.html");
	}
	
	private OnClickListener listener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int color_red=Color.RED;
			int color_black=Color.BLACK;
			int color_white=Color.WHITE;
			int id=v.getId();
			switch (id) {
			case R.id.iv_news_detail_back:
				scrollView.onRefreshComplete();
				finish();
				break;

			case R.id.relative_news_detail_comment:
				if (mNews==null) {
					return;
				}
				Intent intentr =new Intent(context, CommentFromRightActivity.class);
				intentr.putExtra("newsid", id_current);
				startActivity(intentr);
				overridePendingTransition(R.anim.slide_in_from_bottom,R.anim.slide_out_to_bottom);
				break;
			case R.id.iv_news_detail_comment:
				if (mNews==null) {
					return;
				}
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					return;
				}
				Intent intentc =new Intent(context, CommentFromRightActivity.class);
				intentc.putExtra("newsid", id_current);
				startActivity(intentc);
				overridePendingTransition(R.anim.slide_in_from_bottom,R.anim.slide_out_to_bottom);
				break;
			case R.id.iv_news_detail_favorite:
				MobclickAgent.onEvent(context, "news_detail_favorite");
				if (mNews==null) {
					return;
				}
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					return;
				}
				if (StringUtils.isEmpty(appInstance.pass_token)) {
					CustomerToast.showMessage(context, "请先登录！", true, true);
					Intent intent = new Intent(context,LoginActivity.class);   
					intent.putExtra("from", 3);
					startActivity(intent);
					return;
				}
				iv_news_detail_favorite.setEnabled(false);
				isFavorited=databaseHelper.isNewsExist(id_current);
				if (!isFavorited) {
					MobclickAgent.onEvent(context, "news_detail_favorite");
					executorService.submit(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							msgController.createCollection(id_current,DateTimeUtils.getLongDateTime(true));
							sendMessage(201, 0);
						}
					});
				}else{
					MobclickAgent.onEvent(context, "news_detail_unfavorite");
					executorService.submit(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							msgController.delectCollection(id_current+"");
							sendMessage(501, 0);
						}
					});
				}
				
				break;
			case R.id.iv_news_detail_share:
				if (mNews==null) {
					return;
				}
				shareDialog.initView();
				shareDialog.setOnclickListener(shareListener);
				shareDialog.show();
				break;
			case R.id.iv_news_detail_more:
				if (mNews==null) {
					return;
				}
				if (ismore) {
					scrollView.setFocusableInTouchMode(true);
					scrollView.setFocusable(true);
					linear_more.getParent().requestDisallowInterceptTouchEvent(false);
					linear_more.setVisibility(View.GONE);
					ismore=false;
				}else {
					scrollView.setFocusableInTouchMode(false);
					scrollView.setFocusable(false);
					linear_more.getParent().requestDisallowInterceptTouchEvent(true);
					linear_more.setVisibility(View.VISIBLE);
					ismore=true;
				}
				break;
			case R.id.txt_font_size_big:
				MobclickAgent.onEvent(context, "news_detail_font_big");
				if (mNews==null) {
					return;
				}
				textSize=2;
				fontSize_big.setTextColor(color_red);
				if(FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT){
					fontSize_small.setTextColor(color_white);
					fontSize_mid.setTextColor(color_white);
					fontSize_super_big.setTextColor(color_white);
				}else{
					fontSize_small.setTextColor(color_black);
					fontSize_mid.setTextColor(color_black);
					fontSize_super_big.setTextColor(color_black);
				}
				mWebView.reload();
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						mWebView.loadUrl("javascript:resets()");
					}
				}, 800);
				break;
			case R.id.txt_font_size_mid:
				MobclickAgent.onEvent(context, "news_detail_font_mid");
				if (mNews==null) {
					return;
				}
				textSize=1;
				fontSize_mid.setTextColor(color_red);
				if(FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT){
					fontSize_big.setTextColor(color_white);
					fontSize_small.setTextColor(color_white);
					fontSize_super_big.setTextColor(color_white);
				}else{
					fontSize_big.setTextColor(color_black);
					fontSize_small.setTextColor(color_black);
					fontSize_super_big.setTextColor(color_black);
				}
				mWebView.reload();
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						mWebView.loadUrl("javascript:resets()");
					}
				}, 800);
				break;
			case R.id.txt_font_size_samll:
				MobclickAgent.onEvent(context, "news_detail_font_small");
				if (mNews==null) {
					return;
				}
				textSize=0;
				fontSize_small.setTextColor(color_red);
				if(FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT){
					fontSize_big.setTextColor(color_white);
					fontSize_mid.setTextColor(color_white);
					fontSize_super_big.setTextColor(color_white);
				}else{
					fontSize_big.setTextColor(color_black);
					fontSize_mid.setTextColor(color_black);
					fontSize_super_big.setTextColor(color_black);
				}
				mWebView.reload();
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						mWebView.loadUrl("javascript:resets()");
					}
				}, 800);
				break;
			case R.id.txt_font_size_super_big:
				MobclickAgent.onEvent(context, "news_detail_font_super_big");
				if (mNews==null) {
					return;
				}
				textSize=3;
				fontSize_super_big.setTextColor(color_red);
				if(FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT){
					fontSize_big.setTextColor(color_white);
					fontSize_small.setTextColor(color_white);
					fontSize_mid.setTextColor(color_white);
				}else{
					fontSize_big.setTextColor(color_black);
					fontSize_small.setTextColor(color_black);
					fontSize_mid.setTextColor(color_black);
				}
				mWebView.reload();
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						mWebView.loadUrl("javascript:resets()");
					}
				}, 800);
				break;
			}
			SetsKeeper.keepFontSize(context, textSize);
		}
	};
	
	/**
	 * the thread for loading images
	 * @author 东方网升Eilin Yang
	 */
	class LoadImagesThread implements Runnable{

		@Override
		public void run() {
			loadImages();
		}
		
	}
	
	/**
	 * loading image
	 */
	private void loadImages(){
		if (medias!=null) {
			for (MediaInfo md : medias) {
				if (md!=null) {
					File file=new File(md.getPath());
					final MediaInfo med=md;
					if (!file.exists()) {
						executorService.submit(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								BitmapTools.loadImage(FrameConfigure.NORMAL_IMG_DRC, med.getSrc(),new BitmapTools.LoadingListener() {
									
									@Override
									public void finish(String path) {
										// TODO Auto-generated method stub
										sendMessage(101, med);
									
									}
									
									@Override
									public void error(String errorinfo) {
										// TODO Auto-generated method stub
										
									}
								});
							}
						});
					}else {
						sendMessage(101, med);
					}
				}
			}			
		}
	}
	
	/**
	 * loading image
	 */
	private void resetImages(){
		if (medias!=null) {
			for (MediaInfo md : medias) {
				if (md!=null) {
					File file=new File(md.getPath());
					if (file.exists()) {
						String id=md.getId();
						String path="file://"+md.getPath();
						mWebView.loadUrl("javascript:showImage(\""+id+"\",\""+path+"\")");
					}
				}
			}			
		}
	}
	
	private class LoadThread implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			loadDataFromNet();
		}
		
	}
	/**
	 * <pre>从网络获取数据</pre>
	 *
	 */
	private void loadDataFromNet(){
		isFavorited=false;
		title_up="上一篇:";
		title_down="下一篇:";
		switch (from) {
		case 0://来自通知
			int column=getIntent().getIntExtra("type", 1);
			String name0=MD5Utils.MD5(id_current+"")+".0";
			String path0="";
			path0=controller.getNewsPath(column, path0);
			File file0=new File(path0+name0);
			if (file0.exists()) {
				mNews=controller.getNewsFromFile(id_current, column);
			}else {
				mNews=controller.getNewsFromNet(id_current, column);
			}
			title_up="";
			title_down="";
			id_up=id_current;
			id_down=id_current;
			break;
		case 1://来自新闻
			int news_id1=NewsFragment.adapter.getItemObjectId(current_position);
			id_current=news_id1;
			String name1=MD5Utils.MD5(news_id1+"")+".0";
			File file1=new File(FrameConfigure.NORMAL_NEWS_OBJECT_NEWS_DRC+name1);
			if (file1.exists()) {
				mNews=controller.getNewsFromFile(news_id1, NewsConfigure.COLUMN_NEWS);
			}else {
				mNews=controller.getNewsFromNet(news_id1, NewsConfigure.COLUMN_NEWS);
			}
			title_up=title_up+NewsFragment.adapter.getItem(current_position-1).getTitle();
			title_down=title_down+NewsFragment.adapter.getItem(current_position+1).getTitle();
			id_up=NewsFragment.adapter.getItemObjectId(current_position-1);
			id_down=NewsFragment.adapter.getItemObjectId(current_position+1);
			if (id_down==id_current) {
				title_down="加载下一页";
			}
			if (id_up==id_current) {
				title_up="上一页没有数据了";
			}
			break;
		case 3://报告
			int news_id3=ReportFragment.adapter.getItemObjectId(current_position);
			id_current=news_id3;
			String name3=MD5Utils.MD5(news_id3+"")+".0";
			File file3=new File(FrameConfigure.NORMAL_REPORT_OBJECT_NEWS_DRC+name3);
			if (file3.exists()) {
				mNews=controller.getNewsFromFile(news_id3, NewsConfigure.COLUMN_REPORT);
			}else {
				mNews=controller.getNewsFromNet(news_id3, NewsConfigure.COLUMN_REPORT);
			}
			title_up=title_up+ReportFragment.adapter.getItem(current_position-1).getTitle();
			title_down=title_down+ReportFragment.adapter.getItem(current_position+1).getTitle();
			id_up=ReportFragment.adapter.getItemObjectId(current_position-1);
			id_down=ReportFragment.adapter.getItemObjectId(current_position+1);
			if (id_down==id_current) {
				title_down="加载下一页";
			}
			if (id_up==id_current) {
				title_up="上一页没有数据了";
			}
			break;
		case 4://人物
			int news_id4=FigureFragment.adapter.getItemObjectId(current_position);
			id_current=news_id4;
			String name4=MD5Utils.MD5(news_id4+"")+".0";
			File file4=new File(FrameConfigure.NORMAL_FIGURE_OBJECT_NEWS_DRC+name4);
			if (file4.exists()) {
				mNews=controller.getNewsFromFile(news_id4, NewsConfigure.COLUMN_FIGURE);
			}else {
				mNews=controller.getNewsFromNet(news_id4, NewsConfigure.COLUMN_FIGURE);
			}
			title_up=title_up+FigureFragment.adapter.getItem(current_position-1).getTitle();
			title_down=title_down+FigureFragment.adapter.getItem(current_position+1).getTitle();
			id_up=FigureFragment.adapter.getItemObjectId(current_position-1);
			id_down=FigureFragment.adapter.getItemObjectId(current_position+1);
			if (id_down==id_current) {
				title_down="加载下一页";
			}
			if (id_up==id_current) {
				title_up="上一页没有数据了";
			}
			break;
		case 5://新技术
			int news_id5=TechniqueFragment.adapter.getItemObjectId(current_position);
			id_current=news_id5;
			String name5=MD5Utils.MD5(news_id5+"")+".0";
			File file5=new File(FrameConfigure.NORMAL_TECHNIQUE_OBJECT_NEWS_DRC+name5);
			if (file5.exists()) {
				mNews=controller.getNewsFromFile(news_id5, NewsConfigure.COLUMN_TECHNIQUE);
			}else {
				mNews=controller.getNewsFromNet(news_id5, NewsConfigure.COLUMN_TECHNIQUE);
			}
			title_up=title_up+TechniqueFragment.adapter.getItem(current_position-1).getTitle();
			title_down=title_down+TechniqueFragment.adapter.getItem(current_position+1).getTitle();
			id_up=TechniqueFragment.adapter.getItemObjectId(current_position-1);
			id_down=TechniqueFragment.adapter.getItemObjectId(current_position+1);
			if (id_down==id_current) {
				title_down="加载下一页";
			}
			if (id_up==id_current) {
				title_up="上一页没有数据了";
			}
			break;
		
		}
		
		if (null!=mNews) {
			if (load_type==BaseFragment.FIRST_LOAD) {
				sendMessage(BaseFragment.FIRST_LOAD, 0);
			}
			if (load_type==BaseFragment.REFRESH_PULL) {
				sendMessage(BaseFragment.REFRESH_PULL, 0);
			}
			if (load_type==BaseFragment.PAGE_NEXT_LOAD) {
				sendMessage(BaseFragment.PAGE_NEXT_LOAD, 0);
			}
			if (from==0) {
				sendMessage(401, 0);
			}
			isFavorited=mNews.isCollected();
			medias=mNews.getMedias();
		}else {
			if (load_type==BaseFragment.FIRST_LOAD) {
				sendMessage(BaseFragment.FIRST_REQUEST_ERROR, 0);
			}
			if (load_type==BaseFragment.REFRESH_PULL) {
				sendMessage(BaseFragment.REFRESH_REQUEST_ERROR, 0);
			}
			if (load_type==BaseFragment.PAGE_NEXT_LOAD) {
				sendMessage(BaseFragment.PAGE_NEXT_REQUEST_ERROR, 0);
			}
			if (from==0) {
				sendMessage(402, 0);
			}
			medias=null;
		}
	}
	
	/**
	 * <h2>新闻交换类 <h2>
	 * <pre> </pre>
	 * @author 东方网升Eilin.Yang
	 * @since 2013-10-31
	 * @version v1.0
	 * @modify ""
	 */
	private class NewsBean {
		private Context context;
		public NewsBean(Context context){
			this.context=context;
		}
		
		/**
		 * <pre>获取新闻标题</pre>
		 * @return
		 */
		public String getTitle() {
			if (null==mNews) {
				return "";
			}
			return mNews.getTitle();
		}
		/**
		 * <pre>获取新闻id</pre>
		 * @return
		 */
		public String getId() {
			if (null==mNews) {
				return "";
			}
			return mNews.getIdStr();
		}
		/**
		 * <pre>获取新闻来源</pre>
		 * @return
		 */
		public String getSource() {
			if (null==mNews) {
				return "";
			}
			return mNews.getSource();
		}
		/**
		 * <pre>获取新闻发布时间</pre>
		 * @return
		 */
		public String getPtime() {
			if (null==mNews) {
				return "";
			}
			String time=mNews.getPotime();
			time=DateTimeUtils.getWeiBoDateTime(time);
			return time;
		}
		
		/**
		 * <pre>获取新闻正文</pre>
		 * @return
		 */
		public String getContent() {
			if (null==mNews) {
				return "";
			}
			return mNews.getContent();
		}
		
		/**
		 * <pre>获取字体大小</pre>
		 * @return
		 */
		public float getTextSize() {
			return textSize;
		}
		
		/**
		 * <pre>获取夜间模式</pre>
		 * @return
		 */
		public int getReadMode() {
			if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
				return 0;
			}else {
				return 1;
			}
		}
		
		/**
		 * <pre>图片显示</pre>
		 * @param order 图片位置
		 * @param src 图片地址
		 * @param id 图片id
		 */
		public void clickImage(int order, String id, String src) {
			Log.i("LOADIMG", "order="+order+"||id="+id+"||src="+src);
			if (!StringUtils.isEmpty(src)) {
				MediaInfo m=medias.get(order);
				if (src.contains("android_asset/djx.png")||src.contains("android_asset/n_djx.png")) {
					mWebView.loadUrl("javascript:setImageIsLoading(\""+id+"\")");
					String url=m.getSrc();
					String path=m.getPath();
					imageLoader.loadImage("clickImage",id, url, FrameConfigure.NORMAL_IMG_DRC, path, new Callback() {
						
						@Override
						public void imageLoaded(String id, String path, boolean flag) {
							// TODO Auto-generated method stub
							if (flag)
							mWebView.loadUrl("javascript:showImage(\""+id+"\",\"file://"+path+"\")");
						}
					});
				}
				if (src.contains("android_asset/pic_load_def.png")||src.contains("android_asset/pic_load_def_n.png")||src.contains("android_asset/loading_new5.gif")) {
					return;
				}
				if (src.contains("file:///mnt/sdcard/meadinreader/")) {
					showImage(order);
				}
			}
		}
		
		/**
		 * <pre>显示图片大图</pre>
		 * @param order 图片位置,顺序
		 */
		public void showImage(int order) {
			String path=medias.get(order).getPath();
			ShowImageActivity.medias=medias;
			ShowImageActivity.firstPosition=order;
			Intent intent=new Intent();
			intent.setClass(context, ShowImageActivity.class);
			intent.putExtra(ShowImageActivity.CHANNEL, path);
			((Activity)context).startActivityForResult(intent, 111);
		}
		
		/**
		 * <pre>查看原文</pre>
		 *
		 */
		public void linkToResource() {
			if (mNews!=null) {
				Intent intent=new Intent();
				intent.setClass(context, NewsResourceWebActivity.class);
				intent.putExtra("url", mNews.getUrl());
				startActivity(intent);
			}
		}
		
		/**
		 * <pre>从新加载</pre>
		 *
		 */
		public void onReloadWeb() {
//			load_type=BaseFragment.FIRST_LOAD;
			Log.i("WHY4", "onReloadWeb()");
//			mWebView.removeAllViews();
			if (NetWorkUtils.checkNetWork(context)) {
				mWebView.loadUrl("file:///android_asset/newspage.html");
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						executorService.submit(new LoadThread());
					}
				}, 500);
			}else {
				handler.sendEmptyMessage(1001);
				return;
			}
		}
		
		/**
		 * <pre>从新加载</pre>
		 *
		 */
		public void checkLog(int switc,String tag) {
			Log.i("WHY3", "switc="+switc+"         tag="+tag);
		}
		
		/**
		 * <pre></pre>
		 *
		 */
		public void reInitView(int h,int pecent) {
			Log.i("WHY5", "height="+h);
			
		}
		
		/**
		 * <pre></pre>
		 *
		 */
		public void initImageLoad() {
			initImgLoad();
		}
		
		/**
		 * <pre></pre>
		 *
		 */
		public void resetImageLoad() {
			if (NetWorkUtils.checkNetWork(context)) {
				initImgLoad();
			}else {
				resetImages();
			}
		}
	}
	
	private Handler imgHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}
		
	};
	
	private void sendMessage(int what,int value){
		Message msg=handler.obtainMessage();
		msg.arg1=value;
		msg.what=what;
		handler.sendMessage(msg);
	}
	
	private void sendMessage(int what,MediaInfo md){
		Message msg=handler.obtainMessage();
		msg.obj=md;
		msg.what=what;
		handler.sendMessage(msg);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		setParentPosition();
		/**
		 * 此处调用基本统计代码
		 */
		MobclickAgent.onPause(this);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (imageLoader!=null) {
			imageLoader.quit();
		}
		if (databaseHelper!=null) {
			databaseHelper.close();
		}
		setParentPosition();
		unregisterReceiver(mReceiver);
	}
	
	/**
	 * <pre>改变上一级的位置</pre>
	 *
	 */
	private void setParentPosition(){
		switch (from) {
		case 1:
			NewsFragment.current_position=current_position;
			break;
		case 3:
			ReportFragment.current_position=current_position;
			break;
		case 4:
			FigureFragment.current_position=current_position;
			break;
		case 5:
			TechniqueFragment.current_position=current_position;
			break;
		
		}
	}
	
	/**
	 * 分享监听
	 */
	private View.OnClickListener  shareListener=new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			if (mNews==null) {
				return;
			}
			String title=mNews.getTitle();
			String tag_url=mNews.getUrl();
			String content_sina="#迈点阅读#《"+title+"》分享自迈点阅读客户端，原文:"+tag_url;
			String re=StringUtils.removeTagFromText(mNews.getContent());
			String content_wei_p=re.substring(0, (re.length()>100?100:re.length())).trim();
			String content_wei_c=title;
			String content_e="《"+title+"》分享自迈点阅读客户端，原文："+tag_url;
			String content_s="我在迈点阅读客户端发现文章《"+title+"》与你分享，访问："+tag_url;
			String image_url=mNews.getThumb();
			File dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
			File file = new File(dir,MD5Utils.MD5(image_url));
			if (file.exists()) {
				image_url=file.getAbsolutePath();
			}else {
				image_url=null;
			}
			switch (v.getId()) {
			case R.id.txt_share_sina:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					break;
				}
				ThirdHelp.share_Sina(context, content_sina, image_url);
				break;

			case R.id.txt_share_tencent:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					break;
				}
				ThirdHelp.share_Tencent(context, content_sina, image_url);
				break;

			case R.id.txt_share_qqzone:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					break;
				}
				ThirdHelp.share_Qzone(context, content_sina, image_url);
				break;

			case R.id.txt_share_weixin:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					break;
				}
				ThirdHelp.share_Weixin(context, tag_url,title, content_wei_p, image_url,false);
				break;

			case R.id.txt_share_circle:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					break;
				}
				ThirdHelp.share_Weixin(context, tag_url,title, "", image_url,true);
				break;

			case R.id.txt_share_emile:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					break;
				}
				ThirdHelp.share_Emile(context,content_e,image_url);
				break;

			case R.id.txt_share_sms:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					break;
				}
				ThirdHelp.share_SMS(context,content_s);
				break;
			case R.id.txt_share__cancel:
				shareDialog.dismiss();
				break;
			default:
				break;
			}
			shareDialog.dismiss();
		}
	};
	
	
	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    ThirdHelp.onBack(requestCode, resultCode, data);
	    if(resultCode==111){
	    	int state=data.getIntExtra("state", -111);
	    	if (state==1) {
//	    		mWebView.reload();
//				new Handler().postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						mWebView.loadUrl("javascript:resets()");
//					}
//				}, 800);
	    		if (medias==null) {
					return;
				}
	    		for (MediaInfo mdi : medias) {
	    			if (mdi!=null) {
	    				mWebView.loadUrl("javascript:showImage(\""+mdi.getId()+"\",\"file://"+mdi.getPath()+"\")");
					}
	    			
				}
			}
	    }
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
							pop.dismiss();
						} else {
							int[] location = new int[2];
							rela_menu.getLocationOnScreen(location);
							pop.showAtLocation(rela_menu, Gravity.NO_GRAVITY, location[0], location[1]-pop.getHeight());;
						}
					}
				}, 160);
			}
		}
	};
}
