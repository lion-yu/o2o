package com.zmy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zmy.dao.IProductCategoryDao;
import com.zmy.dao.IProductDao;
import com.zmy.dto.ProductCategoryExecution;
import com.zmy.entity.ProductCategory;
import com.zmy.enums.ProductCategoryStateEnum;
import com.zmy.exceptions.ProductCategoryOperationException;
import com.zmy.service.IProductCategoryService;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年4月4日下午10:37:15
*Class Description： 
*/
@Service
public class ProductCategoryServiceImpl implements IProductCategoryService{

	@Autowired
	private IProductCategoryDao productCategoryDao;
	@Autowired
	private IProductDao productDao;
	
	@Override
	public List<ProductCategory> getProductCategoryList(Long shopId) {
		List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
		return productCategoryList;
	}
	
	@Override
	@Transactional
	public ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException {
		if(productCategoryList != null && productCategoryList.size() > 0) {
			try {
				int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
				if(effectedNum <= 0) {
					throw new ProductCategoryOperationException("店铺类别创建失败");
				}else {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS, productCategoryList);
				}
			}catch(Exception e){
				throw new ProductCategoryOperationException("批量添加商品类别错误：" + e.getMessage());
			}
		}else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
		}
	}

	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException {
		//TODO 将此商品类别下的商品的类别id置为空
		//解除tb_product里的商品于改productcategoryid的关联
		try {
			int effectedNum = productDao.updateProductCategoryToNull(productCategoryId);
			if(effectedNum < 0) {
				throw new ProductCategoryOperationException("商品类别更新失败");
			}
		}catch(Exception e) {
			throw new ProductCategoryOperationException("deleteProductCategory error:" + e.getMessage());
		}
		//删除productCategory
		try {
			int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
			if(effectedNum <= 0) {
				throw new ProductCategoryOperationException("商品类别删除失败");
			}else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
		}catch(Exception e){
			throw new ProductCategoryOperationException("delete productCategory error" + e.getMessage());
		}
	}

}
