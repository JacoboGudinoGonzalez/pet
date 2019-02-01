package com.mx.nj.pet.dao;

import java.util.List;

import com.mx.nj.pet.model.Publication;

public interface PublicationDAO {

	Publication addPublication(Publication publication);
	
	List<Publication> getPublications(List<Integer> usersId);

	List<Publication> getPublicationsUser(int userId);
	
	Publication getPublication(int id);
	
	void deletePublication(int publicationId, int userId);

	int getPublicationCounters(int id);

}