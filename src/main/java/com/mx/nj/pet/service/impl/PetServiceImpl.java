package com.mx.nj.pet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.nj.pet.dao.PetServiceDAO;
import com.mx.nj.pet.model.ServicePet;
import com.mx.nj.pet.service.PetService;

@Service("petServiceService")
public class PetServiceImpl implements PetService {
	
	@Autowired PetServiceDAO petServiceDao;

	@Transactional
	public void addPetService(ServicePet sp) {
		petServiceDao.addPetService(sp);
	}

}
