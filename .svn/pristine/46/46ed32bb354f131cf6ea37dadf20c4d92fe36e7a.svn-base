package com.dfws.shhreader.utils;

import java.io.File;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.dfws.shhreader.R;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.tencent.mm.sdk.platformtools.Util;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.UMShareMsg;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.UMSsoHandler;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.media.UMediaObject.MediaType;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.update.UmengUpdateAgent;

public class ThirdHelp {
	/**
	 * 友盟+微信 分享平台整合
	 */
	private static SocializeConfig mConfig;
	/**集成微信API*/
	public static IWXAPI api;
	public static UMSocialService controller;
	public static final String APP_ID = "wx351e7d7528e1a861";
	/**
	 * <pre>
	 * 注册微信
	 * </pre>
	 * 
	 * @param context
	 */
	public static void registerWEIXIN(Context context) {
		api = WXAPIFactory.createWXAPI(context, APP_ID, true);
		api.registerApp(APP_ID);
		controller = UMServiceFactory.getUMSocialService("com.meadin",
				RequestType.SOCIAL);
//		controller.getConfig().setSsoHandler(new SinaSsoHandler());
//        controller.getConfig().setSsoHandler(new TencentWBSsoHandler());
//        controller.getConfig().setSsoHandler(new QZoneSsoHandler((Activity)context));
	}
	
	/**
	 * 关闭sina微博SSO，QQ zone SSO，腾讯微博SSO，
	 * 
	 */
	public static void closeSSO(Context context){
		controller.getConfig().closeQQZoneSso();
		controller.getConfig().closeSinaSSo();
		controller.getConfig().closeTencentWBSso();
	}

	/**
	 * <pre>
	 * 注销微信
	 * </pre>
	 * 
	 * @param context
	 */
	public static void unregisterWEIXIN(Context context) {
		api = WXAPIFactory.createWXAPI(context, APP_ID, true);
		api.unregisterApp();
	}

	// 友盟更新
	public static void UmengUpdate(Context context) {
		UmengUpdateAgent.update(context);
	}

	// 友盟统计
	public static void UmengCensus(Context context, String name) {

	}

	// sina授权
	public static void sinaAut(final Context context) {
		controller.doOauthVerify(context, SHARE_MEDIA.SINA,
				new UMAuthListener() {
					@Override
					public void onError(SocializeException e,
							SHARE_MEDIA platform) {
					}

					@Override
					public void onComplete(Bundle value, SHARE_MEDIA platform) {
						if (value != null
								&& !TextUtils.isEmpty(value.getString("uid"))) {
							Toast.makeText(context, "授权成功.", Toast.LENGTH_SHORT)
									.show();
						} else {
							Toast.makeText(context, "授权失败", Toast.LENGTH_SHORT)
									.show();
						}
					}

					@Override
					public void onCancel(SHARE_MEDIA arg0) {
					}

					@Override
					public void onStart(SHARE_MEDIA arg0) {
					}
				});

	}

	// qq空间
	public static void qqAtu(final Context context) {
		controller.doOauthVerify(context, SHARE_MEDIA.QZONE,
				new UMAuthListener() {
					@Override
					public void onError(SocializeException e,
							SHARE_MEDIA platform) {
					}

					@Override
					public void onComplete(Bundle value, SHARE_MEDIA platform) {
						if (value != null
								&& !TextUtils.isEmpty(value.getString("uid"))) {
							Toast.makeText(context, "授权成功.", Toast.LENGTH_SHORT)
									.show();
						} else {
							Toast.makeText(context, "授权失败", Toast.LENGTH_SHORT)
									.show();
						}
					}

					@Override
					public void onCancel(SHARE_MEDIA arg0) {
					}

					@Override
					public void onStart(SHARE_MEDIA arg0) {
					}
				});
	}

