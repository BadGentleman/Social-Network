package group.zerry.api_server.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import group.zerry.api_server.entity.Comment;
import group.zerry.api_server.enumtypes.CommentStatusEnum;
import group.zerry.api_server.service.CommentService;

/**
 * @author ZerryChu
 * @since  2015 10 3
 * 
 */
@Controller
@RequestMapping(value="/comment")
public class CommentController {
	
	@Autowired
	CommentService                         commentService;

	private static SimplePropertyPreFilter commentFilter = new SimplePropertyPreFilter(Comment.class, "nickname",
			"content", "create_time");

	private static Logger                  logger = Logger.getLogger(CommentController.class);
	
	@ResponseBody
	@RequestMapping(value = "/show", produces = "text/html;charset=UTF-8")
	public String show_comments(int id) {
		StringBuilder regMsg = new StringBuilder("{\"returndata\": ");
		Comment[] comments = commentService.showComments(id);
		if(null == comments) {
			regMsg.append(CommentStatusEnum.CNF.getValue());
			regMsg.append("}");
			return regMsg.toString();
		}
		regMsg.append(JSON.toJSONString(comments, commentFilter));
		regMsg.append("}");
		return regMsg.toString();
	}
	
	//删除评论
	//评论点赞
}
