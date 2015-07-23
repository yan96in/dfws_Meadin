package com.dfws.shhreader.database.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dfws.shhreader.activity.AppInstance;
import com.dfws.shhreader.configures.DatabaseConfigure;
import com.dfws.shhreader.database.CommonDatabase;
import com.dfws.shhreader.database.property.NewsProperty;
import com.dfws.shhreader.entity.News;
import com.dfws.shhreader.entity.User;
import com.dfws.shhreader.utils.DateTimeUtils;

/**
 * <h2>新闻收藏数据库操作</h2>
 * <pre>
 * 新闻收藏,取消收藏...
 * </pre>
 * @author Eilin.Yang
 * @since 2013-10-21
 * @version v1.0
 */
public class FavoriteDatabaseHelper implements IDatabaseHelper {
	
	private static final String TAG="FavoriteDatabaseHelper";
	private Context context;
	/**
	 * 数据库对象
	 */
	private SQLiteDatabase database;
	/**
	 * 创建数据库
	 */
	private CommonDatabase databaseBox;
	/**
	 * 当前用户
	 */
	private User user;
	public FavoriteDatabaseHelper(Context context) {
		this.context=context;
		databaseBox=new CommonDatabase(context, NewsProperty.DB_NAME, new String[]{NewsProperty.CREATE_FV_TABLE}, new String[]{NewsProperty.FV_TABLE});
		database=databaseBox.getWritableDatabase();
	}
	
//	@Override
//	public boolean open() {
//		// TODO Auto-generated method stub
//		if (DatabaseConfigure.NewsDatabaseStatus) {
//			Log.i(TAG, "FavoriteDatabase is been used");
//			return false;
//		}
//		databaseBox=new CommonDatabase(context, NewsProperty.DB_NAME, new String[]{NewsProperty.CREATE_FV_TABLE}, new String[]{NewsProperty.FV_TABLE});
//		database=databaseBox.getWritableDatabase();
//		DatabaseConfigure.NewsDatabaseStatus=true;
//		Log.i(TAG, "Favoritedatabase is opened");
//		return true;
//	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		if (database.isOpen())
		database.close();
//		DatabaseConfigure.NewsDatabaseStatus=false;
		Log.i(TAG, "Favoritedatabase is closed");
	}
	
	/***
	 * judge the favorite News is or not exist
	 * @param news_id news's id
	 * @return true：exist.false：not exist
	 */
	public boolean isNewsExist(int news_id) {
		boolean flag=false;
		Cursor cursor=database.query(
				NewsProperty.FV_TABLE, 
				null, 
				NewsProperty.NEWS_ID+" = "+news_id,
				null, 
				null, 
				null, 
				null);
		flag=(cursor==null ? false:cursor.moveToFirst());
		if (flag) {
			cursor.close();
		}
		return flag;
	}
	
	/***
	 * insert an favorite news to database
	 * @param news news instance
	 * @return true：success;false：not success
	 */
	public boolean insertFavoriteNews(News news){
		boolean flag=false;
		if (news!=null) {
			if (isNewsExist(news.getId())) {
				return false;
			}
			user=((AppInstance)context.getApplicationContext()).getUser();
			ContentValues values=new ContentValues();
			values.put(NewsProperty.NEWS_ID, news.getId());
			values.put(NewsProperty.NEWS_TITLE, news.getTitle());
			values.put(NewsProperty.STATUS,(news.isCollected() ? 1 : 0));		
			values.put(NewsProperty.USER_ID, (user==null ? "":user.getId()));
			values.put(NewsProperty.FAVORITE_TIME, news.getFvtime());
			values.put(NewsProperty.UPDATE_TIME,DateTimeUtils.getLongDateTime(true));
			long r= database.insert(NewsProperty.FV_TABLE, null, values);
			if (r!=-1) {
				flag=true;
			}
		}
		return flag;
	}
	
	/**
	 * insert a favorite news data into database
	 * @param news_id news's id
	 * @param title news's title
	 * @param status favorite status.0:unfavorite,1:favorite
	 * @param fvtime favorite time
	 * @return
	 */
	public boolean insertFavoriteNews(int news_id,String title,int status,String fvtime){
		boolean flag=false;
		if (news_id != -1) {
			user=((AppInstance)context.getApplicationContext()).getUser();
			ContentValues values=new ContentValues();
			values.put(NewsProperty.NEWS_ID, news_id);
			values.put(NewsProperty.NEWS_TITLE, title);
			values.put(NewsProperty.STATUS,status);		
			values.put(NewsProperty.USER_ID, (user==null ? "":user.getId()));
			values.put(NewsProperty.FAVORITE_TIME, fvtime);
			values.put(NewsProperty.UPDATE_TIME,DateTimeUtils.getLongDateTime(true));
			long r= database.insert(NewsProperty.FV_TABLE, null, values);
			if (r!=-1) {
				flag=true;
			}
		}
		return flag;
	}
	
	/**
	 * delete an favorite news
	 * @param news_id news'id
	 * @return delete status.
	 * the number of rows affected if a whereClause is passed in, 0 otherwise.
	 *  To remove all rows and get a count pass "1" as the whereClause.
	 */
	public  int removeFavoriteNew(int news_id) {
		return database.delete(NewsProperty.FV_TABLE, NewsProperty.NEWS_ID+" = ? ", new String[]{""+news_id});
	}
	
	/*** 
	* bulk insert module 
	* @param ids "id1,id2,....idn" 
	* @return true：success;false：not success 
	*/ 
	public boolean removeFavorites(String ids){ 
		boolean flag = false;
		String [] lids=ids.split(",");
		int n=lids.length;
		if (n>0) { 
			database.beginTransaction(); 
			for (int i = 0; i < n; i++) {
				int id=Integer.parseInt(lids[i].trim());
				removeFavoriteNew(id);
			}
			database.setTransactionSuccessful(); 
			flag=true; 
			database.endTransaction(); 
		} 
		Log.i(TAG, "insertModuleAll success"); 
		return flag; 
	} 

	
	/**
	 * delete all favorite news
	 * @return delete status. 
	 * the number of rows affected if a whereClause is passed in, 0 otherwise. 
	 * To remove all rows and get a count pass "1" as the whereClause.
	 */
	public int removeAllFavoriteNews(){
		return database.delete(NewsProperty.FV_TABLE, null, null);
	}

}
