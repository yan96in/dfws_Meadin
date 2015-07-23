package com.dfws.shhreader.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.ClipData;

import android.content.Context;
import android.util.Log;

import com.dfws.shhreader.configures.Configure;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.entity.MediaInfo;

/**
 * The String tools
 * 
 * @author Eilin.Yang 2013-4-1
 */
public class StringUtils extends DeviceUtils{

	/**
	 * <pre>复制</pre>
	 * @param context
	 * @param txt
	 */
	@SuppressLint("NewApi")
	public static void copy(Context context,String txt) {
		int v=getApiVersion();
		if (v<11) {
			android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);  
			clipboardManager.setText(txt);
		}else {
			android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);  
			ClipData cd = ClipData.newPlainText("label", txt);
			clipboardManager.setPrimaryClip(cd);
		}
	}
	/**
	 * <pre>获取复制的数据</pre>
	 * @param context
	 * @return
	 */
	@SuppressLint("NewApi")
	public static String getCopyString(Context context) {
		int v=getApiVersion();
		if (v<11) {
			android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);  
			return clipboardManager.getText().toString();
		}else {
			android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);  
			return clipboardManager.getText().toString();
		}
	}
	/**
	 * filter "null" or null
	 * @param source input
	 * @return source or ""
	 */
	public static String stringFilter(String source) {
		source = (source == null ? "" : source);
		source = ("null".equals(source) ? "" : source);
		return source;
	}
	
	/**
	 * filter "" or null
	 * @param source
	 * @return
	 */
	public static boolean isNullOrEmpty(String source) {
		if (null==source) {
			return true;
		}else if(source.length()==0){
			return true;
		}else
		return false;
	}
	
	 /**
     * Returns true if the string is null or 0-length.
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
	public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }

	/**
     * Returns true if the JSONObject is null or 0-length.
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
	public static boolean isEmpty(JSONObject object) {
        if (object == null ||object==JSONObject.NULL|| object.length() == 0)
            return true;
        else
            return false;
    }
	
	/**
     * Returns true if the JSONArray is null or 0-length.
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
	public static boolean isEmpty(JSONArray object) {
        if (object == null ||object==JSONObject.NULL|| object.length() == 0)
            return true;
        else
            return false;
    }
	
	/**
	 * get the picture name from URL
	 * 
	 * @param url
	 *           the URI of picture
	 * @return name removed the extension
	 */
	public static String getPictureName(String url) {
		if (null != url) {
			String x = url.substring(url.lastIndexOf("/") + 1);
			if (x.contains(".")) {
				return x.substring(0, x.lastIndexOf("."));
			}
			return x;
		}
		return url;
	}

	/**
	 * get file name
	 * 
	 * @param url
	 *            file's URL
	 * @return 
	 */
	public static String getFileName(String url) {
		if (null != url) {
			return url.substring(url.lastIndexOf("/") + 1);
		}
		return url;
	}

	/**
	 * replace the tag of "<img/>" from HTML data
	 * 
	 * @param content
	 *            HTML data
	 * @param imgs_path
	 *            the local path of images
	 * @return has been replace HTML
	 * file:///android_asset/pic.jpg
	 */
	public static String replaceImgTag(int readingid, String content,
			String[] imgs_path) {
		if (imgs_path == null || content == null) {
			return null;
		}
		String regex = "<!--image#[0-9]+-->";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);
		if (matcher == null) {
			return null;
		}
		int n = 0;
		while (matcher.find()) {
			String targt = matcher.group();
			String re = "<img style=\"max-width:95%; box-shadow: 0px 0px 6px #000;\" src =\""
					+ "图片路径"
					+ readingid
					+ "/"
					+ imgs_path[n] + "\"/>";
			content = content.replace(targt, re);
			n++;
		}
		return content;
	}
	
	/**
	 * replace the tag of "<img/>" from HTML data
	 * 
	 * @param content
	 *            HTML data
	 * @param imgs_path
	 *            the local path of images
	 * @return has been replace HTML
	 * 
	 */
	public static String replaceImgToDefualt(int readingid, String content,
			String[] imgs_path) {
		if (imgs_path == null || content == null) {
			return null;
		}
		String regex = "<!--image#[0-9]+-->";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);
		if (matcher == null) {
			return null;
		}
		int n = 0;
		while (matcher.find()) {
			String targt = matcher.group();
			String re = "<p style=\"text-align:center; margin:0; border:1px solid #f00;\"><img style=\"width:95%; box-shadow: 0px 0px 6px #000;\" src =\"file:///android_asset/pic_load_def.png\"/></p>";
			content = content.replace(targt, re);
			n++;
		}
		return content;
	}
	
	/**
	 * replace the tag of "<img/>" from HTML data
	 * 
	 * @param content
	 *            HTML data
	 * @param imgs_path
	 *            the local path of images
	 * @return has been replace HTML
	 * 
	 */
	public static String replaceImgToDefualt(Context context,String content,
			List<MediaInfo> medias) {Log.i("downloadimg", "replaceImgToDefualt()>>------content2="+content);
		if (isEmpty(content) || medias == null||medias.size()==0) {
			return null;
		}
		String src="file:///android_asset/pic_load_def.png";
		if (FrameConfigure.loading_type==FrameConfigure.TYPE_IMG_SMART) {
			if (NetWorkUtils.isWifi(context)) {
				if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
					src="file:///android_asset/pic_load_def_n.png";
				}
			}else {
				src="file:///android_asset/djx.png";
				if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
					src="file:///android_asset/n_djx.png";
				}
			}
		}else {
			if (FrameConfigure.loading_type==FrameConfigure.TYPE_IMG_NULL) {
				src="file:///android_asset/djx.png";
				if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
					src="file:///android_asset/n_djx.png";
				}
			}else {
				 if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
					 src="file:///android_asset/pic_load_def_n.png";
				 }
			}
		}
		int n = medias.size();
		MediaInfo info=null;
		for (int i = 0; i < n; i++) {
			info=medias.get(i);
			if (info!=null) {
				String tag=info.getRef();
				String id=info.getId();
				String local_src=src;
				int w=info.getWidth();
				int h=info.getHeight();
				int imgw=(int)(Configure.screenWidth*0.95);
				int imgh=h;
				if (w>h) {
					imgh=imgw/(w/h);
				}
				String path=info.getPath();
				File file=new File(path);
				if (null!=file&&file.exists()) {
					local_src="file://"+path;
				}
				String re = "<img id=\""+id+"\" style=\"width:95%;height:"+imgh+";margin-left: auto; margin-right: auto;border:4px solid #fff;margin-top: 8px;\" src =\""+local_src+"\"/>";
				content = content.replace(tag, re);
			}
		}Log.i("downloadimg", "replaceImgToDefualt()>>------content1="+content);
		return content;
	}

	/**
	 * restoring the picture name with extension
	 * @param aName	picture name
	 * @param bName	extension
	 * @return	a full name
	 */
	public static String replaceLastNameForPic(String aName, String bName) {
		if (aName != null && bName != null && !"".equals(aName)
				&& !"".equals(bName)) {
			bName = aName.substring(0, aName.lastIndexOf(".") + 1) + bName;
		}
		return bName;
	}
	
	/**
	 * URLencoding an String CharSets
	 * @param str
	 * @param charsetName <li>ISO-8859-1
	 * <li>US-ASCII
	 * <li>UTF-16
	 * <li>UTF-16BE
	 * <li>UTF-16LE
	 * <li>UTF-8
	 * @return 
	 */
	public static String getURLEncode(String str, String charsetName){
		if (!isNullOrEmpty(str)) {
			try {
				return URLEncoder.encode(str, charsetName);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return str;
	}
	
	/**
	  * 功能：去掉所有的<*>标记,去除html标签
	  * 
	  * @param content
	  * @return
	  */
	 public static String removeTagFromText(String content) {
		  Pattern p = null;
		  Matcher m = null;
		  String value = null;
		  // 去掉<>标签
		  p = Pattern.compile("(<[^>]*>)");
		  m = p.matcher(content);
		  String temp = content;
		  while (m.find()) {
			   value = m.group(0);
			   temp = temp.replace(value, "");
		  }
		  // 去掉换行或回车符号
		  p = Pattern.compile("(/r+|/n+)");
		  m = p.matcher(temp);
		  while (m.find()) {
			   value = m.group(0);
			   temp = temp.replace(value, " ");
		  }
		  return temp;
	 }
	 
		/**
		 * 返回当前程序版本名称
		 */
		public static String getAppVersionName(Context context) {
			String versionName = "2.0.0";
			try {
				// Get the package info
				versionName = context.getPackageManager().getPackageInfo("com.dfws.shhreader", 0).versionName;
			} catch (Exception e) {
				return versionName;
			}
			return versionName;
		}
		/**
		 * 返回当前程序版本名称
		 */
		public static int getAppVersionNameInt(Context context) {
			int versionNameInt = 200;
			try {
				// Get the package info
				String versionName = context.getPackageManager().getPackageInfo("com.dfws.shhreader", 0).versionName;
				if (!isEmpty(versionName)&&versionName.contains(".")) {
					String [] a=versionName.split("\\.");
					String b="";
					for (int i = 0; i < a.length; i++) {
						b=b+a[i];
					}
					versionNameInt=Integer.parseInt(b);
				}
			} catch (Exception e) {
				return versionNameInt;
			}
			return versionNameInt;
		}
		
		/**
		 * 返回当前程序版本名称
		 */
		public static int parseArrayToInt(String str) {
			int versionNameInt = 200;
			try {
				// Get the package info
				if (!isEmpty(str)&&str.contains(".")) {
					String [] a=str.split("\\.");
					String b="";
					for (int i = 0; i < a.length; i++) {
						b=b+a[i];
					}
					versionNameInt=Integer.parseInt(b);
				}
			} catch (Exception e) {
				return versionNameInt;
			}
			return versionNameInt;
		}
}
