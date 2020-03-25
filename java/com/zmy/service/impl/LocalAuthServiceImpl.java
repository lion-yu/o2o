package com.zmy.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zmy.dao.ILocalAuthDao;
import com.zmy.dto.LocalAuthExecution;
import com.zmy.entity.LocalAuth;
import com.zmy.enums.LocalAuthStateEnum;
import com.zmy.exceptions.LocalAuthOperationException;
import com.zmy.service.ILocalAuthService;
import com.zmy.util.MD5;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月8日上午7:52:41
*Class Description： 
*/
@Service
public class LocalAuthServiceImpl implements ILocalAuthService {
	@Autowired
	private ILocalAuthDao localAuthDao;
	@Override
	public LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password) {
		return localAuthDao.queryLocalAuthByUserNameAndPwd(userName, MD5.getMd5(password));
	}

	@Override
	public LocalAuth getLocalAuthByUserId(long userId) {
		return localAuthDao.queryLocalAuthByUserId(userId);
	}


	@Override
	@Transactional
	public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException {
		//空值判断，传入的localAuth账号密码，用户信息的user_id为空则直接返回错误
		if(localAuth == null || localAuth.getPassword() == null || localAuth.getUserName() == null
				|| localAuth.getPersonInfo() == null || localAuth.getPersonInfo().getUserId() == null) {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
		//查询此用户是否已绑定过平台账号
		LocalAuth tempAuth = localAuthDao.queryLocalAuthByUserId(localAuth.getPersonInfo().getUserId());
		if(tempAuth != null) {
			//如果绑定过则直接退出，以保证平台账号的唯一性
			return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
		}
		try {
			//如果之前没有绑定账号，则创建一个平台账号与该用户绑定
			localAuth.setCreateTime(new Date());
			localAuth.setLastEditTime(new Date());
			//对密码进行MD5加密
			localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
			int effectedNum = localAuthDao.insertLocalAuth(localAuth);
			if(effectedNum <= 0) {
				throw new LocalAuthOperationException("绑定账号失败");
			}else {
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS, localAuth);
			}
		}catch(Exception e) {
			throw new LocalAuthOperationException("insert localAuth error:" + e.getMessage());
		}
	}

	@Override
	@Transactional
	public LocalAuthExecution modifyLocalAuth(Long userId, String userName, String password, String newPassword,
			Date lastEditTime) throws LocalAuthOperationException {
		//非空判断，判断传入的用户Id,账号，新旧密码是否为空，新旧密码是否相同，若不满足条件则返回错误信息
		if(userId != null && userName != null && password != null && newPassword != null
				&& !password.equals(newPassword)) {
			try {
				//更新密码，并对新密码进行MD5加密
				int effectedNum = localAuthDao.updateLocalAuth(userId, userName, MD5.getMd5(password), MD5.getMd5(newPassword), new Date());
				//判断更新是否成功
				if(effectedNum <= 0) {
					throw new LocalAuthOperationException(LocalAuthStateEnum.CHANGE_PSW_FAILED.getStateInfo());
				}
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
			}catch(Exception e) {
				throw new LocalAuthOperationException("更新密码失败：" + e.toString());
			}
		}
		return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
	}
}
