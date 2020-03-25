package com.zmy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zmy.dao.IPersonInfoDao;
import com.zmy.dto.PersonInfoExecution;
import com.zmy.entity.PersonInfo;
import com.zmy.enums.PersonInfoStateEnum;
import com.zmy.exceptions.PersonInfoException;
import com.zmy.service.IPersonInfoService;
import com.zmy.util.PageCalculator;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月6日下午11:44:19
*Class Description： 
*/
@Service
public class PersonInfoServiceImpl implements IPersonInfoService {
	
	@Autowired
	private IPersonInfoDao personInfoDao;
	
	@Override
	public PersonInfo getPersonInfoById(Long userId) {
		return personInfoDao.queryPersonInfoById(userId);
	}

	@Override
	public PersonInfoExecution getPersonInfoList(PersonInfo personInfoCondition, int pageIndex, int pageSize) {
		// 页转行
				int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
				// 获取用户信息列表
				List<PersonInfo> personInfoList = personInfoDao.queryPersonInfoList(personInfoCondition, rowIndex, pageSize);
				int count = personInfoDao.queryPersonInfoCount(personInfoCondition);
				PersonInfoExecution se = new PersonInfoExecution();
				if (personInfoList != null) {
					se.setPersonInfoList(personInfoList);
					se.setCount(count);
				} else {
					se.setState(PersonInfoStateEnum.INNER_ERROR.getState());
				}
				return se;
	}

	@Override
	@Transactional
	public PersonInfoExecution modifyPersonInfo(PersonInfo personInfo) {
		// 空值判断，主要是判断用户Id是否为空
				if (personInfo == null || personInfo.getUserId() == null) {
					return new PersonInfoExecution(PersonInfoStateEnum.EMPTY);
				} else {
					try {
						// 更新用户信息
						int effectedNum = personInfoDao.updatePersonInfo(personInfo);
						if (effectedNum <= 0) {
							return new PersonInfoExecution(PersonInfoStateEnum.INNER_ERROR);
						} else {
							personInfo = personInfoDao.queryPersonInfoById(personInfo.getUserId());
							return new PersonInfoExecution(PersonInfoStateEnum.SUCCESS, personInfo);
						}
					} catch (Exception e) {
						throw new PersonInfoException("updatePersonInfo error: " + e.getMessage());
					}
				}
	}

}
