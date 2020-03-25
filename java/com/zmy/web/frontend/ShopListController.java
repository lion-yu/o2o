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

import com.zmy.dto.ShopExecution;
import com.zmy.entity.Area;
import com.zmy.entity.Shop;
import com.zmy.entity.ShopCategory;
import com.zmy.service.IAreaService;
import com.zmy.service.IShopCategoryService;
import com.zmy.service.IShopService;
import com.zmy.util.HttpServletRequestUtil;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年4月17日下午5:39:15
*Class Description： 
*/
@Controller
@RequestMapping(value="/frontend",method=RequestMethod.GET)
@ResponseBody
public class ShopListController {
	@Autowired
	private IShopService shopService;
	@Autowired
	private IShopCategoryService shopCategoryService;
	@Autowired
	private IAreaService areaService;
	
	@RequestMapping(value="/listshops",method=RequestMethod.GET)
	@ResponseBody
	//！     该方法会执行两次？？？
	private Map<String, Object> getShopList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		//获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		//获取一页要显示的数据条数
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		//非空判断
		if(pageIndex > -1 && pageSize > -1) {
			//试着获取一级类别Id
			Long parentId = HttpServletRequestUtil.getLong(request, "parentId");
			//试着获取二级类别Id
			Long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
			//试着获取区域Id
			int areaId = HttpServletRequestUtil.getInt(request, "areaId");
			//试着获取模糊查询的名字
			String shopName = HttpServletRequestUtil.getString(request, "shopName");
			//获取组合后的查询条件
			Shop shopCondition = compactShopCondition4Search(parentId, shopCategoryId, areaId, shopName);
			//根据查询条件和分页信息获取店铺列表，并返回总数
			ShopExecution se = shopService.getShopList(shopCondition, pageIndex, pageSize);
			modelMap.put("shopList", se.getShopList());
			modelMap.put("count", se.getCount());
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex");
		}
		return modelMap;
	}
	/**
	 * 组合查询条件，并将条件封装到ShopCondition对象里返回
	 * @param parentId
	 * @param shopCategoryId
	 * @param areaId
	 * @param shopName
	 * @return
	 */
	private Shop compactShopCondition4Search(Long parentId, Long shopCategoryId, int areaId, String shopName) {
		Shop shopCondition = new Shop();
		if(parentId != -1L) {
			//查询某个级别下ShopCategory下面的所有二级ShopCategory里面的店铺列表
			ShopCategory childCategory = new ShopCategory();
			ShopCategory parentCategory = new ShopCategory();
			parentCategory.setShopCategoryId(parentId);
			childCategory.setParent(parentCategory);
			shopCondition.setShopCategory(childCategory);
		}
		if(shopCategoryId != -1L) {
			//查询某个二级shopCategory下面的店铺列表
			ShopCategory shopCategory = new ShopCategory();
			shopCategory.setShopCategoryId(shopCategoryId);
			shopCondition.setShopCategory(shopCategory);
		}
		if(areaId != -1L) {
			//查询位于某个区域下的店铺列表
			Area area = new Area();
			area.setAreaId(areaId);
			shopCondition.setArea(area);
		}
		if(shopName != null) {
			//查询名字里包含shopName的店铺列表
			shopCondition.setShopName(shopName);
		}
		//前端展示的店铺都是审核成功的店铺
		shopCondition.setEnableStatus(1);
		return shopCondition;
	}
	
	@RequestMapping(value="/listshopspageinfo", method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getGrade2AndAreaList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		Long parentId = HttpServletRequestUtil.getLong(request, "parentId");
		List<ShopCategory> shopCategoryList = null;
		List<Area> areaList = null;
		if(parentId != -1) {
			//如果parentId存在，则取出该一级ShopCategory下的二级ShopCategory列表
			try {
				ShopCategory shopCategoryCondition = new ShopCategory();
				ShopCategory parentShopCategory = new ShopCategory();
				parentShopCategory.setShopCategoryId(parentId);
				shopCategoryCondition.setParent(parentShopCategory);
				shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
			}catch(Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}else {
				//如果parentId不存在，则取出所有一级shopCategory(用户在首页选择的是全部商品列表)
				try {
					shopCategoryList = shopCategoryService.getShopCategoryList(null);
				}catch(Exception e) {
					modelMap.put("success", false);
					modelMap.put("errMsg", e.getMessage());
				}
			}
			modelMap.put("shopCategoryList", shopCategoryList);
			try {
				areaList = areaService.getAreaList();
				modelMap.put("areaList", areaList);
				modelMap.put("success", true);
			}catch(Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;
	}
}

