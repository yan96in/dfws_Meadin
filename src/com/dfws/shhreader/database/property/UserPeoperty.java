package com.dfws.shhreader.database.property;

/**
 * <h2>用户表属性</h2>
 * <pre>记录登录用户详细信息</pre>
 * @author Eilin.Yang
 * @since 2013-10-21
 * @version v1.0
 */
public class UserPeoperty {

	/**用户数据库*/
	public static final String DB_NAME="users.db";
	/**表名称*/
	public static final String TABLE="user_table";
	/**行ID*/
	public static final String RAW_ID="raw_id";
	/**用户ID*/
	public static final String ID="user_id";
	/**用户名*/
	public static final String NAME="user_name";
	/**用户性别0:女，1：男，2：未知*/
	public static final String GENDER="gender";
	/**登录地点*/
	public static final String GEO_LOCATION="location";
	/**登录状态。0：未登录，1：已登录*/
	public static final String STATUS="status";
	/**登录时间*/
	public static final String LOGIN_TIME="login_time";
	/**登出时间*/
	public static final String LOGOUT_TIME="logout_time";
	/**IP地址*/
	public static final String IP="ip";
	/**记录更新时间*/
	public static final String UPDATE_TIME="update_time";
	/**创建表格*/
	public static final String CREATE_TABLE="create table if not exists "
			+TABLE+"("
			+RAW_ID+" integer primary key autoincrement, "
			+ID+" text, "
			+NAME+" text, "
			+GENDER+" integer, "
			+GEO_LOCATION+" text, "
			+STATUS+" integer, "
			+IP+" text, "
			+LOGIN_TIME+" text, "
			+LOGOUT_TIME+" text, " 
			+UPDATE_TIME+" text )";
	
	/**查询指定ID的用户*/
	public static final String RAW_QUERY="select * from "+TABLE+" where "+ID+" = ? ";
}
