package com.zmy.util;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年4月3日上午8:29:26
*Class Description： 
*/
public class PageCalculator {
	public static int calculateRowIndex(int pageIndex, int pageSize) {
		return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0 ;
	}
}
