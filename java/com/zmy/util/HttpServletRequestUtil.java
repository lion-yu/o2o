package com.zmy.util;

import javax.servlet.http.HttpServletRequest;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年3月5日下午4:29:27
*Class Description： 
*/
public class HttpServletRequestUtil {
	public static int getInt(HttpServletRequest request,String key) {
		try {
			return Integer.decode(request.getParameter(key));
		}catch (Exception e) {
			return -1;
		}
	}
	public static Long getLong(HttpServletRequest request,String key) {
		try {
			return Long.valueOf(request.getParameter(key));
		}catch (Exception e) {
			return -1l;
		}
	}
	
	public static Float getFloat(HttpServletRequest request,String key) {
		try {
			return Float.parseFloat(request.getParameter(key));
		}catch (Exception e) {
			return -1f;
		}
	}
	
	public static Double getDouble(HttpServletRequest request,String key) {
		try {
			return Double.parseDouble(request.getParameter(key));
		}catch (Exception e) {
			return -1d;
		}
	}
	
	public static Boolean getBoolean(HttpServletRequest request,String key) {
		try {
			return Boolean.valueOf(request.getParameter(key));
		}catch (Exception e) {
			return false;
		}
	}
	
	public static String getString(HttpServletRequest request,String key) {
		try {
			String result = request.getParameter(key);
			if(request != null) {
				result = result.trim();
			}
			if("".equals(key)){
				result = null;
			}
			return result;
		}catch (Exception e) {
			return null;
		}
	}
}
