package com.zmy.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zmy.dao.IUserAwardMapDao;
import com.zmy.dao.IUserShopMapDao;
import com.zmy.dto.UserAwardMapExecution;
import com.zmy.entity.UserAwardMap;
import com.zmy.entity.UserShopMap;
import com.zmy.enums.UserAwardMapStateEnum;
import com.zmy.exceptions.UserAwardMapOperationException;
import com.zmy.service.IUserAwardMapService;
import com.zmy.util.PageCalculator;

/**
 * @author 作者：张哥哥
 * @version 创建时间：2019年5月24日下午6:21:11 Class Description：
 */
@Service
public class UserAwardMapServiceImpl implements IUserAwardMapService {
	@Autowired
	private IUserAwardMapDao userAwardDao;
	@Autowired
	private IUserShopMapDao userShopMapDao;

	@Override
	public UserAwardMapExecution listUserAwardMap(UserAwardMap userAwardConditon, Integer pageIndex, Integer pageSize) {
		if (userAwardConditon != null && pageIndex != null && pageSize != null) {
			int beginIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
			List<UserAwardMap> userAwardMapList = userAwardDao.queryUserMapList(userAwardConditon, beginIndex,
					pageSize);
			int count = userAwardDao.queryUserAwardMapCount(userAwardConditon);
			UserAwardMapExecution ue = new UserAwardMapExecution();
			ue.setCount(count);
			ue.setUserAwardMapList(userAwardMapList);
			return ue;
		} else {
			return null;
		}
	}

	@Override
	public UserAwardMap getUserAwardMapById(long userAwardMapId) {

		return null;
	}

	@Override
	public UserAwardMapExecution addUserAwardMap(UserAwardMap userAwardMap) {
		if (userAwardMap != null && userAwardMap.getUser() != null && userAwardMap.getUser().getUserId() != null
				&& userAwardMap.getShop() != null && userAwardMap.getShop().getShopId() != null) {
			userAwardMap.setCreateTime(new Date());
			userAwardMap.setUsedStatus(0);
			try {
				int effectedNum = 0;
				// 若该积分需要消耗积分，则将tb_user_shop_map对应的积分抵扣
				if (userAwardMap.getPoint() != null && userAwardMap.getPoint() > 0) {
					UserShopMap userShopMap = userShopMapDao.queryUserShopMap(userAwardMap.getUser().getUserId(),
							userAwardMap.getShop().getShopId());
					userShopMap.setPoint(userShopMap.getPoint() - userAwardMap.getPoint());
					effectedNum = userShopMapDao.updateUserShopMapPoint(userShopMap);
					if (effectedNum <= 0) {
						throw new UserAwardMapOperationException("更新积分信息失败");
					}
				}
				// 插入礼品兑换信息
				effectedNum = userAwardDao.insertUserAwardMap(userAwardMap);
				if (effectedNum <= 0) {
					throw new UserAwardMapOperationException("领取失败");
				}
				return new UserAwardMapExecution(UserAwardMapStateEnum.SUCCESS);
			} catch (Exception e) {
				throw new UserAwardMapOperationException("领取失败：" + e.toString());
			}
		} else {
			return new UserAwardMapExecution(UserAwardMapStateEnum.NULL_AWARD_INFO);
		}
	}

	@Override
	public Integer modify(UserAwardMap userAwardMapCondition) {
		return  userAwardDao.updateUserAwardMap(userAwardMapCondition);
	}

}
