package com.zmy.web.shopadmin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.zmy.dto.ShopAuthMapExecution;
import com.zmy.entity.PersonInfo;
import com.zmy.entity.Shop;
import com.zmy.entity.ShopAuthMap;
import com.zmy.enums.ShopAuthMapStateEnum;
import com.zmy.service.IShopAuthMapService;
import com.zmy.util.CodeUtil;
import com.zmy.util.HttpServletRequestUtil;

/**
 * @author 作者：张哥哥
 * @version 创建时间：2019年5月15日下午9:42:11 Class Description： 店铺授权员工接口
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopAuthManagementController {
	@Autowired
	private IShopAuthMapService shopAuthMapService;
	private static final String URL = "http://localhost:8080/o2o/shopadmin/addshopauthmap"; 
	
	@RequestMapping(value="/addshopauthmap")
	@ResponseBody
	private String addShopAuthMap(HttpServletRequest request){
		PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
		Shop currentShop = (Shop)request.getSession().getAttribute("user");
		
		if(user != null && user.getUserId() != null) {
			ShopAuthMap shopAuthMap = new ShopAuthMap();
			shopAuthMap.setShop(currentShop);
			shopAuthMap.setEmployee(user);
			ShopAuthMapExecution same = shopAuthMapService.addShopAuthMap(shopAuthMap);
			if(same.getState() == ShopAuthMapStateEnum.SUCCESS.getState()) {
				return "shop/success";
			}else {
				return "shop/shopauthmanagement";
			}
		}else {
			return "";
		}
	}
	
	@RequestMapping(value = "/listshopauthmapbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShopAuthMapByshop(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> modelMap = new HashMap<>();
		// 提出分页信息
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		// 从session中获取店铺信息
		Shop currentShop = new Shop();
		currentShop.setShopId(17L);
//		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		// 空值判断
		if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() != null)) {
			// 分页获取店铺下面的授权信息列表
			ShopAuthMapExecution se = shopAuthMapService.listShopAuthMapByShopId(currentShop.getShopId(), pageIndex,
					pageSize);
			modelMap.put("shopAuthMapList", se.getShopAuthMapList());
			modelMap.put("count", se.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/getshopauthmapbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShopAuthMapByshop(@RequestParam Long shopAuthId) {
		Map<String, Object> modelMap = new HashMap<>();
		if (shopAuthId != null && shopAuthId > -1) {
			ShopAuthMap shopAuthMap = shopAuthMapService.getShopAuthMapById(shopAuthId);
			modelMap.put("success", true);
			modelMap.put("shopAuthMap", shopAuthMap);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopAuthId");
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/modifyshopauthmap", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object>modifyShopAuthMap(String shopAuthMapStr, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		//是授权编辑时调用还是删除/恢复授权操作的时候调用
		//若为前者则进行验证码判断，厚泽则跳过验证码判断
		boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statesChange");
		if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		} 
		ObjectMapper mapper = new ObjectMapper();
		ShopAuthMap shopAuthMap = null;
		try {
			//将前台传入的json字符串转换成shopAUthMap实例
			shopAuthMap = mapper.readValue(shopAuthMapStr, ShopAuthMap.class);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		if(shopAuthMap != null && shopAuthMap.getShopAuthId() != null) {
			try {
				//被操作的对方是否为店家本身，店家本身不支持修改
				if(!checkPermission(shopAuthMap.getShopAuthId())) {
					modelMap.put("success", false);
					modelMap.put("errMsg", "无法对店家本身权限做操作");
					return modelMap;
				}
				ShopAuthMapExecution se = shopAuthMapService.modifyShopAuthMap(shopAuthMap);
				if(se.getState() == ShopAuthMapStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			}catch(RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入要修改的授权信息");
		}
		return modelMap;
	}
	/**
	 * 检查被操作的对象是否可修改
	 */
	private boolean checkPermission(Long shopAuthId) {
		ShopAuthMap grantedPerson = shopAuthMapService.getShopAuthMapById(shopAuthId);
		if(grantedPerson.getTitleFlag() == 0) {
			//若是商家本身，不能操作
			return false;
		}
		return true;
	}
	/**
	 * 添加店铺内员工
	 * @param resp
	 */
	@RequestMapping("/generateqrcode")
	private void getQRCode(HttpServletResponse resp){
		BitMatrix bitMatirx = CodeUtil.generateQRCodeStream(URL, resp);
		try {
			MatrixToImageWriter.writeToStream(bitMatirx, "png", resp.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
