package com.mx.nj.pet.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mx.nj.pet.dao.PetDAO;
import com.mx.nj.pet.model.Pet;

@Repository
public class PetDAOImpl implements PetDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public Pet addPet(Pet pet) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(pet);
		return pet;
	}

	@Override
	public List<Pet> getMyPets(int fromUser) {

		/*Session session = this.sessionFactory.getCurrentSession();	

		String hql = "SELECT a.id, a.service, a.fromDate, a.toDate, a.fromUser, a.toUser, a.status FROM Appointment a WHERE a.fromUser =:fromUser ORDER BY a.fromDate DESC";
		Query query = session.createQuery(hql);
		query.setParameter("fromUser", fromUser);
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
				a.setStatus((Integer)obj[6]);
				appointmentList.add(a);
			}
		}*/
		return null;
	}
	
	@Override
	public List<Pet> getPets(int toUser) {

		/*Session session = this.sessionFactory.getCurrentSession();	

		String hql = "SELECT a.id, a.service, a.fromDate, a.toDate, a.fromUser, a.toUser, a.status FROM Appointment a WHERE a.toUser =:toUser ORDER BY a.fromDate DESC";
		Query query = session.createQuery(hql);
		query.setParameter("toUser", toUser);
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
				a.setStatus((Integer)obj[6]);
				appointmentList.add(a);
			}
		}*/
		return null;
	}

	@Override
	public Pet getPet(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		return (Pet) session.get(Pet.class, id);
	}
	
	public void deletePet(int fromUser) {
		/*Session session = this.sessionFactory.getCurrentSession();
		Query q = session.createQuery("delete Appointment a where p.id =:appointmentId AND fromUser =:fromUser")
				.setParameter("appointmentId", appointmentId).setParameter("fromUser", fromUser);
		q.executeUpdate();*/
	}
}