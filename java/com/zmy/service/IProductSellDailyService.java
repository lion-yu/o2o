package com.zmy.service;

import java.util.Date;
import java.util.List;

import com.zmy.entity.ProductSellDaily;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月20日下午4:23:28
*Class Description： 每日定时对所有店铺的商铺销量进行统计
*/
public interface IProductSellDailyService {
	/**
	 * 每日定时对所有店铺的商品销量进行统计
	 */
	void dailyCalculate();
	/**
	 * 根据查询条件返回商品日销量的统计列表
	 * @param productSellDailyCondition
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	List<ProductSellDaily> listProductSellDaily(ProductSellDaily productSellDailyCondition, Date beginTime, Date endTime); 
}
