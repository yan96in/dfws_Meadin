package com.dfws.shhreader.configures;

/*
 * 保存常用常量
 */
public final class AppConstants {
	
	public static final int M_SUCCESS = 1;//成功 
	public static final int M_TIMEOUT = 2; //超时
	public static final int M_PROGRAM_ERROR = 3;//程序出错 
	public static final int M_OTHER_ERROR = 4;//其他错误 
	public static boolean iscancel=false;//是否注销
	public static final int PAGESIZE = 20;//每页显示课程数
	public static final int FLIP_PAGESIZE = 5;//每页显示课程数
	public static boolean isread=false;//消息是否已经查看
	public static boolean readover=false;//查看完毕
	public static int current_position=0;//消息列表当前位置
	
	public static double lon=0;
	public static double lat=0;
	
	//订阅类目id
	public static String TypeId="typeid";
	/**
	 * SD卡根目录
	 */
	public static String MEADIN_READING="meadin_reading";
	/**
	 * 当前选中的模块ID
	 */
	public static int dTypeId=1;
	/**
	 * 是否还有下一页
	 */
	public static boolean dHasMore=true; 
	/**
	 * 当前阅读的文章ID
	 */
	public static int dReadingId=0;
	/**
	 * 当前阅读页所在页码
	 */
	public static int dCurPageIndex=0; 
	/**
	 * 当前阅读文章所在列表的位置
	 */
	public static int dReadingLocation=0;

	/**文章名称*/
	public static final String ARTICLE_NAME="article";
	/**
	 * 本地图片存储路径
	 */
	public static String imgPath="";

	
	/**
	 * 文章图片的存储路径
	 */
	public static final String cachePicPath="/mnt/sdcard/meadin_reading/pic/";
	/**首页背景保存路径*/
	public static final String cachePicPath_home_bg="/mnt/sdcard/meadin_reading/pic/homebg/";
	/**读取路径*/
	public static final String reading_cachePicPath="file:///mnt/sdcard/meadin_reading/pic/";
	/**模块图标*/
	public static final String MODULE_LOGO_PATH="/mnt/sdcard/meadin_reading/pic/modules/";
	/**文章地址*/
	public static final String article_cachePicPath="/mnt/sdcard/meadin_reading/article/";
	/**新浪微博地址*/
	public static final String SinaWeibo_cachePicPath="/mnt/sdcard/meadin_reading/sina/";
	/**腾讯微博地址*/
	public static final String TencentWeibo_cachePicPath="/mnt/sdcard/meadin_reading/tencent/";
	//地址集合
	//public static final String SERVICE_ADDRESS_V1="http://168.192.122.29:8090/v1/";
	//服务器上的地址
	public static final String SERVICE_ADDRESS_V1="http://social.interface.veryeast.cn/v1/";
	//服务器上的地址
	public static final String SERVICE_ADDRESS_V2="http://social.interface.veryeast.cn/v2/";
	//登录地址
	public static final String LOGIN_ADDRESS=SERVICE_ADDRESS_V2+"Login";
	//注册地址
	public static final String REGISTER_ADDRESS=SERVICE_ADDRESS_V2+"Register";
	//注销地址
	public static final String LOGIN_OUT_ADDRESS=SERVICE_ADDRESS_V2+"LogOut";
	//欢迎页图片地址
	public static final String WELCOME_PIC_ADDRESS=SERVICE_ADDRESS_V1+"img/home.jpg";
	//首页图片地址
	public static final String HOME_PIC_ADDRESS=SERVICE_ADDRESS_V1+"img/home.jpg";
	//获取版本
	//public static final String GET_VERSION_ADDRESS=SERVICE_ADDRESS_V1+"getversion";
	
	//获取版本
	public static final String GET_VERSION_ADDRESS=SERVICE_ADDRESS_V2+"getversion";
	//获取订阅类型
	//public static final String GET_SUBSCRIBETYPE_ADDRESS=SERVICE_ADDRESS_V1+"getsubscibetype";
	
	public static final String GET_SUBSCRIBETYPE_ADDRESS=SERVICE_ADDRESS_V2+"getsubscibetype";
	//获取甩甩列表
	public static final String GET_THROW_LIST_ADDRESS=SERVICE_ADDRESS_V2+"getThrowList";
	//获取阅读列表
	public static final String GET_READITEM_LIST_ADDRESS=SERVICE_ADDRESS_V2+"getreadingitemlist";
	//获取阅读列表
	public static final String GET_READITEM_LIST_PAGE_ADDRESS=SERVICE_ADDRESS_V2+"getreadingitemlist";
	//获取阅读详情
	public static final String GET_READITEM_DETAIL_ADDRESS=SERVICE_ADDRESS_V2+"getreadingitemdetail";
	//获取我的订阅
	public static final String GET_MY_SUBSCRIBETYPE_ADDRESS=SERVICE_ADDRESS_V2+"getMySubscibeTypeList";
	//设置我的订阅
	public static final String SET_MY_SUBSCRIBE_ADDRESS=SERVICE_ADDRESS_V2+"setmysubscibe";
	//添加我的收藏
	public static final String ADD_MY_FAVORITE_ADDRESS=SERVICE_ADDRESS_V2+"addmyfavorite";
	//获取我的收藏
	public static final String GET_MY_FAVORITE_LIST_ADDRESS=SERVICE_ADDRESS_V2+"getmyfavoritelist";
	//删除我的收藏
	public static final String DELETE_MY_FAVORITE_ADDRESS=SERVICE_ADDRESS_V2+"deletemyfavorite";
	//甩一甩方法
	public static final String Throw_ADDRESS=SERVICE_ADDRESS_V2+"shareThrow";
	//分享
	public static final String FeedBack_ADDRESS=SERVICE_ADDRESS_V2+"userfeedback";
	//设置不被甩到
	public static final String SetThrow_ADDRESS=SERVICE_ADDRESS_V2+"setThrow";
	//更新我的地理位置
	public static final String updateMyLocation_ADDRESS=SERVICE_ADDRESS_V2+"updateMyLocation";
	//获取我的ing月类型的更新内容数
	public static final String GET_MYSUBSCIBENEWNUMS_ADDRESS=SERVICE_ADDRESS_V2+"getmysubscibenewnums";
	//获取我的订阅类型的更新内容数
	public static final String GET_MODULE_NEWS_SUM_ADDRESS=SERVICE_ADDRESS_V2+"getMySubscibeNewNumsForAndroid";
}
