package com.zmy.web.frontend;
/**
*@author 作者：张哥哥
*@version 创建时间：2019年5月26日下午1:38:28
*Class Description： 评价外部请求接口层
*/

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zmy.entity.Comment;
import com.zmy.entity.UserProductMap;
import com.zmy.service.ICommentService;
import com.zmy.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class CommentController {
	@Autowired
	private ICommentService commentService;

	/**
	 * 添加评价信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addcommentbyorderid", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addCommentByOrderId(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		Long orderId = HttpServletRequestUtil.getLong(request, "userProductMapId");
		String comments = HttpServletRequestUtil.getString(request, "comments");
		if (orderId != null && orderId > 0) {
			Comment comment = new Comment();
			UserProductMap userProductMap = new UserProductMap();
			userProductMap.setUserProductId(orderId);
			comment.setUserProductMap(userProductMap);
			comment.setComments(comments);
			// 调用service插入评价记录,更新订单comment_state状态值为1;
			int effectNum = commentService.addCommentByOrderId(comment);
			if (effectNum <= 0) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "评价失败");
				return modelMap;
			}
			modelMap.put("success", true);
			modelMap.put("errMsg", "评价成功");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty orderId or orderId no right");
		}
		return modelMap;
	}

	@RequestMapping(value = "/changecommentbyorderid", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> changeCommentByOrderId(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		Long orderId = HttpServletRequestUtil.getLong(request, "userProductMapId");
		String comments = HttpServletRequestUtil.getString(request, "comments");
		Comment comment = new Comment();
		UserProductMap userProductMap = new UserProductMap();
		userProductMap.setUserProductId(orderId);
		comment.setUserProductMap(userProductMap);
		comment.setComments(comments);
		if (orderId != -1) {
			int effectedNum = commentService.modifyCommentByOrderId(comment);
			if (effectedNum > 0) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "更新评价失败");
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "非法操作");
		}
		return modelMap;
	}

	@RequestMapping(value = "/getcommentbyorderid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getcommentbyorderid(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		Long orderId = HttpServletRequestUtil.getLong(request, "userProductMapId");
		Comment comment = commentService.getCommentByOrderId(orderId);
		if (comment != null && comment.getComments() != null) {
			modelMap.put("success", true);
			modelMap.put("comments", comment.getComments());
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "评论信息为空");
		}
		return modelMap;
	}
}
