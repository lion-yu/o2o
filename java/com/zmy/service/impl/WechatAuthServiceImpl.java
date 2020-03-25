package com.zmy.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zmy.dao.IPersonInfoDao;
import com.zmy.dao.IWechatAuthDao;
import com.zmy.dto.WechatAuthExecution;
import com.zmy.entity.PersonInfo;
import com.zmy.entity.WeChatAuth;
import com.zmy.enums.WechatAuthStateEnum;
import com.zmy.exceptions.WechatAuthOperationException;
import com.zmy.service.IWechatAuthService;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月6日下午11:00:48
*Class Description： 
*/
@Service
public class WechatAuthServiceImpl implements IWechatAuthService{
	//开启日志，记录程序运行信息
	private Logger logger = LoggerFactory.getLogger(WeChatAuth.class);
	@Autowired
	private IWechatAuthDao wechatAuthDao;
	@Autowired
	private IPersonInfoDao personInfoDao;
	@Override
	public WeChatAuth getWechatAuthByOpenId(String openId) {
		return wechatAuthDao.queryWechatAuthByOpenId(openId);
	}

	@Override
	@Transactional
	public WechatAuthExecution register(WeChatAuth wechatAuth) throws WechatAuthOperationException {
		//空值判断
		if(wechatAuth == null || wechatAuth.getOpenId() == null) {
			return new WechatAuthExecution(WechatAuthStateEnum.NULL_AUTH_INFO);
		}
		try {
			//设置创建时间
			wechatAuth.setCreateTime(new Date());
			//如果微信账号里夹带者用户信息并且用户id为空，则认为该用户第一次使用本平台（且通过微信登录）
			//则自动创建用户信息
			if(wechatAuth.getPersonInfo() != null && wechatAuth.getPersonInfo().getUserId() == null) {
				try {
					wechatAuth.getPersonInfo().setCreateTime(new Date());
					wechatAuth.getPersonInfo().setEnableStatus(1);
					PersonInfo personInfo = wechatAuth.getPersonInfo();
					int effectedNum = personInfoDao.insertPersonInfo(personInfo);
					wechatAuth.setPersonInfo(personInfo);
					if(effectedNum <= 0) {
						throw new WechatAuthOperationException("添加用户信息失败");
					}
				}catch(Exception e) {
					logger.error("insertPersonInfo error:" + e.toString());
					throw new WechatAuthOperationException("insertPersonInfo error:" + e.getMessage());
				}
			}
			//创建专属于本平台的微信账号
			int effectedNum = wechatAuthDao.insertWechatAuth(wechatAuth);
			if(effectedNum <= 0) {
				throw new WechatAuthOperationException("账号创建失败");
			}else {
				return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS, wechatAuth);
			}
		}catch(Exception e) {
			logger.error("insert wechatAuth error:" + e.toString());
			throw new WechatAuthOperationException("insertWechatAuth error:" + e.getMessage());
		}
	}

}
