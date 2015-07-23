package com.dfws.shhreader.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

/**
 * the tool for bitmap
 * 图片方法的封装
 * @author Eilin.Yang
 * 
 */

public class BitmapTools {
	private final static String TAG = "BitmapTools";

	private BitmapTools() {
	}

	/**
	 * 从输入流中获取图片
	 * 
	 * @param is
	 *            图片资源的输入流
	 * @return
	 */
	public static Bitmap getBitmap(InputStream is) {
		Log.i(TAG, "getBitmap(InputStream is)");
		return BitmapFactory.decodeStream(is);
	}

	/**
	 * 从输入流中获取图片
	 * 
	 * @param is
	 *            图片资源的输入流
	 * @param scale
	 *            图片大小
	 * @return
	 */
	public static Bitmap getBitmap(InputStream is, int scale) {
		Bitmap bitmap = null;
		Options opts = new Options();
		opts.inSampleSize = scale;
		bitmap = BitmapFactory.decodeStream(is, null, opts);
		Log.i(TAG, "getBitmap(InputStream is, int scale)");
		return bitmap;
	}

	/**
	 * 从字节流中获取图片信息
	 * 
	 * @param bytes
	 *            字节流
	 * @param width
	 *            设置图片的宽度
	 * @param height
	 *            设置图片的高度
	 * @return
	 */
	public static Bitmap getBitmapWithScale(String path, int width, int height) {
		if (path==null) {
			return null;
		}
		Bitmap bitmap = null;
		Options opts = new Options();
		opts.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeFile(path, opts);
		opts.inJustDecodeBounds = false;
		int scaleX = opts.outWidth / width;
		int scaleY = opts.outHeight / height;
		int scale = scaleX > scaleY ? scaleX : scaleY;
		opts.inSampleSize = scale;
		Log.i(TAG, "getBitmap() scale : " + scale);
		bitmap = BitmapFactory.decodeFile(path, opts);
		Log.i(TAG, "getBitmap(byte[] bytes, int width, int height)");
		return bitmap;
	}
	
	
	/**
	 * 从字节流中获取图片信息
	 * 
	 * @param bytes
	 *            字节流
	 * @param width
	 *            设置图片的宽度
	 * @param height
	 *            设置图片的高度
	 * @return
	 */
	public static Bitmap getBitmap(byte[] bytes, int width, int height) {
		if (bytes==null) {
			return null;
		}
		Bitmap bitmap = null;
		Options opts = new Options();
		opts.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
		opts.inJustDecodeBounds = false;
		int scaleX = opts.outWidth / width;
		int scaleY = opts.outHeight / height;
		int scale = scaleX > scaleY ? scaleX : scaleY;
		opts.inSampleSize = scale;
		Log.i(TAG, "getBitmap() scale : " + scale);
		bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
		Log.i(TAG, "getBitmap(byte[] bytes, int width, int height)");
		return bitmap;
	}

	/**
	 * 获取本地图片
	 * 
	 * @param path
	 *            本地路径
	 * @return
	 */
	public static Bitmap getBitmap(String tag, String path) {
		Bitmap bitmap = null;
		bitmap = BitmapFactory.decodeFile(path);
		Log.i(TAG, tag+">>getBitmap(String path)");
		return bitmap;
	}

	/**
	 * 保存图片
	 * 
	 * @param path
	 *            图片保存的位置
	 * @param bitmap
	 *            图片
	 * @throws IOException
	 */
	public static void saveBitmap(String path, Bitmap bitmap)
			throws IOException {
		if (path != null && bitmap != null) {
			File file = new File(path);
			if (!file.exists()) {
				Log.i(TAG, "!file.exists()");
				file.getParentFile().mkdirs();
				file.createNewFile();
			}

			OutputStream stream = new FileOutputStream(file);
			String name = file.getName();
			String end = name.substring(name.lastIndexOf(".") + 1);
			if ("png".equals(end)) {
				bitmap.compress(CompressFormat.PNG, 100, stream);
			} else {
				bitmap.compress(CompressFormat.JPEG, 100, stream);
			}
			Log.i(TAG, "saveBitmap(String path, Bitmap bitmap)");
		}
	}
	
