package com.zmy.exceptions;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年4月5日下午4:05:55
*Class Description： 
*/
public class ProductCategoryOperationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5658959044518161699L;
	public ProductCategoryOperationException(String msg) {
		super(msg);
	}
}
