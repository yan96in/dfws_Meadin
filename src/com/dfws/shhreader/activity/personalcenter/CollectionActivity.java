package com.dfws.shhreader.activity.personalcenter;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.dfws.shhreader.R;
import com.dfws.shhreader.activity.AppInstance;
import com.dfws.shhreader.activity.NewsDetailActivity;
import com.dfws.shhreader.activity.image.ImageActivitys;
import com.dfws.shhreader.adapter.CollectionListviewAdapter;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.controllers.CollectionController;
import com.dfws.shhreader.entity.News;
import com.umeng.analytics.MobclickAgent;

/**
 * 我的收藏
 * 
 * @file： ModelActivity.java
 * @Page： com.dfws.shhreader.activity.personalcenter
 * @description：
 * @since： 2013-10-24
 * @author： Administrator
 */
@SuppressLint("HandlerLeak")
public class CollectionActivity extends BaseActivity {
	private Context context;//上下文
	private ImageView layout_view;// 默认图片
	private TextView layout_collect;// 编辑
	private ListView listView;// 下拉刷新的listview
	private ArrayList<News> newsList;// 数据集合
	private CollectionListviewAdapter adapter;// 数据适配器
	private CollectionController meController;// 控制器
	private AppInstance appInstance;// 实例化
	private String pass_token;// 用户票据
	private ImageView conllection_imagevie;// 返回按钮
	private int pageSize = 20;//条数
	private int pageIndex = 1;//开始
	private boolean status;//返回状态
	private ExecutorService executorService;// 线程池
	private int THREAD_POOL_SIZE = 2;	// 线程池默认大小
	private int FIRST_LOAD = 0;// 首次加载
	private int load_type = FIRST_LOAD;	// 加载模式
	private LinearLayout page_news_loading;
	/**
	 * 默认编辑，否则是删除
	 */
	private boolean flag = true;
	private boolean isOneLine=false;
	private int longIndex=-1;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (FrameConfigure.reading_type == FrameConfigure.TYPE_NIGHT) {
			setContentView(R.layout.night_main_layout_collection);
		} else {
			setContentView(R.layout.main_layout_collection);
		}
		context = this.getApplicationContext();
		meController = new CollectionController(context);
		appInstance = (AppInstance) context.getApplicationContext();
		executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		pass_token = appInstance.pass_token;
		initview();
		firstLoadingDatas();
	}
	
	// 初始化控件和事件
	public void initview() {
		layout_view = (ImageView) findViewById(R.id.layout_view);
		listView = (ListView) findViewById(R.id.layout_message);
		layout_collect = (TextView) findViewById(R.id.layout_collect);
		conllection_imagevie = (ImageView) findViewById(R.id.conllection_imagevie);

		page_news_loading=(LinearLayout) findViewById(R.id.page_news_loading);


		conllection_imagevie.setOnClickListener(listener);
		layout_collect.setOnClickListener(listener);

		
		// listview Item点击事件
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (flag) {
					int index = (int) arg3;
					int ids = adapter.getItemObjectId(index);
					int type = adapter.getItemObjectType(index);
					Intent intent = null;
					if (type == 2) {
						intent = new Intent(context, ImageActivitys.class);
					} else {
						intent = new Intent(context, NewsDetailActivity.class);
						intent.putExtra("from", 0);
						intent.putExtra("type", type);
					}
					intent.putExtra("news_id", ids);
					startActivity(intent);					
				}else {
					
					if (isOneLine) {
						adapter.setLineState(longIndex);
						isOneLine=false;
						
					}else {
						adapter.state((int)arg3);
					}
				}		
			}
		});
		
		
		//listview 长按事件
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				longIndex=(int)arg3;
				isOneLine=true;
				if (flag) {
					adapter.setLineState((int)arg3);
					layout_collect.setText("删除");
					flag=false;
					return true;
				}
				return false;
			}
		});
	}

	/**
	 * 监听器
	 */
	private View.OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			setlist(v);
		}

		private void setlist(View v) {
			switch (v.getId()) {
			// 编辑/删除按钮切换
			case R.id.layout_collect:
				doDelect();
				break;
			// 返回按钮
			case R.id.conllection_imagevie:
				finish();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 删除和编辑的逻辑处理
	 */
	public void doDelect() {
		if (flag) {
			flag = false;
			adapter.toggostatus(true);
			layout_collect.setText("删除");
		} else {
			int [] indexs=adapter.getSelectIetmIndexes();
			int n=indexs.length;
			if (n==0) {
				flag = true;
				layout_collect.setText("编辑");
				adapter.toggostatus(false);
				return;
			}
			StringBuffer sBuffer=new StringBuffer(n);
			String ids="";
			for (int i = 0; i < n; i++) {
				sBuffer.append(adapter.getItem(indexs[i]).getId()+",");
			}
			if (sBuffer.length()>0) {
				ids=sBuffer.toString();
				ids=ids.substring(0, ids.lastIndexOf(","));
			}
			final String idss=ids;
			new Thread() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					status = meController.delectCollection(idss);
					handler.sendEmptyMessage(87);
				}
			}.start();
		}
	}

	/**
	 * 控制线程刷新UI
	 */
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			// 删除
			case 87:
				if (status == true) {
					layout_collect.setEnabled(true);
					layout_collect.setText("编辑");
					Toast.makeText(context, "删除成功", Toast.LENGTH_LONG).show();
					sendEmptyMessage(0);
				} else {
					Toast.makeText(context, "删除失败", Toast.LENGTH_LONG).show();
				}
				firstLoadingDatas();
				show();
				break;
			//首次加载
			case 0:
				page_news_loading.setVisibility(View.GONE);
				if (newsList==null||newsList.size()==0) {
					//如果数据为空，隐藏listview，显示数据为空的图片					
					System.out.println("newsList---为空的时候"+newsList);
					layout_collect.setEnabled(false);
					layout_collect.setVisibility(View.VISIBLE);
					layout_view.setVisibility(View.VISIBLE);
					listView.setVisibility(View.GONE);
				}else{
					//如果有数据,显示正常
					System.out.println("newsList---"+newsList);
					layout_view.setVisibility(View.GONE);
					listView.setVisibility(View.VISIBLE);
					layout_collect.setVisibility(View.VISIBLE);
					layout_collect.setEnabled(true);	
					adapter = new CollectionListviewAdapter(context, listView,newsList);
					listView.setAdapter(adapter);
				}
				break;
			default:
				
				break;
			}
		}
	};
 
	/**
	 * 关闭数据库
	 */
	protected void onDestroy() {
		super.onDestroy();
		if(meController!=null)
		 meController.closeDB();
	}

	// 加载数据
	private class LoadDataThread implements Runnable {
		@Override
		public void run() {
			initDatas();
		}
	}
        //加载数据
	private void initDatas() {
		

		newsList = meController.getCollection(appInstance.pass_token,pageIndex, pageSize);
		
		if (load_type == FIRST_LOAD) {
			sendMessage(FIRST_LOAD);
		}
	}

	// 消息标示
	private void sendMessage(int what) {
		Message msg = handler.obtainMessage();
		msg.what = what;
		handler.sendMessage(msg);
	}

	// 加载数据
	public void firstLoadingDatas() {
		page_news_loading.setVisibility(View.VISIBLE);
		load_type = FIRST_LOAD;
		executorService.submit(new LoadDataThread());
	}
	//判断是否显示图片
	public void show(){
		if (newsList==null||newsList.size()==0) {
			System.out.println("newsList==null"+newsList);
			layout_view.setVisibility(View.VISIBLE);			
			listView.setVisibility(View.GONE);
			layout_collect.setText("编辑");
			
		}else {
			System.out.println("newsList"+newsList);
			layout_view.setVisibility(View.GONE);			
			listView.setVisibility(View.VISIBLE);
			layout_collect.setEnabled(true);		
		}
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