	/**
	 * 该方法用于将一个矩形图片的边角钝化
	 * 
	 * @param bitmap
	 *            待修改的图片
	 * @param roundPx
	 *            边角的弧度
	 * @return 返回修改过边角的新图片
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}
	
	/**
	 * 该方法用于任意缩放指定大小的图片
	 * 
	 * @param bitmap
	 *            待修改的图片
	 * @param newWidth
	 *            新图片的宽度
	 * @param newHeight
	 *            新图片的高度
	 * @return 缩放后的新图片
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int newWidth, int newHeight) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidht = ((float) newWidth / width);
		float scaleHeight = ((float) newHeight / height);
		matrix.postScale(scaleWidht, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return newbmp;
	}
	
	/**
	 * 该方法用于生成图片的下方倒影效果
	 * 
	 * @param bitmap
	 *            代修改的图片
	 * @return 有倒影效果的新图片
	 */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
				width, height / 2, matrix, false);
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		//  Set the Transfer mode to be porter duff and destination in  
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		//  Draw a rectangle using the paint with our linear gradient  
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);
		return bitmapWithReflection;
	}
	
	/**
	 * 该方法用于将bitmap转换为字节数组，png格式质量为100的。
	 * 
	 * @param bm
	 *            待转化的bitmap
	 * @return 返回btmap的字节数组
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 该方法用于将字节数组转化为bitmap图
	 * 
	 * @param b
	 *            字节数组
	 * @return bitmap位图
	 */
	public static Bitmap Bytes2Bitmap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	/**
	 * Drawable转换成Bitmap
	 * @param drawable
	 * @return bitmap 位图
	 * */
	public static Bitmap drawableToBitmap(Drawable drawable) {

		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		// canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}
	
	/**
	 * 下载图片到本地
	 * 
	 * @param url
	 * @return
	 */
	public static synchronized boolean loadImage(String folder, String url,String suffix) {
		try {
			//注意url可能包含?的情况，需要在?前截断
			if(url.indexOf("?")>0){
				url=url.substring(0,url.indexOf("?"));
			}
			String fileName = MD5Utils.MD5(url);
			String encodeFileName = URLEncoder.encode(fileName);
			URL imageUrl = new URL(url.replace(fileName, encodeFileName));
			byte[] data = readInputStream((InputStream) imageUrl.openStream());
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				FileAccess.MakeDir(folder);
				String outFilename = folder + fileName+suffix;
				bitmap.compress(CompressFormat.PNG, 100, new FileOutputStream(
						outFilename));
			Log.i("downloadimg", "loadImage>>finish---"+url);
				return true;
		} catch (Exception e) {
			Log.e("downloadimg", "loadImage>>error---"+url+e.toString());
			return false;
		}
	}
	
	/**
	 * 下载图片到本地
	 * 
	 * @param url
	 * @return
	 */
	public static synchronized void loadImage(String folder, String url,LoadingListener listener) {
		try {
			//注意url可能包含?的情况，需要在?前截断
			if(url.indexOf("?")>0){
				url=url.substring(0,url.indexOf("?"));
			}
			String fileName = MD5Utils.MD5(url);
			String encodeFileName = URLEncoder.encode(fileName);
			URL imageUrl = new URL(url.replace(fileName, encodeFileName));
			byte[] data = readInputStream((InputStream) imageUrl.openStream());
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				FileAccess.MakeDir(folder);
				String outFilename = folder + fileName;
				bitmap.compress(CompressFormat.PNG, 100, new FileOutputStream(
						outFilename));
				listener.finish(outFilename);
		} catch (Exception e) {
			Log.e("downloadimg", "error---"+url+e.toString());
			listener.error(url+">>"+e.toString());
		}
	}
	
	/**
	 * 下载图片到本地
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap loadImage(String folder, String url) {
		try {
			//注意url可能包含?的情况，需要在?前截断
			if(url.indexOf("?")>0){
				url=url.substring(0,url.indexOf("?"));
			}
			String fileName = MD5Utils.MD5(url);
			String encodeFileName = URLEncoder.encode(fileName);
			URL imageUrl = new URL(url.replace(fileName, encodeFileName));
			byte[] data = readInputStream((InputStream) imageUrl.openStream());
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				FileAccess.MakeDir(folder);
				String outFilename = folder + fileName;
				bitmap.compress(CompressFormat.PNG, 100, new FileOutputStream(
						outFilename));
				return bitmap;
		} catch (Exception e) {
			Log.e("download img err", url+e.toString());
			return null;
		}
	}
	
	
	/**
	 * 下载图片到本地
	 * 
	 * @param url
	 * @return
	 */
	public static void loadImageAndStore(String folder, String url,boolean fullName) {
		try {
			//注意url可能包含?的情况，需要在?前截断
			if(url.indexOf("?")>0){
				url=url.substring(0,url.indexOf("?"));
			}
			String fileName = "";
			if (fullName) {
				fileName=url.substring(url.lastIndexOf("/") + 1);
			}else
				fileName=StringUtils.getPictureName(url);
			String encodeFileName = URLEncoder.encode(fileName);
			URL imageUrl = new URL(url.replace(fileName, encodeFileName));
			byte[] data = readInputStream((InputStream) imageUrl.openStream());
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				FileAccess.MakeDir(folder);
				String outFilename = folder + fileName;
				bitmap.compress(CompressFormat.PNG, 100, new FileOutputStream(
						outFilename));
		} catch (Exception e) {
			Log.e("download img err", url+e.toString());
		}
	}
	
	/**
	 * 下载图片到本地
	 * 
	 * @param url
	 * @return
	 */
	public static void loadImageAndStore(String folder, String url) {
		try {
			//注意url可能包含?的情况，需要在?前截断
			if(url.indexOf("?")>0){
				url=url.substring(0,url.indexOf("?"));
			}
			String fileName = StringUtils.getPictureName(url);
			
			String encodeFileName = URLEncoder.encode(fileName);
			URL imageUrl = new URL(url.replace(fileName, encodeFileName));
			byte[] data = readInputStream((InputStream) imageUrl.openStream());
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				FileAccess.MakeDir(folder);
				String outFilename = folder + fileName;
				bitmap.compress(CompressFormat.PNG, 100, new FileOutputStream(
						outFilename));
		} catch (Exception e) {
			Log.e("download img err", url+e.toString());
		}
	}
	
	/**
	 * 下载图片到本地
	 * 
	 * @param url
	 * @return
	 */
	public static void loadIconAndStore(String folder, String url,String name, boolean fullName) {
		try {
			//注意url可能包含?的情况，需要在?前截断
			if(url.indexOf("?")>0){
				url=url.substring(0,url.indexOf("?"));
			}
			String fileName = "";
			if (name!=null) {
				fileName=name;
			}else {
				if (fullName) {
					fileName=url.substring(url.lastIndexOf("/") + 1);
				}else
					fileName=StringUtils.getPictureName(url);
			}
			File icon=new File(folder + fileName);
			if (icon.exists()) {
				return;
			}
			String encodeFileName = URLEncoder.encode(fileName);
			URL imageUrl = new URL(url.replace(fileName, encodeFileName));
			byte[] data = readInputStream((InputStream) imageUrl.openStream());
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				FileAccess.MakeDir(folder);
				String outFilename = folder + fileName;
				bitmap.compress(CompressFormat.PNG, 100, new FileOutputStream(
						outFilename));
		} catch (Exception e) {
			Log.e("download img err", url+e.toString());
		}
	}
	
	/**
	 * 下载图片到本地
	 * 
	 * @param url
	 * @return
	 */
	public static Drawable loadImageFromUrlAndStore(String folder, String url,boolean fullName) {
		try {
			//注意url可能包含?的情况，需要在?前截断
			if(url.indexOf("?")>0){
				url=url.substring(0,url.indexOf("?"));
			}
			String fileName = "";
			if (fullName) {
				fileName=url.substring(url.lastIndexOf("/") + 1);
			}else
				fileName=StringUtils.getPictureName(url);
			String encodeFileName = URLEncoder.encode(fileName);
			URL imageUrl = new URL(url.replace(fileName, encodeFileName));
			byte[] data = readInputStream((InputStream) imageUrl.openStream());
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			String status = Environment.getExternalStorageState();
			if (status.equals(Environment.MEDIA_MOUNTED)) {
				FileAccess.MakeDir(folder);
				String outFilename = folder + fileName;
				bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(
						outFilename));
				Bitmap bitmapCompress = BitmapFactory.decodeFile(outFilename);
				Drawable drawable = new BitmapDrawable(bitmapCompress);
				return drawable;
			}
		} catch (Exception e) {
			Log.e("download_img_err", e.toString());
		}
		return null;
	}
	/**
	 * 读取输入流
	 */
	private static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}
	
	public interface LoadingListener{
		void finish(String path);
		void error(String errorinfo);
	}
}
