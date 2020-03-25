package com.zmy.web.frontend;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zmy.dto.Car;
import com.zmy.entity.PersonInfo;
import com.zmy.entity.Product;
import com.zmy.entity.Shop;
import com.zmy.entity.UserProductMap;
import com.zmy.service.IProductService;
import com.zmy.service.IUserProductMapService;
import com.zmy.util.HttpServletRequestUtil;

/**
 * @author 作者：张哥哥
 * @version 创建时间：2019年5月27日下午4:08:51 Class Description： 前端系统顾客获取订单列表的接口
 */
@Controller
@RequestMapping("/frontend")
public class UserProductListController {
	@Autowired
	private IUserProductMapService userProductMapService;
	@Autowired
	private IProductService productService;

	@RequestMapping(value = "/getuserproductlistbyuserid")
	@ResponseBody
	private Map<String, Object> getUserProductListByUserId(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 获取前端参数
		int rowIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		String productName = HttpServletRequestUtil.getString(request, "productName");
		Product product = new Product();
		if (productName != null) {
			product.setProductName(productName);
		}
		if (rowIndex != -1 && pageSize != -1) {
			PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
			UserProductMap userProductMap = new UserProductMap();
			userProductMap.setProduct(product);
			userProductMap.setUser(user);
			Map<String, Object> resultMap = userProductMapService.getUserProductMapListByUser(userProductMap, rowIndex,
					pageSize);
			modelMap.put("success", true);
			modelMap.put("count", resultMap.get("count"));
			modelMap.put("userProductMapList", resultMap.get("userProductMapList"));
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "pageIndex or pageSize is empty!");
		}
		return modelMap;
	}

	@RequestMapping("batchaddorder")
	@ResponseBody
	private Map<String, Object> addOrder(@RequestBody List<Car> carList, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		if (carList.size() > 0) {
			for (Car car : carList) {
				UserProductMap userProductMap = new UserProductMap();
				Shop shop = (Shop) request.getSession().getAttribute("shop");
				userProductMap.setShop(shop);
				PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
				userProductMap.setUser(user);
				PersonInfo operator = shop.getOwner();
				userProductMap.setOperator(operator);
				userProductMap.setCommentState(0);
				Product product = productService.getProductById(car.getProductId());
				userProductMap.setProduct(product);
				userProductMap.setCreateTime(new Date());
				int effectedNum = userProductMapService.addUserProductMap(userProductMap);
				if (effectedNum > 0) {
					modelMap.put("success", true);
				}
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "没有选择商品哦");
		}

		return modelMap;
	}

	@RequestMapping("addorder")
	@ResponseBody
	private Map<String, Object> addOrder(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		Long productId = HttpServletRequestUtil.getLong(request, "productId");
		if (productId != -1) {
			UserProductMap userProductMap = new UserProductMap();
			Shop shop = (Shop) request.getSession().getAttribute("shop");
			userProductMap.setShop(shop);
			PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
			userProductMap.setUser(user);
			PersonInfo operator = shop.getOwner();
			userProductMap.setOperator(operator);
			userProductMap.setCommentState(0);
			Product product = productService.getProductById(productId);
			userProductMap.setProduct(product);
			userProductMap.setCreateTime(new Date());
			int effectedNum = userProductMapService.addUserProductMap(userProductMap);
			if (effectedNum > 0) {
				modelMap.put("success", true);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "请求参数非法");
			}
		}
		return modelMap;
	}

}
