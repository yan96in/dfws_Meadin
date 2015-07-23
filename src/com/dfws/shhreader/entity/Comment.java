package com.dfws.shhreader.entity;

/**
 * <h2>评论对象</h2>
 * <pre>对于新闻详细的评论</pre>
 * @author Eilin.Yang
 * @since 2013-9-23
 * @version v1.0
 */
public class Comment {

	/**
	 * 评论id
	 */
	private int id;
	/**
	 * 评论的字符串id
	 */
	private String idstr;
	/**
	 * 评论内容
	 */
	private String text;
	/**
	 * 评论来源
	 */
	private int newsId;
	/**
	 * 评论作者
	 */
	private User user;
	/**
	 * 评论的网络地址
	 */
	private String url;
	/**
	 * 是否是给评论的评论.true:是评论的评论，false：不是。
	 */
	private boolean isrecomment;
	/**
	 * 源评论.当isrecomment为false时，recomment为null
	 */
	private Comment recomment;
	/**
	 * 评论日期
	 */
	private String create_time;
	/**
	 * 用户名
	 */
	private String user_name;
	/**
	 * 用户ip
	 */
	private String user_ip;
	/**
	 * 被赞数
	 */
	private int support;
	
	
	@Override
	public String toString() {
		return "Comment [id=" + id + ", idstr=" + idstr + ", text=" + text
				+ ", newsId=" + newsId + ", user=" + user + ", url=" + url
				+ ", isrecomment=" + isrecomment + ", recomment=" + recomment
				+ ", create_time=" + create_time + ", user_name=" + user_name
				+ ", user_ip=" + user_ip + ", support=" + support + "]";
	}

	public Comment() {
		
	}

	public Comment(int id, String idstr, String text, int newsId, User user,
			String url, boolean isrecomment, Comment recomment,
			String create_time, String user_name, String user_ip,int support) {
		super();
		this.id = id;
		this.idstr = idstr;
		this.text = text;
		this.newsId = newsId;
		this.user = user;
		this.url = url;
		this.isrecomment = isrecomment;
		this.recomment = recomment;
		this.create_time = create_time;
		this.user_name = user_name;
		this.user_ip = user_ip;
		this.support=support;
	}

	
	public int getSupport() {
		return support;
	}

	public void setSupport(int support) {
		this.support = support;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_ip() {
		return user_ip;
	}

	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIdstr() {
		return idstr;
	}
	public void setIdstr(String idstr) {
		this.idstr = idstr;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getNewsId() {
		return newsId;
	}
	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isIsrecomment() {
		return isrecomment;
	}
	public void setIsrecomment(boolean isrecomment) {
		this.isrecomment = isrecomment;
	}
	public Comment getRecomment() {
		return recomment;
	}
	public void setRecomment(Comment recomment) {
		this.recomment = recomment;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
	
}
