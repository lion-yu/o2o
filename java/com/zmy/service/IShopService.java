package com.zmy.service;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年3月2日下午10:07:03
*Class Description： 
*/

import com.zmy.dto.ImageHolder;
import com.zmy.dto.ShopExecution;
import com.zmy.entity.Shop;
import com.zmy.exceptions.ShopOperationException;

public interface IShopService {
	/**
	 * 根据shopCondition分页返回相应店铺列表
	 * @param shopConditon
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
	/**
	 * 通过店铺id获取店铺信息
	 * @param shopId
	 * @return
	 */
	Shop getShopById(Long shopId);
	/**
	 * 
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
//	ShopExecution modifyShop(Shop shop,InputStream shopImgInputStream,String fileName) throws ShopOperationException;
	ShopExecution modifyShop(Shop shop,ImageHolder thumbnail) throws ShopOperationException;
	/**
	 * 注册店铺信息，包括图片处理
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	//ShopExecution addShop(Shop shop,InputStream shopImgInputStream,String fileName) throws ShopOperationException;
	ShopExecution addShop(Shop shop,ImageHolder thumbnail) throws ShopOperationException;
}
