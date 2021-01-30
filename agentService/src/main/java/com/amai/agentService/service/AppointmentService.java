package com.amai.agentService.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amai.agentService.entity.Appointments;

@Service
public interface AppointmentService {
	List<Appointments> getAllNewAppointments() throws Exception;
	
	Appointments getAllAppointMents(int id)throws Exception;
}
