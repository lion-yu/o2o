package com.zmy.util;

import java.security.MessageDigest;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月8日下午4:58:37
*Class Description： 对帐户密码进行加解密
*/
public class MD5 {
	/**
	 * 对传入的String进行md5加密
	 * @param s
	 * @return
	 */
	public static final String getMd5(String s) {
		//十六进制数组
		char hexDigits[] = {'5','0','5','6','2','9','6','2','5','q','b','l','e','s','s','5'};
		try {
			char[] str;
			byte[] strTemp = s.getBytes();
			//获取md5加密对象
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			//传入需要加密的目标数组
			mdTemp.update(strTemp);
			byte md[] = mdTemp.digest();
			int j = md.length;
			str = new char[j * 2];
			int k = 0;
			//将数组做位移
			for(int i =0; i<j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			//转换成String并返回
			return new String(str);
		}catch(Exception e) {
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(MD5.getMd5("110"));
		System.out.println("b595052q5b5520l629s22sl99565s0l2".length());
	}
	
}
