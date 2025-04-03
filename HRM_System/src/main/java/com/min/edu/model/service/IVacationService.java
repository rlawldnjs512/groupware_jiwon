package com.min.edu.model.service;

import java.util.List;
import java.util.Map;

import com.min.edu.dto.VacationDto;

public interface IVacationService {

	public List<VacationDto> vacationList();
	
	public Map<String, Object> vacationListByEmpId(String empId);

	public int insertVacation(Map<String, Object> map);
	
	public int selectExtraTime(String empId);
	
	public int updateExtraTime(Map<String, Object> map);
	
	public int selectLeaveRemain(String empId);
}
