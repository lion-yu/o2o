package com.zmy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zmy.dao.IUserProductMapDao;
import com.zmy.dto.UserProductMapExecution;
import com.zmy.entity.UserProductMap;
import com.zmy.service.IUserProductMapService;
import com.zmy.util.PageCalculator;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月21日上午1:29:37
*Class Description： 
*/
@Service
public class UserProductMapServiceImpl implements IUserProductMapService {
	@Autowired
	private IUserProductMapDao userProductMapDao;
	@Override
	public UserProductMapExecution listUserProductMap(UserProductMap userProductMapCondition, Integer pageIndex,
			Integer pageSize) {
		if(userProductMapCondition != null && pageIndex != null && pageSize != null) {
			//页转行
			int beginIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
			List<UserProductMap> userProductMapList = userProductMapDao.queryUserProductMapList(userProductMapCondition, beginIndex, pageSize);
			//按照同等的查询条件获取总数
			int count = userProductMapDao.queryUserProductMapCount(userProductMapCondition);
			UserProductMapExecution se = new UserProductMapExecution();
			se.setUserProductMapList(userProductMapList);
			se.setCount(count);
			return se;
		}else {
			return null;
		}
	}
	/**
	 * 获取顾客所有订单
	 */
	@Override
	public Map<String, Object> getUserProductMapListByUser(UserProductMap userProductMap, int pageIndex, int pageSize) {
		Map<String, Object> resultMap = new HashMap<>();
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<UserProductMap> userProductMapList = userProductMapDao.queryUserProductMapListByUser(userProductMap, rowIndex, pageSize);
		int count = userProductMapDao.queryUserProductMapListByUserCount(userProductMap);
		resultMap.put("userProductMapList", userProductMapList);
		resultMap.put("count", count);
		return resultMap;
		
	}
	@Override
	public Long getUserProductMapByProductId(Long productId) {
		return userProductMapDao.queryUserProductMapByProductId(productId);
	}
	@Override
	public int addUserProductMap(UserProductMap userProductMap) {
		int effectedNum = userProductMapDao.insertUserProductMap(userProductMap);
		return effectedNum;
	}

}
