package com.zmy.web.wechat;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zmy.dto.UserAccessToken;
import com.zmy.dto.WechatAuthExecution;
import com.zmy.dto.WechatUser;
import com.zmy.entity.PersonInfo;
import com.zmy.entity.WeChatAuth;
import com.zmy.enums.WechatAuthStateEnum;
import com.zmy.service.IPersonInfoService;
import com.zmy.service.IWechatAuthService;
import com.zmy.util.wechat.WechatUtil;

/**
 * @author 作者：张哥哥
 * @version 创建时间：2019年4月21日下午11:29:05 Class Description：主要是用来获取已关注公众号的用户信息并作相应处理
 */

@Controller
@RequestMapping("wechatlogin")
/**
 * 获取关注公众号之后的微信用户信息的接口 https://open.weixin.qq.com/connect/oauth2/authorize?
 * appid=wxddf2814d13159239&redirect_uri=
 * http://139.196.73.108/o2o/wechatlogin/logincheck
 * &role_type=1&response_type=code
 * &scope=snsapi_userinfo&state=1#wechat_redirect
 * 则这里将会获取到code,之后再可以通过code获取到access_token 进而获取到用户信息
 *
 */
public class WechatLoginController {

	private static Logger log = LoggerFactory.getLogger(WechatLoginController.class);
	private static final String FRONTEND = "1";
//	private static final String SHOPEND = "2";
	@Autowired
	private IPersonInfoService personInfoService;
	@Autowired
	private IWechatAuthService wechatAuthService;

	@RequestMapping(value = "/logincheck", method = { RequestMethod.GET })
	public String doGet(HttpServletRequest request, HttpServletResponse response) {
		log.debug("weixin login get...");
		// 获取微信公众号传输过来的code,通过code可获取access_token,进而获取用户信息
		String code = request.getParameter("code");
		// 这个state用来传我们自定义的信息，方便程序调用,确定前往前端展示系统还是店家管理系统
		String roleType = request.getParameter("state");
		log.debug("weixin login code:" + code);
		WechatUser user = null;
		String openId = null;
		WeChatAuth wechatAuth = null;
		if (null != code) {
			UserAccessToken token;
			try {
				// 通过code获取access_token
				token = WechatUtil.getUserAccessToken(code);
				log.debug("weixin login token:" + token.toString());
				// 通过token获取accessToken
				String accessToken = token.getAccessToken();
				// 通过token获取openId
				openId = token.getOpenId();
				// 通过access_token和openId获取用户昵称等信息
				user = WechatUtil.getUserInfo(accessToken, openId);
				log.debug("weixin login user:" + user.toString());
				request.getSession().setAttribute("openId", openId);
				wechatAuth = wechatAuthService.getWechatAuthByOpenId(openId);
			} catch (IOException e) {
				log.error("error in getUserAccessToken or getUserInfo or findByOpenId: " + e.toString());
				e.printStackTrace();
			}
		}
		// 若微信账号为空则需要注册微信账号，同时注册用户信息
		if (wechatAuth == null) {
			PersonInfo personInfo = WechatUtil.getPersonInfoFromRequest(user);
			wechatAuth = new WeChatAuth();
			wechatAuth.setOpenId(openId);
			if (FRONTEND.equals(roleType)) {
				personInfo.setUserType(1);
			} else {
				personInfo.setUserType(2);
			}
			wechatAuth.setPersonInfo(personInfo);
			WechatAuthExecution we = wechatAuthService.register(wechatAuth);
			if (we.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
				return null;
			} 
		}else {
			PersonInfo personInfo = personInfoService.getPersonInfoById(wechatAuth.getPersonInfo().getUserId());
			request.getSession().setAttribute("user", personInfo);
		}

		// 微信用户从公众号点击相应入口进入不同身份页面
		if (roleType.equals(FRONTEND)) {
			return "frontend/index";
		} else {
			return "shop/shoplist";
		}

//		if (user != null) {
//			// 获取到微信验证的信息后返回到指定的路由
//			return "frontend/index";
//		} else {
//			return null;
//		}
	}

}