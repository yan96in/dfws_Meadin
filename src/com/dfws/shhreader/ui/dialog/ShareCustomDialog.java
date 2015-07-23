package com.dfws.shhreader.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dfws.shhreader.R;
import com.dfws.shhreader.configures.Configure;
import com.dfws.shhreader.configures.FrameConfigure;
/**
 * <h2>分享对话框</h2>
 * <pre>自定义社会化分享对话框</pre>
 * @author Eilin.Yang
 * @since 2013-9-9
 * @version v1.0
 */
public class ShareCustomDialog {

	/**
	 * 上下文
	 */
	private Context context;
	/**
	 * 社会化分享对话框
	 */
	private Dialog shareDialog;
	/**
	 * 显示状态，true：已显示，false：没有显示
	 */
	private boolean status=false;
	/**
	 * 是否隐藏了。true：已隐藏，false：显示
	 */
	private boolean ishide=false;
	/**新浪微博*/
	private TextView txt_share_sina;
	/**腾讯微博*/
	private TextView txt_share_tencent;
	/**QQ空间*/
	private TextView txt_share_qqzone;
	/**微信朋友*/
	private TextView txt_share_weixin;
	/**朋友圈*/
	private TextView txt_share_circle;
	/**短信*/
	private TextView txt_share_sms;
	/**邮件*/
	private TextView txt_share_emile;
	/**取消*/
	private TextView txt_share__cancel;
	
	public ShareCustomDialog(Context context){
		this.context=context;
		initView();
	}
	
	public void initView(){
		shareDialog=new Dialog(context, R.style.custom_dialog);
		if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
			shareDialog.setContentView(R.layout.night_layout_dialog_share);
		}else
		    shareDialog.setContentView(R.layout.layout_dialog_share);
		Window window=shareDialog.getWindow();
		window.setWindowAnimations(R.style.dialog_in_and_out);
		window.setGravity(Gravity.BOTTOM);
		txt_share_tencent=(TextView)shareDialog.findViewById(R.id.txt_share_tencent);
		txt_share_sina=(TextView)shareDialog.findViewById(R.id.txt_share_sina);
		txt_share_qqzone=(TextView)shareDialog.findViewById(R.id.txt_share_qqzone);
		txt_share_weixin=(TextView)shareDialog.findViewById(R.id.txt_share_weixin);
		txt_share_circle=(TextView)shareDialog.findViewById(R.id.txt_share_circle);
		txt_share_sms=(TextView)shareDialog.findViewById(R.id.txt_share_sms);
		txt_share_emile=(TextView)shareDialog.findViewById(R.id.txt_share_emile);
		txt_share__cancel=(TextView)shareDialog.findViewById(R.id.txt_share__cancel);

		txt_share__cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
	}

	public void show() {
		if (shareDialog==null) {
			return;
		}
		status=shareDialog.isShowing();
		if (!status||ishide) {
			shareDialog.show();
			status=true;
			ishide=false;
			WindowManager.LayoutParams lp = shareDialog.getWindow()
					.getAttributes();
					lp.width = Configure.screenWidth; // 设置宽度
					shareDialog.getWindow().setAttributes(lp);
		}
	}

	public void hide() {
		if (status&&!ishide) {
			shareDialog.hide();
			ishide=true;
		}
	}

	public void dismiss() {
		if (status) {
			shareDialog.dismiss();
			status=false;
		}
	}

	public void cancel() {
		if (status) {
			shareDialog.cancel();
			status=false;
		}
	}
	
	/**
	 * <pre>设置监听</pre>
	 * @param shareListener
	 */
	public void setOnclickListener(View.OnClickListener shareListener) {
		txt_share__cancel.setOnClickListener(shareListener);
		txt_share_circle.setOnClickListener(shareListener );
		txt_share_emile.setOnClickListener(shareListener);
		txt_share_qqzone.setOnClickListener(shareListener);
		txt_share_sina.setOnClickListener(shareListener);
		txt_share_sms.setOnClickListener(shareListener);
		txt_share_tencent.setOnClickListener(shareListener);
		txt_share_weixin.setOnClickListener(shareListener);
	}
}
