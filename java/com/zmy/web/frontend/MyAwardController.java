package com.zmy.web.frontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zmy.dto.UserAwardMapExecution;
import com.zmy.entity.Award;
import com.zmy.entity.PersonInfo;
import com.zmy.entity.Shop;
import com.zmy.entity.UserAwardMap;
import com.zmy.enums.UserAwardMapStateEnum;
import com.zmy.service.IUserAwardMapService;
import com.zmy.util.HttpServletRequestUtil;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月24日下午7:53:12
*Class Description： 
*/
@Controller
@RequestMapping("/frontend")
public class MyAwardController {
	@Autowired
	private IUserAwardMapService userAwardMapService;
	
	@RequestMapping(value = "/listuserawardmap", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listAwardsByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		//从前端获取奖品id
		Long awardId = HttpServletRequestUtil.getLong(request, "awardId");
		//封装成奖品映射对象
		UserAwardMap userAwardMap = compactUserAwardMap4Add(user,awardId);
		if(userAwardMap != null) {
			try {
				//添加兑换信息
				UserAwardMapExecution se = userAwardMapService.addUserAwardMap(userAwardMap);
				if(se.getState() == UserAwardMapStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			}catch(RuntimeException e) {
				modelMap.put("success",false);
				modelMap.put("errMsg", e.toString());
			}
		}
		return modelMap;
	}

	private UserAwardMap compactUserAwardMap4Add(PersonInfo user, Long awardId) {
		
		return null;
	}
	
	@RequestMapping(value="/listuserawardmapsbycustomer")
	@ResponseBody
	private Map<String, Object> listUserAwardsByCustomer(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		//从session中获取用户信息
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		if((pageIndex > -1) && (pageSize > -1) && (user != null) && (user.getUserId()) != null) {
			UserAwardMap userAwardMapCondition = new UserAwardMap();
			userAwardMapCondition.setUser(user);
			long shopId = HttpServletRequestUtil.getLong(request, "shopId");
			if(shopId > -1) {
				Shop shop = new Shop();
				shop.setShopId(shopId);
				userAwardMapCondition.setShop(shop);
			}
			String awardName = HttpServletRequestUtil.getString(request, "awardName");
			if(awardName != null) {
				Award award = new Award();
				award.setAwardName(awardName);
				userAwardMapCondition.setAward(award);
			}
			//根据传入的查询条件分页获取用户奖品映射信息
			UserAwardMapExecution ue = userAwardMapService.listUserAwardMap(userAwardMapCondition, pageIndex, pageSize);
			modelMap.put("userAwardMapList", ue.getUserAwardMapList());
			modelMap.put("count", ue.getCount());
			modelMap.put("success", true);
		}else {
			modelMap.put("errMsg", "empty pageSize or pageIndex or userId");
			modelMap.put("success", false);
		}
		return modelMap;
	}
}
