package com.mx.nj.pet.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mx.nj.pet.dao.FollowDAO;
import com.mx.nj.pet.model.Follow;
import com.mx.nj.pet.model.Usuario;

@Repository
public class FollowDAOImpl implements FollowDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public Follow addFollow(Follow follow) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(follow);
		session.flush();
		return (Follow) session.get(Follow.class, follow.getId());
	}
	
	@Override
	public Follow getFollow(int userId, int followId) {
		Follow follow = null;
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("SELECT f.id, f.usuario, f.followed "
				+ "FROM Follow f WHERE f.usuario=:userId AND f.followed =:followId");	
		Usuario usuario = (Usuario) session.get(Usuario.class, userId);
		Usuario followed = (Usuario) session.get(Usuario.class, followId);
		query.setParameter("userId", usuario).setParameter("followId", followed);
		Object[] o = (Object[]) query.uniqueResult();
		if(o!=null){
			follow = new Follow();
			follow.setId((Integer)o[0]);
			follow.setUsuario((Usuario)o[1]);
			follow.setFollowed((Usuario)o[2]);
		}
		
		return follow;
	}

	@Override
	public int deleteFollow(int userId, int followId) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete Follow f where f.usuario=:userId AND f.followed =:followId");
		Usuario usuario = (Usuario) session.get(Usuario.class, userId);
		Usuario followed = (Usuario) session.get(Usuario.class, followId);
		query.setParameter("userId", usuario).setParameter("followId", followed);
		return query.executeUpdate();
	}
	
	@Override
	public int[] getFollowCounters(int id) {
		int[] followCounters = new int[2];
		Session session = this.sessionFactory.getCurrentSession();		
		Query query = session.createQuery("SELECT COUNT(usuario) "
				+ "FROM Follow WHERE usuario =:id");
		Usuario usuario = (Usuario) session.get(Usuario.class, id);
		query.setParameter("id", usuario);	
		followCounters[0] = Long.valueOf(query.list().get(0).toString()).intValue();
		query = session.createQuery("SELECT COUNT(followed) "
				+ "FROM Follow WHERE followed =:id");
		query.setParameter("id", usuario);	
		followCounters[1] = Long.valueOf(query.list().get(0).toString()).intValue();
		return followCounters;
	}
	
	@Override
	public List<Follow> getMyFollows(int idParam){
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("SELECT f.id, f.usuario, f.followed "
				+ "FROM Follow f WHERE usuario =:idParam").setParameter("idParam", idParam);	

		List<Object[]> objectList = query.list();
		List<Follow> followList = new ArrayList<Follow>();
		if(objectList.size()!=0){
			for(Object[] obj:objectList ){
				Follow f = new Follow();
				f.setId((Integer)obj[0]);
				f.setUsuario((Usuario)obj[1]);
				f.setFollowed((Usuario)obj[2]);
				followList.add(f);
			}
		}
		return followList;
	}
	
	@Override
	public List<Follow> getMyFollowed(int idParam){
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("SELECT f.id, f.usuario, f.followed "
				+ "FROM Follow f WHERE followed =:idParam").setParameter("idParam", idParam);	

		List<Object[]> objectList = query.list();
		List<Follow> followList = new ArrayList<Follow>();
		if(objectList.size()!=0){
			for(Object[] obj:objectList ){
				Follow f = new Follow();
				f.setId((Integer)obj[0]);
				f.setUsuario((Usuario)obj[1]);
				f.setFollowed((Usuario)obj[2]);
				followList.add(f);
			}
		}
		return followList;
	}

}