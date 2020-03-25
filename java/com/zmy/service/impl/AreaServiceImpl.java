package com.zmy.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zmy.cache.JedisUtil;
import com.zmy.dao.IAreaDao;
import com.zmy.dto.AreaExecution;
import com.zmy.entity.Area;
import com.zmy.enums.AreaStateEnum;
import com.zmy.exceptions.AreaOperationException;
import com.zmy.service.IAreaService;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年2月23日下午10:38:06
*Class Description： 
*/

@Service
public class AreaServiceImpl implements IAreaService {
	@Autowired
	private IAreaDao areaDao;
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private JedisUtil.Strings jedisStrings;
	
	
	private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);
//	@Override
//	public List<Area> getAreaList() {
//		return areaDao.queryArea();
//	}
	@Override
	@Transactional
	public List<Area> getAreaList() {
		String key = AREALISTKEY;
		List<Area> areaList = null;
		ObjectMapper mapper = new ObjectMapper();
		if(!jedisKeys.exists(key)) {
			areaList = areaDao.queryArea();
			String jsonString;
			try {
				jsonString = mapper.writeValueAsString(areaList);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			}
			jedisStrings.set(key, jsonString);
		}else {
			String jsonString = jedisStrings.get(key);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
			try {
				areaList = mapper.readValue(jsonString, javaType);
			} catch (JsonParseException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			} catch (JsonMappingException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			}
		}
		return areaList;
	}
	@Override
	public AreaExecution addArea(Area area) {
		// 空值判断，主要是判断areaName不为空
				if (area.getAreaName() != null && !"".equals(area.getAreaName())) {
					// 设置默认值
					area.setCreateTime(new Date());
					area.setLastEditTime(new Date());
					try {
						int effectedNum = areaDao.insertArea(area);
						if (effectedNum > 0) {
							deleteRedis4Area();
							return new AreaExecution(AreaStateEnum.SUCCESS, area);
						} else {
							return new AreaExecution(AreaStateEnum.INNER_ERROR);
						}
					} catch (Exception e) {
						throw new AreaOperationException("添加区域信息失败:" + e.toString());
					}
				} else {
					return new AreaExecution(AreaStateEnum.EMPTY);
				}
	}
	@Override
	public AreaExecution modifyArea(Area area) {
		// 空值判断，主要是areaId不为空
				if (area.getAreaId() != null && area.getAreaId() > 0) {
					// 设置默认值
					area.setLastEditTime(new Date());
					try {
						// 更新区域信息
						int effectedNum = areaDao.updateArea(area);
						if (effectedNum > 0) {
							deleteRedis4Area();
							return new AreaExecution(AreaStateEnum.SUCCESS, area);
						} else {
							return new AreaExecution(AreaStateEnum.INNER_ERROR);
						}
					} catch (Exception e) {
						throw new AreaOperationException("更新区域信息失败:" + e.toString());
					}
				} else {
					return new AreaExecution(AreaStateEnum.EMPTY);
				}
	}
	/**
	 * 移除跟实体类相关的redis key-value
	 */
	private void deleteRedis4Area() {
		String key = AREALISTKEY;
		// 若redis存在对应的key,则将key清除
		if (jedisKeys.exists(key)) {
			jedisKeys.del(key);
		}
	}
}
