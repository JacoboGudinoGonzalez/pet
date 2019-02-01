package com.mx.nj.pet.model;

import java.util.Date;

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
@Table(name="SERVICE_PET")
@JsonRootName(value = "servicPet")
public class ServicePet {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;
	
	@Column(name="id_user_to")
	int idUserTo;
	
	@Column(name="date_from")
	String dateFrom;
	
	@Column(name="date_to")
	String date;
	
	@Column(name="address")
	String address;
	
	@Column(name="message")
	String message;
	
	@Column(name="animal_type")
	String animalType;
	
	@Column(name="animal_size")
	char animalSize;
	
    @ManyToOne
    @JoinColumn(name="id_user_from", nullable=false)
    private Usuario usuario;

	public ServicePet() {
		super();
	}

	public ServicePet(Integer id, int idUserTo, String dateFrom, String date, String address, String message, String animalType, char animalSize,
			Usuario usuario) {
		super();
		this.id = id;
		this.idUserTo = idUserTo;
		this.dateFrom = dateFrom;
		this.date = date;
		this.address = address;
		this.message = message;
		this.animalType = animalType;
		this.animalSize = animalSize;
		this.usuario = usuario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getIdUserTo() {
		return idUserTo;
	}

	public void setIdUserTo(int idUserTo) {
		this.idUserTo = idUserTo;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getAnimalType() {
		return animalType;
	}

	public void setAnimalType(String animalType) {
		this.animalType = animalType;
	}

	public char getAnimalSize() {
		return animalSize;
	}

	public void setAnimalSize(char animalSize) {
		this.animalSize = animalSize;
	}

}
