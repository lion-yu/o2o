package com.zmy.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import com.zmy.dao.IShopCategoryDao;
import com.zmy.dto.ImageHolder;
import com.zmy.dto.ShopCategoryExecution;
import com.zmy.entity.ShopCategory;
import com.zmy.enums.ShopCategoryStateEnum;
import com.zmy.exceptions.AreaOperationException;
import com.zmy.exceptions.ShopCategoryOperationException;
import com.zmy.service.IShopCategoryService;
import com.zmy.util.ImageUtil;
import com.zmy.util.PathUtil;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年3月16日上午12:30:05
*Class Description： 
*/
@Service
public class ShopCategoryImpl implements IShopCategoryService {
	@Autowired
	private IShopCategoryDao shopCategoryDao;
	
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private JedisUtil.Strings jedisStrings;
	
	
	private static Logger logger = LoggerFactory.getLogger(ShopCategoryImpl.class);
//	@Override
//	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
//		
//		return shopCategoryDao.queryShopCategory(shopCategoryCondition);
//	}

	@Override
	@Transactional
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
		String key = SHOPCATEGORYLIST;
		List<ShopCategory> shopCagoryList = null;
		ObjectMapper mapper = new ObjectMapper();
		//拼接处redis的key
		if(shopCategoryCondition == null) {
			//若查询条件为空，则列出所有首页大类，即parentId为空的店铺类别
			key = key + "_allfirstlevel";
		}else if(shopCategoryCondition != null && shopCategoryCondition.getParent() != null
				&& shopCategoryCondition.getParent().getShopCategoryId() != null) {
			key = key + "_parent" + shopCategoryCondition.getParent().getShopCategoryId();
		}else if(shopCategoryCondition != null) {
			//列出所有子类别，不管属于哪个类，都列出来
			key = key + "_allsecondlevel";
		}
		//判断key是否存在
		if(!jedisKeys.exists(key)) {
			shopCagoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
			String jsonString;
			try {
				jsonString = mapper.writeValueAsString(shopCagoryList);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			}
			jedisStrings.set(key, jsonString);
		}else {
			String jsonString = jedisStrings.get(key);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
			try {
				shopCagoryList = mapper.readValue(jsonString, javaType);
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
		return shopCagoryList;
	}

	@Override
	@Transactional
	public ShopCategoryExecution addShopCategory(ShopCategory shopCategory, ImageHolder thumbnail) {
		// 空值判断
				if (shopCategory != null) {
					// 设定默认值
					shopCategory.setCreateTime(new Date());
					shopCategory.setLastEditTime(new Date());
					if (thumbnail != null) {
						// 若上传有图片流，则进行存储操作，并给shopCategory实体类设置上相对路径
						addThumbnail(shopCategory, thumbnail);
					}
					try {
						// 往数据库添加店铺类别信息
						int effectedNum = shopCategoryDao.insertShopCategory(shopCategory);
						if (effectedNum > 0) {
							// 删除店铺类别之前在redis里存储的一切key,for简单实现
							deleteRedis4ShopCategory();
							return new ShopCategoryExecution(ShopCategoryStateEnum.SUCCESS, shopCategory);
						} else {
							return new ShopCategoryExecution(ShopCategoryStateEnum.INNER_ERROR);
						}
					} catch (Exception e) {
						throw new ShopCategoryOperationException("添加店铺类别信息失败:" + e.toString());
					}
				} else {
					return new ShopCategoryExecution(ShopCategoryStateEnum.EMPTY);
				}
	}

	@Override
	@Transactional
	public ShopCategoryExecution modifyShopCategory(ShopCategory shopCategory, ImageHolder thumbnail) {
		// 空值判断，主要判断shopCategoryId不为空
				if (shopCategory.getShopCategoryId() != null && shopCategory.getShopCategoryId() > 0) {
					// 设定默认值
					shopCategory.setLastEditTime(new Date());
					if (thumbnail != null) {
						// 若上传的图片不为空，则先获取之前的图片路径
						ShopCategory tempShopCategory = shopCategoryDao.queryShopCategoryById(shopCategory.getShopCategoryId());
						if (tempShopCategory.getShopCategoryImg() != null) {
							// 若之前图片不为空，则先移除之前的图片
							ImageUtil.deleteFileOrPath(tempShopCategory.getShopCategoryImg());
						}
						// 存储新的图片
						addThumbnail(shopCategory, thumbnail);
					}
					try {
						// 更新数据库信息
						int effectedNum = shopCategoryDao.updateShopCategory(shopCategory);
						if (effectedNum > 0) {
							// 删除店铺类别之前在redis里存储的一切key,for简单实现
							deleteRedis4ShopCategory();
							return new ShopCategoryExecution(ShopCategoryStateEnum.SUCCESS, shopCategory);
						} else {
							return new ShopCategoryExecution(ShopCategoryStateEnum.INNER_ERROR);
						}
					} catch (Exception e) {
						throw new ShopCategoryOperationException("更新店铺类别信息失败:" + e.toString());
					}
				} else {
					return new ShopCategoryExecution(ShopCategoryStateEnum.EMPTY);
				}
	}

	@Override
	public ShopCategory getShopCategoryById(Long shopCategoryId) {
		return shopCategoryDao.queryShopCategoryById(shopCategoryId);
	}
	/**
	 * 存储图片
	 * 
	 * @param shopCategory
	 * @param thumbnail
	 */
	private void addThumbnail(ShopCategory shopCategory, ImageHolder thumbnail) {
		String dest = PathUtil.getShopCategoryPath();
		String thumbnailAddr = ImageUtil.generateNormalImg(thumbnail, dest);
		shopCategory.setShopCategoryImg(thumbnailAddr);
	}

	/**
	 * 移除跟实体类相关的redis key-value
	 */
	private void deleteRedis4ShopCategory() {
		String prefix = "SCLISTKEY";
		// 获取跟店铺类别相关的redis key
		Set<String> keySet = jedisKeys.keys(prefix + "*");
		for (String key : keySet) {
			// 逐条删除
			jedisKeys.del(key);
		}
	}

}

	

