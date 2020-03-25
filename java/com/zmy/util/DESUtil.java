package com.zmy.util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月7日下午9:09:20
*Class Description： 实现DES对称加密算法对配置信息的加解密功能
*/
@SuppressWarnings("restriction")
public class DESUtil {
	private static Key key;
	//设置密钥key
	private static String KEY_STR = "myKey";
	private static String CHARSETNAME = "UTF-8";
	private static String ALGORITHM = "DES";
	
	static {
		try {
			//生成DES算法对象
			KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
			//运用SHA1安全策略
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			//设置密钥种子
			secureRandom.setSeed(KEY_STR.getBytes());
			//初始化基于SHA1的算法对象
			generator.init(secureRandom);
			//生成密钥对象
			key = generator.generateKey();
			generator = null;
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 获取加密后的信息
	 * @param str
	 * @return
	 */
	public  static String getEnctryString(String str) {
		//基于BASE64编码，接收byte[]并转换成String
		BASE64Encoder base64encoder = new BASE64Encoder();
		try {
			//按UTF8编码
			byte[] bytes = str.getBytes(CHARSETNAME);
			//获取加密对象
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			//初始化密码信息
			cipher.init(Cipher.ENCRYPT_MODE, key);
			//加密
			byte[] doFinal = cipher.doFinal(bytes);
			//byte[]to encode好的String 并返回
			return base64encoder.encode(doFinal);
		}catch(Exception e) {
			//TODO:handle exception
			throw new RuntimeException(e);
		}
	}
	/**
	 * 获取解密之后的信息
	 * @param str
	 * @return
	 */
	public static String getDecrypString(String str) {
		//基于BASE64编码，接收byte[]并转换成String
		BASE64Decoder base64decoder = new BASE64Decoder();
		try {
			//将字符串decode成byte[]
			byte[] bytes = base64decoder.decodeBuffer(str);
			//获取解密对象
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			//初始化解密信息
			cipher.init(Cipher.DECRYPT_MODE, key);
			//解密
			byte[] doFinal = cipher.doFinal(bytes);
			//返回解密之后的信息
			return new String(doFinal,CHARSETNAME);
		}catch(Exception e) {
			//TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	public static void main(String[] args) {
		
		System.out.println(getEnctryString("work"));
		System.out.println(getEnctryString("root"));
		System.out.println(getEnctryString("zmy816816"));
		System.out.println(getEnctryString("816816"));
		System.out.println(getEnctryString("wxddf2814d13159239 "));
		String cd = "upload.jpg";
		System.out.println(cd.lastIndexOf("."));
		System.out.println(cd.length());
		System.out.println(cd.substring(cd.lastIndexOf("."), cd.length()));
		
	}
}
