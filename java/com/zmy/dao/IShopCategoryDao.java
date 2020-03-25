package com.zmy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zmy.entity.ShopCategory;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年3月15日下午11:16:11
*Class Description： 
*/
public interface IShopCategoryDao {
	List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition")ShopCategory shopCategoryCondition);
	/**
	 * 通过Id返回唯一的店铺类别信息
	 * 
	 * @param shopCategoryId
	 * @return
	 */
	ShopCategory queryShopCategoryById(long shopCategoryId);

	/**
	 * 根据传入的Id列表查询店铺类别信息(供超级管理员选定删除类别的时候用，主要是处理图片)
	 * 
	 * @param shopCategoryIdList
	 * @return
	 */
	List<ShopCategory> queryShopCategoryByIds(List<Long> shopCategoryIdList);

	/**
	 * 插入一条店铺类别信息
	 * 
	 * @param shopCategory
	 * @return
	 */
	int insertShopCategory(ShopCategory shopCategory);

	/**
	 * 更新店铺类别信息
	 * 
	 * @param shopCategory
	 * @return
	 */
	int updateShopCategory(ShopCategory shopCategory);

	/**
	 * 删除店铺类别
	 * 
	 * @param shopCategoryId
	 * @return
	 */
	int deleteShopCategory(long shopCategoryId);

	/**
	 * 批量删除店铺类别
	 * 
	 * @param shopCategoryIdList
	 * @return
	 */
	int batchDeleteShopCategory(List<Long> shopCategoryIdList);
}
