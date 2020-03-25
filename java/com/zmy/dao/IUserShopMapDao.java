package com.zmy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zmy.entity.UserShopMap;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月15日下午4:46:42
*Class Description： 
*/
public interface IUserShopMapDao {
	/**
	 * 根据查询条件分页返回店铺积分列表
	 * @param userShopCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<UserShopMap> queryUserShopMapList(@Param("userShopCondition")UserShopMap userShopCondition,
			@Param("rowIndex")int rowIndex, @Param("pageSize")int pageSize);
	/**
	 * 配合上面的查询条件返回用户店铺积分记录总数
	 * @param userShopCondition
	 * @return
	 */
	int queryUserShopMapCount(@Param("userShopCondition")UserShopMap userShopCondition);
	/**
	 * 根据用户传入的id和shopid查询该用户在某个店铺下的积分信息
	 * @param userId
	 * @param shopId
	 * @return
	 */
	UserShopMap queryUserShopMap(@Param("userId")long userId, @Param("shopId")long shopId);
	List<UserShopMap> queryUserShopMapByUser(long userId);
	/**
	 * 添加一条用户店铺的积分记录
	 * @param userShopMap
	 * @return
	 */
	int insertUserShopMap(UserShopMap userShopMap);
	/**
	 * 更新用户在某个店铺的积分
	 * @param userShopMap
	 * @return
	 */
	int updateUserShopMapPoint(UserShopMap userShopMap);
}
