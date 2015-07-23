package com.dfws.shhreader.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * <h3>基本数据库的创建</h3>
 * <pre>
 * 批量创建表
 * 继承自SQLiteOpenHelper
 * </pre>
 * @author Eilin.Yang
 * <br/>since 2013-7-4
 */
public class CommonDatabase extends SQLiteOpenHelper {
	private static final String TAG="CommonDatabase";
	/**
	 * 数据库名称
	 */
	private String DBName="";
	/**
	 * 创建表的语句
	 */
	private String []create_table_str=null;
	/**
	 * 表的名称
	 */
	private String [] tables=null;
	/**
	 * 数据库构造函数
	 * @param context 上下文
	 * @param dbName 数据库名称
	 * @param create 批量创建一系列表的语句
	 * @param tables 创建的这一系列表的名称
	 */
	public CommonDatabase(Context context,String dbName,String [] create,String [] tables){
		super(context, dbName, null, 1);
		this.DBName=dbName;
		this.create_table_str=create;
		this.tables=tables;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "create database");
		for (int i = 0; i < create_table_str.length; i++) {
			db.execSQL(create_table_str[i]);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG, "onUpgrade database");
		for (int i = 0; i < tables.length; i++) {
			db.execSQL("drop table if exists "+tables[i]);
		}
		onCreate(db);
	}
}
