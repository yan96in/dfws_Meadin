package com.dfws.shhreader.slidingmenu.fragment;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.dfws.shhreader.R;
import com.dfws.shhreader.activity.NewsDetailActivity;
import com.dfws.shhreader.adapter.FigurePagerAdapter;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.configures.NewsConfigure;
import com.dfws.shhreader.controllers.NewsController;
import com.dfws.shhreader.database.sharedpreferences.SetsKeeper;
import com.dfws.shhreader.entity.News;
import com.dfws.shhreader.ui.dialog.CustomerToast;
import com.dfws.shhreader.utils.DateTimeUtils;
import com.dfws.shhreader.utils.NetWorkUtils;
import com.dfws.shhreader.utils.ScreenUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.umeng.analytics.MobclickAgent;
/**
 * 
 * @file： FigureFragment.java
 * @Page： com.dfws.shhreader.slidingmenu.fragment
 * @description：   人物
 * @since： 2013-10-31
 * @author： Administrator
 */
public class FigureFragment extends BaseFragment{
	private static final String TAG="FigureFragment";
	/**
	 * 加载模式
	 */
	private int load_type=PAGE_NEXT_LOAD;

	/**
	 * 新闻集合
	 */
	private List<News> newsList;

	/**
	 * 新闻缓存集合
	 */
	private static List<News> newsList_temp;
	/**
	 * 数据控制器
	 */
	private static NewsController controller;
	/**线程池*/
	private ExecutorService executorService;
	/**线程池默认大小*/
	private int THREAD_POOL_SIZE=2;
	/**
	 * 页码
	 */
	private static int pageIndex=0;
	/**
	 * 每页大小
	 */
	private int pageSize=10;
	/**
	 * 最后的可视项索引   
	 */
	private int visibleLastIndex = 0;
	/**
	 * 当前窗口可见项总数
	 */
	private int visibleItemCount;
	/**
	 * 当前位置
	 */
	public static int current_position=0;
	/**
	 * 已加载的总页码
	 */
	private int count_page=1;
	/**
	 * 是否加载幻灯片
	 */
	private int need_slide=0;
	/**
	 * 是否加载完整信息
	 */
	private int show_full=0;
	
	private Context context;
	private ListView listView;
	private PullToRefreshListView pullParent;
	public static FigurePagerAdapter adapter;

	private LinearLayout page_news_loading;
	/**
	 * 从新加载模块
	 */
	private LinearLayout linear_reload;
	private ImageButton ibtn_reloading;
	private RelativeLayout rela_loading;
	/**
	 * 是否已经启动
	 */
	public boolean isResume=false;
	/**
	 * handler处理线程事件
	 */
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			int what=msg.what;
			
