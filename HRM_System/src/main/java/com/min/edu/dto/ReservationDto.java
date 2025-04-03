package com.min.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ReservationDto {

	private String reserv_id;
	private String room_id;
	private String emp_id;
	private String rev_date;
	private String slot;
	private String range;
	private String name;
	
}










