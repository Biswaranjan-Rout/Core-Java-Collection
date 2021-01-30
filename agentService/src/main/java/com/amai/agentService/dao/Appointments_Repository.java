package com.amai.agentService.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.amai.agentService.entity.Appointments;

@Repository
public interface Appointments_Repository extends JpaRepository<Appointments,Integer>{
	
	@Query("FROM Appointments ap WHERE ap.status=1")
	List<Appointments> getAllNewAppointments();
	
}
