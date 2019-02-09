package com.mx.nj.pet.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@Table(name="PET")
@JsonRootName(value = "pet")
public class Pet {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;
					
	@ManyToOne
	@JoinColumn(name="owner", nullable=false)
	Usuario owner;
	
	@Column(name="type")
	int type;
		
	@Column(name="name")
	String name;
	
	@Column(name="size")
	int size;
	
	@Column(name="gender")
	int gender;
	
	@Column(name="years")
	int years;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "pet")
    private Set<Appointment> pet;
	
	public Pet() {
		super();
	}

	public Pet(Integer id, Usuario owner, int type, String name, int size, int gender, int years) {
		super();
		this.id = id;
		this.owner = owner;
		this.type = type;
		this.name = name;
		this.size = size;
		this.gender = gender;
		this.years = years;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getOwner() {
		return owner;
	}

	public void setOwner(Usuario owner) {
		this.owner = owner;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getYears() {
		return years;
	}

	public void setYears(int years) {
		this.years = years;
	}	
	
}

