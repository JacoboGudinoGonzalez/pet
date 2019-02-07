package com.mx.nj.pet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mx.nj.pet.model.Message;

@Service("messageService")
public interface MessageService {
	
	void addMessage(Message message);
	
	List<Message> getMyMessages(int userId);
	
	List<Message> getMessages(int userId);
	
	Message getMessage(int id);
	
	void deleteMessage(int messageId, int userId);
	
	long getUnviewedMessages(int userId);
	
	int setViewedMessages(int userId);
	
}