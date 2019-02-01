package com.mx.nj.pet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mx.nj.pet.model.Publication;

@Service("publicationService")
public interface PublicationService {
	
	void addPublication(Publication publication);
	
	List<Publication> getPublications(List<Integer> usersId);
	
	List<Publication> getPublicationsUser(int userId);
	
	Publication getPublication(int id);
	
	void deletePublication(int publicationId, int userId);
	
	int getPublicationCounters(int id);
}