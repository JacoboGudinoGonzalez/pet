package com.mx.nj.pet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.nj.pet.dao.MessageDAO;
import com.mx.nj.pet.model.Message;
import com.mx.nj.pet.service.MessageService;

@Service("messageService")
public class MessageServiceImpl implements MessageService {
	
	@Autowired MessageDAO messageDao;

	@Transactional
	public void addMessage(Message message) {
		messageDao.addMessage(message);
	}

	@Transactional
	public List<Message> getMyMessages(int userId) {
		return messageDao.getMyMessages(userId);
	}
	
	@Transactional
	public List<Message> getMessages(int userId) {
		return messageDao.getMessages(userId);
	}

	@Transactional
	public Message getMessageById(int id) {
		return messageDao.getMessageById(id);
	}
	
	@Transactional
	public void deleteMyMessageById(int messageId, int userId) {
		messageDao.deleteMyMessageById(messageId, userId);
	}
	
	@Transactional
	public long getUnviewedMessages(int userId) {
		return messageDao.getUnviewedMessages(userId);
	}
	
	@Transactional
	public int setViewedMessages(int userId) {
		return messageDao.setViewedMessages(userId);
	}
}