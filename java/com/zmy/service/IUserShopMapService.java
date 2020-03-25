package com.zmy.service;

import java.util.List;

import com.zmy.dto.UserShopMapExecution;
import com.zmy.entity.UserShopMap;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月24日下午5:29:18
*Class Description： 用户店铺内积分
*/
public interface IUserShopMapService {
	/**
	 * 根据传入的查询信息分页查询用户积分列表
	 * @param userShopMapCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	UserShopMapExecution listUserShopMap(UserShopMap userShopMapCondition, int pageIndex,int pageSize);
	/**
	 * 根据用户id和店铺 id返回该用户在某个店铺的积分情况
	 * @param userId
	 * @param shopId
	 * @return
	 */
	UserShopMap getUserShopMap(long userId, long shopId);
	/**
	 * 根据用户id查询积分情况
	 * @param userId
	 * @return
	 */
	List<UserShopMap> getUserShopMap(long userId);
	/**
	 * 主要是修改用户积分信息
	 * @param userShopMap
	 * @return
	 */
	int modifyShopMap(UserShopMap userShopMap);
}
