package com.zmy.web.local;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zmy.dto.LocalAuthExecution;
import com.zmy.entity.LocalAuth;
import com.zmy.entity.PersonInfo;
import com.zmy.entity.UserProductMap;
import com.zmy.enums.LocalAuthStateEnum;
import com.zmy.service.ILocalAuthService;
import com.zmy.service.IPersonInfoService;
import com.zmy.service.IUserProductMapService;
import com.zmy.util.CodeUtil;
import com.zmy.util.HttpServletRequestUtil;

/**
 * @author 作者：张哥哥
 * @version 创建时间：2019年5月8日下午9:42:15 Class Description：
 */
@Controller
@RequestMapping(value = "/local", method = { RequestMethod.GET })
public class LocalAuthController {
	@Autowired
	private ILocalAuthService localAuthService;
	@Autowired
	private IPersonInfoService personInfoService;
	@Autowired
	private IUserProductMapService userProductMapService;
	
	@RequestMapping(value = "/getusertype", method = { RequestMethod.GET})
	@ResponseBody
	private Map<String, Object> getUserType(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		PersonInfo personInfo = (PersonInfo)request.getSession().getAttribute("user");
		//判断用户是否处于登录状态
		if(personInfo != null && personInfo.getUserId() != null) {
			int userType = personInfoService.getPersonInfoById(personInfo.getUserId()).getUserType();
			modelMap.put("success", true);
			modelMap.put("userType", userType);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "权限不足或未登录");
		}
		return modelMap;
	}
	
	/**
	 * 将用户信息与平台账号绑定
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bindlocalauth", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	private Map<String, Object> bindLocalAuth(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 验证码校验
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		// 获取输入的账号信息
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		// 从session中查看用户是否登录
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		if (userName != null && password != null && user != null && user.getUserId() != null) {
			// 创建localauth对象并赋值
			LocalAuth localAuth = new LocalAuth();
			localAuth.setUserName(userName);
			localAuth.setPassword(password);
			localAuth.setPersonInfo(user);
			// 绑定账号
			LocalAuthExecution le = localAuthService.bindLocalAuth(localAuth);
			int userType = personInfoService.getPersonInfoById(user.getUserId()).getUserType();
			if (le.getState() == LocalAuthStateEnum.ONLY_ONE_ACCOUNT.getState()) {
				modelMap.put("errMsg", le.getStateInfo());
				modelMap.put("success", false);
				modelMap.put("userType", userType);
			}else {
				modelMap.put("errMsg", le.getStateInfo());
				modelMap.put("success", true);
				modelMap.put("userType", userType);
			}
		}else {
			modelMap.put("errMsg", LocalAuthStateEnum.NULL_AUTH_INFO.getStateInfo());
			modelMap.put("success", false);
		}
		return modelMap;
	}

	@RequestMapping(value = "/changelocalpwd", method = { RequestMethod.POST })
	@ResponseBody
	private Map<String, Object> changeLocalPwd(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 验证码校验
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		// 获取输入的账号信息
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		// 获取新密码
		String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
		// 从session中获取当前用户信息
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		if (userName != null && password != null && newPassword != null && user != null && user.getUserId() != null
				&& !password.equals(newPassword)) {
			try {
				// 查看原先账号，看看输入的账号是否一致，不一致则认为是非法操作
				LocalAuth localAuth = localAuthService.getLocalAuthByUserId(user.getUserId());
				if (localAuth != null && !localAuth.getUserName().equals(userName)) {
					// 不一致则直接退出
					modelMap.put("success", false);
					modelMap.put("errMsg", "输入的账号非本次登陆的账号");
					return modelMap;
				}
				// 修改平台账号的用户密码
				int userType = personInfoService.getPersonInfoById(user.getUserId()).getUserType();
				LocalAuthExecution le = localAuthService.modifyLocalAuth(user.getUserId(), userName, password,
						newPassword, new Date());
				if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
					modelMap.put("userType", userType);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", le.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", LocalAuthStateEnum.NULL_AUTH_INFO.getStateInfo());
		}
		return modelMap;
	}

	@RequestMapping(value = "/logincheck", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> loginCheck(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 获取是否需要验证码校验的标识符
		boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
		if (needVerify && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		// 获取输入的账号信息
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		PersonInfo personInfo = null;
		if (userName != null && password != null) {
			LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAndPwd(userName, password);
			if (localAuth != null) {
				modelMap.put("success", true);
				personInfo = personInfoService.getPersonInfoById(localAuth.getPersonInfo().getUserId());
				request.getSession().setAttribute("user", personInfo);
				modelMap.put("userType", personInfo.getUserType());
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户名或密码错误");
			}
		}
		return modelMap;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> logOut(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 将用户session置为空
		request.getSession().setAttribute("user", null);
		modelMap.put("success", true);
		return modelMap;
	}

	@RequestMapping(value = "/loginstatecheck")
	@ResponseBody
	private Map<String, Object> loginStateCheck(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("user");
		if (personInfo != null && personInfo.getUserId() != null) {
			Long userId = personInfo.getUserId();
			personInfo = personInfoService.getPersonInfoById(userId);
			int userType = personInfo.getUserType();
			modelMap.put("userType", userType);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请先登录系统！");
		}
		return modelMap;
	}
	/**
	 * 顾客信息页面 + 三条订单记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/myinfomation")
	@ResponseBody
	private Map<String, Object> getMyInfomation(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("user");
		if (personInfo != null && personInfo.getUserId() != null) {
			UserProductMap userProductMap = new UserProductMap();
			userProductMap.setUser(personInfo);
			Map<String, Object> resultMap = userProductMapService.getUserProductMapListByUser(userProductMap, 1, 3);
			//获取的订单总数
			int count = (int)resultMap.get("count");
			modelMap.put("count", count);
			modelMap.put("userProductMapList", resultMap.get("userProductMapList"));
			personInfo = personInfoService.getPersonInfoById(personInfo.getUserId());
			modelMap.put("personInfo", personInfo);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请先登录系统!");
		}
		return modelMap;
	}
}
