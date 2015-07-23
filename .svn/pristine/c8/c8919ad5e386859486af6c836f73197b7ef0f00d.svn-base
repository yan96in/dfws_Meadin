/**
 * 
 */
package com.dfws.shhreader.activity.personalcenter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.dfws.shhreader.R;
import com.dfws.shhreader.activity.NewsDetailActivity;
import com.dfws.shhreader.activity.image.ImageActivitys;
import com.dfws.shhreader.adapter.MsgLigstviewAdpaper;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.controllers.NewsController;
import com.dfws.shhreader.entity.Messages;
import com.umeng.analytics.MobclickAgent;

/**
 * 我的消息
 * 
 * @file： MessageActivity.java
 * @Page： com.dfws.shhreader.activity.personalcenter
 * @description：
 * @since： 2013-10-24
 * @author： Administrator
 */
@SuppressLint("HandlerLeak")
public class MessageActivity extends BaseActivity {
	private ListView listview;// listview
	private ImageView message_imageview;// 图片
	private MsgLigstviewAdpaper adapter;// 适配器
	private NewsController newsController;// 控制器
	private ImageView message_return;// 返回按钮
	private Context context;
	private List<Messages> listnews;// 数据集合
	// 传参
	private int since_id = 0, max_id = 0;
	private int pageSize = 20;
	private int pageIndex = 1;
	// 线程池
	private ExecutorService executorService;
	// 线程池默认大小
	private int THREAD_POOL_SIZE = 2;
	// 页码

	// 加载模式
	private int load_type = FIRST_LOAD;
	public static final int FIRST_LOAD = 0x0000;// 首次加载

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (FrameConfigure.reading_type == FrameConfigure.TYPE_NIGHT) {
			setContentView(R.layout.night_main_layout_message);
		} else {
			setContentView(R.layout.main_layout_message);
		}
		context = this;
		newsController = new NewsController(context);

		executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		initview();

	}

	// 初始化
	public void initview() {
		listview = (ListView) findViewById(R.id.layout_messages);

		message_imageview = (ImageView) findViewById(R.id.message_imageview);
		message_return = (ImageView) findViewById(R.id.message_return);

		executorService.submit(new LoadDataThread());
		// 返回
		message_return.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		firstLoadingDatas();
		// 监听Iem
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				System.out.println("arg2====" + arg2);
				int index = (int) arg3;
				int ids = adapter.getItemObjectId(index);
				int type = adapter.getItemObjectType(index);
				Intent intent = null;
				if (type == 2) {
					intent = new Intent(context, ImageActivitys.class);
				} else {
					intent = new Intent(context, NewsDetailActivity.class);
				}
				intent.putExtra("from", 0);
				intent.putExtra("type", type);
				intent.putExtra("news_id", ids);
				startActivity(intent);
			}
		});
	}

	/**
	 * 刷新UI
	 */
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case FIRST_LOAD:
				if (listnews==null ||listnews.size()==0) {
					message_imageview.setVisibility(View.VISIBLE);
				}else {
					listview.setVisibility(View.VISIBLE);
					message_imageview.setVisibility(View.GONE);
					adapter = new MsgLigstviewAdpaper(context, listview, listnews);
					listview.setAdapter(adapter);
				}	
				break;						
			}
		}
	};
	// 加载数据
	private class LoadDataThread implements Runnable {
		@Override
		public void run() {
			initDatas();
		}
	}

	private void initDatas() {
		listnews = newsController.getNewsPush(pageIndex, pageSize, since_id,max_id);	
		if (load_type == FIRST_LOAD) {
			sendMessage(FIRST_LOAD);
		}
	}


  //消息标示
	private void sendMessage(int what) {
		Message msg = handler.obtainMessage();
		msg.what = what;
		handler.sendMessage(msg);
	}
   //加载数据
	public void firstLoadingDatas() {
		load_type = FIRST_LOAD;
		executorService.submit(new LoadDataThread());
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
