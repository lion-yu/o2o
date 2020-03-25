package com.zmy.web.frontend;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.zmy.dto.AwardExecution;
import com.zmy.dto.UserAwardMapExecution;
import com.zmy.entity.Award;
import com.zmy.entity.LocalAuth;
import com.zmy.entity.PersonInfo;
import com.zmy.entity.Shop;
import com.zmy.entity.ShopAuthMap;
import com.zmy.entity.UserAwardMap;
import com.zmy.entity.UserShopMap;
import com.zmy.enums.UserAwardMapStateEnum;
import com.zmy.service.IAwardService;
import com.zmy.service.ILocalAuthService;
import com.zmy.service.IShopAuthMapService;
import com.zmy.service.IUserAwardMapService;
import com.zmy.service.IUserShopMapService;
import com.zmy.util.CodeUtil;
import com.zmy.util.HttpServletRequestUtil;

/**
 * @author 作者：张哥哥
 * @version 创建时间：2019年5月24日下午7:36:11 Class Description：
 */
@Controller
@RequestMapping("/frontend")
public class AwardController {
	@Autowired
	private ILocalAuthService localAuthService;
	@Autowired
	private IAwardService awardService;
	@Autowired
	private IUserShopMapService userShopMapService;
	@Autowired
	private IUserAwardMapService userAwardMapService;
	@Autowired
	private IShopAuthMapService shopAuthMapService;

	/**
	 * 前端系统获取某个店铺名下的可兑换奖品列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listawardbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listAwardsByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if ((pageIndex > -1) && (pageSize > -1) && shopId > -1) {
			// 获取前端输入的奖品名模糊查询
			String awardName = HttpServletRequestUtil.getString(request, "awardName");
			Award awardConditiion = new Award();
			awardConditiion.setShopId(shopId);
			awardConditiion.setAwardName(awardName);
			// 传入查询条件分页获取奖品信息
			AwardExecution ae = awardService.getAwardList(awardConditiion, pageIndex, pageSize);
			modelMap.put("awardList", ae.getAwardList());
			modelMap.put("count", ae.getCount());
			modelMap.put("success", true);
			// 从session中获取用户信息，主要是显示该用户在本店铺的积分信息
			PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
			if (user != null && user.getUserId() != null) {
				// 获取积分信息
				UserShopMap userShopMap = userShopMapService.getUserShopMap(user.getUserId(), shopId);
				if (userShopMap == null) {
					modelMap.put("totalPoint", 0);
				} else {
					modelMap.put("totalPoint", userShopMap.getPoint());
				}
			}
		}
		return modelMap;
	}

	/**
	 * 用户兑换奖品的接口，扣除用户积分，生成兑换记录到user_award_map表中
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/exchangeaward", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	private Map<String, Object> exchangeAward(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		Long awardId = HttpServletRequestUtil.getLong(request, "awardId");
		Integer point = HttpServletRequestUtil.getInt(request, "point");
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		// 先检查用户是否登录
		if (shopId != -1 && awardId != -1 && user != null && user.getUserId() != null) {
			Shop shop = new Shop();
			shop.setShopId(shopId);
			Award award = new Award();
			award.setAwardId(awardId);
			UserAwardMap userAwardMapCondition = new UserAwardMap();
			userAwardMapCondition.setShop(shop);
			userAwardMapCondition.setAward(award);
			userAwardMapCondition.setUser(user);
			userAwardMapCondition.setPoint(point);
			UserAwardMapExecution ue = userAwardMapService.addUserAwardMap(userAwardMapCondition);
			if (ue.getState() == UserAwardMapStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", ue.getStateInfo());
			}
		}
		return modelMap;
	}

	/**
	 * 生成待店家扫描的二维码
	 * 
	 * @param resp
	 */
	@RequestMapping("/generateqrcode")
	private void getQRCode(HttpServletResponse resp) {
		String URL = "http://139.196.73.108/o2o/frontend/login";
		BitMatrix bitMatirx = CodeUtil.generateQRCodeStream(URL, resp);
		try {
			MatrixToImageWriter.writeToStream(bitMatirx, "png", resp.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getuserawardlist")
	@ResponseBody
	private Map<String, Object> getUserAwardList(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		String awardName = HttpServletRequestUtil.getString(request, "awardName");
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		UserAwardMap userAwardConditon = new UserAwardMap();
		userAwardConditon.setUser(user);
		Award award = new Award();
		award.setAwardName(awardName);
		userAwardConditon.setAward(award);
		// 查询用户所兑换的奖品列表
		UserAwardMapExecution ue = userAwardMapService.listUserAwardMap(userAwardConditon, pageIndex, pageSize);
		if (ue != null) {
			modelMap.put("success", true);
			modelMap.put("userAwardMapList", ue.getUserAwardMapList());
			modelMap.put("count", ue.getCount());
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "pageIndex or pageSize is empty");
		}
		return modelMap;
	}

	// 扫码兑换奖品
	@RequestMapping(value = "/logincheck", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> loginCheck(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 获取是否需要验证码校验的标识符
		boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
		if (needVerify && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		// 获取输入的账号信息
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		Long awardId = HttpServletRequestUtil.getLong(request, "awardId");
		//判断url参数是否非法
		if(shopId == -1 || awardId == -1) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "url 参数非法");
			return modelMap;
		}
		Shop shop = new Shop();
		shop.setShopId(shopId);
		if (userName != null && password != null) {
			LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAndPwd(userName, password);
			if (localAuth != null) {
				//将user_id和shop_id组合查询用户是否有二维码扫码权限
				ShopAuthMap shopAuthMap = new ShopAuthMap();
				shopAuthMap.setShop(shop);
				PersonInfo personInfo = new PersonInfo();
				personInfo.setUserId(localAuth.getPersonInfo().getUserId());
				shopAuthMap.setEmployee(personInfo);
				shopAuthMap = shopAuthMapService.getShopAuthMapByUserId(shopAuthMap);
				if(shopAuthMap != null) {
					//扫码人有权限扫码，修改tb_user_award的used_state状态值，改为已领取1
					UserAwardMap userAwardMapCondition = new UserAwardMap();
					userAwardMapCondition.setUsedStatus(1);
					int effectedNum = userAwardMapService.modify(userAwardMapCondition);
					if(effectedNum != 0) {
						modelMap.put("success", true);
					}else {
						modelMap.put("success", false);
						modelMap.put("errMsg", "奖品领取失败");
					}
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户名或密码错误");
			}
		}
		return modelMap;
	}
}
