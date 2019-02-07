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

		String hql = "SELECT a.id, a.service, a.fromDate, a.toDate, a.fromUser, a.toUser FROM Appointment a WHERE a.fromUser =:fromUser ORDER BY a.fromDate DESC";
		Query query = session.createQuery(hql);
		query.setParameter("fromUser", fromUser);
		List<Object[]> objectList = query.list();
		List<Appointment> appointmentList = new ArrayList<Appointment>();
		if(objectList.size()!=0){
			for(Object[] obj:objectList ){
				Appointment m = new Appointment();
				m.setId((Integer)obj[0]);
				m.setService((Integer)obj[1]);
				m.setFromDate((Date)obj[2]);
				m.setToDate((Date)obj[3]);
				m.setFromUser((Usuario)obj[4]);
				m.setToUser((Usuario)obj[5]);
				appointmentList.add(m);
			}
		}
		return appointmentList;
	}
	
	@Override
	public List<Appointment> getAppointments(int toUser) {

		Session session = this.sessionFactory.getCurrentSession();	

		String hql = "SELECT a.id, a.service, a.fromDate, a.toDate, a.fromUser, a.toUser FROM Appointment a WHERE a.toUser =:toUser ORDER BY a.fromDate DESC";
		Query query = session.createQuery(hql);
		query.setParameter("toUser", toUser);
		List<Object[]> objectList = query.list();
		List<Appointment> appointmentList = new ArrayList<Appointment>();
		if(objectList.size()!=0){
			for(Object[] obj:objectList ){
				Appointment m = new Appointment();
				m.setId((Integer)obj[0]);
				m.setService((Integer)obj[1]);
				m.setFromDate((Date)obj[2]);
				m.setToDate((Date)obj[3]);
				m.setFromUser((Usuario)obj[4]);
				m.setToUser((Usuario)obj[5]);
				appointmentList.add(m);
			}
		}
		return appointmentList;
	}

	@Override
	public Appointment getAppointment(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		return (Appointment) session.get(Appointment.class, id);
	}
	
	public void deleteAppointment(int appointmentId, int fromUser) {
		Session session = this.sessionFactory.getCurrentSession();
		Query q = session.createQuery("delete Appointment a where p.id =:appointmentId AND fromUser =:fromUser")
				.setParameter("appointmentId", appointmentId).setParameter("fromUser", fromUser);
		q.executeUpdate();
	}
}