package com.zmy.cache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月8日上午8:54:38
*Class Description： 根据配置创建出redis连接池
*/
public class JedisPoolWriper {
	/**
	 * redis连接池对象
	 */
	private JedisPool jedisPool;
	
	public JedisPoolWriper(final JedisPoolConfig poolConfig, final String host, final int port) {
		try {
			jedisPool = new JedisPool(poolConfig, host, port);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//创建Redis连接池对象
	public JedisPool getJedisPool() {
		return jedisPool;
	}
	//注入redis连接池对象
	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
}
