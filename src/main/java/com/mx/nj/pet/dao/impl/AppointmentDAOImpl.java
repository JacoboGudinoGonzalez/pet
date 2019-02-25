package com.mx.nj.pet.dao.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mx.nj.pet.dao.AppointmentDAO;
import com.mx.nj.pet.model.Appointment;
import com.mx.nj.pet.model.Usuario;

@Repository
public class AppointmentDAOImpl implements AppointmentDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public Appointment addAppointment(Appointment message) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(message);
		return message;
	}

	@Override
	public List<Appointment> getMyAppointments(int fromUser) {

		Session session = this.sessionFactory.getCurrentSession();	

		String hql = "SELECT a.id, a.service, a.fromDate, a.toDate, a.fromUser, a.toUser, a.rating, a.review, a.status FROM Appointment a WHERE a.status!=0 AND a.fromUser =:fromUser ORDER BY a.fromDate DESC";
		Query query = session.createQuery(hql);
		Usuario usuario = (Usuario) session.get(Usuario.class, fromUser);
		query.setParameter("fromUser", usuario);
		List<Object[]> objectList = query.list();
		List<Appointment> appointmentList = new ArrayList<Appointment>();
		if(objectList.size()!=0){
			for(Object[] obj:objectList ){
				Appointment a = new Appointment();
				a.setId((Integer)obj[0]);
				a.setService((Integer)obj[1]);
				a.setFromDate((Date)obj[2]);
				a.setToDate((Date)obj[3]);
				a.setFromUser((Usuario)obj[4]);
				a.setToUser((Usuario)obj[5]);
				a.setRating((Integer)obj[6]);
				a.setReview((String)obj[7]);
				a.setStatus((Integer)obj[8]);
				appointmentList.add(a);
			}
		}
		return appointmentList;
	}
	
	@Override
	public List<Appointment> getAppointments(int toUser) {

		Session session = this.sessionFactory.getCurrentSession();	

		String hql = "SELECT a.id, a.service, a.fromDate, a.toDate, a.fromUser, a.toUser, a.rating, a.review, a.status FROM Appointment a WHERE a.status!=0 AND a.toUser =:toUser ORDER BY a.fromDate DESC";
		Query query = session.createQuery(hql);
		Usuario usuario = (Usuario) session.get(Usuario.class, toUser);
		query.setParameter("toUser", usuario);
		List<Object[]> objectList = query.list();
		List<Appointment> appointmentList = new ArrayList<Appointment>();
		if(objectList.size()!=0){
			for(Object[] obj:objectList ){
				Appointment a = new Appointment();
				a.setId((Integer)obj[0]);
				a.setService((Integer)obj[1]);
				a.setFromDate((Date)obj[2]);
				a.setToDate((Date)obj[3]);
				a.setFromUser((Usuario)obj[4]);
				a.setToUser((Usuario)obj[5]);
				a.setRating((Integer)obj[6]);
				a.setReview((String)obj[7]);
				a.setStatus((Integer)obj[8]);
				appointmentList.add(a);
			}
		}
		return appointmentList;
	}

	@Override
	public Appointment getAppointment(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		return (Appointment) session.get(Appointment.class, id);
	}
	
	@Override
	public void deleteAppointment(int appointmentId, int fromUser) {
		Session session = this.sessionFactory.getCurrentSession();
		Query q = session.createQuery("DELETE Appointment a where a.id =:appointmentId AND fromUser =:fromUser")
				.setParameter("appointmentId", appointmentId).setParameter("fromUser", fromUser);
		q.executeUpdate();
	}
	
	@Override
	public void changeAppointmentStatus(int appointmentId, int status) {
		Session session = this.sessionFactory.getCurrentSession();
		Query q = session.createQuery("UPDATE Appointment a SET a.status=:status WHERE a.id =:appointmentId")
				.setParameter("appointmentId", appointmentId).setParameter("status", status);
		q.executeUpdate();
	}
	
	@Override
	public void changeAppointmentReview(int appointmentId, int rating, String review) {
		Session session = this.sessionFactory.getCurrentSession();
		Query q = session.createQuery("UPDATE Appointment a SET a.rating=:rating, a.review=:review WHERE a.id =:appointmentId")
				.setParameter("appointmentId", appointmentId).setParameter("rating", rating).setParameter("review", review);
		q.executeUpdate();
	}
}