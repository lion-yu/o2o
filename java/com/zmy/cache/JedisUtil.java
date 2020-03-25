package com.zmy.cache;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.SafeEncoder;

/**
 * @author 作者：张哥哥
 * @version 创建时间：2019年5月8日上午9:03:30 Class Description：
 */
public class JedisUtil {
	// 操作Key的方法
	public Keys KEYS;
	// 对存储结构为String类型的操作
	public Strings STRINGS;
	// Redis连接池对象
	private JedisPool jedisPool;

	// 获取redis连接池
	public JedisPool getJedisPool() {
		return jedisPool;
	}

	// 设置redis连接池
	public void setJedisPool(JedisPoolWriper jedisPoolWriper) {
		this.jedisPool = jedisPoolWriper.getJedisPool();
	}

	// 从jedis连接池中获取jedis对象
	public Jedis getJedis() {
		return jedisPool.getResource();
	}

	public class Keys {
		/**
		 * 清空所有key
		 */
		public String flushAll() {
			Jedis jedis = getJedis();
			String stata = jedis.flushAll();
			jedis.close();
			return stata;
		}
		/**
		 * 删除keys对应的记录，可以是多个key
		 * @param keys
		 * @return
		 */
		public long del(String... keys) {
			Jedis jedis = getJedis();
			long count = jedis.del(keys);
			jedis.close();
			return count;
		}
		/**
		 * 判断key是否存在
		 * @param key
		 * @return
		 */
		public boolean exists(String key) {
			Jedis jedis = getJedis();
			boolean exis = jedis.exists(key);
			jedis.close();
			return exis;
		}
		/**
		 * 查找所有匹配给定的模式的键
		 * @param pattern  key的表达式，*表示多个，？表示一个
		 * @return
		 */
		public Set<String> keys(String pattern){
			Jedis jedis = getJedis();
			Set<String> set = jedis.keys(pattern);
			jedis.close();
			return set;
		}
	}
	
	public class Strings{
		/**
		 * 根据key获取记录
		 * @param key
		 * @return
		 */
		public String get(String key) {
			Jedis sjedis = getJedis();
			String value = sjedis.get(key);
			sjedis.close();
			return value;
		}
		/**
		 * 添加记录，如果记录存在则将覆盖原有的value
		 * @param key
		 * @param value
		 * @return  状态码
		 */
		public String set(String key, String value) {
			return set(SafeEncoder.encode(key), SafeEncoder.encode(value));
		}
		/**
		 * 添加记录，如果记录存在则将覆盖原有的value
		 * @param key
		 * @param value
		 * @return 状态码
		 */
		public String set(byte[] key, byte[] value) {
			Jedis jedis = getJedis();
			String status = jedis.set(key, value);
			jedis.close();
			return status;
		}
	}
}
