package com.zmy.web.frontend;

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

import com.zmy.dto.ProductExecution;
import com.zmy.entity.Comment;
import com.zmy.entity.Product;
import com.zmy.entity.ProductCategory;
import com.zmy.entity.Shop;
import com.zmy.entity.UserProductMap;
import com.zmy.service.ICommentService;
import com.zmy.service.IProductCategoryService;
import com.zmy.service.IProductService;
import com.zmy.service.IShopService;
import com.zmy.util.HttpServletRequestUtil;

/**
 * @author 作者：张哥哥
 * @version 创建时间：2019年4月19日下午12:07:06 Class Description：
 */
@Controller
@RequestMapping("/frontend")
public class ShopDetailController {
	@Autowired
	private IShopService shopService;
	@Autowired
	private IProductService productService;
	@Autowired
	private IProductCategoryService productCategoryService;
	@Autowired
	private ICommentService commentService;

	/**
	 * 获取店铺信息以及该店铺下的商品类别列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listshopdetailpageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShopDetailPageInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		Shop shop = null;
		List<ProductCategory> productCategoryList = null;
		if (shopId != -1) {
			// 获取店铺Id为shopId的店铺信息
			shop = shopService.getShopById(shopId);
			request.getSession().setAttribute("shop", shop);
			// 获取店铺下面的商品类别列表
			productCategoryList = productCategoryService.getProductCategoryList(shopId);
			modelMap.put("shop", shop);
			modelMap.put("productCategoryList", productCategoryList);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/listproductsbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listProductsByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		// 获取一页要显示的数据条数
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		//空值判断
		if(pageIndex > -1 && pageSize > -1 && shopId > -1) {
			long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
			//尝试获取模糊查找的商品名
			String productName = HttpServletRequestUtil.getString(request, "productName");
			//组合查询条件
			Product productCondition = compactProductCondition4Search(shopId, productCategoryId, productName);
			//按照传入的查询条件返回相应商品列表以及总数
			ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
			List<Product> productList = pe.getProductList();
			List<Integer> countList = new ArrayList<>();
			//获取到商品列表后查看评价的条数
			for(int i = 0; i < productList.size() ;  i++) {
				Product product = productList.get(i);
				UserProductMap userProductMap = new UserProductMap();
				userProductMap.setProduct(product);
				List<Comment> commentList = commentService.getCommentByShopAndProduct(userProductMap);
				if(commentList.size() == 0) {
					countList.add(0);
				}else {
					countList.add(commentList.size());
				}
			}
			modelMap.put("countList", countList);
			modelMap.put("productList", pe.getProductList());
			modelMap.put("count", pe.getCount());
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}
	/**
	 * 组合查询条件，并将条件封装到productCondition对象里返回
	 * @param shopId
	 * @param productCategoryId
	 * @param productName
	 * @return
	 */
	private Product compactProductCondition4Search(long shopId, long productCategoryId, String productName) {
		Product productCondition = new Product();
		Shop shop = new Shop();
		shop.setShopId(shopId);
		productCondition.setShop(shop);
		if(productCategoryId != -1) {
			ProductCategory productCategory = new ProductCategory();
			productCategory.setProductCategoryId(productCategoryId);
			productCondition.setProductCategory(productCategory);
		}
		if(productName != null) {
			productCondition.setProductName(productName);
		}
		//只允许选出状态为上架的商品
		productCondition.setEnableStatus(1);
		return productCondition;
	}
	
}
