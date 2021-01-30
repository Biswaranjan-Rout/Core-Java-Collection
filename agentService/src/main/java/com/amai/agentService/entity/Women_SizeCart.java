package com.amai.agentService.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter 
@NoArgsConstructor @AllArgsConstructor
@ToString
@Data
@Entity
@Table(name = "WOMEN_SIZECART")
public class Women_SizeCart implements Serializable {

	private static final long serialVersionUID = -8618964738239586288L;

	@Id
	@Column(name = "ID")
	private Long id;
	@Column(name = "NECK_ROUND")
	private float neck_round;
	@Column(name = "SHOULDER")
	private float shoulder;
	@Column(name = "UPPER_BUST_ROUND")
	private float upper_bust_round;
	@Column(name = "BUST_ROUND")
	private float bust_round;
	@Column(name = "UNDER_BUST_ROUND")
	private float under_bust_round;
	@Column(name = "LOW_WAIST_ROUND")
	private float low_waist_round;
	@Column(name = "HIP_ROUND")
	private float hip_round;
	@Column(name = "APEX")
	private float apex;

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

	@Column(name = "THIGH_ROUND")
	private float thigh_round;
	@Column(name = "MID_THIGH_ROUND")
	private float mid_thigh_round;
	@Column(name = "KNEE_ROUND")
	private float knee_round;
	@Column(name = "CALF_ROUND")
	private float calf_round;
	@Column(name = "ANKLE_ROUND")
	private float ankle_round;
	@Column(name = "SHOULDER_TO_CALF_LENGTH")
	private float shoulder_to_calf_length;
	@Column(name = "SHOULDER_TO_ANKLE_LENGTH")
	private float shoulder_to_ankle_length;
	@Column(name = "SHOULDER_TO_FLOOR_LENGTH_AFTER_HEELS")
	private float shoulder_to_floor_length_after_heels;

}
