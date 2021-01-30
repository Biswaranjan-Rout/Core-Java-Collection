/**
 * 
 */
package com.amai.agentService.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amai.agentService.entity.Appointments;
import com.amai.agentService.exception.AppointmentException;
import com.amai.agentService.service.AppointmentService;

/**
 * @author Biswaranjan Rout
 *
 */

@RestController
@RequestMapping("/v1/agent/appointments")
public class AppointmentsController {

	@Autowired
	private AppointmentService appointmentService;

	@GetMapping("/connectionCheck")
	private String connectionCheck() {
		return "Success: Amai Agents Portal is Up And Running....";
	}

	@GetMapping("/getAllNewAppointments")
	public ResponseEntity<List<Appointments>> getAllNewAppointments() {
		List<Appointments> appointmentList;
		try {
			appointmentList = appointmentService.getAllNewAppointments();
			System.out.println("Inside getAllNewAppointments");

			if (!appointmentList.isEmpty()) {
				return new ResponseEntity<List<Appointments>>(appointmentList, HttpStatus.OK);
			} else
				return new ResponseEntity<List<Appointments>>(HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Appointments>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getAllNewAppointmentsByAgentID/{agentId}")
	public List<Appointments> getAllNewAppointmentsByAgentID(@PathVariable String agentId) {
		try {
			appointmentService.getAllNewAppointments();

			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping("/getAppointments/{id}")
	public ResponseEntity<Appointments> getAppointmentsByAppointmentId(@PathVariable int id) {
		Appointments appointment;
		try {
			appointment = appointmentService.getAllAppointMents(id);
			if(appointment.getUser() != null) {
				return new ResponseEntity<Appointments>(appointment, HttpStatus.OK);
			}
			else return new ResponseEntity<Appointments>(appointment, HttpStatus.NO_CONTENT);
			
		}
		catch(AppointmentException ape) {
			return new ResponseEntity<Appointments>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<Appointments>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
