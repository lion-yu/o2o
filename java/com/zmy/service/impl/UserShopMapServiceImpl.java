package com.zmy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zmy.dao.IUserShopMapDao;
import com.zmy.dto.UserShopMapExecution;
import com.zmy.entity.UserShopMap;
import com.zmy.service.IUserShopMapService;
import com.zmy.util.PageCalculator;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月24日下午5:32:31
*Class Description： 
*/
@Service
public class UserShopMapServiceImpl implements IUserShopMapService {
	@Autowired
	private IUserShopMapDao userShopMapDao;
	@Override
	public UserShopMap getUserShopMap(long userId, long shopId) {
		return userShopMapDao.queryUserShopMap(userId, shopId);
	}

	@Override
	public UserShopMapExecution listUserShopMap(UserShopMap userShopMapCondition, int pageIndex, int pageSize) {
		if(userShopMapCondition != null && pageIndex != -1 && pageSize != -1) {
			int beginIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
			List<UserShopMap> userShopMapList = userShopMapDao.queryUserShopMapList(userShopMapCondition, beginIndex, pageSize);
			int count = userShopMapDao.queryUserShopMapCount(userShopMapCondition);
			UserShopMapExecution ue = new UserShopMapExecution();
			ue.setCount(count);
			ue.setUserShopMapList(userShopMapList);
			return ue;
		}else {
			return null;
		}
	}

	@Override
	public List<UserShopMap> getUserShopMap(long userId) {
		return userShopMapDao.queryUserShopMapByUser(userId);
	}

	@Override
	public int modifyShopMap(UserShopMap userShopMap) {
		int effectedNum = userShopMapDao.updateUserShopMapPoint(userShopMap);
		return effectedNum;
	}

}
