package com.mx.nj.pet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mx.nj.pet.model.ServicePet;
import com.mx.nj.pet.model.Usuario;

@Service("usuarioService")
public interface UsuarioService {
	
	List<Usuario> getAllUsers(String type);
	
	List<Usuario> getAllUsersLocation(String type, String latitude, String longitude);
	
	List<ServicePet> getUsersService(int idUser);

	Usuario getUserById(Integer id);
	
	Usuario getUserByEmail(String email);
	
	Usuario login(String email, String password);

	void addUsuario(Usuario usuario);

	void updateUsuario(Usuario usuario);
	
	void updateImageUser(int id, String image);

	void deleteUsuario(int id);

}