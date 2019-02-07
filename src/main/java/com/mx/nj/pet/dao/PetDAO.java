package com.mx.nj.pet.dao;

import java.util.List;

import com.mx.nj.pet.model.Appointment;
import com.mx.nj.pet.model.Pet;

public interface PetDAO {

	Pet addPet(Pet pet);
	
	List<Pet> getMyPets(int fromUser);
	
	Pet getAppointment(int id);
	
	void deletePet(int petId);

	List<Pet> getPets(int toUser);
	
}