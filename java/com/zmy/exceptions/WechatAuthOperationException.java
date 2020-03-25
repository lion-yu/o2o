package com.zmy.exceptions;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月6日下午10:52:18
*Class Description： 
*/
public class WechatAuthOperationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4719778818848711465L;
	public WechatAuthOperationException(String msg){
		super(msg);
	}
}
