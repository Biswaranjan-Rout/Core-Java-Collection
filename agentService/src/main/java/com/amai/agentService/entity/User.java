package com.amai.agentService.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name = "Users", uniqueConstraints = { @UniqueConstraint(columnNames = "id"),
		@UniqueConstraint(columnNames = "email") })
public class User implements Serializable {

	private static final long serialVersionUID = 8924336229286382077L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator")
	@SequenceGenerator(name = "users_generator", sequenceName = "user_sequence")
	@Column(name = "id", nullable = false)
	private int user_id;
	
	@Column(name = "user_name", nullable = false)
	private String user_name;
	@Column(name = "email",nullable = false)
	private String email;
	@Column(name = "contact_number",nullable = false)
	private String contact_number;
	@Column(name = "password")
	private String password;
	@Column(name = "is_active")
	private int is_active;
	@Column(name = "created_at")
	private Date created_at;
	@Column(name = "updated_at")
	private Date updated_at;
	@Column(name = "is_phone_verified")
	private int is_phone_verified;
	@Column(name = "is_email_verified")
	private int is_email_verified;
	@Column(name = "is_approved")
	private int is_approved;

}
