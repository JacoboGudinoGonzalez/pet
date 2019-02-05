package com.mx.nj.pet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@Table(name="FOLLOW")
@JsonRootName(value = "follow")
public class Follow {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;
	
	@ManyToOne
    @JoinColumn(name="usuario", nullable=false)
	Usuario usuario;
	
	@ManyToOne
    @JoinColumn(name="followed", nullable=false)
	Usuario followed;
	
	public Follow() {
		super();
	}
	
	public Follow(Integer id, Usuario usuario, Usuario followed) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.followed = followed;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getFollowed() {
		return followed;
	}

	public void setFollowed(Usuario followed) {
		this.followed = followed;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}

