package com.zmy.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zmy.dao.IShopDao;
import com.zmy.dto.ImageHolder;
import com.zmy.dto.ShopExecution;
import com.zmy.entity.Shop;
import com.zmy.enums.ShopStateEnum;
import com.zmy.exceptions.ShopOperationException;
import com.zmy.service.IShopService;
import com.zmy.util.ImageUtil;
import com.zmy.util.PageCalculator;
import com.zmy.util.PathUtil;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年3月2日下午10:08:48
*Class Description： 
*/

@Service
public class ShopServiceImpl implements IShopService {
	
	@Autowired
	private IShopDao shopDao;
	@Transactional
	public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {
		//空值判断
		if(shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			//给店铺信息赋初始值
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			//添加店铺信息
			int effectNum = shopDao.insertShop(shop);
			if(effectNum <= 0) {
				throw new ShopOperationException("店铺创建失败");
			}else {
				if(thumbnail.getImage() != null) {
					//存储图片
					try {
					addShopImg(shop,thumbnail);
					}catch (Exception e) {
						throw new ShopOperationException("addShopImg error:" + e.getMessage());
					}
					//更新店铺的图片地址
					effectNum = shopDao.updateShop(shop);
					if(effectNum <=0) {
						throw new ShopOperationException("更新图片地址失败");
					}
				}
			}
		}catch (Exception e) {
			throw new ShopOperationException("addShop error:" + e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK,shop);
	}
	
	private void addShopImg(Shop shop, ImageHolder thumbnail) {
		//获取shop图片目录的相对值路径
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(thumbnail,dest);
		shop.setShopImg(shopImgAddr);
	}
	
	@Override
	public Shop getShopById(Long shopId) {
		return shopDao.queryShopById(shopId);
	}
	
	@Override
	public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail)
			throws ShopOperationException {
		//1.判断是否需要处理图片
		try {
		if(shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}else {
			if(thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())) {
				Shop tempShop = shopDao.queryShopById(shop.getShopId());
				if(tempShop.getShopImg() != null) {
					ImageUtil.deleteFileOrPath(tempShop.getShopImg());
				}
				addShopImg(shop, thumbnail);
			}
		}
		//2.更新店铺信息
		shop.setLastEditTime(new Date());
		int effectedNum = shopDao.updateShop(shop);
		if(effectedNum <= 0) {
			return new ShopExecution(ShopStateEnum.INNER_ERROR);
		}else{
			shop = shopDao.queryShopById(shop.getShopId());
			return new ShopExecution(ShopStateEnum.SUCCESS,shop);
		}
		}catch(Exception e){
			throw new ShopOperationException("modifyShop error:" + e.getMessage());
		}
	}

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
		int count = shopDao.queryShopListCount(shopCondition);
		ShopExecution se = new ShopExecution();
		if(shopList != null) {
			se.setShopList(shopList);
			se.setCount(count);
		}else {
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return  se;
	}

}
