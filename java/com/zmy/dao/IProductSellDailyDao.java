package com.zmy.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zmy.entity.ProductSellDaily;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月15日下午3:17:22
*Class Description： 商品日销量
*/
public interface IProductSellDailyDao {
	/**
	 * 根据查询条件返回商品日销量的统计列表
	 * @param productSellDailyCondition
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	List<ProductSellDaily> queryProductSellDailyList(@Param("productSellDailyCondition")ProductSellDaily productSellDailyCondition,
			@Param("beginTime")Date beginTime, @Param("endTime")Date endTime);
	/**
	 * 统计平台所有商品的日销量
	 * @return
	 */
	int insertProdcuctSellDaily();
	/**
	 * 统计当天没有销量的商品，补全信息，将销量置为0
	 * @return
	 */
	int insertDefaultProductSellDaily();
}
