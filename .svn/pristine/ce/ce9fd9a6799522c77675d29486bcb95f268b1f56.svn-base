package com.dfws.shhreader.ui.dialog;

import com.dfws.shhreader.R;
import com.dfws.shhreader.configures.Configure;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * <h2>自定义对话框</h2>
 * 定制的对话框，包含动画、样式自定义
 * @author Eilin.Yang
 * @version v1.0
 * @since 2013-9-10
 */
public class CusDialog implements android.view.View.OnClickListener{

	private Context context;
	private LayoutInflater inflater;
	/**
	 * 自定义对话框
	 */
	private Dialog dialog;
	/**
	 * 确定按钮
	 */
	private TextView btn_ok;
	/**
	 * 取消按钮
	 */
	private TextView btn_cancel;
	/**
	 * 消息容器
	 */
	private LinearLayout linear_container;
	/**
	 * 显示状态，true：已显示，false：没有显示
	 */
	private boolean status=false;
	/**
	 * 是否隐藏了。true：已隐藏，false：显示
	 */
	private boolean ishide=false;
	/**
	 * 是否初始化完成了消息内容控件
	 */
	private boolean isfinishmessageview=false;
	/**
	 * 是否初始化完"确定"按钮
	 */
	private boolean isfinishokbtn=false;
	/**
	 * 是否初始化完"取消"按钮
	 */
	private boolean isfinishcancelbtn=false;
	private LinearLayout linear_dialog_btn_container;
	public CusDialog(Context context){
		this.context=context;
		this.inflater=LayoutInflater.from(context);
		init();
	}
	private void init(){
		dialog=new Dialog(context, R.style.custom_dialog);
		dialog.setContentView(R.layout.layout_dialog_container);
		linear_container=(LinearLayout)dialog.findViewById(R.id.linear_dialog_container);
		btn_ok=(TextView)dialog.findViewById(R.id.txt_dialog_btn_ok);
		btn_cancel=(TextView)dialog.findViewById(R.id.txt_dialog_btn_cancel);
		linear_dialog_btn_container=(LinearLayout)dialog.findViewById(R.id.linear_dialog_btn_container);
		Window window=dialog.getWindow();
		window.setWindowAnimations(R.style.dialog_in_and_out);
		window.setGravity(Gravity.BOTTOM);
		btn_ok.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}
	
	/**
	 * 隐藏底部按钮
	 * @param 
	 */
	public void hideBtn() {
		if (linear_dialog_btn_container!=null) {
			linear_dialog_btn_container.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 显示底部按钮
	 * @param 
	 */
	public void showBtn() {
		if (linear_dialog_btn_container!=null) {
			linear_dialog_btn_container.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * 添加消息的内容布局
	 * @param res XML资源布局
	 */
	public void setMessageView(int res) {
		if (linear_container!=null) {
			linear_container.addView(inflater.inflate(res, null));
			isfinishmessageview=true;
		}
	}
	
	/**
	 * 添加消息的内容布局
	 * @param view 布局的View对象
	 */
	public void setMessageView(View view) {
		if (linear_container!=null) {
			linear_container.addView(view);
			isfinishmessageview=true;
		}
	}
	
	/**
	 * 初始化"确定"按钮
	 * @param title 按钮标题
	 * @param listener 按钮监听
	 */
	public void setUpOkBtn(CharSequence title, android.view.View.OnClickListener listener){
		if (listener==null&&btn_ok!=null) {
			btn_ok.setOnClickListener(this);
		}
		else {
			btn_ok.setOnClickListener(listener);
		}
		if (btn_ok!=null&&!TextUtils.isEmpty(title)) {
			btn_ok.setText(title);
		}
		isfinishokbtn=true;
	}
	
	/**
	 * 初始化"取消"按钮
	 * @param title 按钮标题
	 * @param listener 按钮监听
	 */
	public void setUpCancelBtn(CharSequence title,android.view.View.OnClickListener listener) {
		if (listener==null&&btn_cancel!=null) {
			btn_cancel.setOnClickListener(this);
		}else {
			btn_cancel.setOnClickListener(listener);
		}
		if (btn_cancel!=null&&!TextUtils.isEmpty(title)) {
			btn_cancel.setText(title);
		}
		isfinishcancelbtn=true;
	}

	/**<h3>
	 * <pre>显示对话框,在调用该方法前必须先调用以下方法初始化对话框，否则不会响应。
	 * setMessageView(xxx)
	 * setUpOkBtn(CharSequence title, android.view.View.OnClickListener listener)
	 * setUpCancelBtn(CharSequence title,android.view.View.OnClickListener listener)
	 * </pre></h3>
	 */
	public void show() {
		if (dialog==null||!(isfinishmessageview&&isfinishcancelbtn&&isfinishokbtn)) {
			return;
		}
		status=dialog.isShowing();
		if (!status||ishide) {
			dialog.show();
			status=true;
			ishide=false;
			WindowManager.LayoutParams lp = dialog.getWindow()
					.getAttributes();
					lp.width = Configure.screenWidth; // 设置宽度
					dialog.getWindow().setAttributes(lp);
		}
		
	}

	/**
	 * 隐藏对话框，Hide the dialog, but do not dismiss it.
	 */
	public void hide() {
		if (status&&!ishide) {
			dialog.hide();
			ishide=true;
		}
	}

	 /**
     * Dismiss this dialog, removing it from the screen. This method can be
     * invoked safely from any thread.  Note that you should not override this
     * method to do cleanup when the dialog is dismissed, instead implement
     * that in {@link #onStop}.
     */
	public void dismiss() {
		if (status) {
			dialog.dismiss();
			status=false;
		}
	}

	/**
     * Cancel the dialog.  This is essentially the same as calling {@link #dismiss()}, but it will
     * also call your {@link DialogInterface.OnCancelListener} (if registered).
     */
	public void cancel() {
		if (status) {
			dialog.cancel();
			status=false;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_dialog_btn_ok:
			dismiss();
			break;
		case R.id.txt_dialog_btn_cancel:
			dismiss();
			break;
		default:
			break;
		}
	}
}
