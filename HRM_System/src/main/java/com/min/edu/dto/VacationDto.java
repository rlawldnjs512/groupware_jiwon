package com.min.edu.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VacationDto {

	private int leave_id;
	private String emp_id;
	private String start_date;
	private String end_date;
	private double leave_total;
	private double leave_use;
	private double leave_remain;
	private int extra_time;
}
