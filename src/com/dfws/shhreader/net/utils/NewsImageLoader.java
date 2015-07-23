package com.dfws.shhreader.net.utils;

import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.http.client.ClientProtocolException;

import com.dfws.shhreader.utils.BitmapTools;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup.LayoutParams;

/**
 * 图片异步加载工具
 * @author Eilin.Yang
 *
 */
public class NewsImageLoader {
	private Context context;
	private boolean isLoop = true;
	private String tag="";
	//图片软引用
	private HashMap<String, SoftReference<Bitmap>> caches;
	
	private ArrayList<ImageLoadTask> taskQueue;

	private Thread thread = new Thread(){
		public void run() {
			while(isLoop){
				while(taskQueue.size()>0){
					try {
						ImageLoadTask task = taskQueue.remove(0);		
						byte[] bytes = HttpTools.getBytes(task.path, null, HttpTools.METHOD_GET);
						if ("Header".equals(tag)||"ImagePager".equals(tag)) {
							task.bitmap = BitmapTools.getBitmap(bytes, 217, 163);
						}else{
							task.bitmap = BitmapTools.getBitmap(bytes, 64, 48);
						}
						if(task.bitmap!=null){
							caches.put(task.path, new SoftReference<Bitmap>(task.bitmap));
							File dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
							if(dir!=null && !dir.exists()){
								dir.mkdirs();
							}
							File file = new File(dir,task.name);
							BitmapTools.saveBitmap(file.getAbsolutePath(), task.bitmap);
							Message msg = Message.obtain();
							msg.obj = task;
							handler.sendMessage(msg);
						}
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		};
	};
	private Handler handler = new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		ImageLoadTask task = (ImageLoadTask)msg.obj;
			task.callback.imageLoaded(task.path,task.name, task.bitmap);
    		
    	};
    };
	
	private class ImageLoadTask{
		String path;
		String name;
		Bitmap bitmap;
		Callback callback;
	}
	
	public interface Callback{
		void imageLoaded(String path,String names,Bitmap bitmap);
	}
	
	public void quit(){
		isLoop = false;
	}

	public NewsImageLoader(Context context){
		this.context = context;
		caches = new HashMap<String, SoftReference<Bitmap>>();
		taskQueue = new ArrayList<NewsImageLoader.ImageLoadTask>();
		thread.start();
	}

	public Bitmap loadImage(String tag, String path, String names, Callback callback){
		Bitmap bitmap = null;
		this.tag=tag;
		if(caches.containsKey(path)){
			bitmap = caches.get(path).get();

			if(bitmap==null)
				caches.remove(path);
			else
				return bitmap;
		}
		File dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);	
//		File file = new File(dir,path.substring(path.lastIndexOf("/")+1));
		File file = new File(dir,names);
		bitmap = BitmapTools.getBitmap(tag,file.getAbsolutePath());
		if(bitmap!=null)
			return bitmap;
		ImageLoadTask task = new ImageLoadTask();
		task.path = path;
		task.name=names;
		task.callback = callback;
		taskQueue.add(task);
		synchronized (thread) {
			thread.notify();
		}
		return null;
	}
}