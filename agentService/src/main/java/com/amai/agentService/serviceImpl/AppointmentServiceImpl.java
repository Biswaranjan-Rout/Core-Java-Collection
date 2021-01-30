package com.amai.agentService.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amai.agentService.dao.Appointments_Repository;
import com.amai.agentService.entity.Appointments;
import com.amai.agentService.exception.AppointmentException;
import com.amai.agentService.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private Appointments_Repository appointmentsRepository;

	@Override
	public List<Appointments> getAllNewAppointments() throws AppointmentException, Exception {
		List<Appointments> apnmtsList;
		System.out.println("Inside getAllNewAppointments serviceImpl");
		try {
			apnmtsList = appointmentsRepository.getAllNewAppointments();
			if (!apnmtsList.isEmpty()) {
				apnmtsList.stream().forEach((i) -> System.out.println(i));
				return apnmtsList;
			} else {
				System.out.println("No data found");
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new AppointmentException(e.getMessage());
		}

	}

	@Override
	public Appointments getAllAppointMents(int id) throws Exception {
		Appointments apnmt;
		try {
			//change this code
			apnmt = appointmentsRepository.getOne(id);

			return apnmt;

		} catch (Exception e) {
			e.printStackTrace();
			throw new AppointmentException(e.getMessage());
		}

	}

}
