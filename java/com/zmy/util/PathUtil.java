package com.zmy.util;


/**
*@author 作者：张哥哥
*@version 创建时间：2019年3月2日下午8:06:41
*Class Description： 该工具类提供两类路径：
*					1.根据执行环境的不同，提供不同的根路径
*					2.提供相对子路径
*/
public class PathUtil {
	//获取执行环境文件系统的路径分隔符
	private static String seperator = System.getProperty("file.separator");
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if(os.toLowerCase().startsWith("win")) {
			basePath="F:/IndustryMall/image/";
		}else {
			basePath="/home/mingyu/image/";
		}
		basePath.replace("/", seperator);
		return basePath;
	}
	
	public static String getShopImagePath(Long shopId) {
		String imagePath ="/upload/shop/" + shopId + "/";
		return imagePath.replace("/", seperator);
	}
	
	public static String getHeadLineImagePath() {
		String imagePath="/upload/headline/";
		return imagePath.replace("/", seperator);
	}
	
	public static String getAwardImagePath(Long shopId) {
		String imagePath ="/upload/shop/" + shopId + "/award/";
		return imagePath.replace("/", seperator);
	}

	public static String getShopCategoryPath() {
		
		return null;
	}
	
	
}
