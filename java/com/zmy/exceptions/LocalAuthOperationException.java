package com.zmy.exceptions;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月8日下午4:46:41
*Class Description： 
*/
public class LocalAuthOperationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2988499009059108106L;
	public LocalAuthOperationException(String msg) {
		super(msg);
	}
}
