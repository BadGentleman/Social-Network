package group.zerry.api_server.service;

import group.zerry.api_server.entity.Message;
import group.zerry.api_server.enumtypes.MessageStatusEnum;

public interface MessageService {
	public MessageStatusEnum send_message(String username, String content, int type);
	
	public MessageStatusEnum delete_message(String username, int id);
	
	public Message[] show_messages(String username, int type);
	
	public MessageStatusEnum addRepostTimes(String username, int id);  //转发
	
	public MessageStatusEnum addComment(String username, String content, int id); //评论
	
	public MessageStatusEnum addSupportTimes(String username, int id); //点赞
	
}