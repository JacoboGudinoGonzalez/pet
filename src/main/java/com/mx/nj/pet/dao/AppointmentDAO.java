package com.mx.nj.pet.dao;

import java.util.List;

import com.mx.nj.pet.model.Appointment;

public interface AppointmentDAO {

	Appointment addAppointment(Appointment appointment);
	
	List<Appointment> getMyAppointments(int fromUser);
	
	Appointment getAppointment(int id);
	
	void deleteAppointment(int appointmentId, int fromUser);

	List<Appointment> getAppointments(int toUser);
	
}