package com.zmy.web.shopadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zmy.dto.UserProductMapExecution;
import com.zmy.entity.Comment;
import com.zmy.entity.Product;
import com.zmy.entity.Shop;
import com.zmy.entity.UserProductMap;
import com.zmy.service.ICommentService;
import com.zmy.service.IUserProductMapService;
import com.zmy.util.HttpServletRequestUtil;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年6月6日下午8:58:49
*Class Description：店铺评论管理模块 
*/
@Controller
@RequestMapping("shopadmin")
public class CommentManagement {
	@Autowired
	private IUserProductMapService userProductMapService;
	@Autowired
	private ICommentService commentService;
	
	@RequestMapping(value ="getuserproductmaplist", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getUserProductMapList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		//获取前端是否有通过商品名搜索账单
		String productName = HttpServletRequestUtil.getString(request, "productName");
		Shop shop = (Shop)request.getSession().getAttribute("currentShop");
		UserProductMap userProductMap = new UserProductMap();
		if(productName != null) {
			Product product = new Product();
			product.setProductName(productName);
			userProductMap.setProduct(product);
		}
		if(shop != null && shop.getShopId() != null) {
			userProductMap.setShop(shop);
		}
		UserProductMapExecution ue = userProductMapService.listUserProductMap(userProductMap, 0, 100);
		if(ue.getUserProductMapList().size() > 0) {
			List<UserProductMap> userProductMapList = ue.getUserProductMapList();
			List<Comment> commentList = new ArrayList<>();
			for (UserProductMap userProductMap2 : userProductMapList) {
				Comment comment = commentService.getCommentByOrderId(userProductMap2.getUserProductId());
				commentList.add(comment);
			}
			modelMap.put("commentList", commentList);
			modelMap.put("userProductMapList", userProductMapList);
			modelMap.put("count", ue.getCount());
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "该店铺无订单记录");
		}
		return modelMap;
	}
}
