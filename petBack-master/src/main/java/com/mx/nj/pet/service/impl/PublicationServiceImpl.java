package com.mx.nj.pet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.nj.pet.dao.PublicationDAO;
import com.mx.nj.pet.model.Publication;
import com.mx.nj.pet.service.PublicationService;

@Service("publicationService")
public class PublicationServiceImpl implements PublicationService {
	
	@Autowired PublicationDAO publicationDao;

	@Transactional
	public void addPublication(Publication publication) {
		publicationDao.addPublication(publication);
	}

	@Transactional
	public List<Publication> getPublications(List<Integer> usersId) {
		return publicationDao.getPublications(usersId);
	}

	@Transactional
	public List<Publication> getPublicationsUser(int userId) {
		return publicationDao.getPublicationsUser(userId);
	}

	@Transactional
	public Publication getPublication(int id) {
		return publicationDao.getPublication(id);
	}
	
	@Transactional
	public void deletePublication(int publicationId, int userId) {
		publicationDao.deletePublication(publicationId, userId);
	}
	
	@Transactional
	public int getPublicationCounters(int id) {
		return publicationDao.getPublicationCounters(id);
	}
}