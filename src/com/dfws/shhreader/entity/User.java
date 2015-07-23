package com.dfws.shhreader.entity;

import java.io.Serializable;

/**
 * <h2>用户</h2>
 * @author Eilin.Yang
 * @since 2013-9-23
 * @version v1.0
 */
public final class User implements Serializable{

	/**
	 * 
	 */
	private static long serialVersionUID = -6910229058524300255L;

	/**
	 * 用户id
	 */
	private String id;
	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 性别//0:女，1：男，2：不确定
	 */
	private int gender;
	/**
	 * ip地址
	 */
	private String ip;
	/**
	 * 登录状态。true：已登录，false：未登录
	 */
	private boolean hasLogin=false;
	/**
	 * 位置信息
	 */
	private UserGEO geo;
	/**
	 * 头像地址
	 */
	private String icon;
	/**
	 * 注册时间
	 */
	private String regtime;
	/**
	 * 登录时间
	 */
	private String logintime;
	/**
	 * 登出时间
	 */
	private String logouttime;
	
	
	public User() {
	}
	public User(String id, String name, int gender, String ip,
			boolean hasLogin, UserGEO geo, String icon, String regtime,
			String logintime, String logouttime) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.ip = ip;
		this.hasLogin = hasLogin;
		this.geo = geo;
		this.icon = icon;
		this.regtime = regtime;
		this.logintime = logintime;
		this.logouttime = logouttime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public boolean isHasLogin() {
		return hasLogin;
	}
	public void setHasLogin(boolean hasLogin) {
		this.hasLogin = hasLogin;
	}
	public UserGEO getGeo() {
		return geo;
	}
	public void setGeo(UserGEO geo) {
		this.geo = geo;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getRegtime() {
		return regtime;
	}
	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}	
	public String getLogintime() {
		return logintime;
	}
	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}
	public String getLogouttime() {
		return logouttime;
	}
	public void setLogouttime(String logouttime) {
		this.logouttime = logouttime;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", gender=" + gender
				+ ", ip=" + ip + ", hasLogin=" + hasLogin + ", geo=" + geo
				+ ", icon=" + icon + ", regtime=" + regtime + ", logintime="
				+ logintime + ", logouttime=" + logouttime + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
