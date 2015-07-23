package com.dfws.shhreader.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dfws.shhreader.R;
import com.dfws.shhreader.configures.Configure;
import com.dfws.shhreader.utils.StringUtils;

/**
 * <h2>赞对话框</h2>
 * 定制的对话框，包含动画、样式自定义
 * @author Eilin.Yang
 * @version v1.0
 * @since 2013-9-10
 */
public class SupportDialog {

	private Context context;
	private LayoutInflater inflater;
	/**
	 * 自定义对话框
	 */
	private Dialog dialog;
	/**顶*/
	private ImageView iv_comment_support;
	/**复制*/
	private ImageView iv_comment_copy;
	/**顶标签*/
	private TextView txt_comment_support_count;
	/**
	 * 显示状态，true：已显示，false：没有显示
	 */
	private boolean status=false;
	/**
	 * 是否隐藏了。true：已隐藏，false：显示
	 */
	private boolean ishide=false;
	private String copy_str="";
	
	public SupportDialog(Context context){
		this.context=context;
		this.inflater=LayoutInflater.from(context);
		init();
	}
	private void init(){
		dialog=new Dialog(context, R.style.support_dialog);
		dialog.setContentView(R.layout.layout_comment_support);
		iv_comment_support=(ImageView)dialog.findViewById(R.id.iv_comment_support);
		iv_comment_copy=(ImageView)dialog.findViewById(R.id.iv_comment_copy);
		txt_comment_support_count=(TextView)dialog.findViewById(R.id.txt_comment_support_count);
		Window window=dialog.getWindow();
		window.setGravity(Gravity.CENTER);
	}
	/**
	 * <pre>赞回调</pre>
	 * @param listener
	 */
	public void setOnSupportButtonListener(View.OnClickListener listener) {
		iv_comment_support.setOnClickListener(listener);
	}
	/**
	 * <pre>复制回调</pre>
	 * @param listener
	 */
	public void setOnCopyButtonListener(View.OnClickListener listener) {
		iv_comment_copy.setOnClickListener(listener);
	}

	/**
	 * <pre>设置赞总数</pre>
	 * @param support 赞总数
	 */
	public void setSupportCount(int support) {
		txt_comment_support_count.setText(support+"顶");
	}
	
	/**
	 * <pre>设置赞总数</pre>
	 * @param support 赞总数
	 */
	public void setCopyText(String txt) {
		copy_str=txt;
	}
	/**
	 * <pre>复制内容</pre>
	 *
	 */
	public void copyContent() {
		StringUtils.copy(context, copy_str);
	}
	
	/**
	 * <pre>设置赞是否可用</pre>
	 * @param enable 是否可用
	 */
	public void setSupportEnable(boolean enable) {
		iv_comment_support.setEnabled(enable);
	}

	/**
	 * <pre>获取dialog状态
	 * 是否显示</pre>
	 */
	public boolean getSupportDialogState() {
		return dialog.isShowing();
	}
	
	/**<h3>
	 * <pre>显示对话框,在调用该方法前必须先调用以下方法初始化对话框，否则不会响应。
	 * setMessageView(xxx)
	 * setUpOkBtn(CharSequence title, android.view.View.OnClickListener listener)
	 * setUpCancelBtn(CharSequence title,android.view.View.OnClickListener listener)
	 * </pre></h3>
	 */
	public void show() {
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
}
