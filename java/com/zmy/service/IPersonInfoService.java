package com.zmy.service;

import com.zmy.dto.PersonInfoExecution;
import com.zmy.entity.PersonInfo;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月6日下午11:41:19
*Class Description： 
*/
public interface IPersonInfoService {
	/**
	 * 根据用户id获取personInfo信息
	 * @param userId
	 * @return
	 */
	PersonInfo getPersonInfoById(Long userId);
	/**
	 * 根据查询条件分页返回用户信息列表
	 * 
	 * @param personInfoCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PersonInfoExecution getPersonInfoList(PersonInfo personInfoCondition, int pageIndex, int pageSize);

	/**
	 * 根据传入的PersonInfo修改对应的用户信息
	 * 
	 * @param pi
	 * @return
	 */
	PersonInfoExecution modifyPersonInfo(PersonInfo pi);
}
