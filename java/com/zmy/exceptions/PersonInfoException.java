package com.zmy.exceptions;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年6月7日下午5:52:25
*Class Description： 
*/
public class PersonInfoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4819980323585211093L;
	public PersonInfoException(String msg) {
		super(msg);
	}
}