	/**
	 * <pre>
	 * 微信分享
	 * </pre>
	 * 
	 * @param context
	 * @param tag_url
	 *            内容网络地址，微信图文分享必须设置一个url。
	 * @param content
	 *            分享内容
	 * @param toCircle
	 *            分享内容到朋友圈
	 */
	public static void share_Weixin(Context context, String tag_url,
			String content,boolean toCircle) {
		// 添加微信平台
		controller.getConfig().supportWXPlatform(context, APP_ID, tag_url);
		// 设置分享文字内容、图片内容
		controller.setShareContent(content);
		if (toCircle) {
			// 支持微信朋友圈
			controller.getConfig()
					.supportWXCirclePlatform(context, APP_ID, tag_url);
		}
		controller.directShare(context, SHARE_MEDIA.WEIXIN, new SnsPostListener() {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onComplete(SHARE_MEDIA arg0, int arg1, SocializeEntity arg2) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	/**
	 * <pre>
	 * 微信分享
	 * </pre>
	 * 
	 * @param context
	 * @param tag_url
	 *            内容网络地址，微信图文分享必须设置一个url。
	 * @param content
	 *            分享内容
	 * @param img_url
	 *            图片地址,网络地址或本地地址
	 * @param toCircle
	 *            分享内容到朋友圈
	 */
	@SuppressLint("DefaultLocale")
	public static void share_Weixin(Context context, String tag_url,String title,
			String content, String img_url,boolean toCircle) {

		controller.getConfig().supportWXPlatform(context, APP_ID, tag_url);
		UMImage mMedia = null;
		if (!StringUtils.isEmpty(img_url)) {
			img_url.toLowerCase(Locale.ENGLISH);
			if (img_url.startsWith("http://")) {
				mMedia = new UMImage(context, img_url);
			} else {
				mMedia = new UMImage(context, BitmapFactory.decodeFile(img_url));
			}
		}else {
			mMedia = new UMImage(context, BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));// 获取固定图片
		}
		mMedia.setTargetUrl(tag_url);
		if (toCircle) {
			// 支持微信朋友圈
			controller.getConfig()
					.supportWXCirclePlatform(context, APP_ID, tag_url);
		}
		sendByWX(context, tag_url, title, content, mMedia, toCircle);
	}

	/**
	 * <pre>
	 * 微信分享
	 * </pre>
	 * 
	 * @param context
	 * @param tag_url
	 *            内容网络地址，微信图文分享必须设置一个url。
	 * @param content
	 *            分享内容
	 * @param resid
	 *            图片资源
	 * @param toCircle
	 *            分享内容到朋友圈
	 */
	public static void share_Weixin(Context context, String tag_url,String title,
			String content, int resid,boolean toCircle) {

		if (resid != -1) {

			controller.getConfig().supportWXPlatform(context, APP_ID, tag_url);

			UMImage mMedia = new UMImage(context, resid);
			mMedia.setTitle("分享到微信");

			// 设置分享文字内容、图片内容
			mMedia.setTargetUrl(tag_url);
//			controller.setShareContent(content);
//			controller.setShareMedia(mMedia);
			
			if (toCircle) {
				// 支持微信朋友圈
				controller.getConfig()
						.supportWXCirclePlatform(context, APP_ID, tag_url);
			}
			sendByWX(context, tag_url, title, content, mMedia, toCircle);
		}
	}
	
	 /**
     * 
     * @param api
     * @param shareContent 分享的文字内容
     * @param shareImage 分享的图片（只支持非url形式的Image）
     * @param toCircle 是否分享到朋友圈
     * @return
     */
    public static boolean sendByWX(Context context,String tag_url,String title,String shareContent,UMediaObject shareImage,boolean toCircle) {
        UMShareMsg shareMsg = new UMShareMsg();//Umeng share message 用户统计
        
        WXWebpageObject webpage = new WXWebpageObject();
        //为什么需要填写url? 当前Demo使用的微信SDK不支持图文分享，使用图文分享必须转成URL分享，所以需要填写一个URL
        webpage.webpageUrl = tag_url; 
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = shareContent;
        shareMsg.mText = shareContent;

        if (shareImage != null) {
            
            if(shareImage.isUrlMedia()){
                android.util.Log.w(SocializeConstants.COMMON_TAG, "微信分享不支持非图片类型的媒体分享。");
            }else if(shareImage.getMediaType() != MediaType.IMAGE){
                android.util.Log.w(SocializeConstants.COMMON_TAG, "微信分享不支持非图片类型的媒体分享。");
            }else{
                byte[] b = shareImage.toByte();
                    if (b != null) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
                        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
                        bmp.recycle();
                        msg.thumbData = Util.bmpToByteArray(thumbBmp, true); // 设置缩略图
                        shareMsg.setMediaData(shareImage);
                    }
            }
        }

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "3474313bee15e71653fc339c65633554";
        req.message = msg;
        req.scene = toCircle ? SendMessageToWX.Req.WXSceneTimeline
                            : SendMessageToWX.Req.WXSceneSession;
        boolean sendReq = api.sendReq(req);
        
        //发送分享统计
        UMSocialService anaService = UMServiceFactory.getUMSocialService("微信分享统计", RequestType.ANALYTICS);
        anaService.postShareByCustomPlatform(context, null, toCircle?"wxtimeline":"wxsession", shareMsg, null);
        
        return sendReq;
    }

	/**
	 *  友盟集成 +自定义文字
	 * @param context
	 */
	public static void share(final Context context) {
//		controller.setConfig(mConfig);
		controller.getConfig().setSinaSsoHandler(new SinaSsoHandler());// 设置sina微博SSO
		controller.openShare((Activity) context, false);
		String content = CommonUtil.GetSmsContent(context);
		controller.setShareContent(content);
		UMImage shImage = new UMImage(context, R.drawable.ic_launcher);// 获取固定图片
		controller.setShareImage(shImage);
	}

	/**
	 * <pre>新浪微博分享</pre>
	 * @param context
	 * @param content 内容
	 * @param image_url 图片地址 没有则为null或“”。
	 */
	public static void share_Sina(final Context context,String content,String image_url) {
		
//		controller.setConfig(mConfig);
		controller.setShareContent(content);
		UMImage shImage=null;
		if (!StringUtils.isEmpty(image_url)) {
			if (image_url.startsWith("http://")) {
				shImage = new UMImage(context, image_url);// 获取网络图片
			}else {
				shImage = new UMImage(context, new File(image_url));// 获取固定图片
			}
		}else {
			shImage = new UMImage(context, BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));// 获取固定图片
		}
		controller.setShareImage(shImage);
		controller.postShare(context, SHARE_MEDIA.SINA, new SnsPostListener() {
			@Override
			public void onStart() {
				Toast.makeText(context, "开始分享.", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode,
					SocializeEntity entity) {
			}
		});
	}

	/**
	 * <pre>腾讯微博</pre>
	 * @param context
	 * @param content 内容
	 * @param image_url 图片地址 ，没有则为null或“”。
	 */
	public static void share_Tencent(final Context context,String content,String image_url) {
//		controller.setConfig(mConfig);
		controller.setShareContent(content);
		UMImage shImage=null;
		if (!StringUtils.isEmpty(image_url)) {
			if (image_url.startsWith("http://")) {
				shImage = new UMImage(context, image_url);// 获取网络图片
			}else {
				shImage = new UMImage(context, new File(image_url));// 获取固定图片
			}
		}else {
			shImage = new UMImage(context, BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));// 获取固定图片
		}
		controller.setShareImage(shImage);
		controller.postShare(context, SHARE_MEDIA.TENCENT,
				new SnsPostListener() {
					@Override
					public void onStart() {
						Toast.makeText(context, "开始分享", Toast.LENGTH_SHORT)
								.show();
					}

					@Override
					public void onComplete(SHARE_MEDIA platform, int eCode,
							SocializeEntity entity) {
					}
				});
	}

