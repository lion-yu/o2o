package com.zmy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zmy.entity.ShopAuthMap;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月15日下午6:07:42
*Class Description： 店铺授权
*/
public interface IShopAuthMapDao {
	/**
	 * 分页查出该店铺下面的授权信息
	 * @param shopId
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<ShopAuthMap> queryShopAuthMapListByShopId(@Param("shopId")long shopId, @Param("rowIndex")int rowIndex,
			@Param("pageSize")int pageSize);
	/**
	 * 获取授权总数
	 * @param shopId
	 * @return
	 */
	int  queryShopAuthMapCountByShopId(@Param("shopId")long shopId);
	/**
	 * 增加一条店铺与店员的授权关系
	 * @param shopAuthMap
	 * @return
	 */
	int insertShopAuthMap(ShopAuthMap shopAuthMap);
	/**
	 * 更新授权信息
	 * @param shopAuthMap
	 * @return
	 */
	int updateShopAuthMap(ShopAuthMap shopAuthMap);
	/**
	 * 解除员工授权
	 * @param shopAuthMap
	 * @return
	 */
	int deleteShopAuthMap(ShopAuthMap shopAuthMap);
	/**
	 * 通过shopAuthId查询员工授权信息
	 * @param shopAuthId
	 * @return
	 */
	ShopAuthMap queryShopAuthMapById(Long shopAuthId);
	/**
	 * 通过shop_id + user_id获取操作员授权信息
	 * @param shopAuthId
	 * @return
	 */
	ShopAuthMap queryShopAuthMapByUserId(ShopAuthMap shopAuthMap);
}
