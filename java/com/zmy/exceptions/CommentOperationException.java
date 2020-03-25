package com.zmy.exceptions;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月26日下午3:07:23
*Class Description： 用户添加评价
*/
public class CommentOperationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8950538274715210636L;
	public CommentOperationException(String msg) {
		super(msg);
	}

}
