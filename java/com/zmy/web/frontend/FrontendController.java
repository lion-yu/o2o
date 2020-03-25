package com.zmy.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年4月14日下午4:49:13
*Class Description： 
*/
@Controller
@RequestMapping(value="/frontend")
public class FrontendController {
	@RequestMapping(value="/index", method=RequestMethod.GET)
	private String index() {
		return "frontend/index";
	}
	@RequestMapping(value="/myinfomation",method=RequestMethod.GET)
	private String information() {
		return "frontend/myinfomation";
	}
	@RequestMapping(value="/bossmyinfomation",method=RequestMethod.GET)
	private String bossinformation() {
		return "frontend/bossmyinfomation";
	}
	@RequestMapping(value="/shoplist", method=RequestMethod.GET)
	private String showShopList() {
		return "frontend/shoplist";
	}
	@RequestMapping(value="/shopdetail", method=RequestMethod.GET)
	private String showShopDetail() {
		return "frontend/shopdetail";
	}
	@RequestMapping(value="/productdetail", method=RequestMethod.GET)
	private String showProductDetail() {
		return "frontend/productdetail";
	}
	@RequestMapping(value="/personinfo", method=RequestMethod.GET)
	private String getPersoninfo() {
		return "frontend/personinfo";
	}
	@RequestMapping(value="/gaode", method=RequestMethod.GET)
	private String getMap() {
		return "frontend/gaodemap";
	}
	@RequestMapping(value="/productlist", method=RequestMethod.GET)
	private String getProductList() {
		return "frontend/productlist";
	}
	//兑换奖品页
	@RequestMapping(value="/awardlist", method=RequestMethod.GET)
	private String getAwartList() {
		return "frontend/awardlist";
	}
	//商品详情页路由
	@RequestMapping(value="/pointrecord", method=RequestMethod.GET)
	private String showPointRecord() {
		return "frontend/pointrecord";
	}
	//商品评价页路由
	@RequestMapping(value="/comment", method=RequestMethod.GET)
	private String comment() {
		return "frontend/comment";
	}
	//商品评价页路由
	@RequestMapping(value="/userawardlist", method=RequestMethod.GET)
	private String userAwardList() {
		return "frontend/userawardlist";
	}
	//兑换奖品二维码
	@RequestMapping(value="/userawarddetail", method=RequestMethod.GET)
	private String userAwardDetail() {
		return "frontend/userawarddetail";
	}
	@RequestMapping(value="/commentedit", method=RequestMethod.GET)
	private String commentEdit() {
		return "frontend/commentedit";
	}
	//用户所有订单页路由
	@RequestMapping(value="/userproductlist", method=RequestMethod.GET)
	private String userProductList() {
		return "frontend/userproductlist";
	}
	//商家扫码登录路口
	@RequestMapping(value="/login", method=RequestMethod.GET)
	private String login() {
		return "frontend/login";
	}
	//商家扫码登录路口
	@RequestMapping(value="/noneed", method=RequestMethod.GET)
	private String noNeed() {
		return "frontend/noneed";
	}
	//商家扫码登录路口
	@RequestMapping(value="/success", method=RequestMethod.GET)
	private String success() {
		return "frontend/success";
	}
}
