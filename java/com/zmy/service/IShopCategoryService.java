package com.zmy.service;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年3月16日上午12:24:39
*Class Description： 
*/

import java.util.List;

import com.zmy.dto.ImageHolder;
import com.zmy.dto.ShopCategoryExecution;
import com.zmy.entity.ShopCategory;

public interface IShopCategoryService {
	public static final String SHOPCATEGORYLIST = "shopcategorylist";

	/**
	 * 根据查询条件获取shopCategory列表
	 * 
	 * @param shopCategoryCondition
	 * @return
	 */
	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
	/**
	 * 添加店铺类别，并存储店铺类别图片
	 * 
	 * @param shopCategory
	 * @param thumbnail
	 * @return
	 */
	ShopCategoryExecution addShopCategory(ShopCategory shopCategory, ImageHolder thumbnail);

	/**
	 * 修改店铺类别
	 * 
	 * @param shopCategory
	 * @param thumbnail
	 * @return
	 */
	ShopCategoryExecution modifyShopCategory(ShopCategory shopCategory, ImageHolder thumbnail);

	/**
	 * 根据Id返回店铺类别信息
	 * 
	 * @param shopCategoryId
	 * @return
	 */
	ShopCategory getShopCategoryById(Long shopCategoryId);

}
