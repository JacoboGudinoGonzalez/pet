package com.mx.nj.pet.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mx.nj.pet.dao.PetServiceDAO;
import com.mx.nj.pet.model.ServicePet;

@Repository
public class PetServiceDAOImpl implements PetServiceDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public ServicePet addPetService(ServicePet sp) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(sp);
		return sp;
	}

}
