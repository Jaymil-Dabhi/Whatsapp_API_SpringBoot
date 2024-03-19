package com.wp.whatsapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wp.whatsapp.exception.ChatException;
import com.wp.whatsapp.exception.MessageException;
import com.wp.whatsapp.exception.UserException;
import com.wp.whatsapp.modal.Chat;
import com.wp.whatsapp.modal.Message;
import com.wp.whatsapp.modal.User;
import com.wp.whatsapp.repository.MessageRepository;
import com.wp.whatsapp.request.SendMessageRequest;

@Service
public class MessageServiceImplementation implements MessageService {
	
	private MessageRepository messageRepository;
	private UserService userService;
	private ChatService chatService;
	
	public MessageServiceImplementation(MessageRepository messageRepository,UserService userService,ChatService chatService) {
		this.messageRepository=messageRepository;
		this.userService=userService;
		this.chatService=chatService;
	}
	

	@Override
	public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {
		
		User user=userService.findUserById(req.getUserId());
		Chat chat=chatService.findChatById(req.getChatId());
		
		Message message=new Message();
		message.setChat(chat);
		message.setUser(user);
		message.setContent(req.getContent());
		message.setTimestamp(LocalDateTime.now());
		
		return message;
	}

	@Override
	public List<Message> getChatsMessages(Integer chatId, User reqUser) throws ChatException, UserException {
		Chat chat=chatService.findChatById(chatId);
		
		if(!chat.getUsers().contains(reqUser)) {
			throw new UserException("you are not related to this chat "+chat.getId());
		}
		List<Message> messages=messageRepository.findByChatId(chatId);
		
		return messages;
	}

	@Override
	public Message findMessageById(Integer messageId) throws MessageException {

        Optional<Message> opt=messageRepository.findById(messageId);
        
        if(opt.isPresent()) {
        	return opt.get();        }
        
		throw new MessageException("message not found with id "+messageId);
	}

	@Override
	public void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException {
		Message message= findMessageById(messageId);
		
		if(message.getUser().getId().equals(reqUser.getId())) {
			messageRepository.deleteById(messageId);
		}
		
		throw new UserException("you cant delete another user's message "+reqUser.getFull_name());
	}

}
