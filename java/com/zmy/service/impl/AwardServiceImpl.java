package com.zmy.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zmy.dao.IAwardDao;
import com.zmy.dto.AwardExecution;
import com.zmy.dto.ImageHolder;
import com.zmy.entity.Award;
import com.zmy.enums.AwardStateEnum;
import com.zmy.exceptions.AwardOperationException;
import com.zmy.service.IAwardService;
import com.zmy.util.ImageUtil;
import com.zmy.util.PageCalculator;
import com.zmy.util.PathUtil;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月21日下午8:48:57
*Class Description： 
*/
@Service
public class AwardServiceImpl implements  IAwardService{
	@Autowired
	private IAwardDao awardDao;
	@Override
	@Transactional
	public AwardExecution addAward(Award awardCondition, ImageHolder thumbnail) {
		if(awardCondition == null){
			return new AwardExecution(AwardStateEnum.NULL_AWARD_INFO);
		}
		try {
			awardCondition.setCreateTime(new Date());
			awardCondition.setLastEditTime(new Date());
			//商品直接在前端展示，无需审核
			awardCondition.setEnableStatus(1);
			//获取奖品的店铺id值
			Long shopId = awardCondition.getShopId();
			//获取图片流
			if(thumbnail.getImage() != null &&  shopId != null) {
				//存储图片
				try {
					String awardImgAddr = addAwardImg(shopId, thumbnail);
					awardCondition.setAwardImg(awardImgAddr);
				}catch(Exception e) {
					throw new AwardOperationException("add award img error:" + e.toString());
				}
				int effectedNum = awardDao.insertAward(awardCondition);
				if(effectedNum <= 0) {
					throw new AwardOperationException("奖品信息添加失败");
				}
			}else {
				return new AwardExecution(AwardStateEnum.FAILED);
			}
		}catch (Exception e) {
			throw new AwardOperationException("add award error" + e.getMessage());
		}
		return new AwardExecution(AwardStateEnum.SUCCESS, awardCondition);
	}

	private String addAwardImg(Long shopId, ImageHolder thumbnail) {
		//获取奖品存储的根路径
		String  dest = PathUtil.getAwardImagePath(shopId);
		String awardImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		return awardImgAddr;
	}

	@Override
	public AwardExecution getAwardList(Award awardCondition, Integer pageIndex, Integer pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Award> awardList = awardDao.queryAwardList(awardCondition, rowIndex, pageSize);
		int count = awardDao.queryAwardCount(awardCondition, rowIndex, pageSize);
		AwardExecution ae = new AwardExecution();
		if(awardList != null) {
			ae.setAwardList(awardList);
			ae.setCount(count);
			ae.setState(AwardStateEnum.SUCCESS.getState());
		}else {
			return new AwardExecution(AwardStateEnum.INNER_ERROR);
		}
		return ae;
	}

	@Override
	public AwardExecution modifyAward(Award award, ImageHolder thumbnail) {
		try {
			if(award == null || award.getAwardId() == null) {
				return new AwardExecution(AwardStateEnum.NULL_AWARD_INFO);
			}else {
				if(thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())) {
					Award tempAward = awardDao.queryAwardByAwardId(award.getAwardId());
					if(tempAward.getAwardImg() != null) {
						ImageUtil.deleteFileOrPath(tempAward.getAwardImg());
					}
					String awardImgAddr = addAwardImg(tempAward.getShopId(), thumbnail);
					award.setAwardImg(awardImgAddr);
				}
			}
			//2.更新奖品信息
			award.setLastEditTime(new Date());
			int effectedNum = awardDao.updateAward(award);
			if(effectedNum <= 0) {
				return new AwardExecution(AwardStateEnum.INNER_ERROR);
			}else{
				return new AwardExecution(AwardStateEnum.SUCCESS);
			}
			}catch(Exception e){
				throw new AwardOperationException("modifyAward error:" + e.getMessage());
			}
	}

	@Override
	public int deleteAward(Long awardId, Long shopId) {
		return awardDao.deleteAward(awardId, shopId);
	}

	@Override
	public Award getAwardByAwardId(Long awardId) {
		return awardDao.queryAwardByAwardId(awardId);
	}

	@Override
	public AwardExecution modifyAward(Award award) {
		if(award != null && award.getAwardId() != null && award.getShopId() != null) {
			int effectedNum = awardDao.updateAward(award);
			if(effectedNum <= 0){
				return new AwardExecution(AwardStateEnum.FAILED);
			}
			return new AwardExecution(AwardStateEnum.SUCCESS);
		}else {
			return null;
		}
	}

}
