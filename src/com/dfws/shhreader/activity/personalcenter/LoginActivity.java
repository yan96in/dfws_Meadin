package com.dfws.shhreader.activity.personalcenter;

import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dfws.shhreader.R;
import com.dfws.shhreader.activity.AppInstance;
import com.dfws.shhreader.adapter.AutoTextViewAdapter;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.controllers.SetsController;
import com.dfws.shhreader.database.tools.UserDatabaseHelper;
import com.dfws.shhreader.entity.User;
import com.dfws.shhreader.slidingmenu.fragment.RightSets;
import com.dfws.shhreader.ui.dialog.CustomerToast;
import com.dfws.shhreader.utils.NetWorkUtils;
import com.dfws.shhreader.utils.StringUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * <h2>登录</h2>
 * 
 * @author Eilin.Yang
 * @since 2013-10-18
 * @version v1.0
 */
public class LoginActivity extends BaseActivity {
	private TextView regView;// 注册
	private AutoCompleteTextView ed_name, ed_pass;// 账号、密码
	private TextView login_loginbtn;// 登录
	private SetsController setsController;// 设置控制器
	private ImageView login_return;// 返回
	private Context context;// 上下文
	private int strsult;// 返回状态
	private AppInstance appInstance;// 实例
	private AutoTextViewAdapter adapter;
	private List<User> listuser;// 用户数据集合
	private SharedPreferences sp = null;// 保存账号密码
	private String userName = null, userPass = null;// 账号和密码
	private Drawable c = null;// 图片
	private Drawable b = null;// 图片
	private Drawable a = null;// 图片
	private UserDatabaseHelper userDatabaseHelper;// 数据库
	private int from;// 判断来自哪里的
	private boolean stuats = true;// 默认通过

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (FrameConfigure.reading_type == FrameConfigure.TYPE_NIGHT) {
			setContentView(R.layout.night_layout_login);
		} else {
			setContentView(R.layout.layout_login);
		}
		context = this;
		appInstance = (AppInstance) getApplicationContext();

		userDatabaseHelper = new UserDatabaseHelper(context);

		setsController = new SetsController(context);

