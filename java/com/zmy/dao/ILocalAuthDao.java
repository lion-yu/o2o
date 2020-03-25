package com.zmy.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.zmy.entity.LocalAuth;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月7日下午11:31:22
*Class Description： 
*/
public interface ILocalAuthDao {
	/**
	 * 通过账号和密码查询对应信息，供登录使用
	 * @param userName
	 * @param password
	 * @return
	 */
	LocalAuth queryLocalAuthByUserNameAndPwd(@Param("username") String userName, @Param("password") String password);
	/**
	 * 通过用户id查询对应localauth
	 * @param userId
	 * @return
	 */
	LocalAuth queryLocalAuthByUserId(long userId);
	/**
	 * 添加平台账号
	 * @param localAuth
	 * @return
	 */
	int insertLocalAuth(LocalAuth localAuth);
	/**
	 * 通过userid，username,password更改密码
	 * @param userId
	 * @param username
	 * @param password
	 * @param newPassword
	 * @param lastEditTime
	 * @return
	 */
	int updateLocalAuth(@Param("userId") Long userId, @Param("userName") String username, @Param("password") String password,
			@Param("newPassword") String newPassword, @Param("lastEditTime") Date lastEditTime);
}
