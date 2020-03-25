package com.zmy.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zmy.dao.IProductSellDailyDao;
import com.zmy.entity.ProductSellDaily;
import com.zmy.service.IProductSellDailyService;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月20日下午4:24:53
*Class Description： 
*/
@Service
public class ProductSellDailyServiceImpl implements IProductSellDailyService {
	//private static final Logger log = LoggerFactory.getLogger(ProductSellDailyServiceImpl.class);
	@Autowired
	private IProductSellDailyDao productSellDailyDao;
	@Override
	public void dailyCalculate() {
		System.out.println(new Date());
//		log.info("定时销量统计方法开始执行·");
//		productSellDailyDao.insertProdcuctSellDaily();
	}
	@Override
	public List<ProductSellDaily> listProductSellDaily(ProductSellDaily productSellDailyCondition, Date beginTime,
			Date endTime) {
		return productSellDailyDao.queryProductSellDailyList(productSellDailyCondition, beginTime, endTime);
	}

}
