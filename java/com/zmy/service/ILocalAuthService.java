package com.zmy.service;

import java.util.Date;

import com.zmy.dto.LocalAuthExecution;
import com.zmy.entity.LocalAuth;
import com.zmy.exceptions.LocalAuthOperationException;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月8日上午7:46:20
*Class Description： 
*/
public interface ILocalAuthService {
	/**
	 * 根据账号名和密码查询账户信息
	 * @param userName
	 * @param password
	 * @return
	 */
	public LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password);
	/**
	 * 通过userId查询账户信息
	 * @param userId
	 * @return
	 */
	public LocalAuth getLocalAuthByUserId(long userId);
	/**
	 * 注册账号
	 * 绑定微信，生成平台专属账号
	 * @param localAuth
	 * @return
	 */
	LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException;
	/**
	 * 修改平台账号的登陆密码
	 * @param userId
	 * @param userName
	 * @param password
	 * @param newPassword
	 * @param lastEditTime
	 * @return
	 */
	LocalAuthExecution modifyLocalAuth(Long userId, String userName, String password, String newPassword,
			Date lastEditTime) throws LocalAuthOperationException;
}
