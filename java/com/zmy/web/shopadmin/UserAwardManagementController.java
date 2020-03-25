package com.zmy.web.shopadmin;

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
import com.zmy.entity.Shop;
import com.zmy.entity.UserAwardMap;
import com.zmy.service.IUserAwardMapService;
import com.zmy.util.HttpServletRequestUtil;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月24日下午6:29:50
*Class Description： 
*/
@Controller
@RequestMapping("/shopadmin")
public class UserAwardManagementController {
	@Autowired
	private IUserAwardMapService userAwardService;
	
	@RequestMapping(value="/listuserawardmapsbyshop",method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listUserShopMapsByShop(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() != null)) {
			UserAwardMap userAwardMapCondition = new UserAwardMap();
			//查询条件
			userAwardMapCondition.setShop(currentShop);
			String awardName = HttpServletRequestUtil.getString(request, "awardName");
			if(awardName != null) {
				Award award = new Award();
				award.setAwardName(awardName);
				userAwardMapCondition.setAward(award);
			}
			//分页查询顾客积分列表
			UserAwardMapExecution ue = userAwardService.listUserAwardMap(userAwardMapCondition, pageIndex, pageSize);
			modelMap.put("userShopMapList", ue.getUserAwardMapList());
			modelMap.put("count", ue.getCount());
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}
}
