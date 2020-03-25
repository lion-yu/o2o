package com.zmy.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年3月16日下午9:27:21
*Class Description： 前端验证码比对 
*/
public class CodeUtil {
	public static boolean checkVerifyCode(HttpServletRequest request) {
		String verifyCodeExpected = (String)request.getSession().getAttribute(
				com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
		if(verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExpected)) {
			return false;
		}
		return true;
	}
	public static BitMatrix generateQRCodeStream(String content, HttpServletResponse resp) {
		//给响应头添加头部信息，告诉浏览器返回的是图片流
		resp.setHeader("Cache-Control", "no-store");
		resp.setHeader("Pragma", "no-cache");
		resp.setDateHeader("expires", 0);
		resp.setContentType("image/png");
		//设置图片的文件编码以及内边距框
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.MARGIN, 0);
		BitMatrix bitMatrix;
		try {
			//参数顺序分别是：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
			bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return bitMatrix;
	}
}
