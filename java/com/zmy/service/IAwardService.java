package com.zmy.service;

import com.zmy.dto.AwardExecution;
import com.zmy.dto.ImageHolder;
import com.zmy.entity.Award;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月21日下午8:17:32
*Class Description： 
*/
public interface IAwardService {
	/**
	 * 插入奖品
	 * @param awardCondition
	 * @return
	 */
	AwardExecution addAward(Award awardCondition, ImageHolder thumbnail);
	/**
	 * 根据shopid分页展示奖品信息
	 * @param awardCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	AwardExecution getAwardList(Award awardCondition, Integer pageIndex, Integer pageSize);
	/**
	 * 更新奖品信息
	 * @param awardId
	 * @return
	 */
	AwardExecution modifyAward(Award award, ImageHolder thumbnail);
	/**
	 * 更新奖品信息
	 * @param awardId
	 * @return
	 */
	AwardExecution modifyAward(Award award);
	/**
	 * 依据传入的奖品id和店铺id删除奖品
	 * @param awardId
	 * @param shopId
	 * @return
	 */
	int deleteAward(Long awardId, Long shopId);
	/**
	 * 
	 * @param awardId
	 * @return
	 */
	Award getAwardByAwardId(Long awardId);
}
