package com.zmy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zmy.entity.Product;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年4月6日上午1:31:53
*Class Description： 
*/
public interface IProductDao {
	/**
	 * 根据商品名称实现分页查询商品列表
	 * @param productName
	 * @return
	 */
	List<Product> queryProductListByName(@Param("productName") String productName, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
	/**
	 * 查询商品列表并分页，可输入的条件由：商品名（模糊），商品状态，店铺Id，商品类别
	 * rowIndex表示从第几行取数据
	 * pageSize表示要返回多少行数据
	 */
	List<Product> queryProductList(@Param("productCondition") Product productCondition,@Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);
	/**
	 * 查询对应的商品总数
	 * @param productCondition
	 * @return
	 */
	int queryProductCount(@Param("productCondition") Product productCondition);
	
	
	/**
	 * 通过product id查询店铺
	 * @param productId
	 * @return Product
	 */
	Product queryProductById(Long productId);
	/**
	 * 插入商品
	 * @param Product
	 * @return 
	 */
	int insertProduct(Product product);
	/**
	 * 更新商品信息
	 * @param product
	 * @return 
	 */
	int updateProduct(Product product);
	/**
	 * 删除商品类别前，将商品类别ID置为空
	 * @param productCategoryId
	 * @return
	 */
	int updateProductCategoryToNull(long productCategoryId);
}
