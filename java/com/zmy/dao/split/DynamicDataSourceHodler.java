package com.zmy.dao.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年3月27日下午8:34:00
*Class Description： 
*/
public class DynamicDataSourceHodler {
	private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceHodler.class);
	//可以保证线程安全
	private static ThreadLocal<String> contextHolder = new ThreadLocal<>();
	public static final String DB_MASTER="master";
	public static final String DB_SLAVE="slave";
	public static String getDbType() {
		String db = contextHolder.get();
		if(db == null) {
			db = DB_MASTER;
		}
		return db;
	}
	/**
	 * 设置线程的dbtype
	 * @param str
	 */
	public static void setDbType(String str) {
		logger.debug("所使用的数据源为：" + str);
		contextHolder.set(str);
	}
	/**
	 * 清理连接类型
	 */
	public static void clearDbType() {
		contextHolder.remove();
	}
}
