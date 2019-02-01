package com.mx.nj.pet.model;

import java.sql.Timestamp;

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
@Table(name="PUBLICATION")
@JsonRootName(value = "publication")
public class Publication {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;
		
	@Column(name="text")
	String text;
	
	@Column(name="file")
	String file;
	
	@Column(name="created_at")
	Timestamp createdAt;
	
	@ManyToOne
    @JoinColumn(name="usuario", nullable=false)
    Usuario usuario;
	
	public Publication() {
		super();
	}

	public Publication(Integer id, String text, String file, Timestamp createdAt, Usuario user) {
		super();
		this.id = id;
		this.text = text;
		this.file = file;
		this.createdAt = createdAt;
		this.usuario = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario user) {
		this.usuario = user;
	}
	
}

