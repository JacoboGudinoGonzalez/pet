package com.mx.nj.pet.dao;

import java.util.List;

import com.mx.nj.pet.model.Message;

public interface MessageDAO {

	Message addMessage(Message message);
	
	List<Message> getMyMessages(int userId);
	
	Message getMessageById(int id);
	
	void deleteMyMessageById(int messageId, int userId);

	List<Message> getMessages(int userId);
	
	long getUnviewedMessages(int userId);

	int setViewedMessages(int userId);

}