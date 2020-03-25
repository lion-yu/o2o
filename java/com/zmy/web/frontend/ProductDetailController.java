package com.zmy.web.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zmy.entity.Comment;
import com.zmy.entity.PersonInfo;
import com.zmy.entity.Product;
import com.zmy.entity.UserProductMap;
import com.zmy.service.ICommentService;
import com.zmy.service.IProductService;
import com.zmy.service.IUserProductMapService;
import com.zmy.util.HttpServletRequestUtil;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年4月19日下午10:39:59
*Class Description： 商品详情页后台数据索取控制器
*/
@Controller
@RequestMapping(value="/frontend", method = RequestMethod.GET)
public class ProductDetailController {
	@Autowired
	private IProductService productService;
	@Autowired
	private ICommentService commentService;
	@Autowired
	private IUserProductMapService userProductMapService;
	
	@RequestMapping(value="/getproductdetail",method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getProductDetailInfo(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long productId = HttpServletRequestUtil.getLong(request, "productId");
		if(productId > -1) {
			try {
				Product product = productService.getProductById(productId);
				UserProductMap userProductMap = new UserProductMap();
				userProductMap.setProduct(product);
				List<Comment> comments = commentService.getCommentByShopAndProduct(userProductMap);
				//商品销量
				modelMap.put("sales", userProductMapService.getUserProductMapByProductId(productId));
				modelMap.put("comment", comments);
				modelMap.put("commentCount", comments.size());
				modelMap.put("success", true);
				modelMap.put("product", product);
			}catch(Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "获取商品信息失败，请重试");
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "参数非法");
		}
		return modelMap;
	}
	
	
	@RequestMapping(value="/listproductdetailpageinfo",method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listProductDetailPageInfo(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long productId = HttpServletRequestUtil.getLong(request, "productId");
		@SuppressWarnings("unused")
		Product product = null;
		//空值判断
		if(productId != -1) {
			//根据productId获取商品信息，包含商品详情图列表
			product = productService.getProductById(productId);
			PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
			if(user == null) {
				modelMap.put("needQRCode", false);
			}else {
				modelMap.put("needQRCode", true);
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty productId");
		}
		return modelMap;
	}
	
}