	/**
	 * <pre>QQ空间</pre>
	 * @param context 
	 * @param content 内容
	 * @param image_url 图片地址，没有则为null或“”。
	 */
	public static void share_Qzone(final Context context,String content,String image_path) {
//		controller.setConfig(mConfig);
		controller.setShareContent(content);
		UMImage shImage=null;
		if (!StringUtils.isEmpty(image_path)) {
			if (image_path.startsWith("http://")) {
				shImage = new UMImage(context, image_path);// 获取网络图片
			}else {
				shImage = new UMImage(context, new File(image_path));// 获取固定图片
			}
		}else {
			shImage = new UMImage(context, BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));// 获取固定图片
		}
		controller.setShareImage(shImage);
		controller.postShare(context, SHARE_MEDIA.QZONE, new SnsPostListener() {
			@Override
			public void onStart() {
				Toast.makeText(context, "开始分享", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode,
					SocializeEntity entity) {
			}
		});
	}

	/**
	 * <pre>邮件分享</pre>
	 * @param context 
	 * @param content 内容
	 * @param image_url 图片地址，没有则为null或“”。
	 */
	public static void share_Emile(final Context context,String content,String image_url) {
//		controller.setConfig(mConfig);
		controller.setShareContent(content);
		UMImage shImage=null;
		if (!StringUtils.isEmpty(image_url)) {
			if (image_url.startsWith("http://")) {
				shImage = new UMImage(context, image_url);// 获取网络图片
			}else {
				shImage = new UMImage(context, new File(image_url));// 获取固定图片
			}
		}else {
			shImage = new UMImage(context, BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));// 获取固定图片
		}
		controller.setShareImage(shImage);
		controller.shareEmail(context);
	}

	/**
	 * <pre>短信</pre>
	 * @param context
	 * @param content 内容
	 */
	public static void share_SMS(final Context context,String content) {
//		controller.setConfig(mConfig);
		controller.setShareContent(content);
		controller.setShareImage(null);
		controller.shareSms(context);
	}
	
	/**
	 * <pre>SSO回调,在activity调用</pre>
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	public static void onBack(int requestCode, int resultCode, Intent data) {
		 /**使用SSO授权必须添加如下代码 */
	    UMSsoHandler ssoHandler = controller.getConfig().getSsoHandler(requestCode);
	    if(ssoHandler != null&& requestCode == UMSsoHandler.DEFAULT_AUTH_ACTIVITY_CODE){
	       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
	    }
	}
}
