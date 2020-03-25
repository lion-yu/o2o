package com.zmy.service;

import java.util.Map;

import com.zmy.dto.UserProductMapExecution;
import com.zmy.entity.UserProductMap;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月21日上午1:26:21
*Class Description： 
*/
public interface IUserProductMapService {
	/**
	 * 通过传入的查询条件分页列出用户消费信息列表
	 * @param userProductMapCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	UserProductMapExecution listUserProductMap(UserProductMap userProductMapCondition, Integer pageIndex, Integer pageSize);
	/**
	 * 根据用户返回订单信息，显示三条
	 * @param userId
	 * @return
	 */
	Map<String, Object> getUserProductMapListByUser(UserProductMap userProductMap, int rowIndex, int pageSize);
	/**
	 * 通过prouduct_id查询商品的同销量
	 * @param productId
	 * @return
	 */
	Long getUserProductMapByProductId(Long productId);
	/**
	 * 添加新订单
	 * @param userProductMap
	 * @return
	 */
	int  addUserProductMap(UserProductMap userProductMap);
}
