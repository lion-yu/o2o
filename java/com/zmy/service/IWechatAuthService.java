package com.zmy.service;

import com.zmy.dto.WechatAuthExecution;
import com.zmy.entity.WeChatAuth;
import com.zmy.exceptions.WechatAuthOperationException;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月6日下午10:36:57
*Class Description： 
*/
public interface IWechatAuthService {
	/**
	 * 通过openId查找怕平台对应的微信账号
	 * @param openId
	 * @return
	 */
	WeChatAuth getWechatAuthByOpenId(String openId);
	/**
	 * 注册本平台的微信账号
	 * @param wechatAuth
	 * @return
	 * @throws WechatAuthOperationException
	 */
	WechatAuthExecution register(WeChatAuth wechatAuth) throws WechatAuthOperationException;
}
