package com.mx.nj.pet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.nj.pet.dao.FollowDAO;
import com.mx.nj.pet.dao.UsuarioDAO;
import com.mx.nj.pet.model.Follow;
import com.mx.nj.pet.model.ServicePet;
import com.mx.nj.pet.model.Usuario;
import com.mx.nj.pet.service.UsuarioService;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired UsuarioDAO usuarioDao;
	@Autowired FollowDAO followDao;
	
	@Transactional
	public List<Usuario> getAllUsers(String type) {
		return usuarioDao.getAllUsers(type);
	}
	
	@Transactional
	public List<ServicePet> getUsersService(int idUser) {
		return usuarioDao.getUsersService(idUser);
	}

	@Transactional
	public Usuario getUserByEmail(String email) {
		return usuarioDao.getUserByEmail(email);
	}
	
	@Transactional
	public Usuario getUserById(Integer id) {
		return usuarioDao.getUserById(id);
	}
	
	@Transactional
	public Usuario login(String email, String password) {
		return usuarioDao.login(email, password);
	}

	@Transactional
	public void addUsuario(Usuario usuario) {
		usuarioDao.addUser(usuario);
	}

	@Transactional
	public void updateUsuario(Usuario usuario) {
		usuarioDao.updateUser(usuario);
	}
	
	@Transactional
	public void updateImageUser(int id, String image){
		usuarioDao.updateImageUser(id, image);
	}

	@Transactional
	public void deleteUsuario(int id) {
		usuarioDao.deleteUser(id);
	}

}