			switch (what) {
			case FIRST_LOAD:
				page_news_loading.setVisibility(View.GONE);
				rela_loading.setVisibility(View.GONE);
				newsList=newsList_temp;
				adapter=new FigurePagerAdapter(context, newsList,listView);
				listView.setAdapter(adapter);
				NewsConfigure.list_news_max_id=adapter.getLastItemObjectId();
				break;

			case PAGE_NEXT_LOAD:
				pullParent.onRefreshComplete();
				adapter.addItems(newsList_temp);
				break;
			case REFRESH_PULL:
				pullParent.onRefreshComplete();
				adapter.clearItems();
				adapter.addItems(newsList_temp);
				SetsKeeper.keepRefreshTime(context, DateTimeUtils.getLongDateTime(true), 4);
				break;
			case FIRST_REQUEST_ERROR:
				pullParent.onRefreshComplete();
				page_news_loading.setVisibility(View.GONE);
				linear_reload.setVisibility(View.VISIBLE);
				break;
			case PAGE_NEXT_REQUEST_ERROR:
				pullParent.onRefreshComplete();
				break;
			case REFRESH_REQUEST_ERROR:
				pullParent.onRefreshComplete();
				break;
			default:
				break;
			}
		}
		
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context=getActivity();
		executorService=Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		controller=new NewsController(context);
		current_position=0;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, TAG+">>onCreateView");
		View view = null;
		if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
			view = inflater.inflate(R.layout.night_page_news, null);
		}else {
			view = inflater.inflate(R.layout.page_news, null);
		}
		pullParent=(PullToRefreshListView)view.findViewById(R.id.page_news_listveiw);
		pullParent.setMode(Mode.BOTH);
		pullParent.setRefreshLabelTextSize(14);
		pullParent.setRefreshLabelColor(Color.GRAY);
		pullParent.setLoadingDrawable(context.getResources().getDrawable(R.drawable.xlistview_arrow),Mode.PULL_FROM_START);
		pullParent.setLoadingPregressDrawable(context.getResources().getDrawable(R.drawable.xlistview_loadings), Mode.PULL_FROM_START);
		pullParent.setLoadingPregressWidthAndHeight(ScreenUtil.dip2px(context, 32), ScreenUtil.dip2px(context, 32), Mode.PULL_FROM_START);
		listView=pullParent.getRefreshableView();	
		page_news_loading=(LinearLayout)view.findViewById(R.id.page_news_loading);
		linear_reload=(LinearLayout)view.findViewById(R.id.linear_reload);
		rela_loading=(RelativeLayout)view.findViewById(R.id.rela_loading);
		ibtn_reloading=(ImageButton)view.findViewById(R.id.ibtn_reloading);
		setupListener();
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG, TAG+">>onActivityCreated");
		firstLoadingDatas();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i(TAG, TAG+">>onResume");
		if (listView!=null) {
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					listView.setSelection(current_position);
				}
			},500);
		}
		isResume=true;
	}
	
	private void setupListener(){
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MobclickAgent.onEvent(context, "enter_cloumn_figure");
				current_position=(int)id;
				Intent intent=new Intent(context, NewsDetailActivity.class);
				intent.putExtra("from", 4);
				startActivity(intent);
				((Activity)context).overridePendingTransition(R.anim.sliding_in_right,R.anim.sliding_out_left);
			}
		});
		pullParent.setOnRefreshListener(new com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				count_page=pageIndex;
				load_type=REFRESH_PULL;
				executorService.submit(new LoadDataThread());
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "没有网络！", false, true);
					pullParent.onRefreshComplete();
					return;
				}else {
					load_type=PAGE_NEXT_LOAD;
					executorService.submit(new LoadDataThread());
				}
			}
			
		});
		pullParent.setOnPullEventListener(new OnPullEventListener<ListView>() {

			@Override
			public void onPullEvent(PullToRefreshBase<ListView> refreshView,
					State state, Mode direction) {
				// TODO Auto-generated method stub
				pullParent.setLastUpdatedLabel("");
				if (direction==Mode.PULL_FROM_START) {
					String time=SetsKeeper.readRefreshTime(context, 4);
					pullParent.setLastUpdatedLabel("上次刷新:"+DateTimeUtils.getPutDate(time));
				}else if(direction==Mode.PULL_FROM_END) {
					
					if (!NewsConfigure.list_figure_has_more) {
						pullParent.onRefreshComplete();
						pullParent.setPullLabel("没有其他数据了!",Mode.PULL_FROM_END);
						pullParent.setReleaseLabel("没有其他数据了!",Mode.PULL_FROM_END);
						pullParent.setRefreshingLabel("没有其他数据了!",Mode.PULL_FROM_END);
						pullParent.setLastUpdatedLabel("");
						return;
					}else {
						pullParent.setLastUpdatedLabel("");
						pullParent.setRefreshingLabel("正在载入",Mode.PULL_FROM_END);
						pullParent.setPullLabel("正在加载更多",Mode.PULL_FROM_END);
					}
				}
			}
		});
		ibtn_reloading.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络！", false, true);
					return;
				}
				firstLoadingDatas();
			}
		});
	}
	
	/**
	 * <pre>首次加载数据</pre>
	 *
	 */
	public void firstLoadingDatas(){
		rela_loading.setVisibility(View.VISIBLE);
		page_news_loading.setVisibility(View.VISIBLE);
		linear_reload.setVisibility(View.GONE);
		load_type=FIRST_LOAD;
		executorService.submit(new LoadDataThread());
	}

	/**
	 * 
	 *<pre>加载数据</pre>
	 */
	private void initDatas(){
		if (load_type==FIRST_LOAD) {
			isFirstLoad();
		}
		if (load_type==PAGE_NEXT_LOAD) {
			isPageNext();
		}
		if (load_type==REFRESH_PULL) {
			isPullToRefresh();
		}if (NetWorkUtils.checkNetWork(context)) {
			newsList_temp=controller.getNewsListFromNet(pageIndex, pageSize, NewsConfigure.list_news_min_id, NewsConfigure.list_news_max_id, NewsConfigure.COLUMN_FIGURE, 0, show_full);
		}else
			newsList_temp=controller.getNewsListFromFile(NewsConfigure.COLUMN_FIGURE);
		if (newsList_temp!=null&&newsList_temp.size()>0) {
			if (load_type==FIRST_LOAD) {
				NewsConfigure.list_news_count=newsList_temp.size();				
				sendMessage(FIRST_LOAD);
			}
			if(load_type==PAGE_NEXT_LOAD) {
				sendMessage(PAGE_NEXT_LOAD);
			}
			if (load_type==REFRESH_PULL) {
				sendMessage(REFRESH_PULL);
			}
		}else {
			if (load_type==FIRST_LOAD) {
				newsList_temp=controller.getNewsListFromFile(NewsConfigure.COLUMN_FIGURE);
				if (newsList_temp!=null&&newsList_temp.size()>0) {
					NewsConfigure.list_figure_count=newsList_temp.size();
					sendMessage(FIRST_LOAD);
				}else
				sendMessage(FIRST_REQUEST_ERROR);
			}
			if(load_type==PAGE_NEXT_LOAD) {
				sendMessage(PAGE_NEXT_REQUEST_ERROR);
			}
			if (load_type==REFRESH_PULL) {
				sendMessage(REFRESH_REQUEST_ERROR);
			}
		}
		
	}
	
	/**
	 * 
	 * <h2>数据加载 <h2>
	 * <pre> </pre>
	 * @author 东方网升Eilin.Yang
	 * @since 2013-10-25
	 * @version v1.0
	 * @modify ""
	 */
	private class LoadDataThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			initDatas();
		}
		
	}
	
	/**
	 * 
	 *<pre>发送消息</pre>
	 * @param what 消息标识
	 */
	private void sendMessage(int what){
		Message msg=handler.obtainMessage();
		msg.what=what;
		handler.sendMessage(msg);
	}
	
	/**
	 * 
	 *<pre>第一次加载数据</pre>
	 */
	private void isFirstLoad(){
		pageIndex=1;
		pageSize=10;
		NewsConfigure.list_news_max_id=0;
	}
	
	/**
	 * 
	 *<pre>翻页加载数据</pre>
	 */
	private void isPageNext(){
		pageIndex=pageIndex+1;
		pageSize=10;
		NewsConfigure.list_news_max_id=adapter.getLastItemObjectId();
	}
	
	/**
	 * 
	 *<pre>下拉刷新加载数据</pre>
	 */
	private void isPullToRefresh(){
		if (adapter!=null) {
			pageSize=(adapter.getCount()>10?adapter.getCount():10);
		}
		pageIndex=1;
		NewsConfigure.list_news_max_id=0;
	}
	
	/**
	 * <h2>自动加载下一页 <h2>
	 * <pre> </pre>
	 * @author 东方网升Eilin.Yang
	 * @since 2013-11-1
	 * @version 
	 * @modify ""
	 */
	private static class LoadNextPageThread implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			++pageIndex;
			newsList_temp=controller.getNewsListFromNet(pageIndex, 10, NewsConfigure.list_news_min_id, NewsConfigure.list_news_max_id, NewsConfigure.COLUMN_FIGURE, 0, 0);
			if (newsList_temp!=null) {
				nextPageHandler.sendEmptyMessage(LOADING_NEXT_PAGE);
			}else {
				nextPageHandler.sendEmptyMessage(LOADING_NEXT_PAGE_ERROR);
			}
		}
		
	}
	
	private static Handler nextPageHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOADING_NEXT_PAGE:
				adapter.addItems(newsList_temp);
				figureDataListener.loadFinish();
				break;
			case LOADING_NEXT_PAGE_ERROR:
				--pageIndex;
				figureDataListener.loadEorror();
				break;
			}
		}
		
	};
	
	public static void loadNextPage(FigureLoadNextListener listener) {
		figureDataListener=listener;
		new Thread(new LoadNextPageThread()).start();
	}
	
	public interface FigureLoadNextListener {
		void loadFinish();
		void loadEorror();
	}
	public static FigureLoadNextListener figureDataListener;

	/* (non-Javadoc)    关闭加载图片的线程
	 * @see android.support.v4.app.Fragment#onDestroyView()
	 */
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.i(TAG, TAG+">>onDestroyView");
		if (null!=adapter) {
			adapter.release();
		}
		isResume=false;
	}
}

