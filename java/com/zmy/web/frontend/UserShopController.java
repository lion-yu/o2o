package com.zmy.web.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zmy.entity.PersonInfo;
import com.zmy.entity.UserShopMap;
import com.zmy.service.IUserShopMapService;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月25日下午4:39:28
*Class Description： 顾客获取所有店铺所积累的积分信息
*/
@Controller
@RequestMapping("/frontend")
public class UserShopController {
	@Autowired
	private IUserShopMapService userShopMapService;
	@RequestMapping(value ="/getuserpoint")
	@ResponseBody
	private Map<String, Object> getUserPointInfo(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		//获取用户是否登录
		PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
		if(user != null && user.getUserId() != null) {
			List<UserShopMap> userShopMapList = userShopMapService.getUserShopMap(user.getUserId());
			modelMap.put("userShopMapList", userShopMapList);
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请登录查询");
		}
		return modelMap;
	}
}
