package com.zmy.web.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月8日下午10:25:29
*Class Description： 
*/
@Controller
@RequestMapping(value="/local")
public class LocalAuthAdminController {
	/**
	 * 绑定账号页路由
	 */
	@RequestMapping(value="/accountbind",method=RequestMethod.GET)
	private String accountBind() {
		return "local/accountbind";
	}
	/**
	 * 修改密码信息页路由
	 */
	@RequestMapping(value="/changepsw",method=RequestMethod.GET)
	private String changePsw() {
		return "local/changepsw";
	}
	/**
	 * 登陆页路由
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	private String login() {
		return "local/login";
	}
	/**
	 * 退出页路由
	 */
	@RequestMapping(value="/logouthtml",method=RequestMethod.GET)
	private String logOut() {
		return "local/logouthtml";
	}
}
