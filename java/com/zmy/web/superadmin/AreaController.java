package com.zmy.web.superadmin;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zmy.dto.AreaExecution;
import com.zmy.entity.Area;
import com.zmy.enums.AreaStateEnum;
import com.zmy.service.IAreaService;


/**
*@author 作者：张哥哥
*@version 创建时间：2019年2月23日下午10:51:58
*Class Description： 
*/
@Controller
@RequestMapping("/superadmin")
public class AreaController {
	Logger logger = LoggerFactory.getLogger(AreaController.class);
	@Autowired
	private IAreaService areaService;
	@RequestMapping(value ="/listarea",method =RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> listarea(){
		logger.info("===start===");
		long startTime = System.currentTimeMillis();
		Map<String,Object> modelMap = new HashMap<String,Object>();
		List<Area> list = new ArrayList<Area>();
		try {
			list =areaService.getAreaList();
			modelMap.put("rows", list);
			modelMap.put("total", list.size());
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			
		}
		logger.error("test error!");
		long endTime = System.currentTimeMillis();
		logger.debug("costTime:[{}ms]",endTime-startTime);
		logger.info("===end===");
		return modelMap;
	}
	/**
	 * 添加区域信息
	 * 
	 * @param areaStr
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addarea", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addArea(String areaStr, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		Area area = null;
		try {
			// 接收前端传递过来的area json字符串信息并转换成Area实体类实例
			area = mapper.readValue(areaStr, Area.class);
			// decode可能有中文的地方
			area.setAreaName((area.getAreaName() == null) ? null : URLDecoder.decode(area.getAreaName(), "UTF-8"));
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		// 空值判断
		if (area != null && area.getAreaName() != null) {
			try {
				// 添加区域信息
				AreaExecution ae = areaService.addArea(area);
				if (ae.getState() == AreaStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", ae.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入区域信息");
		}
		return modelMap;
	}

	/**
	 * 修改区域信息，主要修改名字
	 * 
	 * @param areaStr
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/modifyarea", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyArea(String areaStr, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		Area area = null;
		try {
			// 接收前端传递过来的area json字符串信息并转换成Area实体类实例
			area = mapper.readValue(areaStr, Area.class);
			area.setAreaName((area.getAreaName() == null) ? null : URLDecoder.decode(area.getAreaName(), "UTF-8"));
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		// 空值判断
		if (area != null && area.getAreaId() != null) {
			try {
				// 修改区域信息
				AreaExecution ae = areaService.modifyArea(area);
				if (ae.getState() == AreaStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", ae.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入区域信息");
		}
		return modelMap;
	}
}
