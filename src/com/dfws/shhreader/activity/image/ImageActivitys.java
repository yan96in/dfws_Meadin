/** 
 * Copyright © 2013 www.veryeast.com 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
package com.dfws.shhreader.activity.image;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.dfws.shhreader.R;
import com.dfws.shhreader.activity.AppInstance;
import com.dfws.shhreader.activity.CommentFromRightActivity;
import com.dfws.shhreader.activity.personalcenter.BaseActivity;
import com.dfws.shhreader.activity.personalcenter.LoginActivity;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.configures.NewsConfigure;
import com.dfws.shhreader.controllers.CollectionController;
import com.dfws.shhreader.controllers.NewsController;
import com.dfws.shhreader.database.tools.FavoriteDatabaseHelper;
import com.dfws.shhreader.entity.MediaInfo;
import com.dfws.shhreader.entity.News;
import com.dfws.shhreader.ui.CustomViewPager;
import com.dfws.shhreader.ui.ViewPageAdapter;
import com.dfws.shhreader.ui.dialog.CustomerToast;
import com.dfws.shhreader.ui.dialog.ShareCustomDialog;
import com.dfws.shhreader.utils.BitmapTools;
import com.dfws.shhreader.utils.DateTimeUtils;
import com.dfws.shhreader.utils.FileAccess;
import com.dfws.shhreader.utils.MD5Utils;
import com.dfws.shhreader.utils.NetWorkUtils;
import com.dfws.shhreader.utils.StringUtils;
import com.dfws.shhreader.utils.ThirdHelp;
import com.umeng.analytics.MobclickAgent;

/**
 * @file： ImageActivitys.java
 * @Page： com.dfws.shhreader.activity.image
 * @description： 图片新闻详细页
 * @since： 2013-11-25
 * @author： Administrator
 */
