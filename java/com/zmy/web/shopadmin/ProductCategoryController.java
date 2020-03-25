package com.zmy.web.shopadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zmy.dto.ProductCategoryExecution;
import com.zmy.dto.Result;
import com.zmy.entity.ProductCategory;
import com.zmy.entity.Shop;
import com.zmy.enums.ProductCategoryStateEnum;
import com.zmy.exceptions.ProductCategoryOperationException;
import com.zmy.service.IProductCategoryService;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年4月4日下午10:45:10
*Class Description： 店铺商品类别操作集散地
*/
@Controller
@RequestMapping(value="shopadmin")
public class ProductCategoryController {
	@Autowired
	private IProductCategoryService productCategoryService;
	
	@RequestMapping(value="removeproductcategory",method=RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> removeProductCategory(Long productCategoryId, HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		if(productCategoryId != null && productCategoryId > 0) {
			try {
				Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
				ProductCategoryExecution pc = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
				if(pc.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pc.getStateInfo());
				}
			}catch(ProductCategoryOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请至少输入一个商品类别");
		}
		return modelMap;
	}
	@RequestMapping(value="addproductcategorys",method=RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
			HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		for(ProductCategory pc : productCategoryList) {
			pc.setShopId(currentShop.getShopId());
		}
		if(productCategoryList != null && productCategoryList.size() > 0) {
			try {
				ProductCategoryExecution pe = productCategoryService.batchInsertProductCategory(productCategoryList);
				if(pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch(ProductCategoryOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入至少一个商品类别");
		}
		return modelMap;
		
	}
	
	@RequestMapping(value="getproductcategorylist",method=RequestMethod.GET)
	@ResponseBody
	private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request){
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		List<ProductCategory> list = null;
		if(currentShop != null && currentShop.getShopId() > 0) {
			list = productCategoryService.getProductCategoryList(currentShop.getShopId());
			return new Result<List<ProductCategory>>(true,list);
		}else {
			ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
			return new Result<List<ProductCategory>>(false,ps.getState(),ps.getStateInfo());
		} 
	}
}
