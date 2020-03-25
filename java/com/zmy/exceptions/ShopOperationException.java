package com.zmy.exceptions;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年3月3日下午3:11:58
*Class Description： 
*/
public class ShopOperationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ShopOperationException(String msg) {
		super(msg);
	}
}
