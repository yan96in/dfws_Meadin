/**
 * Copyright © 2013 MeadinReader www.veryeast.com
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

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dfws.shhreader.R;
import com.dfws.shhreader.activity.personalcenter.BaseActivity;
import com.dfws.shhreader.adapter.CommentAdapter;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.configures.NewsConfigure;
import com.dfws.shhreader.controllers.NewsController;
import com.dfws.shhreader.entity.Comment;
import com.dfws.shhreader.slidingmenu.fragment.BaseFragment;
import com.dfws.shhreader.ui.dialog.CusDialog;
import com.dfws.shhreader.ui.dialog.CustomerToast;
import com.dfws.shhreader.utils.NetWorkUtils;
import com.dfws.shhreader.utils.ScreenUtil;
import com.dfws.shhreader.utils.StringUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.umeng.analytics.MobclickAgent;

/**
 * <h2> <h2>
 * 
 * <pre> </pre>
 * 
 * @author 东方网升Eilin.Yang
 * @since 2013-11-9
 * @version
 * @modify ""
 */
public class CommentFromRightActivity extends BaseActivity {

	private Context context;
	/** 新闻id */
	private int newsid;
	/** list列表 */
	private ListView listView;
	private PullToRefreshListView pullParent;
	/** 数据集 */
	private List<Comment> comments;
	/** 数据集 */
	private List<Comment> comments_temp;
	/** 评论总数 */
	private int comment_count;
	/** 返回按钮 */
	private ImageView iv_comment_goback;
	/** 写评论 */
	private ImageView iv_comment_create;
	/** 写评论对话框 */
	private CusDialog dialog;
	/** 清除评论 */
	private ImageView iv_dialog_comment_edit_clear;
	/** 提交评论 */
	private ImageView iv_dialog_comment_edit_yes;
	/** 文本框 */
	private EditText edi_dialog_comment;
	/** 评论的总数 */
	private TextView txt_comment_counts;
	private ExecutorService executorService;
	/** 线程池默认大小 */
	private int THREAD_POOL_SIZE = 3;
	/** 当前位置 */
	private int current_position = 0;
	/** 首次加载 */
	private int load_first = 0;
	/** 刷新 */
	private int load_up = 1;
	/** 下一页 */
	private int load_next = 2;
	/** 页码 */
	private int pageindex = 1;
	/** 每页大小 */
	private int pagesize = 10;
	/** 加载模式 */
	private int load_type = load_first;
	/** 控制器 */
	private NewsController controller;
	/** 最新一条评论 */
	private Comment comment;
	/** 评论内容 */
	private String txt = "";
	/** 赞的评论id */
	private int comment_id = -1;
	/** 赞 */
	// private SupportDialog supportDialog;
	/** 赞 */
	private ImageView iv_comment_support;
	/** 复制 */
	private ImageView iv_comment_copy;
	/** 赞，总数 */
	private TextView txt_comment_support_count;
	/** adapter */
	private CommentAdapter adapter;
	// 完成带箭头的对话框
	private PopupWindow pop;
	/** 赞的内容 */
	private String support_str = "";
	private int support_index = 0;
	private LinearLayout linear_loading;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			int what = msg.what;
			switch (what) {
			case BaseFragment.FIRST_LOAD:
				linear_loading.setVisibility(View.GONE);
				txt_comment_counts.setText(NewsConfigure.comments_count + "");
				adapter = new CommentAdapter(context, comments);
				listView.setAdapter(adapter);
				break;

			case BaseFragment.FIRST_REQUEST_ERROR:
				linear_loading.setVisibility(View.GONE);
				adapter = new CommentAdapter(context, comments);
				listView.setAdapter(adapter);
				txt_comment_counts.setText(NewsConfigure.comments_count+ "");
				CustomerToast.showMessage(context, "还没有评论，赶快发表你的评论吧！", true,
						true);
				break;
			case BaseFragment.REFRESH_PULL:
				adapter.clear();
				adapter.addItems(comments_temp);
				pullParent.onRefreshComplete();
				txt_comment_counts.setText(NewsConfigure.comments_count+ "");
				break;

			case BaseFragment.REFRESH_REQUEST_ERROR:
				pullParent.onRefreshComplete();
				break;
			case BaseFragment.PAGE_NEXT_LOAD:
				adapter.addItems(comments_temp);
				pullParent.onRefreshComplete();
				break;

			case BaseFragment.PAGE_NEXT_REQUEST_ERROR:
				pullParent.onRefreshComplete();
				break;
			case 101:// 发表评论成功
				dialog.dismiss();
				CustomerToast.showMessage(context, "评论成功", false, true);
				iv_dialog_comment_edit_yes.setEnabled(true);
				adapter.addItem(comment);
				adapter.notifyDataSetChanged();
				NewsConfigure.comments_count=NewsConfigure.comments_count+1;
				txt_comment_counts.setText(NewsConfigure.comments_count+ "");
				break;

			case 100:// 发表评论失败
				dialog.dismiss();
				CustomerToast.showMessage(context, "发表评论失败！", false, true);
				iv_dialog_comment_edit_yes.setEnabled(true);
				break;

			case 201:// 赞评论成功
				adapter.setItem(support_index, comment);
				break;

			case 200:// 赞评论失败
				CustomerToast.showMessage(context, "赞失败！", false, true);
				break;
			}
		}

	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dfws.shhreader.activity.personalcenter.BaseActivity#onCreate(android
	 * .os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		newsid = getIntent().getIntExtra("newsid", -1);
		if (newsid == -1) {
			finish();
		}
		if (FrameConfigure.reading_type == FrameConfigure.TYPE_NIGHT) {
			setContentView(R.layout.night_layout_comment);
		} else {
			setContentView(R.layout.layout_comment);
		}
		executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		controller = new NewsController(context);
		initView();
		linear_loading.setVisibility(View.VISIBLE);
		load_type = load_first;
		executorService.submit(new LoadDataThread());
	}

	private void initView() {
		pullParent = (PullToRefreshListView) findViewById(R.id.lv_comment);
		pullParent.setMode(Mode.BOTH);
		pullParent.setRefreshLabelTextSize(14);
		pullParent.setRefreshLabelColor(Color.GRAY);
		pullParent.setLoadingDrawable(
				context.getResources().getDrawable(R.drawable.xlistview_arrow),
				Mode.PULL_FROM_START);
		pullParent.setLoadingPregressDrawable(context.getResources()
				.getDrawable(R.drawable.xlistview_loadings),
				Mode.PULL_FROM_START);
		pullParent.setLoadingPregressWidthAndHeight(
				ScreenUtil.dip2px(context, 32), ScreenUtil.dip2px(context, 32),
				Mode.PULL_FROM_START);
		listView = pullParent.getRefreshableView();
		iv_comment_goback = (ImageView) findViewById(R.id.iv_comment_goback);
		iv_comment_create = (ImageView) findViewById(R.id.iv_comment_create);
		txt_comment_counts = (TextView) findViewById(R.id.txt_comment_counts);
		linear_loading = (LinearLayout) findViewById(R.id.linear_loading);
		iv_comment_goback.setOnClickListener(listeners);
		iv_comment_create.setOnClickListener(listeners);

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@SuppressLint("NewApi")
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Comment comment1 = adapter.getItem((int) id);
				if (comment1 != null) {
					txt_comment_support_count.setText(comment1.getSupport()
							+ "顶");
					comment_id = comment1.getId();
					support_str = comment1.getText();
					support_index = (int) id;
					int[] location = new int[2];
					view.getLocationOnScreen(location);
					pop.showAtLocation(view, Gravity.TOP|Gravity.CENTER_HORIZONTAL, location[0],
							location[1] - pop.getHeight()-view.getHeight()+15);
				}
				return false;
			}
		});
		pullParent
				.setOnRefreshListener(new com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						if (!NetWorkUtils.checkNetWork(context)) {
							CustomerToast.showMessage(context, "没有网络！", false,
									true);
							pullParent.onRefreshComplete();
							return;
						}
						load_type = load_up;
						executorService.submit(new LoadDataThread());
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						if (!NetWorkUtils.checkNetWork(context)) {
							CustomerToast.showMessage(context, "没有网络！", false,
									true);
							pullParent.onRefreshComplete();
							return;
						}
						load_type = load_next;
						executorService.submit(new LoadDataThread());
					}

				});
		pullParent.setOnPullEventListener(new OnPullEventListener<ListView>() {

			@Override
			public void onPullEvent(PullToRefreshBase<ListView> refreshView,
					State state, Mode direction) {
				// TODO Auto-generated method stub

			}
		});
		initDialog();
		initPopupWindow();
	}

	/**
	 * <pre>
	 * 初始化写评论模块
	 * </pre>
	 * 
	 */
	private void initDialog() {
		dialog = new CusDialog(context);
		View dialogView = null;
		if (FrameConfigure.reading_type == FrameConfigure.TYPE_NIGHT) {
			dialogView = LayoutInflater.from(context).inflate(
					R.layout.night_layout_dialog_create_comment, null);
		} else {
			dialogView = LayoutInflater.from(context).inflate(
					R.layout.layout_dialog_create_comment, null);
		}
		dialog.setMessageView(dialogView);
		iv_dialog_comment_edit_clear = (ImageView) dialogView
				.findViewById(R.id.iv_dialog_comment_edit_clear);
		iv_dialog_comment_edit_yes = (ImageView) dialogView
				.findViewById(R.id.iv_dialog_comment_edit_yes);
		edi_dialog_comment = (EditText) dialogView
				.findViewById(R.id.edi_dialog_comment);
		dialog.hideBtn();
		dialog.setUpCancelBtn(null, null);
		dialog.setUpOkBtn(null, null);
		iv_dialog_comment_edit_clear.setOnClickListener(listeners);
		iv_dialog_comment_edit_yes.setOnClickListener(listeners);
	}

	/**
	 * <h2>初始化评论列表 <h2>
	 * 
	 * <pre> </pre>
	 * 
	 * @author 东方网升Eilin.Yang
	 * @since 2013-11-18
	 * @version
	 * @modify ""
	 */
	private class LoadDataThread implements Runnable {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			loadComments();
		}

	}

	/**
	 * <pre>
	 * 发表评论
	 * </pre>
	 * 
	 */
	private void postComment() {
		txt = edi_dialog_comment.getText().toString().trim();
		if (!StringUtils.isEmpty(txt)) {
			executorService.submit(new PostCommentThread());
		} else {
			iv_dialog_comment_edit_yes.setEnabled(true);
			CustomerToast.showMessage(context, "评论不能为空！", false, true);
		}
	}

	/**
	 * <h2>发表评论 <h2>
	 * 
	 * <pre> </pre>
	 * 
	 * @author 东方网升Eilin.Yang
	 * @since 2013-11-18
	 * @version
	 * @modify ""
	 */
	private class PostCommentThread implements Runnable {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			comment = controller.createComment(newsid, txt);
			if (comment != null) {
				sendMessage(101);
			} else {
				sendMessage(100);
			}
		}

	}

	/**
	 * <pre>
	 * 赞评论
	 * </pre>
	 * 
	 */
	private void postSupport() {
		if (comment_id != -1)
			executorService.submit(new PostSupportThread());
	}

	/**
	 * <h2>赞评论 <h2>
	 * 
	 * <pre> </pre>
	 * 
	 * @author 东方网升Eilin.Yang
	 * @since 2013-11-18
	 * @version
	 * @modify ""
	 */
	private class PostSupportThread implements Runnable {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			comment = controller.supportComment(newsid, comment_id + "");
			if (comment != null) {
				sendMessage(201);
			} else {
				sendMessage(200);
			}
		}

	}

	/**
	 * <pre>
	 * 加载列表数据
	 * </pre>
	 * 
	 */
	private void loadComments() {

		if (load_type == load_first) {
			comments = controller.getCommentList(newsid, pageindex, pagesize);
			if (comments != null) {
				sendMessage(BaseFragment.FIRST_LOAD);
			} else {
				sendMessage(BaseFragment.FIRST_REQUEST_ERROR);
			}
		}
		if (load_type == load_up) {
			comments_temp = controller.getCommentList(newsid, 1, pagesize
					* pageindex);
			if (comments_temp != null) {
				if (comments != null) {
					comments.clear();
					comments = comments_temp;
				}
				sendMessage(BaseFragment.REFRESH_PULL);
			} else {
				sendMessage(BaseFragment.REFRESH_REQUEST_ERROR);
			}
		}
		if (load_type == load_next) {
			comments_temp = controller.getCommentList(newsid, ++pageindex,
					pagesize);
			if (comments_temp != null) {
				if (comments != null) {
					comments.addAll(comments_temp);
				}
				sendMessage(BaseFragment.PAGE_NEXT_LOAD);
			} else {
				--pageindex;
				sendMessage(BaseFragment.PAGE_NEXT_REQUEST_ERROR);
			}
		}
	}

	private OnClickListener listeners = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.iv_comment_goback:
				finish();
				break;

			case R.id.iv_comment_create:
				dialog.show();
				edi_dialog_comment.setText(" ");
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						edi_dialog_comment.setFocusable(true);
						edi_dialog_comment.requestFocus();
						edi_dialog_comment.setFocusableInTouchMode(true);
						edi_dialog_comment.setSelection(1);
						boolean isFocus = edi_dialog_comment.hasFocus();
						InputMethodManager imm = (InputMethodManager) context
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						if (isFocus) {
							imm.toggleSoftInput(0,
									InputMethodManager.HIDE_NOT_ALWAYS);
						} else {
							imm.hideSoftInputFromWindow(
									edi_dialog_comment.getWindowToken(), 0);
						}
					}
				}, 80);
				break;
			case R.id.iv_dialog_comment_edit_clear:
				dialog.dismiss();
				edi_dialog_comment.setText("");
				break;
			case R.id.iv_dialog_comment_edit_yes:
				MobclickAgent.onEvent(context, "comment_submit");
				iv_dialog_comment_edit_yes.setEnabled(false);
				postComment();
				break;
			case R.id.iv_comment_support:
				pop.dismiss();
				postSupport();
				break;
			case R.id.iv_comment_copy:
				StringUtils.copy(context, support_str);
				pop.dismiss();
				break;
			case R.id.linear_comment_support:
				pop.dismiss();
				postSupport();
				break;
			case R.id.linear_comment_copy:
				pop.dismiss();
				StringUtils.copy(context, support_str);
				break;
			}
		}
	};

	/**
	 * 
	 * <pre>
	 * 发送消息
	 * </pre>
	 * 
	 * @param what
	 *            消息标识
	 */
	private void sendMessage(int what) {
		Message msg = handler.obtainMessage();
		msg.what = what;
		handler.sendMessage(msg);
	}

	/**
	 * 初始化popupWindow
	 */
	private void initPopupWindow() {

		View view = this.getLayoutInflater().inflate(
				R.layout.layout_comment_support, null);
		iv_comment_copy = (ImageView) view.findViewById(R.id.iv_comment_copy);
		iv_comment_support = (ImageView) view
				.findViewById(R.id.iv_comment_support);
		txt_comment_support_count = (TextView) view
				.findViewById(R.id.txt_comment_support_count);
		view.findViewById(R.id.linear_comment_support).setOnClickListener(
				listeners);
		view.findViewById(R.id.linear_comment_copy).setOnClickListener(
				listeners);
		pop = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		pop.setFocusable(true);
		pop.setTouchable(true);
		pop.setOutsideTouchable(true);
		pop.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.shap_support_pop_bg));
		pop.setAnimationStyle(R.style.pop_anim_style);
		pop.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
					pop.dismiss();
				return false;
			}
		});
		view.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN
						&& keyCode == KeyEvent.KEYCODE_MENU) {
					pop.dismiss();
				}
				return false;
			}
		});
		iv_comment_support.setOnClickListener(listeners);
		iv_comment_copy.setOnClickListener(listeners);
	}

	@Override
	public void onResume() {
		super.onResume();

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
}
