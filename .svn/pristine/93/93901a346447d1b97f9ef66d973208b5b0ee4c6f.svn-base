package com.dfws.shhreader.activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.dfws.shhreader.R;
import com.dfws.shhreader.activity.personalcenter.BaseActivity;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.ConstantsAPI;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.ShowMessageFromWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXAppExtendObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
/**
 * 微信 
 * @author Administrator
 * 本类主要作用：微信resp,req返回信息处理
 * @since 2013-10-23
 */
public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
	private IWXAPI api;
	
	public static final String STitle = "showmsg_title";
	public static final String SMessage = "showmsg_message";
	public static final String BAThumbData = "showmsg_thumb_data";
	                                                 
	public  static final String APP_ID="wx967daebe835fbeac";
	
	/**
	 * 分享之后跳转到这个里面处理返回数据
	 */
	public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api=WXAPIFactory.createWXAPI(this,APP_ID,false );
        api.handleIntent(getIntent(), this);        
	}
 
	  



	/**
	 * 创建新的意图
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}
	
	private void goToGetMsg() {

	}
	
	private void goToShowMsg(ShowMessageFromWX.Req showReq) {
		WXMediaMessage wxMsg = showReq.message;		
		WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;
		
		StringBuffer msg = new StringBuffer(); // 组织一个待显示的消息内容
		msg.append("description: ");
		msg.append(wxMsg.description);
		msg.append("\n");
		msg.append("extInfo: ");
		msg.append(obj.extInfo);
		msg.append("\n");
		msg.append("filePath: ");
		msg.append(obj.filePath);
		

	}
	/**
	 * 微信发送请求到第三方应用时，会回调到该方法
	 */
	@Override
	public void onReq(BaseReq req) {
		System.out.println("进入req===================================="+req);
		switch (req.getType()) {		
		case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
			goToGetMsg();	
			break;
		case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
			goToShowMsg((ShowMessageFromWX.Req) req);
			break;
		default:
			break;
		}
	}
    /**
     * 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
     */
	@Override
	public void onResp(BaseResp resp) {
		 int result = 0;		
			System.out.println("进入resp===================================="+resp);
			switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				result = R.string.share_errcode_success;
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				result = R.string.share_errcode_cancel;
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				result = R.string.share_errcode_deny;
				break;
			default:
				result = R.string.share_errcode_unknown;
				break;
			}			
			
			Toast.makeText(this, result, Toast.LENGTH_LONG).show();		
			this.finish();
	}

}
