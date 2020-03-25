package com.zmy.service;

import java.util.List;

import com.zmy.dto.ImageHolder;
import com.zmy.dto.ProductExecution;
import com.zmy.entity.Product;
import com.zmy.exceptions.ProductOperationException;

/**
 * @author 作者：张哥哥
 * @version 创建时间：2019年4月6日下午1:54:13 Class Description：
 */
public interface IProductService {
	/**
	 * 根据商品名查询商品列表
	 * @param productName
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	ProductExecution getProductListByName(String productName, int rowIndex, int pageSize);
	/**
	 * 查询商品列表并分页，可输入的条件有：商铺名（模糊），商品状态，店铺id，商品名称
	 * @param productCondition rowIndex pageSize
	 * @return
	 */
	ProductExecution getProductList(Product productCondition, int rowIndex, int pageSize);
	/**
	 * 通过商品id查询唯一的商品信息
	 * @param productId
	 * @return
	 */
	Product getProductById(Long productId);
	/**
	 * 添加商品信息以及图片处理
	 * 
	 * @param product
	 * @param thumbnail
	 * @param productImgs
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution addProduct(Product product, ImageHolder thumbnail,List<ImageHolder> productImgList) 
			throws ProductOperationException;
	/**
	 * 修改商品信息以及图片处理
	 * @param product
	 * @param thumbnail
	 * @param productImgList
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution modifyProduct(Product product, ImageHolder thumbnail,List<ImageHolder> productImgList) 
			throws ProductOperationException;
}