public class ImageActivitys extends BaseActivity {
	private TextView share, comments, collection, download;// 四个功能按钮
	private TextView t1, t2, t3;// 图片数
	private TextView textTitle, textContent;// 文章标题，文章内容
	private ImageView iv_news_detail_back;//
	private int news_id = 0;// 新闻ID
	private News news;// 新闻实体
	private NewsController controller;// 新闻控制器
	private List<MediaInfo> mediaInfos;// 媒体实体
	private CollectionController messageController;// 信息
	private AppInstance appInstance;// 全局
	private Context context;// 上下文
	private boolean strsult;// 返回状态
	private boolean cancel;// 取消收藏
	private MediaInfo mif;// 媒体
	private static final int COLLERTION = 0;// 收藏
	private static final int DOWNLOAD = 1;// 下载
	private static final int DOWNLOADS = 2;// 已缓存
	private ViewPageAdapter adapter;// 左右滑动适配器
	private CustomViewPager viewpager;// 存放图片
	private int indexs;// 当前页码
	private boolean downloadImage;// 下载
	private int numeber;// 评论条数
	private ExecutorService executorService;// 线程池
	private TextView txt_news_detail_notice;// 显示评论
	private ShareCustomDialog shareDialog;// 分享
	private boolean flag = true;// 默认是收藏，再次点击是取消收藏
	private LinearLayout main_linearlayout_l2;// 显示数目
	private LinearLayout page_news_loading;// loding加载页
	/** 收藏新闻用 */
	private FavoriteDatabaseHelper databaseHelper;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout_image_minutes);

		context = this;
		appInstance = (AppInstance) context.getApplicationContext();

		executorService = Executors.newFixedThreadPool(2);
		databaseHelper = new FavoriteDatabaseHelper(context);
		news_id = getIntent().getIntExtra("news_id", 0);
		controller = new NewsController(this);
		messageController = new CollectionController(this);
		initView();
		initListener();
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				String name1 = MD5Utils.MD5(news_id + "") + ".0";
				File file1 = new File(FrameConfigure.NORMAL_IMAGE_OBJECT_NEWS_DRC + name1);
				if (file1.exists()) {
					news = controller.getNewsFromFile(news_id,
							NewsConfigure.COLUMN_IMAGE);
				} else {
					news = controller.getNewsFromNet(news_id,
							NewsConfigure.COLUMN_IMAGE);
				}
				handler.sendEmptyMessage(11);
			}
		});
	}

	/**
	 * 收藏请求网络
	 */
	public void favoriteThread() {
		if (StringUtils.isEmpty(appInstance.pass_token)) {
			Toast.makeText(context, "请先登录！", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(context, LoginActivity.class);
			intent.putExtra("from", 2);// shoucang
			startActivity(intent);
			return;
		}
		if (news == null) {
			return;
		}
		collection.setEnabled(false);
		// 默认是收藏，再次点击是取消收藏
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				flag = databaseHelper.isNewsExist(news_id);
				if (!flag) {
					// 收藏
					messageController.createCollection(news_id,DateTimeUtils.getLongDateTime(true));
					handler.sendEmptyMessage(COLLERTION);
				} else {
					// 取消收藏
					messageController.delectCollection(news_id + "");
					handler.sendEmptyMessage(101);
				}
			}
		});
	}

	/**
	 * 下载 请求网络
	 */
	public void downloadThread() {
		final String src = mif.getSrc();
		final String path = mif.getPath();
		final String pathto = FrameConfigure.IMG_FAVORITE_DRC
				+ MD5Utils.MD5(src) + ".png";
		File file = new File(path);
		// 如果存在
		if (file.exists()) {
			FileAccess.copyFileTo(path, pathto);
			Toast.makeText(context, "下载成功", Toast.LENGTH_LONG).show();
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
					Uri.parse("file://"
							+ Environment.getExternalStorageDirectory())));
		} else {
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					downloadImage = BitmapTools.loadImage(
							FrameConfigure.IMG_FAVORITE_DRC, src, ".png");
					handler.sendEmptyMessage(DOWNLOAD);
				}
			});
		}
	}

	// 图片加载
	public void initAdapter() {
		adapter = new ViewPageAdapter(context, mediaInfos);
		viewpager.setAdapter(adapter);
	}

	// 分享窗口初始化
	private void initShareDialog() {
		shareDialog = new ShareCustomDialog(context);
		shareDialog.setOnclickListener(shareListener);
	}

	/**
	 * 加载组件
	 */
	public void initView() {
		// 图片
		viewpager = (CustomViewPager) findViewById(R.id.viewpager);
		viewpager.setCurrentItem(0);
		viewpager.setOffscreenPageLimit(1);
		main_linearlayout_l2 = (LinearLayout) findViewById(R.id.main_linearlayout_l2);
		page_news_loading = (LinearLayout) findViewById(R.id.page_news_loading);
		page_news_loading.setVisibility(View.VISIBLE);
		// 四个按钮
		share = (TextView) findViewById(R.id.main_layout_share_test);
		comments = (TextView) findViewById(R.id.main_layout_comments);
		collection = (TextView) findViewById(R.id.main_layout_collection);
		download = (TextView) findViewById(R.id.main_layout_more_download);
		// 翻页数字
		t1 = (TextView) findViewById(R.id.main_layout_number_one);
		t2 = (TextView) findViewById(R.id.main_layout_number_two);
		t3 = (TextView) findViewById(R.id.main_layout_number_three);
		// 标题内容
		textTitle = (TextView) findViewById(R.id.main_layout_title);
		textContent = (TextView) findViewById(R.id.main_layout_content);
		// 返回
		iv_news_detail_back = (ImageView) findViewById(R.id.iv_news_detail_back);
		// 评论条数
		txt_news_detail_notice = (TextView) findViewById(R.id.txt_news_detail_notice);
		// 分享窗口
		initShareDialog();
	}

	/**
	 * 添加事件
	 */
	public void initListener() {
		// 四个按钮
		share.setOnClickListener(clickListener);
		comments.setOnClickListener(clickListener);
		collection.setOnClickListener(clickListener);
		download.setOnClickListener(clickListener);
		// 返回
		iv_news_detail_back.setOnClickListener(clickListener);

		// 每次滑动
		viewpager
				.setOnPageChangeCallback(new CustomViewPager.OnPageChangeCallback() {
					@Override
					public void changeView(boolean left, boolean right) {
						// TODO Auto-generated method stub
						Log.i("AAAAA", "左/右" + left + "/" + right);
					}

					@Override
					public void onCurrentPage(int index) {
						// TODO Auto-generated method stub
						indexs = index;
						t1.setText((indexs + 1) + "");
						textContent.setText(adapter.getItemAlt(indexs));
						mif = adapter.getItem(indexs);
						main_linearlayout_l2.setVisibility(View.VISIBLE);
					}

					@Override
					public void OnPageStateChanged(int flag) {
						// TODO Auto-generated method stub

					}

				});
		// 第一次进入时加载
		viewpager.setDirectionListener(new CustomViewPager.DirectionListener() {
			@Override
			public void direction(boolean left, boolean right) {
				// TODO Auto-generated method stu
				if (!NetWorkUtils.checkNetWork(context)) {
					return;
				}
				if (indexs == adapter.getCount() - 1) {
					if (left && !right) {
						Intent intent = new Intent(context,
								CommentFromRightActivity.class);
						intent.putExtra("newsid", news_id);
						startActivity(intent);
						overridePendingTransition(R.anim.sliding_in_right,
								R.anim.sliding_out_right);
					}
				}
			}

			@Override
			public void ontouch() {
				// TODO Auto-generated method stub
				// if (main_linearlayout_l2.getVisibility()==View.VISIBLE) {
				// main_linearlayout_l2.setVisibility(View.GONE);
				// }else if (main_linearlayout_l2.getVisibility()==View.GONE) {
				// main_linearlayout_l2.setVisibility(View.VISIBLE);
				// }
			}
		});
	}

	/**
	 * 数字显示处理
	 */
	public void initNumbers() {
		t1.setText((indexs + 1) + "");// 当前第几个图片
		t3.setText(adapter.getCount() + "");// 显示当前图片总数
		textTitle.setText(news.getTitle());// 获取标题
		textContent.setText(mediaInfos.get(0).getAlt());
		mif = adapter.getItem(0);
	}

	/**
	 * 事件处理
	 */
	private View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			ClickListener(v);
		}

		private void ClickListener(View v) {
			switch (v.getId()) {
			// 分享
			case R.id.main_layout_share_test:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					return;
				}
				if (mif == null) {
					return;
				}
				shareDialog.initView();
				shareDialog.setOnclickListener(shareListener);
				shareDialog.show();
				break;
			// 评论
			case R.id.main_layout_comments:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					return;
				}
				if (mif == null) {
					return;
				}
				Intent in = new Intent(context, CommentFromRightActivity.class);
				in.putExtra("newsid", news_id);
				startActivity(in);
				overridePendingTransition(R.anim.sliding_in_right,
						R.anim.sliding_out_left);
				break;
			// 收藏
			case R.id.main_layout_collection:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					return;
				}
				if (mif == null) {
					return;
				}
				favoriteThread();
				break;
			// 下载
			case R.id.main_layout_more_download:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					return;
				}
				if (mif == null) {
					return;
				}
				downloadThread();
				break;
			// 返回
			case R.id.iv_news_detail_back:
				finish();
				break;
			default:
				break;
			}

		}
	};
	// 刷新UI
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			// 下载
			case DOWNLOAD:
				if (downloadImage == true) {
					Toast.makeText(context, "下载成功", Toast.LENGTH_LONG).show();
					sendBroadcast(new Intent(
							Intent.ACTION_MEDIA_MOUNTED,
							Uri.parse("file://"
									+ Environment.getExternalStorageDirectory())));
				} else {
					Toast.makeText(context, "下载失败", Toast.LENGTH_LONG).show();
				}
				break;
				//yixiazai
			case DOWNLOADS:
				Toast.makeText(context, "图片已下载", Toast.LENGTH_LONG).show();
				break;
				//jia zai tu pian 
			case 11:
				if (news != null) {
					System.out.println("news" + news + "");
					mediaInfos = news.getMedias();
					initAdapter();
					initNumbers();
					page_news_loading.setVisibility(View.GONE);
				} else {

					Toast.makeText(context, "内容加载失败！", Toast.LENGTH_LONG)
							.show();
				}
				break;
			// 收藏
			case COLLERTION:
				flag = true;
				news.setCollected(true);
				collection.setEnabled(true);
				CustomerToast.showMessage(context,getString(R.string.strulst_create), true, false);
				break;
			// 取消收藏
			case 101:
				flag = false;
				news.setCollected(false);
				collection.setEnabled(true);
				CustomerToast.showMessage(context,getString(R.string.strulst_create_1), true, false);
				break;
			default:
				break;
			}
		}
	};
	// 分享监听
	private View.OnClickListener shareListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			shareListener(v);
		}

		private void shareListener(View v) {
			if (news == null || mif == null) {
				return;
			}

			String title = news.getTitle();
			String tag_url = news.getUrl();
			String content_sina = "#迈点阅读#《" + title + "》分享自迈点阅读客户端，原文:"
					+ tag_url;
			String re = mif.getAlt();
			String content_wei_p = re.substring(0,
					(re.length() > 100 ? 100 : re.length())).trim();
			String content_wei_c = title;
			String content_e = "《" + title + "》分享自迈点阅读客户端，原文：" + tag_url;
			String content_s = "我在迈点阅读客户端发现文章《" + title + "》与你分享，访问：" + tag_url;
			String image_url = news.getThumb();
			File dir = context
					.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
			File file = new File(dir, MD5Utils.MD5(image_url));
			if (file.exists()) {
				image_url = file.getAbsolutePath();
			} else {
				image_url = null;
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
				ThirdHelp.share_Weixin(context, tag_url, title, content_wei_p,
						image_url, false);
				break;

			case R.id.txt_share_circle:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					break;
				}
				ThirdHelp.share_Weixin(context, tag_url, title, "", image_url,
						true);
				break;

			case R.id.txt_share_emile:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					break;
				}
				ThirdHelp.share_Emile(context, content_e, image_url);
				break;

			case R.id.txt_share_sms:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					break;
				}
				ThirdHelp.share_SMS(context, content_s);
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
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 关闭数据库
		if (messageController != null)
			messageController.closeDB();
		if (databaseHelper != null)
			databaseHelper.close();
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ThirdHelp.onBack(requestCode, resultCode, data);
	}
}