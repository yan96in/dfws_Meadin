package com.dfws.shhreader.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.util.Log;
/**
 * the tools for date time
 * @author Eilin.Yang
 *
 */
public final class DateTimeUtils extends StringUtils{
	/**
	 * 获取完整的日期时间类型
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getLongDateTime(boolean hasFormat) {
		String format="yyyy-MM-dd HH:mm:ss";
		if (!hasFormat) {
			format="yyyyMMddHHmmss";
		}
		SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
		String date = sDateFormat.format(new java.util.Date()) + "";
		return date;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String getLongDateTime00(boolean hasFormat) {
		String format="yyyy-MM-dd HH:mm:ss";
		if (!hasFormat) {
			format="yyyyMMddHHmmss";
		}
		SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
		String date = sDateFormat.format(new java.util.Date()) + "";
		return (hasFormat?date.substring(0, 10)+" 00:00:00":date.substring(0, 8)+" 000000");
	}

	@SuppressLint("SimpleDateFormat")
	public static String getSimpleDateTime(boolean hasFormat) {
		String format="yyyy-MM-dd";
		if (!hasFormat) {
			format="yyyyMMdd";
		}
		SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
		String date = sDateFormat.format(new java.util.Date()) + "";
		return date;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String getPutDateTime(String date) {
		Log.i("getPutDateTime", "date:"+date);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sDateFormat = new SimpleDateFormat("MM月dd日 HH:mm");
		String dates="";
		try {
			dates = sDateFormat.format(df.parse(date));
			Log.i("getPutDateTime", "dates:"+dates);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dates;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String getWeiBoDateTime(String date) {
		Log.i("getPutDateTime", "date:"+date);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd HH:mm");
		String dates="";
		try {
			dates = sDateFormat.format(df.parse(date));
			Log.i("getPutDateTime", "dates:"+dates);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dates;
	}
	
	/*
	 * 从时间中获取日期
	 * date "yyyy-MM-dd HH:mm:ss"
	 * 返回 "yyyy-MM-dd"
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDateFromDatetime(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dates="";
		try {
			dates = sDateFormat.format(df.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dates;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static long getDiffer(String time) {
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 long l=0;
		 try {
			 String today=getLongDateTime(true);
			 java.util.Date now = df.parse(today);
			 java.util.Date date=df.parse(time);
			 l=now.getTime()-date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		  return l;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static long getDiffer00(String time) {
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 long l=0;
		 try {
			 String today=getLongDateTime00(true);
			 if (null!=time&&time.length()>0) {
				time=time.substring(0, 10)+" 00:00:00";
			 }
			 java.util.Date now = df.parse(today);
			 java.util.Date date=df.parse(time);
			 l=now.getTime()-date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		  return l;
	}
	
	public static String getPutDate(String date){
		if (date==null||date.length()==0) {
			return "";
		}
		long l=DateTimeUtils.getDiffer(date);
		long day=l/(24*60*60*1000);
		long hour=(l/(60*60*1000)-day*24);
		long min=((l/(60*1000))-day*24*60-hour*60);
		if (day>0) {
			return day+"天前";
		}else {
			if (hour>0) {
				return hour+"小时前";
			}else {
				if (min>0) {
					return min+"分钟前";
				}else {
					return "1分钟前";
				}
			}
		}
	}
	
	/**
	 * 时间格式转化
	 * @param date 完整日期格式 
	 * @return
	 */
	public static String parseDateTime(String date) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("MM月dd日 HH:mm");
		String dates="";
		Date d=new Date(date);
		dates = sDateFormat.format(d);
		return dates;
	}
	
	
}
