package com.zmy.web.shopadmin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zmy.dto.AwardExecution;
import com.zmy.dto.ImageHolder;
import com.zmy.entity.Award;
import com.zmy.entity.Shop;
import com.zmy.enums.AwardStateEnum;
import com.zmy.enums.ShopStateEnum;
import com.zmy.exceptions.AwardOperationException;
import com.zmy.service.IAwardService;
import com.zmy.util.CodeUtil;
import com.zmy.util.HttpServletRequestUtil;

/**
 * @author 作者：张哥哥
 * @version 创建时间：2019年5月21日下午8:16:19 Class Description： 某个店铺名下所有奖品的web层接口
 */
@Controller
@RequestMapping("/shopadmin")
public class AwardManagementController {
	@Autowired
	private IAwardService awardService;

	@RequestMapping(value = "/addaward", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addAwardByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
//		if (currentShop != null && currentShop.getShopId() == null) {
//			modelMap.put("succrss", false);
//			modelMap.put("errMsg", "非法操作");
//			return modelMap;
//		}
		// 1.接收并转换相应的参数，包括獎品信息以及图片信息
		String awardStr = HttpServletRequestUtil.getString(request, "awardStr");
		ObjectMapper mapper = new ObjectMapper();
		Award award = null;
		try {
			award = mapper.readValue(awardStr, Award.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}

		// springMVC读取上传文件的类
		CommonsMultipartFile awardImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			awardImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("awardImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "图片不能为空");
			return modelMap;
		}
		// 添加商品
		if (award != null && awardImg != null) {
			AwardExecution ae;
			try {
				ImageHolder imageHolder = new ImageHolder(awardImg.getInputStream(), awardImg.getOriginalFilename());
				award.setShopId(currentShop.getShopId());
				ae = awardService.addAward(award, imageHolder);
				if (ae.getState() == AwardStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
		}
		return modelMap;
	}
	
	@RequestMapping(value="/getawardlistbyshopid", method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getAwardListByShopId(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		String awardName = HttpServletRequestUtil.getString(request, "awardName");
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		Award awardCondition = new Award();
		if(currentShop != null && currentShop.getShopId() != null) {
			awardCondition.setShopId(currentShop.getShopId());
			awardCondition.setAwardName(awardName);
			AwardExecution ae = awardService.getAwardList(awardCondition, pageIndex, pageSize);
			if(ae.getState() == AwardStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
				modelMap.put("awardList", ae.getAwardList());
				modelMap.put("count", ae.getCount());
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", ae.getStateInfo());
			}
		}
		return modelMap;
	}
	
	@RequestMapping(value="/modifyaward",method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> deleteWard(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		Integer status = HttpServletRequestUtil.getInt(request, "enableStatus");
		status = (status ==0) ? 1 : 0;
		Long awardId = HttpServletRequestUtil.getLong(request, "awardId");
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		Award awardCondition = new Award();
		//组合查询条件
		awardCondition.setEnableStatus(status);
		awardCondition.setAwardId(awardId);
		awardCondition.setShopId(currentShop.getShopId());
		AwardExecution ae = awardService.modifyAward(awardCondition);
		if(ae.getState() == AwardStateEnum.SUCCESS.getState()) {
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", ae.getStateInfo());
		}
		return modelMap;
	}
	
	@RequestMapping(value="/getawardinfo",method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getAwardInfo(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		Long awardId = HttpServletRequestUtil.getLong(request, "awardId");
		Award award = awardService.getAwardByAwardId(awardId);
		if(award != null) {
			modelMap.put("success", true);
			modelMap.put("award", award);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "查询失败");
		}
		return modelMap;
	}
	@RequestMapping(value="/modifyaward",method=RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyAward(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		Shop shop = (Shop)request.getSession().getAttribute("currentShop");
		Award award= new Award();
		
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		// 1.接收并转换相应的参数，包括店铺信息以及图片信息
		String awardStr = HttpServletRequestUtil.getString(request, "awardStr");
		ObjectMapper mapper = new ObjectMapper();
		try {
			award = mapper.readValue(awardStr, Award.class);
			award.setShopId(shop.getShopId());
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		// springMVC读取上传文件的类
		CommonsMultipartFile awardImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			awardImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("awardImg");
		}
		// 2.修改店铺
		if (award != null && award.getAwardId() != null) {
			AwardExecution ae;
			try {
				if (awardImg == null) {
					ae = awardService.modifyAward(award, null);
				} else {
					ImageHolder imageHolder = new ImageHolder(awardImg.getInputStream(), awardImg.getOriginalFilename());
					ae = awardService.modifyAward(award, imageHolder);
				}
				if (ae.getState() == ShopStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", ae.getStateInfo());
				}
			} catch (AwardOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入奖品Id");
			return modelMap;
		}
	}
}
