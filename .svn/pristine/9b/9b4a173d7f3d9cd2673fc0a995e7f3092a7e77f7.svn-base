package com.dfws.shhreader.database.property;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.dfws.shhreader.configures.NetConfigure;
/**
 * <h2>本地配置文件模型 <h2>
 * <pre>读取properties文件 </pre>
 * @author 东方网升Eilin.Yang
 * @since 2013-10-23
 * @version v1.0
 * @modify ""
 */
public class ConfigureProperty {

	/**
	 * 配置文件
	 */
	private static Properties configure;
	/**
	 * 配置文件属性
	 */
	public static final String NEWS_LIST_URI="news_list";
	/**
	 *<pre>加载配置文件</pre>
	 * @return
	 */
	public static void loadConfigureProperties() {  
        Properties props = new Properties();
        InputStream in = ConfigureProperty.class.getResourceAsStream("/assets/configure.properties");
        try {  
            props.load(in);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        configure=props;
        NetConfigure.news_interface=configure.getProperty(NEWS_LIST_URI, "").trim();
        NetConfigure.news_detail_interface=configure.getProperty("news_detail", "").trim();
        NetConfigure.news_push_interface=configure.getProperty("news_push", "").trim();
        NetConfigure.favorites_list_interface=configure.getProperty("favorites_list", "").trim();
        NetConfigure.favorites_create_interface=configure.getProperty("favorites_create", "").trim();
        NetConfigure.favorites_destroy_interface=configure.getProperty("favorites_destroy", "").trim();
        NetConfigure.comments_list_interface=configure.getProperty("comments_list", "").trim();
        NetConfigure.comments_create_interface=configure.getProperty("comments_create", "").trim();
        NetConfigure.comments_support_interface=configure.getProperty("comments_support", "").trim();
        NetConfigure.user_login_interface=configure.getProperty("user_login", "").trim();
        NetConfigure.user_logout_interface=configure.getProperty("user_logout", "").trim();
        NetConfigure.user_regist_interface=configure.getProperty("user_regist", "").trim();
        NetConfigure.common_feedback_interface=configure.getProperty("common_feedback", "").trim();
        NetConfigure.common_pushSetting_interface=configure.getProperty("common_pushSetting", "").trim();
        NetConfigure.common_checkVersion_interface=configure.getProperty("common_checkVersion", "").trim();
    }
	
	/** 
	 *<pre>得到configure.properties配置文件中的所有配置属性</pre>
	 * @param key 配置文件的key值
	 * @param def 如果取不到返回默认值
	 * @return
	 */
    public static String getUri(String key,String def) {
		return configure.getProperty(key,def);
	}
}
