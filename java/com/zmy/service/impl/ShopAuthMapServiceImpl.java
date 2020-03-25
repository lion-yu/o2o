package com.zmy.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zmy.dao.IShopAuthMapDao;
import com.zmy.dto.ShopAuthMapExecution;
import com.zmy.entity.ShopAuthMap;
import com.zmy.enums.ShopAuthMapStateEnum;
import com.zmy.exceptions.ShopAuthMapOperationException;
import com.zmy.exceptions.ShopOperationException;
import com.zmy.service.IShopAuthMapService;
import com.zmy.util.PageCalculator;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月15日下午8:48:52
*Class Description： 
*/
@Service
public class ShopAuthMapServiceImpl implements IShopAuthMapService{
	@Autowired
	private IShopAuthMapDao shopAuthMapDao;
	
	@Override
	public ShopAuthMapExecution listShopAuthMapByShopId(Long shopId, Integer pageIndex, Integer pageSize) {
		//空值判断
		if(shopId != null && pageIndex != null && pageSize != null) {
			int beginIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
			//查询返回该店铺的授权信息列表
			List<ShopAuthMap> shopAuthMapList = shopAuthMapDao.queryShopAuthMapListByShopId(shopId, beginIndex, pageSize);
			//返回总数
			int count = shopAuthMapDao.queryShopAuthMapCountByShopId(shopId);
			ShopAuthMapExecution se = new ShopAuthMapExecution();
			se.setShopAuthMapList(shopAuthMapList);
			se.setCount(count);
			return se;
		}else {
			return null;
		}
	}

	@Override
	public ShopAuthMap getShopAuthMapById(Long shopAuthId) {
		return shopAuthMapDao.queryShopAuthMapById(shopAuthId);
	}

	@Override
	@Transactional
	public ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException {
		//空值判断，主要是对店铺Id和员工Id做校验
		if(shopAuthMap != null && shopAuthMap.getShop() != null && shopAuthMap.getShop().getShopId() != null
				&& shopAuthMap.getEmployee() != null && shopAuthMap.getEmployee().getUserId() != null) {
			shopAuthMap.setCreateTime(new Date());
			shopAuthMap.setLastEditTime(new Date());
			shopAuthMap.setEnableStatus(1);
			shopAuthMap.setTitleFlag(0);
			try {
				//添加授权信息
				int effectedNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
				if(effectedNum <= 0) {
					throw new ShopOperationException("添加授权失败");
				}
				return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS,shopAuthMap);
			}catch(Exception e) {
				throw new ShopAuthMapOperationException("添加授权失败:" + e.getMessage());
			}
		}else {
			return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_INFO);
		}
	}

	@Override
	@Transactional
	public ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException {
		//空值判断，主要对授权id做校验
		if(shopAuthMap == null || shopAuthMap.getShopAuthId() == null) {
			return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_ID);
		}else {
			try {
				int effectedNum = shopAuthMapDao.updateShopAuthMap(shopAuthMap);
				if(effectedNum <= 0) {
					return new ShopAuthMapExecution(ShopAuthMapStateEnum.INNER_ERROR);
				}else {
					return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS,shopAuthMap);
				}
			}catch(Exception e) {
				throw new ShopAuthMapOperationException("modifyshopauthmap error:" + e.getMessage());
			}
		}
	}

	@Override
	public ShopAuthMap getShopAuthMapByUserId(ShopAuthMap shopAuthMap) {
		return shopAuthMapDao.queryShopAuthMapByUserId(shopAuthMap);
	}

}
