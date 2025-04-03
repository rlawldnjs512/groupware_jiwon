package com.min.edu.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomDto {

	private String name;
	private String rev_date;
	private String reserv_id;
	private String emp_id;
	
	private int room_id;
	private String room_name;
	private String slot;
	private String range;
	
	private List<ReservationDto> reservation;
	private ReservationDto myreservation;
}


