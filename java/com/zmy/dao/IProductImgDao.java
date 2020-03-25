package com.zmy.dao;

import java.util.List;

import com.zmy.entity.ProductImg;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年4月6日上午1:40:00
*Class Description： 
*/
public interface IProductImgDao {
	/**
	 * 批量查询某个商品的详情页
	 * @param productId
	 * @return
	 */
	List<ProductImg> queryProductImgList(long productId);
	/**
	 * 批量添加商品详情图片
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);
	/**
	 * 删除某个商品的图片
	 * @param productId
	 * @return
	 */
	int deleteProductImgByProductId(long productId);
}
