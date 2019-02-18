package com.mx.nj.pet.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@Table(name="USUARIO")
@JsonRootName(value = "user")
public class Usuario {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;
	
	@Column(name="name")
	String name;
	
	@Column(name="email")
	String email;
	
	@Column(name="tel")
	String tel;
	
	@Column(name="password")
	String password;
	
	@Column(name="type")
	String type;
	
	@Column(name="image")
	String image;
	
	@Column(name="rating")
	int rating;
	
	@Column(name="description")
	String description;
	
	@Column(name="address")
	String address;
	
	@Column(name="coordinates")
	String coordinates;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "usuario")
    private Set<ServicePet> servicePet;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "usuario")
    private Set<Publication> publication;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "usuario")
    private Set<Follow> usuario;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "followed")
    private Set<Follow> followed;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "receiver")
    private Set<Message> receiver;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "emmiter")
    private Set<Message> emmiter;
	
	@Transient
	Boolean getToken;
	
	public Usuario() {
		super();
	}
	
	public Usuario(Integer id, String name, String email, String tel, String password, String type, Boolean getToken, 
			String image, int rating, String description, String address, String coordinates) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.tel = tel;
		this.password = password;
		this.type = type;
		this.getToken = getToken;
		this.image = image;
		this.rating = rating;
		this.description = description;
		this.address = address;
		this.coordinates = coordinates;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String pass) {
		this.password = pass;
	}
	public String getType() {
		return type;
	}	
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getGetToken() {
		return getToken;
	}
	public void setGetToken(Boolean getToken) {
		this.getToken = getToken;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
}

