package com.zmy.dao.split;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年3月27日下午7:31:44
*Class Description： 实现读写分离
*/
public class DynamicDataSource extends AbstractRoutingDataSource{

	@Override
	protected Object determineCurrentLookupKey() {
		//DynamicDataSourceHodler  根据不同请求保存Key
		return DynamicDataSourceHodler.getDbType();
	}

}
