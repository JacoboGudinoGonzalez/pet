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
		
	@Column(name="name")
	String name;
	
	@Column(name="size")
	int size;
	
	@Column(name="gender")
	int gender;
	
	@Column(name="years")
	int years;
	
	public Pet() {
		super();
	}	
}

