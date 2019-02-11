package com.mx.nj.pet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.nj.pet.dao.AppointmentDAO;
import com.mx.nj.pet.model.Appointment;
import com.mx.nj.pet.service.AppointmentService;

@Service("appointmentService")
public class AppointmentServiceImpl implements AppointmentService {
	
	@Autowired AppointmentDAO appointmentDao;

	@Transactional
	public void addAppointment(Appointment appointment) {
		appointmentDao.addAppointment(appointment);
	}

	@Transactional
	public List<Appointment> getMyAppointments(int fromUser) {
		return appointmentDao.getMyAppointments(fromUser);
	}
	
	@Transactional
	public List<Appointment> getAppointments(int toUser) {
		return appointmentDao.getAppointments(toUser);
	}

	@Transactional
	public Appointment getAppointment(int id) {
		return appointmentDao.getAppointment(id);
	}
	
	@Transactional
	public void deleteAppointment(int appointmentId, int fromUser) {
		appointmentDao.deleteAppointment(appointmentId, fromUser);
	}
	
	@Transactional
	public void changeAppointmentStatus(int appointmentId, int status) {
		appointmentDao.changeAppointmentStatus(appointmentId, status);
	}
}