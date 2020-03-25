package com.zmy.exceptions;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年4月6日下午1:55:34
*Class Description： 
*/
public class ProductOperationException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4141034161528273134L;

	public ProductOperationException(String msg) {
		super(msg);
	}
}
