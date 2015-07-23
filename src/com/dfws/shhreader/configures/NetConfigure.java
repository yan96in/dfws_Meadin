package com.dfws.shhreader.configures;
/**
 * <h2>本地数据配置文件 <h2>
 * <pre> 保存本地配置文件信息</pre>
 * @author 东方网升Eilin.Yang
 * @since 2013-10-23
 * @version 1.0
 * @modify ""
 */
public class NetConfigure {

	/**<pre>
	 * 请求异常，返回异常代码.
	 * 
	 * 错误代码	错误信息	详细描述
	 *1001		服务不可用
	 *1002		参数错误
	 *1003		参数值类型不匹配
	 *2001		用户名已存在，注册失败
	 *2002		用户名不符合规则，注册失败
	 *2003		密码不符合规则，注册失败
	 *2004		用户名与密码不匹配，登录失败
	 *2101		用户登录票据pass_token过期
	 *2201		新闻栏目column不存在
	 *2202		新闻news_id不存在
	 *2203		评论内容不能为空
	 *2301		联系方式不合法（必须为手机号或邮箱）
	 *2302		反馈内容字数超出1000字上限
	 *2303		设备类型device_type不存在，1为iOS，2为Android</pre>
	 */
	public static String error_code;
	/**
	 * 错误信息
	 */
	public static String error_msg;
	
    //private static String main_uri="http://168.192.122.29:8092/";
	private static String main_uri="http://v2.social.interface.veryeast.cn/";
	/**
	 * 新闻列表接口地址
	 */
	public static String news_interface=main_uri+"news/list";
	/**
	 *新闻详细接口 
	 */
	public static String news_detail_interface=main_uri+"news/detail";
	/**
	 * 新闻推送接口地址
	 */
	public static String news_push_interface=main_uri+"news/push";
	/**
	 * 新闻收藏列表接口 
	 */
	public static String favorites_list_interface=main_uri+"favorites/list";
	/**
	 * 新闻收藏接口地址
	 */
	public static String favorites_create_interface=main_uri+"favorites/create";
	/**
	 * 删除新闻收藏接口 
	 */
	public static String favorites_destroy_interface=main_uri+"favorites/destroy";
	/**
	 * 评论列表接口地址
	 */
	public static String comments_list_interface=main_uri+"comments/list";
	/**
	 *发表评论接口 
	 */
	public static String comments_create_interface=main_uri+"comments/create";
	/**
	 * 评论赞接口地址
	 */
	public static String comments_support_interface=main_uri+"comments/support";
	/**
	 *用户登录接口 
	 */
	public static String user_login_interface=main_uri+"user/login";
	/**
	 * 登出接口地址
	 */
	public static String user_logout_interface=main_uri+"user/logout";
	/**
	 *注册接口 
	 */
	public static String user_regist_interface=main_uri+"user/regist";
	/**
	 * 反馈接口地址
	 */
	public static String common_feedback_interface=main_uri+"common/feedback";
	/**
	 * 推送设置接口 
	 */
	public static String common_pushSetting_interface=main_uri+"common/pushSetting";
	/**
	 * 检查更新接口 
	 */
	public static String common_checkVersion_interface=main_uri+"common/checkVersion";
}
