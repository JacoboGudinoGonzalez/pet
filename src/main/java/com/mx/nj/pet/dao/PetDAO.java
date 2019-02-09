package com.mx.nj.pet.dao;

import java.util.List;

import com.mx.nj.pet.model.Pet;

public interface PetDAO {

	Pet addPet(Pet pet);
	
	List<Pet> getMyPets(int fromUser);
	
	Pet getPet(int id);
	
	void deletePet(int petId);

	List<Pet> getPets(int toUser);
	
}