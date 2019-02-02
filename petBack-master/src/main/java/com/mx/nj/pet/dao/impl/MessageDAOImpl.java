package com.mx.nj.pet.dao.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mx.nj.pet.dao.MessageDAO;
import com.mx.nj.pet.model.Message;
import com.mx.nj.pet.model.Usuario;

@Repository
public class MessageDAOImpl implements MessageDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public Message addMessage(Message message) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(message);
		return message;
	}

	@Override
	public List<Message> getMyMessages(int userId) {

		Session session = this.sessionFactory.getCurrentSession();	

		String hql = "SELECT m.id, m.text, m.createdAt, m.emmiter, m.receiver m.viewed FROM Message m WHERE receiver =:userId";
		Query query = session.createQuery(hql);
		query.setParameter("userId", userId);
		
		List<Object[]> objectList = query.list();
		List<Message> messageList = new ArrayList<Message>();
		if(objectList.size()!=0){
			for(Object[] obj:objectList ){
				Message m = new Message();
				m.setId((Integer)obj[0]);
				m.setText((String)obj[1]);
				m.setCreatedAt((Date)obj[2]);
				m.setEmmiter((Usuario)obj[3]);
				m.setReceiver((Usuario)obj[4]);
				m.setViewed((boolean)obj[4]);
				messageList.add(m);
			}
		}
		return messageList;
	}
	
	@Override
	public List<Message> getMessages(int userId) {

		Session session = this.sessionFactory.getCurrentSession();	

		String hql = "SELECT m.id, m.text, m.createdAt, m.emmiter, m.receiver FROM Message m WHERE emmiter =:userId";
		Query query = session.createQuery(hql);
		query.setParameter("userId", userId);
		
		List<Object[]> objectList = query.list();
		List<Message> messageList = new ArrayList<Message>();
		if(objectList.size()!=0){
			for(Object[] obj:objectList ){
				Message m = new Message();
				m.setId((Integer)obj[0]);
				m.setText((String)obj[1]);
				m.setCreatedAt((Date)obj[2]);
				m.setEmmiter((Usuario)obj[3]);
				m.setReceiver((Usuario)obj[4]);
				messageList.add(m);
			}
		}
		return messageList;
	}

	@Override
	public Message getMessageById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		return (Message) session.get(Message.class, id);
	}
	
	public void deleteMyMessageById(int messageId, int userId) {
		Session session = this.sessionFactory.getCurrentSession();
		Query q = session.createQuery("delete Message p where p.id =:messageId AND usuario =:userId")
				.setParameter("messageId", messageId).setParameter("userId", userId);
		q.executeUpdate();
	}
	
	@Override
	public long getUnviewedMessages(int userId) {
		Session session = this.sessionFactory.getCurrentSession();	
		String hql = "SELECT count(*) FROM Message m WHERE m.viewed='false' AND receiver=:userId";
		Query query = session.createQuery(hql);
		Usuario usuario = (Usuario) session.get(Usuario.class, userId);
		query.setParameter("userId", usuario);
		long unviewed = (long) query.uniqueResult();
		return unviewed;
	}
	
	@Override
	public int setViewedMessages(int userId) {
		Session session = this.sessionFactory.getCurrentSession();	
		String hql = "Update Message set viewed='true' WHERE receiver=:userId";
		Query query = session.createQuery(hql);
		Usuario usuario = (Usuario) session.get(Usuario.class, userId);
		query.setParameter("userId", usuario);
		int unviewed = query.executeUpdate();
		return unviewed;
	}
}