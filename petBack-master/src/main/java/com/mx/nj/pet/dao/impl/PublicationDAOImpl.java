package com.mx.nj.pet.dao.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mx.nj.pet.dao.PublicationDAO;
import com.mx.nj.pet.model.Publication;
import com.mx.nj.pet.model.Usuario;

@Repository
public class PublicationDAOImpl implements PublicationDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public Publication addPublication(Publication publication) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(publication);
		return publication;
	}

	@Override
	public List<Publication> getPublications(List<Integer> usersId) {

		Session session = this.sessionFactory.getCurrentSession();	

		String hql = "SELECT p.id, p.text, p.file, p.createdAt, p.usuario FROM Publication p WHERE usuario in (:usersId) ORDER BY p.createdAt DESC";
		Query query = session.createQuery(hql);
		query.setParameterList("usersId", usersId);
		
		List<Object[]> objectList = query.list();
		List<Publication> PublicationList = new ArrayList<Publication>();
		if(objectList.size()!=0){
			for(Object[] obj:objectList ){
				Publication p = new Publication();
				p.setId((Integer)obj[0]);
				p.setText((String)obj[1]);
				p.setFile((String)obj[2]);
				p.setCreatedAt((Timestamp)obj[3]);
				p.setUsuario((Usuario)obj[4]);
				PublicationList.add(p);
			}
		}
		return PublicationList;
	}
	
	@Override
	public List<Publication> getPublicationsUser(int userId) {

		Session session = this.sessionFactory.getCurrentSession();	

		String hql = "SELECT p.id, p.text, p.file, p.createdAt, p.usuario FROM Publication p WHERE usuario =:userId ORDER BY p.createdAt DESC";
		Query query = session.createQuery(hql);
		query.setParameter("userId", userId);
		
		List<Object[]> objectList = query.list();
		List<Publication> PublicationList = new ArrayList<Publication>();
		if(objectList.size()!=0){
			for(Object[] obj:objectList ){
				Publication p = new Publication();
				p.setId((Integer)obj[0]);
				p.setText((String)obj[1]);
				p.setFile((String)obj[2]);
				p.setCreatedAt((Timestamp)obj[3]);
				p.setUsuario((Usuario)obj[4]);
				PublicationList.add(p);
			}
		}
		return PublicationList;
	}

	@Override
	public Publication getPublication(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		return (Publication) session.get(Publication.class, id);
	}
	
	@Override
	public void deletePublication(int publicationId, int userId) {
		Session session = this.sessionFactory.getCurrentSession();
		Query q = session.createQuery("delete Publication p where p.id =:publicationId AND usuario =:userId")
				.setParameter("publicationId", publicationId).setParameter("userId", userId);
		q.executeUpdate();
	}
	
	@Override
	public int getPublicationCounters(int id) {
		int publicationCounters;
		Session session = this.sessionFactory.getCurrentSession();		
		Query query = session.createQuery("SELECT COUNT(id) "
				+ "FROM Publication WHERE usuario =:id");
		Usuario usuario = (Usuario) session.get(Usuario.class, id);
		query.setParameter("id", usuario);	
		publicationCounters = Long.valueOf(query.list().get(0).toString()).intValue();
		return publicationCounters;
	}
	
	@Override
	public void updateFilePublication(int id, String file) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("UPDATE Publication SET file =:file WHERE id =:id")
				.setParameter("file", file).setParameter("id", id);
		query.executeUpdate();
	}
}