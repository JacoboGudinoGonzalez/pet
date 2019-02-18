package com.mx.nj.pet.dao.impl;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mx.nj.pet.dao.UsuarioDAO;
import com.mx.nj.pet.model.ServicePet;
import com.mx.nj.pet.model.Usuario;

@Repository
public class UsuarioDAOImpl implements UsuarioDAO{

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public List<Usuario> getAllUsers(String type) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("select u.id, u.name, u.type, u.image, u.email, u.tel, u.rating, u.description, u.coordinates, u.address"
				+ " from Usuario u where u.type != :type ")
				.setParameter("type", type);	

		List<Object[]> objectList = query.list();
		List<Usuario> userList = new ArrayList<Usuario>();
		if(objectList.size()!=0){
			for(Object[] obj:objectList ){
				Usuario u = new Usuario();
				u.setId((Integer)obj[0]);
				u.setName((String)obj[1]);
				u.setType((String)obj[2]);
				u.setImage((String)obj[3]);
				u.setEmail((String)obj[4]);
				u.setTel((String)obj[5]);
				u.setRating((Integer)obj[6]);
				u.setDescription((String)obj[7]);
				u.setCoordinates((String)obj[8]);
				u.setAddress((String)obj[9]);
				userList.add(u);
			}
		}
		return userList;
	}
	
	@Override
	public List<ServicePet> getUsersService(int idUser) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("select s.id, s.usuario, s.idUserTo, s.dateFrom, s.date, "
				+ "s.address, s.message, s.animalType, s.animalSize" 
				+ " from ServicePet s where s.idUserTo=:idUserTo ")
				.setParameter("idUserTo", idUser);	
		List<Object[]> objectList = query.list();
		List<ServicePet> servicePetList = new ArrayList<ServicePet>();
		if(objectList.size()!=0){
			for(Object[] obj:objectList ){
				ServicePet s = new ServicePet();
				s.setId((Integer)obj[0]);
				s.setUsuario(getUserById(((Usuario)obj[1]).getId()));
				s.setIdUserTo((Integer)obj[2]);
				s.setDateFrom((String)obj[3]);
				s.setDate((String)obj[4]);
				s.setAddress((String)obj[5]);
				s.setMessage((String)obj[6]);
				s.setAnimalType((String)obj[7]);
				s.setAnimalSize((char)obj[8]);
				servicePetList.add(s);
			}
		}
		return servicePetList;
	}

	@Override
	public Usuario getUserByEmail(String email) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Usuario u where u.email = :email ");
		query.setParameter("email", email);
		return (Usuario)query.uniqueResult();
	}

	@Override
	public Usuario getUserById(Integer id) {
		Session session = this.sessionFactory.getCurrentSession();
		return (Usuario) session.get(Usuario.class, id);
	}

	@Override
	public Usuario login(String email, String password) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Usuario u where u.email = :email and u.password = :password");
		query.setParameter("email", email);
		query.setParameter("password", password);
		return (Usuario)query.uniqueResult();
	}

	@Override
	public Usuario addUser(Usuario usuario) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(usuario);
		return usuario;
	}

	@Override
	public void updateUser(Usuario usuario) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(usuario);
	}

	@Override
	public void updateImageUser(int id, String image) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("UPDATE Usuario SET image =:image WHERE id =:id")
				.setParameter("image", image).setParameter("id", id);
		query.executeUpdate();
	}

	@Override
	public void deleteUser(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Usuario p = (Usuario) session.load(Usuario.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	}	

}