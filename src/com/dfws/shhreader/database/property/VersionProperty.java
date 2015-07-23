package com.dfws.shhreader.database.property;
/**
 * <h2>版本控制属性</h2>
 * <pre>版本控制相关参数</pre>
 * @author Eilin.Yang
 * @since 2013-10-22
 * @version v1.0
 */
public class VersionProperty {

	/**1612606282
	 * int型文件序列号
	 */
	public int id;
	/**
	 * String型文件序列号
	 */
	public String idstr;
	/**
	 * 文件名
	 */
	public String name;
	/**
	 * 文件总大小
	 */
	public long size;
	/**
	 * 文件网络地址
	 */
	public String url;
	/**
	 * 本地路径
	 */
	public String path;
	/**
	 * 版本名称
	 */
	public String version;
	/**
	 * 版本号
	 */
	public int version_code;
	/**
	 * 更新信息
	 */
	public String update_info;
}
