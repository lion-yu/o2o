package com.zmy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zmy.dao.IProductDao;
import com.zmy.dao.IProductImgDao;
import com.zmy.dto.ImageHolder;
import com.zmy.dto.ProductExecution;
import com.zmy.entity.Product;
import com.zmy.entity.ProductImg;
import com.zmy.enums.ProductStateEnum;
import com.zmy.exceptions.ProductOperationException;
import com.zmy.service.IProductService;
import com.zmy.util.ImageUtil;
import com.zmy.util.PageCalculator;
import com.zmy.util.PathUtil;

/**
 * @author 作者：张哥哥
 * @version 创建时间：2019年4月6日下午2:41:31 
 * Class Description：
 */
@Service
public class ProductServiceImpl implements IProductService {
	@Autowired
	private IProductDao productDao;
	@Autowired
	private IProductImgDao productImgDao;

	@Override
	@Transactional
	// 1.处理缩略图，获取缩略图相对路径并赋值给product
	// 2.往tb_product写入商品信息，获取productId
	// 3.结合productId批量处理商品详情图
	// 4.将商品详情图列表批量插入tb_product_img中
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
			throws ProductOperationException {
		// 控制判断
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			// 给商品设置上默认属性
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			// 默认为上架的状态
			product.setEnableStatus(1);
			// 该商品缩略图不为空则添加
			if (thumbnail != null) {
				addThumbnail(product, thumbnail);
			}
			try {
				// 创建商品信息
				int effectedNum = productDao.insertProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperationException("创建商品失败");
				}
			} catch (ProductOperationException e) {
				throw new ProductOperationException("创建商品失败：" + e.getMessage());
			}
			// 若商品详情图不为空则添加
			if (productImgHolderList != null && productImgHolderList.size() > 0) {
				addProductImgList(product, productImgHolderList);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS, product);
		} else {
			// 传参为空则返回空值错误信息
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	/**
	 * 添加缩略图
	 * 
	 * @param product
	 * @param thumbnail
	 */
	private void addThumbnail(Product product, ImageHolder thumbnail) {
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		product.setImgAddr(thumbnailAddr);
	}

	/**
	 * 批量添加照片
	 * 
	 * @param product
	 * @param productImgHolderList
	 */
	private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
		// 获取图片存储路径
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> productImgList = new ArrayList<>();
		// 遍历图片一次去处理，并添加进productImg实体类里
		for (ImageHolder productImgHolder : productImgHolderList) {
			String imgAddr = ImageUtil.generateNormalImg(productImgHolder, dest);
			ProductImg productImg = new ProductImg();
			productImg.setPriority(10);
			productImg.setImgAddr(imgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImgList.add(productImg);
		}
		// 如果确实是有图片是需要添加的， 就执行批量添加操作
		if (productImgList.size() > 0) {
			try {
				int effectedNum = productImgDao.batchInsertProductImg(productImgList);
				if (effectedNum <= 0) {
					throw new ProductOperationException("创建商品详情图片失败");
				}
			} catch (Exception e) {
				throw new ProductOperationException("创建商品详情图片失败" + e.getMessage());
			}
		}
	}

	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
		int count = productDao.queryProductCount(productCondition);
		ProductExecution pe = new ProductExecution();
		pe.setProductList(productList);
		pe.setCount(count);
		return pe;
	}

	@Override
	public Product getProductById(Long productId) {
		return productDao.queryProductById(productId);
	}

	@Override
	@Transactional
	public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws ProductOperationException {
		// 1.若缩略图有参数值，则处理缩略图
		// 若原先存在缩略图则现删除再添加新图，之后获取缩略图相对路径并赋值给product
		// 2.若商品详情页列表参数有值，对商品详情图片列表进行同样的操作
		// 3. 将tb_product_img下面的该商品原先的商品详情图记录全部删除
		// 4.更新tb_product,tb_product_img的信息

		// 控制判断
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			// 给商品设置上默认属性
			product.setLastEditTime(new Date());
			// 若商品缩略图不为空且原有缩略图不为空则删除原有缩略图并添加
			if (thumbnail != null) {
				// 先获取一遍原有信息，因为原有信息里有图片地址
				Product tempProduct = productDao.queryProductById(product.getProductId());
				if (tempProduct.getImgAddr() != null) {
					ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
				}
				addThumbnail(product, thumbnail);
			}
			// 如果有新传入的商品详情图，则将原先的删除，并添加新的图片
			if (productImgList != null && productImgList.size() > 0) {
				deleteProductImgList(product.getProductId());
				addProductImgList(product, productImgList);
			}
			try {
				// 更新商品信息
				int effectedNum = productDao.updateProduct(product);
				if (effectedNum < 0) {
					throw new ProductOperationException("更新商品信息失败");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS, product);
			} catch (Exception e) {
				throw new ProductOperationException("更新商品详情页失败" + e.toString());
			}
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	private void deleteProductImgList(Long productId) {
		// 根据productid获取原来的照片
		List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
		for (ProductImg productImg : productImgList) {
			ImageUtil.deleteFileOrPath(productImg.getImgAddr());
		}
		// 删除数据库里原有的图片信息
		productImgDao.deleteProductImgByProductId(productId);
	}

	@Override
	public ProductExecution getProductListByName(String productName, int rowIndex, int pageSize) {
		List<Product> productList = productDao.queryProductListByName(productName, rowIndex, pageSize);
		ProductExecution pe = new ProductExecution();
		pe.setCount(productList.size());
		pe.setProductList(productList);
		return pe;
	}
}
