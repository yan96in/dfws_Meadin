package com.dfws.shhreader.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.dfws.shhreader.activity.image.ImageActivitys;
import com.dfws.shhreader.utils.StringUtils;
import com.igexin.sdk.Consts;

public class GexinSdkMsgReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		switch (bundle.getInt(Consts.CMD_ACTION)) {
		case Consts.GET_MSG_DATA:
			// 获取透传数据
			// String appid = bundle.getString("appid");
			byte[] payload = bundle.getByteArray("payload");
 			if (payload != null) {
				String data = new String(payload);
				int news_type=1;
				int news_id=1;
				if (!StringUtils.isEmpty(data)) {
					news_type=Integer.parseInt(data.substring(data.length()-1));
					news_id=Integer.parseInt(data.substring(0, data.length()-1));
				
				Boolean isAppRunning=false;
				isAppRunning=MainActivity.isResume;
				Intent intent1 = null;
				if (isAppRunning) {
					
					if (news_type==2) {
						intent1=new Intent(context, ImageActivitys.class);
					}else{
						intent1=new Intent(context, NewsDetailActivity.class);
						intent1.putExtra("type", news_type);
					}
					intent1.putExtra("from", 0);
					intent1.putExtra("news_id", news_id);
					intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent1);
				}
				else {
					intent1 = new Intent(context, MainActivity.class);
					intent1.putExtra("type", news_type);
					intent1.putExtra("from", 0);
					intent1.putExtra("news_id", news_id);
					intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent1);
				}
				}
			}
			break;
		case Consts.GET_CLIENTID:
			// 获取ClientID(CID)
			// 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
			String cid = bundle.getString("clientid");
			// 将该clientid和userid进行绑定。
			AppInstance.client_id = cid;
			break;

		case Consts.BIND_CELL_STATUS:
			String cell = bundle.getString("cell");
			break;
		default:
			break;
		}
	}
}
