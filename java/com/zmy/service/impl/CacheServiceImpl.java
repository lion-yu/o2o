package com.zmy.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.zmy.cache.JedisUtil;
import com.zmy.service.ICacheService;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月8日下午4:34:08
*Class Description： 供超级管理员使用
*/
public class CacheServiceImpl implements ICacheService {
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Override
	public void removeFromCache(String keyPrefix) {
		Set<String> keySet = jedisKeys.keys(keyPrefix + "*");
		for(String key : keySet) {
			jedisKeys.del(key);
		}
	}

}
