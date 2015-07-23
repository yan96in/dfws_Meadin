package com.dfws.shhreader.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
/**
 * 
 * <h2>获取设备信息 <h2>
 * <pre> 设备操作</pre>
 * @author 东方网升Eilin.Yang
 * @since 2013-10-26
 * @version v1.0
 * @modify ""
 */
public class DeviceUtils {

	/**
	 * 
	 *<pre>获取ip地址</pre>
	 * @return
	 */
	public static String getIpAddress() {  
        String ipaddress="";          
        try {  
            for (Enumeration<NetworkInterface> en = NetworkInterface  
                    .getNetworkInterfaces(); en.hasMoreElements();) {  
                NetworkInterface intf = en.nextElement();  
                for (Enumeration<InetAddress> enumIpAddr = intf  
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {  
                    InetAddress inetAddress = enumIpAddr.nextElement();  
                    if (!inetAddress.isLoopbackAddress()) {  
                            ipaddress=ipaddress+";"+ inetAddress.getHostAddress().toString();  
                    }  
                }  
            }  
        } catch (SocketException ex) {  
        }  
        return ipaddress; 
    }
	/**
	 * 
	 *<pre>获取MAC地址</pre>
	 * @param context
	 * @return
	 */
	public static String getMacAddress(Context context) {   
	    WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
	    WifiInfo info = wifi.getConnectionInfo();   
	    return info.getMacAddress();   
	}
	
	/**
	 * 
	 *<pre>获取IMEI号</pre>
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {   
		TelephonyManager telephonyManager=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei=telephonyManager.getDeviceId();
	    return imei;   
	}
	
	/**
	 * 
	 *<pre>获取手机型号</pre>
	 * @param context
	 * @return
	 */
	public static String getMobleInfo() {   
	    return android.os.Build.MODEL;   
	}
	
	/**
	 * 
	 *<pre>获取系统版本</pre>
	 * @param context
	 * @return
	 */
	public static String getSystemVersion() {   
	    return android.os.Build.VERSION.RELEASE;   
	}
	
	/**
	 * 
	 *<pre>获取系统API版本</pre>
	 * @param context
	 * @return
	 */
	public static int getApiVersion() {   
	    return android.os.Build.VERSION.SDK_INT;   
	}
}
