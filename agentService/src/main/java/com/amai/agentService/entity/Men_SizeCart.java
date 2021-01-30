package com.amai.agentService.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "MEN_SIZE_CART")
public class Men_SizeCart implements Serializable {

	private static final long serialVersionUID = -3858343869065264917L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", updatable = false, nullable = false)
	private Long id;
	@Column(name = "NECK_ROUND")
	private float neck_round;
	@Column(name = "SHOULDER")
	private float shoulder;
	@Column(name = "CROSS_FRONT")
	private float cross_front;
	@Column(name = "CROSS_BACK")
	private float cross_back;
	@Column(name = "CHEST_ROUND")
	private float chest_round;
	@Column(name = "STOMACH_ROUND")
	private float stomach_round;
	@Column(name = "LOW_WAIST_ROUND")
	private float low_waist_round;
	@Column(name = "HIP_ROUND")
	private float hip_round;

	@Column(name = "ARM_HOLE")
	private float armHole;
	@Column(name = "ARM_ROUND")
	private float arm_round;
	@Column(name = "ELBOW_ROUND")
	private float elbow_round;
	@Column(name = "FULL_SLEEVE_LENGTH")
	private float full_sleeve_length;
	@Column(name = "HALF_SLEEVE_LENGTH")
	private float half_sleeve_length;
	@Column(name = "WAIST_ROUND")
	private float waist_round;

	@Column(name = "UPPER_THIGH_ROUND")
	private float upper_thigh_round;
	@Column(name = "BANDH_GALA_LENGTH")
	private float bandh_gala_length;
	@Column(name = "MID_THIGH_ROUND")
	private float mid_thigh_round;
	@Column(name = "KNEE_LENGTH")
	private float knee_length;
	@Column(name = "SHOULDER_TO_CALF_LENGTH")
	private float shoulder_to_calf_length;
	@Column(name = "CALF_ROUND")
	private float calf_round;
	@Column(name = "ANKLE_ROUND")
	private float ankle_round;

}
