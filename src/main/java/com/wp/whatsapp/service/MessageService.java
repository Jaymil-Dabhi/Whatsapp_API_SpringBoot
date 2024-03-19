package com.wp.whatsapp.service;

import java.util.List;

import com.wp.whatsapp.exception.ChatException;
import com.wp.whatsapp.exception.MessageException;
import com.wp.whatsapp.exception.UserException;
import com.wp.whatsapp.modal.Message;
import com.wp.whatsapp.modal.User;
import com.wp.whatsapp.request.SendMessageRequest;

public interface MessageService {

	public Message sendMessage(SendMessageRequest req) throws UserException, ChatException;
	
	public List<Message> getChatsMessages(Integer chatId, User reqUser) throws ChatException, UserException;
	
	public Message findMessageById(Integer messageId) throws MessageException;
	
	public void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException;
}
