package com.zmy.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月7日下午10:09:10
*Class Description： 
*/
public class EncryptPropertyPlaceHolderConfigurer extends PropertyPlaceholderConfigurer {
	//需要加密的字段数组
	private String[] encryptPropNames = {"jdbc.username","jdbc.password"};

	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		if(isEncryptProp(propertyName)) {
			//对已加密字段进行解密工作
			String decryptValue = DESUtil.getDecrypString(propertyValue);
			return decryptValue;
		}else {
			return propertyValue;
		}
	}
	/**
	 * 该属性是否已加密
	 * @param propertyName
	 * @return
	 */
	private boolean isEncryptProp(String propertyName) {
		for(String encryptpropertyName : encryptPropNames) {
			if(encryptpropertyName.equals(propertyName)) {
				return true;
			}
		}
		return false;
	}
	
}
