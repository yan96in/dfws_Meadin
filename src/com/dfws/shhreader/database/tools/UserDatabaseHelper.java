package com.dfws.shhreader.database.tools;

import java.util.ArrayList;
import java.util.List;
import com.dfws.shhreader.configures.DatabaseConfigure;
import com.dfws.shhreader.database.CommonDatabase;
import com.dfws.shhreader.database.property.UserPeoperty;
import com.dfws.shhreader.entity.User;
import com.dfws.shhreader.entity.UserGEO;
import com.dfws.shhreader.utils.DateTimeUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

/**
 * <h2>用户数据操作</h2>
 * <pre>用户信息管理</pre>
 * @author Eilin.Yang
 *2013-10-21
 */
public class UserDatabaseHelper implements IDatabaseHelper {

	private static final String TAG="UserDatabaseHelper";
	/**上下文*/
	private Context context;
	/**数据库对象*/
	private SQLiteDatabase database=null;
	/**创建数据库*/
	private CommonDatabase databaseBox=null;
	private boolean userDatabaseStatus;
	
	public UserDatabaseHelper(Context context) {
		this.context=context;
		databaseBox=new CommonDatabase(context,UserPeoperty.DB_NAME,new String[]{UserPeoperty.CREATE_TABLE},new String[]{UserPeoperty.TABLE});
		database=databaseBox.getWritableDatabase();
	}
	
//	@Override
//	public boolean open() {
//		// TODO Auto-generated method stub
////		if (userDatabaseStatus) {
////			Log.i(TAG, "UserDatabase is been used");
////			return false;
////		}
//////		databaseBox=new CommonDatabase(context,UserPeoperty.DB_NAME,new String[]{UserPeoperty.CREATE_TABLE},new String[]{UserPeoperty.TABLE});
//////		database=databaseBox.getWritableDatabase();
////		userDatabaseStatus=true;
////		Log.i(TAG, "userdatabase is openned");
//		return true;
//	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		if (database.isOpen()) {
			database.close();
		}
//		userDatabaseStatus=false;
		Log.i(TAG, "userdatabase is closed");
	}
	
	/***
	 * get all User from database
	 * @return the list of User or null.
	 */
	public List<User> getAllUsers(){
		ArrayList<User> list=null;
		Cursor cursor =database.query(UserPeoperty.TABLE, null, null, null, null, null, UserPeoperty.UPDATE_TIME+" DESC");
		if (cursor!=null) {
			if (cursor.moveToFirst()) {
				list=new ArrayList<User>();
				do{
					User user=new User();
					user.setId(cursor.getString(1));
					user.setName(cursor.getString(2));
					user.setGender(cursor.getInt(3));
					user.setGeo(new UserGEO(null, null, null, null, null, null, cursor.getString(4), null, null));
					user.setHasLogin((cursor.getInt(5)==1) ? true:false);
					user.setIp(cursor.getString(6));
					user.setLogintime(cursor.getString(7));
					user.setLogouttime(cursor.getString(8));
					list.add(user);
				}while (cursor.moveToNext());
			}
		}
		cursor.close();
		return list;
	}
	
	/***
	 * get the User through User id
	 * @param user_id  User ID
	 * @return user or null.
	 */
	public User getUser(String user_id){
		User user=null;
		Cursor cursor=database.rawQuery(UserPeoperty.RAW_QUERY, new String[]{user_id});
		if (cursor!=null) {
			if (cursor.moveToFirst()) {
				user=new User();
				user.setId(cursor.getString(1));
				user.setName(cursor.getString(2));
				user.setGender(cursor.getInt(3));
				user.setGeo(new UserGEO(null, null, null, null, null, null, cursor.getString(4), null, null));
				user.setHasLogin((cursor.getInt(5)==1) ? true:false);
				user.setIp(cursor.getString(6));
				user.setLogintime(cursor.getString(7));
				user.setLogouttime(cursor.getString(8));
			}
		}
		cursor.close();
		return user;
	}
	

	/***
	 * judge the user is or not exist
	 * @param user_id user ID
	 * @return true：exist。false：not exist
	 */
	public boolean isUserExist(String user_id) {
		boolean flag=false;
		if (!TextUtils.isEmpty(user_id)) {
			Cursor cursor=database.query(
					UserPeoperty.TABLE,
					null, 
					UserPeoperty.ID+" = "+user_id,
					null, 
					null, 
					null, 
					null);
			flag=(cursor==null ? false:cursor.moveToFirst());
			if (cursor!=null) {
				cursor.close();
			}
		}
		return flag;
	}
	
	/***
	 * insert an user to database
	 * @param content user instance
	 * @return true：success;false：not success
	 */
	public boolean insertUser(User user){
		boolean flag=false;
		if (user!=null) {
			if (isUserExist(user.getId())) {
				return false;
			}
			ContentValues values=new ContentValues();
			values.put(UserPeoperty.ID, user.getId());
			values.put(UserPeoperty.NAME, user.getName());
			values.put(UserPeoperty.GENDER,user.getGender());
			values.put(UserPeoperty.GEO_LOCATION, user.getGeo().getAddress());
			values.put(UserPeoperty.STATUS, (user.isHasLogin() ? 1:0));
			values.put(UserPeoperty.LOGIN_TIME,user.getLogintime());
			values.put(UserPeoperty.LOGOUT_TIME, user.getLogouttime());
			values.put(UserPeoperty.IP,user.getIp());
			values.put(UserPeoperty.UPDATE_TIME, DateTimeUtils.getLongDateTime(true));
			long r= database.insert(UserPeoperty.TABLE, null, values);
			if (r!=-1) {
				flag=true;
			}
		}
		return flag;
	}
	
	/***
	 * insert an user to database
	 * @param user user
	 * @param status login status.true:login,false:logout
	 * @return true：success;false：not success
	 */
	public boolean updateUser(User user,boolean status){
		boolean flag=false;
		if (user!=null) {
			if (!isUserExist(user.getId())) {
				return false;
			}
			ContentValues values=new ContentValues();
			values.put(UserPeoperty.ID, user.getId());
			values.put(UserPeoperty.NAME, user.getName());
			values.put(UserPeoperty.GEO_LOCATION, user.getGeo().getAddress());
			values.put(UserPeoperty.STATUS, (status ? 1:0));
			values.put((status ? UserPeoperty.LOGIN_TIME:UserPeoperty.LOGOUT_TIME),(status ? user.getLogintime():user.getLogouttime()));
			values.put(UserPeoperty.IP,user.getIp());
			values.put(UserPeoperty.UPDATE_TIME, DateTimeUtils.getLongDateTime(true));
			long r= database.update(UserPeoperty.TABLE, values, UserPeoperty.ID+" = ?", new String[]{user.getId()});
			if (r>0) {
				flag=true;
			}
		}
		return flag;
	}
	
	/**
	 * delete an User
	 * @param user_id user's id
	 * @return delete status.
	 * the number of rows affected if a whereClause is passed in, 0 otherwise.
	 *  To remove all rows and get a count pass "1" as the whereClause.
	 */
	public  int deleteUser(String user_id) {
		return database.delete(UserPeoperty.TABLE, UserPeoperty.ID+" = ? ", new String[]{user_id});
	}
	
	/**
	 * delete all User
	 * @return delete status. 
	 * the number of rows affected if a whereClause is passed in, 0 otherwise. 
	 * To remove all rows and get a count pass "1" as the whereClause.
	 */
	public int deleteAllUser(){
		return database.delete(UserPeoperty.TABLE, null, null);
	}
}
