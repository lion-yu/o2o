package com.zmy.dao;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年4月4日下午9:48:38
*Class Description： 
*/

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zmy.entity.ProductCategory;

public interface IProductCategoryDao {
	/**
	 * 两个参数mybatis无法判别，用注解标明
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 */
	int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);
	/**
	 * 通过shopid查询商品类别
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategoryList(Long shopId);
	/**
	 * 批量新增商品类别 返回影响的行数
	 * @param productCategoryList
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);
}
