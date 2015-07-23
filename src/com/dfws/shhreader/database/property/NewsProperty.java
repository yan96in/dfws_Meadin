package com.dfws.shhreader.database.property;
/**
 * <h2>新闻数据库配置</h2>
 * @author Eilin.Yang
 * @since 2013-10-21
 * @version v1.0
 */
public class NewsProperty {

	/**新闻数据库*/
	public static final String DB_NAME="News.db";
	/**行ID*/
	public static final String RAW_ID="raw_id";
	
	/************************新闻阅读收藏状态*******************************/
	/**新闻收藏表名*/
	public static final String FV_TABLE="news_favorites";
	/**新闻ID*/
	public static final String NEWS_ID="news_id";
	/**新闻ID*/
	public static final String NEWS_TITLE="news_TITLE";
	/**文章收藏状态,0：未收藏；1:收藏*/
	public static final String STATUS="news_favorite";
	/**用户id*/
	public static final String USER_ID="user_id";
	/**新闻收藏时间*/
	public static final String FAVORITE_TIME="favorite_time";
	/**记录更新时间*/
	public static final String UPDATE_TIME="update_time";
	/**创建表格*/
	public static final String CREATE_FV_TABLE="create table if not exists "
			+FV_TABLE+"("
			+RAW_ID+" integer primary key autoincrement, "
			+NEWS_ID+" integer not null, "
			+NEWS_TITLE+" text, "
			+STATUS+" integer not null, "
			+USER_ID+" text, "
			+FAVORITE_TIME+" text, "
			+UPDATE_TIME+" text )";
	
	/**查询指定ID的新闻*/
	public static final String FV_RAW_QUERY="select * from "+FV_TABLE+" where "+NEWS_ID+" = ? ";
}
