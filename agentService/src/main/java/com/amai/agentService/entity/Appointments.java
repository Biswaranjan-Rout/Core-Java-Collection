package com.amai.agentService.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Appointments", uniqueConstraints = { @UniqueConstraint(columnNames = "id"),
		@UniqueConstraint(columnNames = "appointment_id") })
public class Appointments implements Serializable {

	private static final long serialVersionUID = -6805138727776952020L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "apntmnt_generator")
	@SequenceGenerator(name = "apntmnt_generator", sequenceName = "apnt_sequence")
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name = "user_id")
	private Long user_id;
	
	@Column(name = "apn_id")
	private String appointment_id;
	@Column(name = "slot")
	private String slot;
	@Column(name = "product_id")
	private String product_id;
	@Column(name = "address")
	private String address;
	@Column(name = "booked_date")
	private Date booked_date;
	@Column(name = "status")
	private int status;
	@Column(name = "agn_name")
	private String agent_name;
	@Column(name = "agn_contact")
	private String agent_contact;
	@Column(name = "created_at")
	private Timestamp created_at;
	@Column(name = "updated_at")
	private Timestamp updated_at;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;

}
