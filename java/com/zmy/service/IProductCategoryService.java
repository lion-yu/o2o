package com.zmy.service;

import java.util.List;

import com.zmy.dto.ProductCategoryExecution;
import com.zmy.entity.ProductCategory;
import com.zmy.exceptions.ProductCategoryOperationException;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年4月4日下午10:35:24
*Class Description： 
*/
public interface IProductCategoryService {
	/**
	 * 查询某个店铺下所有的商品类别信息
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> getProductCategoryList(Long shopId);
	/**
	 * 批量插入商品分类
	 * @param productCategoryList
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList) throws 
		ProductCategoryOperationException;
	/**
	 * 将此类别下的商品里的类别id置为空，在删除掉该商品类别
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException;
}
