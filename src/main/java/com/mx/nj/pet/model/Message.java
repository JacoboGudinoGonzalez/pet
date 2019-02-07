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
@Table(name="MESSAGE")
@JsonRootName(value = "message")
public class Message {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;
	
	@Column(name="text")
	String text;
	
	@ManyToOne
    @JoinColumn(name="emmiter", nullable=false)
    Usuario emmiter;
	
	@ManyToOne
    @JoinColumn(name="receiver", nullable=false)
    Usuario receiver;
	
	@Column(name="created_at")
	Timestamp createdAt;
	
	@Column(name="viewed")
	boolean viewed;
	
	public Message() {
		super();
	}

	public Message(int id, String text, Usuario emmiter, Usuario receiver, Timestamp createdAt, boolean viewed) {
		super();
		this.id = id;
		this.text = text;
		this.emmiter = emmiter;
		this.receiver = receiver;
		this.createdAt = createdAt;
		this.viewed = viewed;
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

	public Usuario getEmmiter() {
		return emmiter;
	}

	public void setEmmiter(Usuario emmiter) {
		this.emmiter = emmiter;
	}

	public Usuario getReceiver() {
		return receiver;
	}

	public void setReceiver(Usuario receiver) {
		this.receiver = receiver;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isViewed() {
		return viewed;
	}

	public void setViewed(boolean viewed) {
		this.viewed = viewed;
	}
	
}

