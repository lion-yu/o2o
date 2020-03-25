package com.zmy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zmy.entity.Shop;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年3月1日上午12:30:35
*Class Description： 
*/
public interface IShopDao {
	/**
	 * 分页查询某人所有店铺列表（可输入店铺名(模糊)，店铺状态，店铺类别，区域id,owner）
	 * rowIndex表示从第几行取数据
	 * pageSize表示要返回多少行数据
	 */
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,@Param("rowIndex") int rowIndex,
			@Param("rowSize") int rowSize);
	int queryShopListCount(@Param("shopCondition") Shop shopCondition);
	
	
	/**
	 * 通过shop id查询店铺
	 * @param shopId
	 * @return shop
	 */
	Shop queryShopById(Long shopId);
	/**
	 * 新增店铺
	 * @param shop
	 * @return 
	 */
	int insertShop(Shop shop);
	/**
	 * 更新店铺信息
	 * @param shop
	 * @return 
	 */
	int updateShop(Shop shop);
}
