package com.zmy.service;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月8日下午4:29:41
*Class Description： 
*/
public interface ICacheService {
	/**
	 * 依据key前缀删除匹配该模式下的所有key-value如传入：shopcategory，则shopcategory_allfirstlevel等
	 * 以shopcategory打头的key_value都会被清空
	 * @param keyPrefix
	 */
	void removeFromCache(String keyPrefix);
}
