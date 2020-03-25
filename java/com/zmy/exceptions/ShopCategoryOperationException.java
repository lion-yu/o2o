package com.zmy.exceptions;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年6月7日下午5:56:51
*Class Description： 
*/
public class ShopCategoryOperationException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6095703116046873931L;

	public ShopCategoryOperationException(String msg) {
		super(msg);
	}
}
