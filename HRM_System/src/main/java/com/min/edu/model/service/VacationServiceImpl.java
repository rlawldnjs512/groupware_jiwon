package com.min.edu.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.min.edu.dto.VacationDto;
import com.min.edu.model.mapper.IVacationDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacationServiceImpl implements IVacationService {

	private final IVacationDao dao;
	
	@Override
	public List<VacationDto> vacationList() {
		return dao.vacationList();
	}
	
	@Override
	public Map<String, Object> vacationListByEmpId(String empId) {
		return dao.vacationListByEmpId(empId);
	}
	
	@Override
	public int insertVacation(Map<String, Object> map) {
		return dao.insertVacation(map);
	}

	@Override
	public int selectExtraTime(String empId) {
		return dao.selectExtraTime(empId);
	}

	@Override
	public int updateExtraTime(Map<String, Object> map) {
		return dao.updateExtraTime(map);
	}

	@Override
	public int selectLeaveRemain(String empId) {
		return dao.selectLeaveRemain(empId);
	}

	

}
