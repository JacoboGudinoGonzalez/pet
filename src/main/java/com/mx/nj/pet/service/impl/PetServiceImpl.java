package com.mx.nj.pet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.nj.pet.dao.PetDAO;
import com.mx.nj.pet.model.Pet;
import com.mx.nj.pet.service.PetService;

@Service("petService")
public class PetServiceImpl implements PetService {
	
	@Autowired PetDAO petDao;

	@Transactional
	public Pet addPet(Pet pet) {
		return petDao.addPet(pet);
	}

	@Transactional
	public List<Pet> getMyPets(int idUser) {
		return petDao.getMyPets(idUser);
	}

	@Transactional
	public List<Pet> getPets(int idUser) {
		return petDao.getPets(idUser);
	}

	@Transactional
	public Pet getPet(int id) {
		return petDao.getPet(id);
	}

	@Transactional
	public void deletePet(int id) {
		petDao.deletePet(id);
	}
}
