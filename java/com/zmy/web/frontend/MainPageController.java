package com.zmy.web.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zmy.dto.Notice;
import com.zmy.entity.HeadLine;
import com.zmy.entity.PersonInfo;
import com.zmy.entity.ShopCategory;
import com.zmy.service.IHeadLineService;
import com.zmy.service.IShopCategoryService;

/**
 * @author 作者：张哥哥
 * @version 创建时间：2019年4月14日下午2:44:04 Class Description： 前端操作相关方法集散地
 */
@Controller
@RequestMapping(value = "/frontend")
public class MainPageController {
	@Autowired
	private IHeadLineService headLineService;
	@Autowired
	private IShopCategoryService shopCategoryService;

	// 此方法是用来检测前端系统用户的登录状态
	@RequestMapping(value = "/checkuserloginstatus", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getUserType(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("user");
		// 判断用户是否处于登录状态
		if (personInfo != null && personInfo.getUserId() != null) {
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "未登录");
		}
		return modelMap;
	}

	/**
	 * 初始化前端展示系统的主页信息，包括以及店铺类别列表以及头条列表
	 * 
	 * @return
	 */

	@RequestMapping(value = "/getmainpagelist", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getHeadLineList() {
		Map<String, Object> modelMap = new HashMap<>();
		List<ShopCategory> shopCategoryList = new ArrayList<>();
		try {
			// 获取一级店铺类别列表
			shopCategoryList = shopCategoryService.getShopCategoryList(null);
			modelMap.put("shopCategoryList", shopCategoryList);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		try {
			// 获取一级状态为1的头条列表
			List<HeadLine> headLineList = headLineService.getHeadLineList();
			modelMap.put("headLineList", headLineList);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "获取头条列表失败");
			return modelMap;
		}
		@SuppressWarnings("resource")
		ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:spring-dao.xml");
		Notice notice = (Notice) ac.getBean("notice");
		modelMap.put("notice", notice);
		modelMap.put("success", true);
		return modelMap;
	}
}
