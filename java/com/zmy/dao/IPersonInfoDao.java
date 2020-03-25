package com.zmy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zmy.entity.PersonInfo;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月3日下午7:50:56
*Class Description： 
*/
public interface IPersonInfoDao {
	/**
	 * 根据用户id查询用户信息
	 * @param userId
	 * @return
	 */
	PersonInfo queryPersonInfoById(long userId);
	/**
	 * 添加用户
	 * @param personInfo
	 * @return
	 */
	int insertPersonInfo(PersonInfo personInfo);
	/**
	 * 修改用户信息
	 * 
	 * @param personInfo
	 * @return
	 */
	int updatePersonInfo(PersonInfo personInfo);
	/**
	 * 根据查询条件分页返回用户信息列表
	 * 
	 * @param personInfoCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<PersonInfo> queryPersonInfoList(@Param("personInfoCondition")PersonInfo personInfoCondition, @Param("rowIndex")int rowIndex, @Param("pageSize")int pageSize);

	/**
	 * 根据查询条件返回总数，配合queryPersonInfoList使用
	 * 
	 * @param personInfoCondition
	 * @return
	 */
	int queryPersonInfoCount(@Param("personInfoCondition")PersonInfo personInfoCondition);

}
