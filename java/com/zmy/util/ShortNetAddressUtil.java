package com.zmy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月20日上午10:41:35
*Class Description： 根据传入的url，通过访问百度短视频的接口，将其转换成短的URL
*/
public class ShortNetAddressUtil {
	private static Logger log = LoggerFactory.getLogger(ShortNetAddressUtil.class);
	
	public static int TIMEOUT = 30 * 1000;
	public static String ENCODING = "UTF-8";
	
	public static String getShortURL(String originURL) {
		String tinyUrl = null;
		try {
			//百度短视频接口
			URL url = new URL("https://dwz.cn/create.php");
			//建立链接
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			//设置连接的参数
			//使用连接进行输入
			connection.setDoInput(true);
			//使用连接进行输出
			connection.setDoOutput(true);
			//不适用缓存
			connection.setUseCaches(false);
			//设置连接超时时间为三十秒
			connection.setConnectTimeout(TIMEOUT);
			//设置请求模式为POST
			connection.setRequestMethod("POST");
			//设置post信息，这里为传入的原始URL
			String postData = URLEncoder.encode(originURL.toString(), "utf-8");
			//输出原始的url
			connection.getOutputStream().write(("url=" + postData).getBytes());
			//连接百度短视频接口
			connection.connect();
			//获取返回的字符串
			String responseStr = getResponseStr(connection);
			log.info("response str:" + responseStr);
			//在字符串中获取tinyUrl
			tinyUrl = getValueByKey(responseStr, "tinyurl");
			log.info("tinyurl:" + tinyUrl);
			//关闭连接
			connection.disconnect();
		}catch(IOException e) {
			log.error("getshortURL error:" + e.toString());
		}
		return tinyUrl;
	}
/**
 * JSON依据传入的key获取value
 * @param responseStr
 * @param string
 * @return
 */
	private static String getValueByKey(String responseStr, String key) {
		ObjectMapper mapper = new ObjectMapper();
		//定义json节点
		JsonNode node;
		String targetValue = null;
		try {
			//将调用返回的消息串转换成json对象
			node =  mapper.readTree(responseStr);
			//依据key从json对象中获取值
			targetValue = node.get(key).asText();
		}catch (JsonProcessingException e) {
			log.error("getValueByKey error:" + e.toString());
			e.printStackTrace();
		}catch(IOException e) {
			log.error("getValueByKey error:" + e.toString());
		}
		return targetValue;
	}
	/**
	 * 通过HttpConnection获取返回的字符串
	 * @param connection
	 * @return
	 */
	private static String getResponseStr(HttpURLConnection connection) throws IOException{
		StringBuffer result = new StringBuffer();
		//从连接中获取http状态码
		int responseCode = connection.getResponseCode();
		if(responseCode == HttpURLConnection.HTTP_OK) {
			//获取连接输出流
			InputStream in = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, ENCODING));
			String inputLine = "";
			while((inputLine = reader.readLine()) != null) {
				//将消息逐行读入结果中
				result.append(inputLine);
			}
		}
		//将结果转换成String 并返回
		return String.valueOf(result);
	}
	/**
	 * 百度短链接接口
	 * @param args
	 */
	public static void main(String[] args) {
		getShortURL("https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login");
	}
}
