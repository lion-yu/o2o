package com.zmy.web.wechat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zmy.util.MessageHandleUtil;
import com.zmy.util.wechat.SignUtil;

@Controller
@RequestMapping("wechat")
public class WeChatController {

    private static Logger log = LoggerFactory.getLogger(WeChatController.class);

    @RequestMapping(method = { RequestMethod.GET})
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("weixin get...");
        // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        PrintWriter out = null;
        try {
            out = response.getWriter();
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                log.debug("weixin get success....");
                out.print(echostr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null)
                out.close();
        }
    }
    @RequestMapping(method = { RequestMethod.POST })
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	System.out.println("正在请求数据：");
    	String result = "";
    	try {
    		Map<String, String> map = MessageHandleUtil.parseXml(request);
    		System.out.println("回复的信息是：");
    		result = MessageHandleUtil.buildXml(map);
    		System.out.println(result);
    		if(result.equals("")) {
    			result = "代号不正确";
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    		System.out.println("发生异常：" + e.getMessage());
    	}
    	response.getWriter().println(result);
    }
}