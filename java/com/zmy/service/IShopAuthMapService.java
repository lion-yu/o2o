package com.zmy.service;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月15日下午8:42:06
*Class Description： 
*/

import com.zmy.dto.ShopAuthMapExecution;
import com.zmy.entity.ShopAuthMap;
import com.zmy.exceptions.ShopAuthMapOperationException;

public interface IShopAuthMapService {
	/**
	 * 根据店铺id分页显示该店铺的授权信息
	 * @param shopId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ShopAuthMapExecution listShopAuthMapByShopId(Long shopId, Integer pageIndex, Integer pageSize);
	/**
	 * 根据shopAuthId返回对应的授权信息
	 * @param shopAuthId
	 * @return
	 */
	ShopAuthMap getShopAuthMapById(Long shopAuthId);
	/**
	 * 添加授权信息（扫码）
	 * @param shopAuthMap
	 * @return
	 * @throws ShopAuthMapOperationException
	 */
	ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException;
	/**
	 * 更新授权信息，包括职位，状态等
	 * @param shopAuthMap
	 * @return
	 * @throws ShopAuthMapOperationException
	 */
	ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap)  throws ShopAuthMapOperationException;
	/**
	 * 获取用户授权信息，确认扫码者是否有权限
	 * @param shopAuthMap
	 * @return
	 */
	ShopAuthMap getShopAuthMapByUserId(ShopAuthMap shopAuthMap);
}
