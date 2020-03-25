package com.zmy.service;

import com.zmy.dto.UserAwardMapExecution;
import com.zmy.entity.UserAwardMap;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月24日下午6:15:40
*Class Description： 用户奖品兑换记录
*/
public interface IUserAwardMapService {
	/**
	 * 根据传入的查询条件分页获取映射列表和总数
	 * @param userAwardConditon
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	UserAwardMapExecution listUserAwardMap(UserAwardMap userAwardConditon, Integer pageIndex, Integer pageSize);
	/**
	 * 通过 userAwardMapId查询用户领取的奖品信息
	 * @param userAwardMapId
	 * @return
	 */
	UserAwardMap getUserAwardMapById(long userAwardMapId);
	
	/**
	 * 领取奖品，添加映射信息
	 * @param userAwardMapCondition
	 * @return
	 */
	UserAwardMapExecution addUserAwardMap(UserAwardMap userAwardMapCondition);
	/**
	 * 修改映射信息，主要是修改奖品的领取状态
	 * @param userAwardMapCondition
	 * @return
	 */
	Integer modify(UserAwardMap userAwardMapCondition);
}
