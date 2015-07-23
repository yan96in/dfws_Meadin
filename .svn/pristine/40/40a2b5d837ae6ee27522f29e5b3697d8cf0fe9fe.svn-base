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
package com.dfws.shhreader.net.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.controllers.NewsController;
import com.dfws.shhreader.utils.BitmapTools;
import com.dfws.shhreader.utils.FileAccess;
import com.dfws.shhreader.utils.MD5Utils;

/**<h2> <h2>
 * <pre> </pre>
 * @author 东方网升Eilin.Yang
 * @since 2013-12-13
 * @version 
 * @modify ""
 */
public class DownloadHander {

	private ExecutorService executorService;
	/**线程池默认大小*/
	private int THREAD_POOL_SIZE=2;
	private Context mContext; //上下文
    private NewsController newsController;//控制器
    private LoadListener listener;
    private List<String> images_url;//下载集合
    String thumb_dir="";
    private boolean isloading=false;
    private int getSize=0;
   
    
    public DownloadHander(Context context){
    	mContext=context;
    	newsController=new NewsController(context);
    	executorService=Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    	thumb_dir=context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    }
    
    public void loading(){
    	getSize=0;
    	executorService.submit(new LoadData());
    }
	
    /**
     * <pre>设置下载状态</pre>
     * @param state true:下载,false:取消
     */
    public void setLoadingState(boolean state){
    	isloading=state;
    }
    
    /**
     * <pre>获取下载状态</pre>
     * 
     */
    public boolean getLoadingState(){
    	return isloading;
    }
    
    class LoadData implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			isloading=true;
			listener.loadFile(0);//开始下载文件
			boolean flag=newsController.offLineDownLoad();
			if (flag) {
				listener.loadFile(2);
				images_url=newsController.getImageUrList();
				List<String> thumbs=newsController.getImageThumbUrList();
				images_url.addAll(thumbs);
				if (thumbs!=null&&thumbs.size()>0) {
					listener.loadThumb(0);
					String lastThumb=thumbs.get(thumbs.size()-1);
					File dir = new File(thumb_dir);
					if(dir!=null && !dir.exists()){
						dir.mkdirs();
					}
					for (String string : thumbs) {
						File file = new File(thumb_dir,MD5Utils.MD5(string));
						if(!file.exists()){
							BitmapTools.loadImage(thumb_dir, string,"");
						}
						listener.loadThumb(1);
						if (lastThumb.equalsIgnoreCase(string)) {
							listener.loadThumb(2);//缩略图下载完成
						}
					}
				}else {
					listener.loadThumb(-1);//缩略图下载失败
				}
				if (images_url!=null&&images_url.size()>0) {
					int n=images_url.size();
					listener.loadImage(0,n,0);
					String lastImage=images_url.get(n-1);
					File dir = new File(FrameConfigure.NORMAL_IMG_DRC);
					if(dir!=null && !dir.exists()){
						dir.mkdirs();
					}
					for (String src : images_url) {
						if (!isloading) {
							listener.loadImage(2, n, getSize);
							break;
						}
						File file = new File(FrameConfigure.NORMAL_IMG_DRC,MD5Utils.MD5(src));
						if (!file.exists()) {
							BitmapTools.loadImage(FrameConfigure.NORMAL_IMG_DRC, src,"");
						}
						getSize++;
						listener.loadImage(1, n, getSize);
						if (lastImage.equalsIgnoreCase(src)) {
							listener.loadImage(3, n, n);
						}
					}
				}else {
					isloading=false;
					listener.loadImage(-1,0,0);//图片下载失败
				}
			}else {
				listener.loadFile(-1);//文件下载失败
			}
		}
    	
    }
    
    /**
   	 * <pre>清除缓存</pre>
   	 *
   	 */
   	private void clearBuffer(){
   		boolean f1=FileAccess.DeleteFile(FrameConfigure.NORMAL_IMG_DRC);
   	}
   	
   	/**
   	 * <pre>设置下载监听</pre>
   	 * @param listener
   	 */
   	public void setLoadListener(LoadListener listener){
   		this.listener=listener;
   	}
   	
   	/**
   	 * <h2>下载监听 <h2>
   	 * <pre> </pre>
   	 * @author 东方网升Eilin.Yang
   	 * @since 2013-12-13
   	 * @version 
   	 * @modify ""
   	 */
   	public interface LoadListener{
   		/**
   		 * <pre>下载文件</pre>
   		 * @param state 0:开始下载,1：下载中，2：下载完成,-1:失败
   		 */
   		void loadFile(int state);
   		/**
   		 * <pre>下载缩略图</pre>
   		 * @param state 0:开始下载,1：下载中，2：下载完成，-1:失败
   		 */
   		void loadThumb(int state);
   		/**
   		 * <pre>下载图片</pre>
   		 * @param state 0:开始下载,1：下载中，3：下载完成，2:取消下载，-1:失败
   		 * @param count 总数
   		 * @param getSize 已下载数
   		 */
   		void loadImage(int state,int count,int getSize);
   	}
}
