package com.zmy.dao;

import com.zmy.entity.WeChatAuth;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月3日下午7:53:51
*Class Description： 
*/
public interface IWechatAuthDao {
	/**
	 * 根据openId查询本平台对应的唯一微信账号
	 * @param openId
	 * @return
	 */
	WeChatAuth queryWechatAuthByOpenId(String openId);
	/**
	 * 向平台添加微信账号
	 * @param weChatAuth
	 * @return
	 */
	int insertWechatAuth(WeChatAuth weChatAuth);
}
