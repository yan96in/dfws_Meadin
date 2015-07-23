package com.dfws.shhreader.configures;
/**
 * <h2> 新闻相关的参数配置<h2>
 * <pre> </pre>
 * @author 东方网升Eilin.Yang
 * @since 2013-10-24
 * @version v1.0
 * @modify ""
 */
public class NewsConfigure {
	
	/**************通用属性***************/
	/**
	 * 普通新闻
	 */
	public static final int GENERAL_NEWS=1;
	/**
	 * 图片新闻
	 */
	public static final int IMAGE_NEWS=2;
	/**
	 * 新闻类型
	 */
	public static int news_type=GENERAL_NEWS;
	/**
	 * 热点栏目
	 */
	public static final int COLUMN_NEWS=1;
	/**
	 * 图片栏目
	 */
	public static final int COLUMN_IMAGE=2;
	/**
	 * 报告栏目
	 */
	public static final int COLUMN_REPORT=3;
	/**
	 * 人物栏目
	 */
	public static final int COLUMN_FIGURE=4;
	/**
	 * 新技术栏目
	 */
	public static final int COLUMN_TECHNIQUE=5;
	/**
	 * 默认新闻栏目
	 */
	public static int column_def=COLUMN_NEWS;
	


	/**************新闻栏目***************/
	/**
	 * 新闻栏目是否有下一页
	 */
	public static boolean list_news_has_more=true;
	/**
	 * 新闻栏目当前总数
	 */
	public static int list_news_count;
	/**
	 * 新闻栏目获取到当前新闻的最大id。在列表显示分页获取的时候用到。
	 */
	public static int list_news_max_id;
	/**
	 * 新闻栏目获取到当前新闻的 最小id。在列表显示分页获取的时候用到。
	 */
	public static int list_news_min_id;
	
	
	
	/**************图片栏目***************/
	/**
	 * 图片栏目是否有下一页
	 */
	public static boolean list_image_has_more=true;
	/**
	 * 图片栏目当前总数
	 */
	public static int list_image_count;
	/**
	 * 图片栏目获取到当前新闻的最大id。在列表显示分页获取的时候用到。
	 */
	public static int list_image_max_id;
	/**
	 * 图片栏目获取到当前新闻的 最小id。在列表显示分页获取的时候用到。
	 */
	public static int list_image_min_id;
	
	
	
	/**************报告栏目***************/
	/**
	 * 报告栏目是否有下一页
	 */
	public static boolean list_report_has_more=true;
	/**
	 * 报告栏目当前总数
	 */
	public static int list_report_count;
	/**
	 * 报告栏目获取到当前新闻的最大id。在列表显示分页获取的时候用到。
	 */
	public static int list_report_max_id;
	/**
	 * 报告栏目获取到当前新闻的 最小id。在列表显示分页获取的时候用到。
	 */
	public static int list_report_min_id;
	
	
	
	/**************人物栏目***************/
	/**
	 * 人物栏目是否有下一页
	 */
	public static boolean list_figure_has_more=true;
	/**
	 * 人物栏目当前总数
	 */
	public static int list_figure_count;
	/**
	 * 人物栏目获取到当前新闻的最大id。在列表显示分页获取的时候用到。
	 */
	public static int list_figure_max_id;
	/**
	 * 人物栏目获取到当前新闻的 最小id。在列表显示分页获取的时候用到。
	 */
	public static int list_figure_min_id;
	
	
	
	/**************新技术栏目***************/
	/**
	 * 新技术栏目是否有下一页
	 */
	public static boolean list_technique_has_more=true;
	/**
	 * 新技术栏目当前总数
	 */
	public static int list_technique_count;
	/**
	 * 新技术栏目获取到当前新闻的最大id。在列表显示分页获取的时候用到。
	 */
	public static int list_technique_max_id;
	/**
	 * 新技术栏目获取到当前新闻的 最小id。在列表显示分页获取的时候用到。
	 */
	public static int list_technique_min_id;
	
	
	
	/**************幻灯片栏目***************/
	/**
	 * 幻灯片栏目当前总数
	 */
	public static int list_slide_count;
	/**
	 * 幻灯片栏目获取到当前新闻的最大id。
	 */
	public static int list_slide_max_id;
	/**
	 * 幻灯片栏目获取到当前新闻的 最小id。
	 */
	public static int list_slide_min_id;
	
	
	/**************评论***************/
	/**
	 * 当前新闻评论总数
	 */
	public static int comments_count;
	/**
	 * 当前新闻评论是否还有下一页
	 */
	public static boolean comments_has_more;
	
}