		sp = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		from = getIntent().getIntExtra("from", 0);
		initview();
	}

	/**
	 * handler 刷新UI
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:
				stuats = true;
				if (strsult == 1) {
					MobclickAgent.onEvent(context, "login");
					CustomerToast.showMessage(context,
							getString(R.string.strulst_login_2), true, false);
					RightSets.txt_right_login_status.setText(appInstance.user.getName());
					RightSets.login_image_8_gone.setVisibility(View.VISIBLE);
					RightSets.login_image_1_view.setVisibility(View.GONE);
					// 记住用户名、密码、
					Editor editor = sp.edit();
					editor.putString("userNmae", userName);
					editor.putString("userPass", userPass);
					editor.commit();
					if (from == 1) {// 来自个人中心收藏
						Intent intent = new Intent(context,
								CollectionActivity.class);
						startActivity(intent);
					} else if (from == 2) {// 图片收藏
						finish();
					} else if (from == 3) {
						finish();
					}
					finish();
				} else if(strsult==-1){
					CustomerToast.showMessage(context,
							getString(R.string.strulst_login_11), true, false);
				}else{//登录失败
					CustomerToast.showMessage(context,
							getString(R.string.strulst_login_1), true, false);
				}
				break;
			case 2:
				CustomerToast.showMessage(context,
						getString(R.string.strulst_reg_1), true, false);
				break;
			case 3:
				CustomerToast.showMessage(context,
						getString(R.string.strulst_reg_2), true, false);
				break;
			default:
				break;
			}
		}
	};

	// 初始化控件
	public void initview() {
		regView = (TextView) findViewById(R.id.login_regist_iv);
		login_loginbtn = (TextView) findViewById(R.id.login_loginbtn);
		ed_name = (AutoCompleteTextView) findViewById(R.id.login_userid_etxt);
		ed_pass = (AutoCompleteTextView) findViewById(R.id.login_password_etxt);
		login_return = (ImageView) findViewById(R.id.login_return);
		

		if (FrameConfigure.reading_type == FrameConfigure.TYPE_NIGHT) {
			// setContentView(R.layout.night_layout_login);
			b = getResources().getDrawable(R.drawable.ic_iput_user_52_52_night);
			a = getResources().getDrawable(
					R.drawable.ic_iput_password_52_52_night);
		} else {
			// setContentView(R.layout.layout_login);
			b = getResources().getDrawable(R.drawable.ic_iput_user_52_52);
			a = getResources().getDrawable(R.drawable.ic_iput_password_52_52);
		}
		c = getResources().getDrawable(R.drawable.close_gary);

		ed_name.setText(sp.getString("userNmae", userName));
		ed_pass.setText(sp.getString("userPass", userPass));

		login_return.setOnClickListener(listenter);

		ed_name.addTextChangedListener(username_TextChanged);
		ed_name.setOnTouchListener(txtSearch_OnTouch_Deletetxt);

		ed_pass.addTextChangedListener(userpass_TextChanged);
		ed_pass.setOnTouchListener(txtSearch_1_OnTouch_Deletetxt);

		regView.setOnClickListener(listenter);

		login_loginbtn.setOnClickListener(listenter);

		ed_name.setOnClickListener(listenter);
		ed_pass.setOnClickListener(listenter);

	}

	// 获取用户名 显示在edittext 上
	public void initDate() {

		listuser = userDatabaseHelper.getAllUsers();
		adapter = new AutoTextViewAdapter(context, listuser);
		ed_name.setAdapter(adapter);
		ed_name.setThreshold(0);
		ed_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String username = adapter.getUserName((int) arg3);
				if (!StringUtils.isEmpty(username)) {
					ed_name.setText(username);
				}
			}

		});
	}

	// 监听事件
	private View.OnClickListener listenter = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			listenter(v);
		}

		private void listenter(View v) {
			switch (v.getId()) {
			// 注册
			case R.id.login_regist_iv:
				doRegsin();
				break;
			// 登录
			case R.id.login_loginbtn:
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomerToast.showMessage(context, "请检查网络连接！", false, true);
					return;
				}
				if (stuats) {
					stuats = false;

					doLogin();
				}
				break;
			// 返回
			case R.id.login_return:
				finish();
				break;
			// 点击输入框就出现 删除用户名的X图片
			case R.id.login_userid_etxt:
				if (ed_name.length() > 1) {
					initDate();
					ed_name.setCompoundDrawablesWithIntrinsicBounds(b, null, c,
							null);
				} else {
					ed_name.setCompoundDrawablesWithIntrinsicBounds(b, null, c,
							null);
				}
				break;
			// 点击输入框就出现删除密码的X图片
			case R.id.login_password_etxt:
				if (ed_pass.length() > 1) {
					ed_pass.setCompoundDrawablesWithIntrinsicBounds(a, null, c,
							null);
				} else {
					ed_pass.setCompoundDrawablesWithIntrinsicBounds(a, null, c,
							null);
				}
				break;
			default:
				break;
			}
		}
	};
	// 监听 username 输入框
	private TextWatcher username_TextChanged = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable s) {
			if (!TextUtils.isEmpty(s)) {
				ed_name.setCompoundDrawablesWithIntrinsicBounds(b, null, c,
						null);
			} else {
				ed_name.setCompoundDrawablesWithIntrinsicBounds(b, null, null,
						null);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}
	};

	// 监听 userpass输入框
	private TextWatcher userpass_TextChanged = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable s) {
			if (!TextUtils.isEmpty(s)) {
				ed_pass.setCompoundDrawablesWithIntrinsicBounds(a, null, c,
						null);
			} else {
				ed_pass.setCompoundDrawablesWithIntrinsicBounds(a, null, null,
						null);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}
	};

	// 触屏事件 点击删除账号
	private OnTouchListener txtSearch_OnTouch_Deletetxt = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curX = (int) event.getX();
				if (curX > v.getWidth() - 38&& !TextUtils.isEmpty(ed_name.getText())) {
					ed_name.setText("");
					ed_pass.setText("");
					ed_name.setCompoundDrawablesWithIntrinsicBounds(b, null,
							null, null);
					int cacheInputType = ed_name.getInputType();// backup
					ed_name.setInputType(InputType.TYPE_NULL);// disable
					ed_name.onTouchEvent(event);// call native
					ed_name.setInputType(cacheInputType);// restore
					ed_name.setCompoundDrawablesWithIntrinsicBounds(b, null, c,
							null);
					return true;
				}
				break;
			}
			return false;
		}
	};
	// 触屏事件 点击删除密码
	private OnTouchListener txtSearch_1_OnTouch_Deletetxt = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curX = (int) event.getX();
				if (curX > v.getWidth() - 38
						&& !TextUtils.isEmpty(ed_pass.getText())) {
					ed_pass.setText("");
					ed_pass.setCompoundDrawablesWithIntrinsicBounds(a, null,
							null, null);
					int cacheInputType = ed_pass.getInputType();// backup
					ed_pass.setInputType(InputType.TYPE_NULL);// disable
					ed_pass.onTouchEvent(event);// call native
					ed_pass.setInputType(cacheInputType);// restore
					ed_pass.setCompoundDrawablesWithIntrinsicBounds(a, null, c,
							null);
					return true;
				}
				break;
			}
			return false;
		}
	};

	// 处理登录
	public void doLogin() {
		userName = ed_name.getText().toString();
		userPass = ed_pass.getText().toString();
		// 判断登录
		if (userName == null || userName.trim().equals("")) {
			handler.sendEmptyMessage(2);
			return;
		}
		if (userPass == null || userPass.trim().equals("")) {
			handler.sendEmptyMessage(3);
			return;
		}
		new Thread() {
			@Override
			public void run() {
				// 这里写入子线程需要做的工作
				strsult = setsController.login(userName, userPass);
				handler.sendEmptyMessage(0);
			}
		}.start();
	}

	// 处理注册
	public void doRegsin() {
		AppInstance.isProgramExit=false;
		Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
		startActivity(intent);
		 finish();
	}

	// 关闭数据库
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// ed_name.setCompoundDrawablesWithIntrinsicBounds(null, null, null,
		// null);
		 ed_name.dismissDropDown();
		// ed_name.clearListSelection();
		// ed_pass.setCompoundDrawablesWithIntrinsicBounds(null, null, null,
		// null);
		
		setsController.closeDB();
		userDatabaseHelper.close();
	}

	public void onResume() {
		super.onResume();

		/**
		 * 此处调用基本统计代码
		 */
		stuats = true;
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
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		AppInstance mApp = (AppInstance) getApplication();
		if (mApp.isExit()) {
			finish();

		}

	}

}