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
import com.zmy.entity.Product;
import com.zmy.service.IProductService;
import com.zmy.service.IUserProductMapService;
import com.zmy.util.HttpServletRequestUtil;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月14日下午9:48:58
*Class Description： 
*/
@Controller
@RequestMapping(value="/frontend")
public class ProductListController {
	@Autowired
	private IProductService productService;
	@Autowired
	private IUserProductMapService userProductMapService;
	
	@RequestMapping(value="/getproductlistbyname",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getProductListByProductName(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		String productName = HttpServletRequestUtil.getString(request, "productName");
//		int rowIndex = HttpServletRequestUtil.getInt(request, "rowIndex");
//		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		Product productCondition = new Product();
		productCondition.setProductName(productName);
		ProductExecution pe = productService.getProductListByName(productName, 0, 100);
		List<Product> productList = pe.getProductList();
		List<Long> saleCountList = new ArrayList<>();
		for(int i = 0; i < pe.getProductList().size(); i++) {
			productCondition = productList.get(i);
			Long sales = userProductMapService.getUserProductMapByProductId(productCondition.getProductId());
			saleCountList.add(sales);
		}
		modelMap.put("saleCountList", saleCountList);
		if(pe.getCount() > 0) {
			modelMap.put("success", true);
			modelMap.put("count", pe.getCount());
			modelMap.put("productList", pe.getProductList());
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "无此类商品");
		}
		return modelMap;
	}
}
