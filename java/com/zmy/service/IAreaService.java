package com.zmy.service;

import java.util.List;

import com.zmy.dto.AreaExecution;
import com.zmy.entity.Area;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年2月23日下午10:36:02
*Class Description： 区域列表Service层接口
*/
public interface IAreaService {
	public static final String AREALISTKEY = "arealist";
	List<Area> getAreaList();
	/**
	 * 增加区域信息
	 * 
	 * @param area
	 * @return
	 */
	AreaExecution addArea(Area area);

	/**
	 * 修改区域信息
	 * 
	 * @param area
	 * @return
	 */
	AreaExecution modifyArea(Area area);
}
