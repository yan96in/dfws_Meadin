package com.dfws.shhreader.utils;

import java.security.MessageDigest;
/**
 * MD5操作运算
 * @author Eilin.Yang
 * @since 2013-6-26
 * @version v2.0
 */
public class MD5Utils extends StringUtils{

	/**
	 * MD5加码32位
	 * @param inStr
	 * @return 32位
	 */
	public static String MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (null==inStr) {
			return inStr;
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}
	
	/**
	 * MD5加码16位
	 * @param inStr 字符
	 * @param isfront 是否前16位,true:前16位，false：后16位
	 * @return
	 */
	public static String MD5_16(String inStr,boolean isfront) {
		String encrypt=MD5(inStr);
		if (null==encrypt||encrypt.length()==0) {
			return null;
		}
		if (!isfront) {
			return encrypt.substring(16);
		}
		return encrypt.substring(0, 16);
	}

	/**
	 * 可逆的加密算法
	 * @param inStr
	 * @param key 密钥
	 * @return
	 */
	public static String encoding(String inStr,char key) {
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ key);
		}
		return new String(a);
	}

	/**
	 * 加密后解密 
	 * @param inStr
	 * @param key 密钥
	 * @return
	 */
	public static String decoding(String inStr,char key) {
		return encoding(inStr, key);
	}

}
