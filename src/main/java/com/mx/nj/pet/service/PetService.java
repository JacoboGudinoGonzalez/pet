package com.mx.nj.pet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mx.nj.pet.model.Pet;

@Service("petService")
public interface PetService {
	
	Pet addPet(Pet pet);
	
	List<Pet> getMyPets(int fromUser);
	
	List<Pet> getPets(int toUser);
	
	Pet getPet(int id);
	
	void deletePet(int fromUser);
}
