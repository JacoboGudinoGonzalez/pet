package com.mx.nj.pet.service;

import org.springframework.stereotype.Service;

import com.mx.nj.pet.model.ServicePet;

@Service("petServiceService")
public interface PetService {
	
	void addPetService(ServicePet sp);
}
