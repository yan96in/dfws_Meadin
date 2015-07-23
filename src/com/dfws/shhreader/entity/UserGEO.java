package com.dfws.shhreader.entity;
/**
 * 地理位置信息
 * @author Eilin.Yang
 * @since 2013-10-21
 * @version v1.0
 */
public class UserGEO {

	/**
	 * 经度坐标
	 */
	private String longitude;
	/**
	 * 维度坐标
	 */
	private String latitude;
	/**
	 * 所在城市的代码
	 */
	private String city;
	/**
	 * 所在省份的省份代码
	 */
	private String province;
	/**
	 * 所在城市的名称
	 */
	private String city_name;
	/**
	 * 所在省份的名称
	 */
	private String province_name;
	/**
	 * 所在的实际地址，可以为空
	 */
	private String address;
	/**
	 * 地址的欢愉拼音，不是所有情况都返回该字段
	 */
	private String pinyin;
	/**
	 * 更多信息，不是所有情况都返回该字段
	 */
	private String more;
	
	
	public UserGEO() {
	}
	public UserGEO(String longitude, String latitude, String city,
			String province, String city_name, String province_name,
			String address, String pinyin, String more) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.city = city;
		this.province = province;
		this.city_name = city_name;
		this.province_name = province_name;
		this.address = address;
		this.pinyin = pinyin;
		this.more = more;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getProvince_name() {
		return province_name;
	}
	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public String getMore() {
		return more;
	}
	public void setMore(String more) {
		this.more = more;
	}
	@Override
	public String toString() {
		return "Sina_GEO [longitude=" + longitude + ", latitude=" + latitude
				+ ", city=" + city + ", province=" + province + ", city_name="
				+ city_name + ", province_name=" + province_name + ", address="
				+ address + ", pinyin=" + pinyin + ", more=" + more + "]";
	}
	
	
}
