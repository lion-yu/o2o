package com.zmy.dao.split;

import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.Executor;

import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author 作者：张哥哥
 * @version 创建时间：2019年3月27日下午8:45:05 Class Description：
 */
public class DynamicDataSourceInterceptor implements Interceptor {
	private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);
	private static final String REGEX =".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		//接收mybatis转换过来的SQL变量参数
		Object[] objects = invocation.getArgs();
		//第一个参数往往就是携带增加等信息
		MappedStatement ms =(MappedStatement)objects[0];
		String lookupKey = DynamicDataSourceHodler.DB_MASTER;
		//判断当前是不是事务的
		boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
		if(synchronizationActive != true) {
			
			//读方法
			if(ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
				//如果selectKey为自增id查询主键（SELECT LAST_INSERT_ID（方法），使用主库）
				if(ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
					lookupKey = DynamicDataSourceHodler.DB_MASTER;
				}else {
					//objects[1]参数表示传入的SQL语句
					BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
					String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
					if(sql.matches(REGEX)) {
						lookupKey = DynamicDataSourceHodler.DB_SLAVE;
					}
				}
			}
		}else {
			lookupKey = DynamicDataSourceHodler.DB_MASTER;
		}
		logger.debug("设置方法[{}] use [{}] Strategy,SqlCommanType[{}]..",ms.getId(),lookupKey,ms.getSqlCommandType().name());
		DynamicDataSourceHodler.setDbType(lookupKey);
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {
	}

}
