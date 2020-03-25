package com.zmy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zmy.entity.UserAwardMap;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月15日上午10:21:20
*Class Description： 奖品兑换信息
*/
public interface IUserAwardMapDao {
	/**
	 * 根据传进来的查询条件分页返回用户兑换奖品记录的列表信息
	 * @param userAwardCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<UserAwardMap> queryUserMapList(@Param("userAwardCondition") UserAwardMap userAwardCondition,@Param("rowIndex")int rowIndex,
			@Param("pageSize")int pageSize);
	/**
	 * 配合queryUserMapList返回相同查询条件下的兑换奖品记录数
	 * @param userAwardCondition
	 * @return
	 */
	int queryUserAwardMapCount(@Param("userAwardCondition") UserAwardMap userAwardCondition);
	/**
	 * 根据userAwardId返回某条奖品兑换信息
	 * @param userAwardId
	 * @return
	 */
	UserAwardMap queryUserAwardMapById(long userAwardId);
	/**
	 * 添加一条奖品兑换信息
	 * @param userAwardMap
	 * @return
	 */
	int insertUserAwardMap(UserAwardMap userAwardMap);
	/**
	 * 更新奖品兑换信息，主要更新奖品领取状态
	 * @param userAwardMap
	 * @return
	 */
	int updateUserAwardMap(UserAwardMap userAwardMap);
}
