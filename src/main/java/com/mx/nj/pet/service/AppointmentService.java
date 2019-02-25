package com.mx.nj.pet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mx.nj.pet.model.Appointment;

@Service("appointmentService")
public interface AppointmentService {
	
	void addAppointment(Appointment appointment);
	
	List<Appointment> getMyAppointments(int fromUser);
	
	List<Appointment> getAppointments(int toUser);
	
	Appointment getAppointment(int id);
	
	void deleteAppointment(int appointmentId, int fromUser);
	
	void changeAppointmentStatus(int appointmentId, int status);
	
	void changeAppointmentReview(int appointmentId, int rating, String review);
	
}