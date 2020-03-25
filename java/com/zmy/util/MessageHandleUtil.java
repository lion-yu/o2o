package com.zmy.util;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author 作者：张哥哥
 * @version 创建时间：2019年5月10日下午5:24:40 Class Description： 消息处理工具类
 */
public class MessageHandleUtil {
	/**
	 * 解析微信发来的xml数据请求
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<>();
		// 从request中取得输入流
		InputStream input = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(input);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		@SuppressWarnings("unchecked")
		List<Element> elementList = root.elements();
		// 遍历所有子节点
		for (Element e : elementList) {
			System.out.println(e.getName() + "|" + e.getText());
			map.put(e.getName(), e.getText());
		}
		// 释放资源
		input.close();
		input = null;
		return map;
	}

	public static String buildXml(Map<String, String> map) {
		String result;
		String msgType = map.get("MsgType").toString();
		System.out.println("MsgType:" + msgType);
		if (msgType.toUpperCase().equals("TEXT")) {
			String customer = "<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxddf2814d13159239&redirect_uri=http://139.196.73.108/o2o/wechatlogin/logincheck&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect'>顾客注册入口</a>";
			String boss = "<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxddf2814d13159239&redirect_uri=http://139.196.73.108/o2o/wechatlogin/logincheck&response_type=code&scope=snsapi_userinfo&state=2#wechat_redirect'>商家注册入口</a>";
			result = buildTextMessage(map, customer + "\n\n" + boss);
		} else {
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			result = String.format(
					"<xml>" + "<ToUserName><![CDATA[%s]]></ToUserName>" + "<FromUserName><![CDATA[%s]]></FromUserName>"
							+ "<CreateTime>%s</CreateTime>" + "<MsgType><![CDATA[text]]></MsgType>"
							+ "<Content><![CDATA[%s]]></Content>" + "</xml>",
					fromUserName, toUserName, getUtcTime(), "1：感谢您关注本公众号，该测试号仅作为商城的传送门\n2：点击任意一个入口，然后点击继续访问按钮，该操作将收集您微信基本信息用作网站个人信息的注册\n"
							+ "3：进入网站后请您尽快绑定账号，方便日后的使用...\n" + "\n\n\n回复任意文本召唤系统入口");
		}
		return result;
	}

	private static String buildTextMessage(Map<String, String> map, String content) {
		// 发送方账号
		String fromUserName = map.get("FromUserName");
		String toUserName = map.get("ToUserName");
		return String.format(
				"<xml>" + "<ToUserName><![CDATA[%s]]></ToUserName>" + "<FromUserName><![CDATA[%s]]></FromUserName>"
						+ "<CreateTime>%s</CreateTime>" + "<MsgType><![CDATA[text]]></MsgType>"
						+ "<Content><![CDATA[%s]]></Content>" + "</xml>",
				fromUserName, toUserName, getUtcTime(), content);
	}

	private static String getUtcTime() {
		Date dt = new Date();// 如果不需要格式,可直接用dt,dt就是当前系统时间
		DateFormat df = new SimpleDateFormat("yyyyMMddhhmm");// 设置显示格式
		String nowTime = df.format(dt);
		long dd = (long) 0;
		try {
			dd = df.parse(nowTime).getTime();
		} catch (Exception e) {

		}
		return String.valueOf(dd);
	}

}
