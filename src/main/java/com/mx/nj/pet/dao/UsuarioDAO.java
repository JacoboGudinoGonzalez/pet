package com.mx.nj.pet.dao;

import java.util.List;

import com.mx.nj.pet.model.ServicePet;
import com.mx.nj.pet.model.Usuario;

public interface UsuarioDAO {

	List<Usuario> getAllUsers(String type);
	
	List<Usuario> getAllUsersLocation(String type, String latitude, String longitude);
	
	List<ServicePet> getUsersService(int idUser);

	Usuario getUserByEmail(String email);
	
	Usuario getUserById(Integer id);
	
	Usuario login(String email, String password);

	Usuario addUser(Usuario usuario);

	void updateUser(Usuario usuario);
	
	void updateImageUser(int id, String image);

	void deleteUser(int id);	

}