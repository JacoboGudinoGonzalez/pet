package com.mx.nj.pet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.nj.pet.dao.PetServiceDAO;
import com.mx.nj.pet.model.ServicePet;
import com.mx.nj.pet.service.PetServiceService;

@Service("petServiceService")
public class PetServiceServiceImpl implements PetServiceService {
	
	@Autowired PetServiceDAO petServiceDao;

	@Transactional
	public void addPetService(ServicePet sp) {
		petServiceDao.addPetService(sp);
	}

}
