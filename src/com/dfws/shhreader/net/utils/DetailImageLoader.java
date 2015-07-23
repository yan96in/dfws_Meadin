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
public class DetailImageLoader {
	private Context context;
	private boolean isLoop = true;
//图片软引用
//	private HashMap<String, SoftReference<Bitmap>> caches;
	
	private ArrayList<ImageLoadTask> taskQueue;

	private Thread thread = new Thread(){
		public void run() {
			while(isLoop){
				while(taskQueue.size()>0){
					try {
						ImageLoadTask task = taskQueue.remove(0);		
						byte[] bytes = HttpTools.getBytes(task.src, null, HttpTools.METHOD_GET);
						task.bitmap = BitmapTools.getBitmap(bytes, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
						if(task.bitmap!=null){
//							caches.put(task.src, new SoftReference<Bitmap>(task.bitmap));
							File dir = new File(task.folder);
							if(dir!=null && !dir.exists()){
								dir.mkdirs();
							}
							File file = new File(task.path);
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
			task.callback.imageLoaded(task.id,task.path, true);
    		
    	};
    };
	
	private class ImageLoadTask{
		String id;
		String src;
		String folder;
		String path;
		Bitmap bitmap;
		Callback callback;
	}
	
	public interface Callback{
		void imageLoaded(String src,String path,boolean flag);
	}
	
	public void quit(){
		isLoop = false;
	}

	public DetailImageLoader(Context context){
		this.context = context;
//		caches = new HashMap<String, SoftReference<Bitmap>>();
		taskQueue = new ArrayList<DetailImageLoader.ImageLoadTask>();
	}
	/**
	 * <pre>开启线程</pre>
	 *threa.start()
	 */
	public void start() {
		if (thread!=null) {
			thread.start();
		}
	}

	/**
	 * <pre>加载图片</pre>
	 * @param tag 标签，任意字符串
	 * @param src 网络地址
	 * @param folder 本地目录
	 * @param path 文件据对路径
	 * @param callback 回调
	 * @return
	 */
	public boolean loadImage(String tag,String id, String src,String folder, String path, Callback callback){
//		Bitmap bitmap = null;
//
//		if(caches.containsKey(src)){
//			bitmap = caches.get(src).get();
//
//			if(bitmap==null)
//				caches.remove(src);
//			else
//				return true;
//		}
//		File dir = new File(folder);
//		if(dir!=null && !dir.exists()){
//			dir.mkdirs();
//		}
		File file = new File(path);
//		bitmap = BitmapTools.getBitmap(tag, file.getAbsolutePath());
//		if(bitmap!=null)
		if (null!=file&&file.exists())
			return true;
		ImageLoadTask task = new ImageLoadTask();
		task.id = id;
		task.src = src;
		task.path=path;
		task.folder=folder;
		task.callback = callback;
		taskQueue.add(task);
		synchronized (thread) {
			thread.notify();
		}
		return false;
	}
}
