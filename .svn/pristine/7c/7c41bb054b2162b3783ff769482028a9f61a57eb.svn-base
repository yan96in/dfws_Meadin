package com.dfws.shhreader.activity;

import java.util.List;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.controllers.NewsController;
import com.dfws.shhreader.net.utils.ImageLoader;
import com.dfws.shhreader.slidingmenu.fragment.RightSets;
import com.dfws.shhreader.utils.FileAccess;
import com.dfws.shhreader.utils.MD5Utils;
/**
 * 
 * @file： DownloadFileService.java
 * @Page： com.dfws.shhreader.activity
 * @description：  个人中心下载
 * @since： 2013-11-27
 * @author： Administrator
 */
public class DownloadFileService extends Service {
	
    private Context mContext; //上下文
    private NewsController newsController;//控制器
    private List<String> images_url;//下载集合
    private int count_size=0;//总共多少
    private ImageLoader loader;//图片工具类
    private ImageLoader loaderThumb;//缩略图图片工具类
    private int getsize=0;//已下载总量
    private String lastSrc="";
    //刷新UI
    private Handler handMessage = new Handler()
    {
    	public void handleMessage(Message msg)
    	{
    		switch(msg.what)
    		{
    			case 0:
    				Toast.makeText(mContext, "服务器连接失败，请稍后再试！", Toast.LENGTH_SHORT).show();
    				break;
    			case 1:
    				Toast.makeText(mContext, "服务器端文件不存在，下载失败！", Toast.LENGTH_SHORT).show();
    				break;
    			case 100:
    				Log.i("MessageService", "case 100:"); 
    				sendMsgToAct(0, 0);
    				break;
    				//下载中
    			case 101:
    				Log.i("MessageService", "case 101:");
    				images_url=newsController.getImageUrList();
                	if (images_url!=null&&images_url.size()>0) {
                		loader=new ImageLoader(mContext);
						count_size=images_url.size();
						lastSrc=images_url.get(count_size-1).trim();
						getsize=0;
						sendMsgToAct(count_size, getsize);
						for (int i = 0; i < count_size; i++) {
							String path=images_url.get(i);
							String names=MD5Utils.MD5(path);
							Log.i("MessageService", "sendMsgToAct---path= " + path 
							    	+ " names= " + names); 
							//下载图片
							Bitmap bitmap=loader.loadImage("DownloadFileService", path, FrameConfigure.NORMAL_IMG_DRC, names, new ImageLoader.Callback() {								
								@Override
								public void imageLoaded(String path, String names, Bitmap bitmap) {
									// TODO Auto-generated method stub
									if (bitmap!=null) {
										getsize=getsize+1;
										sendMsgToAct(count_size, getsize);
										if (getsize==count_size) {
											loader.quit();
										}
									}
									if (lastSrc.equalsIgnoreCase(path)) {
										sendMsgToAct(count_size, count_size);
									}
								}
							});
							if(bitmap!=null){
								getsize=getsize+1;
								sendMsgToAct(count_size, getsize);
								if (getsize==count_size) {
									loader.quit();
								}
								if (lastSrc.equalsIgnoreCase(path)) {
									sendMsgToAct(count_size, count_size);
								}
							}
						}
					}
                	break;
              
    			case 108:
    				List<String> thumbs= newsController.getImageThumbUrList();
    				int tn=thumbs.size();
    				String lastThumb="";
    				String dir=getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    				if(thumbs!=null&&tn>0){
    					lastThumb=thumbs.get(tn-1).trim();
    					loaderThumb=new ImageLoader(mContext);
	    				for(String thum : thumbs){
	    					String names = MD5Utils.MD5(thum);
	    					loaderThumb.loadImage("DownloadFileService", thum, dir, names, new ImageLoader.Callback() {								
								@Override
								public void imageLoaded(String path, String names, Bitmap bitmap) {
									// TODO Auto-generated method stub
									
								}
							});
	    					if(lastThumb.equalsIgnoreCase(thum)){
	    		             handMessage.sendEmptyMessage(101);//开始下载缩略图
	    					}
	    				}
    				}
    				break;
    		}  
    	}
    };
    
    @Override  
    public void onCreate() 
    {  
        super.onCreate();  
        mContext=this;
        newsController=new NewsController(mContext);
    	start();
    }
    
    @Override  
    public IBinder onBind(Intent intent)
    {  
        // 返回自定义的DownloadBinder实例   
        return null;  
    }  
  
    @Override  
    public void onDestroy() 
    {  
        super.onDestroy();  
        if (loader!=null) {
        	loader.quit();
		}
    } 

    /** 
     * 开始下载
     */  
    public void start() 
    {  
        new Thread() 
        {  
            public void run() 
            {      
            	handMessage.sendEmptyMessage(100);
                //下载   
            	clearBuffer();
            	boolean flag=newsController.offLineDownLoad();
            	Log.i("MessageService", "flag= " + flag);
                if (flag==true) {
//					//下载没出错，就刷新handle,内容下载解析完成
                	handMessage.sendEmptyMessage(108);//开始下载缩略图
				}else {
					handMessage.sendEmptyMessage(0); 
				}
            };  
        }.start();
    }
    
    /**
	 * <pre>清除缓存</pre>
	 *
	 */
	private void clearBuffer(){
		boolean f1=FileAccess.DeleteFile(FrameConfigure.NORMAL_IMG_DRC);
	}
   
    /**
     * 传递到 RightSets
     * @param countsize  文件
     * @param partsize   内容
     * @param progress   进度条 
     */
    private void sendMsgToAct(int countsize, int getsize) { 
    	Log.i("MessageService", "sendMsgToAct-------countsize= " + countsize 
    	+ " getsize= " + getsize); 
    	Intent intent = new Intent();
    	intent.setAction("android.intent.action.DOLOAD_RECEIVER"); 
    	intent.putExtra("countsize", countsize); 
    	intent.putExtra("getsize", getsize);
    	sendBroadcast(intent); 
    }        
}
