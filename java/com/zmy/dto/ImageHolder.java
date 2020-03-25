package com.zmy.dto;

import java.io.InputStream;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年4月6日下午2:15:18
*Class Description： 
*/
public class ImageHolder {
	private String imageName;
	private InputStream image;
	
	public ImageHolder(InputStream image, String imageName) {
		this.imageName = imageName;
		this.image = image;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public InputStream getImage() {
		return image;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}
}
