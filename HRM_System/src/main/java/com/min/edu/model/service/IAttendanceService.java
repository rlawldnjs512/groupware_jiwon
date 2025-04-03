package com.min.edu.model.service;

import java.util.List;
import java.util.Map;

import com.min.edu.dto.AttendanceDto;

public interface IAttendanceService {

	public List<AttendanceDto> attendanceList();
	
	public List<AttendanceDto> attendanceListByEmpId(String empId);
	
	public int insertAttendance(String empId);
	
	public int updateAttendance(String empId);
	
	public String selectClockIn(String empId);
	
	public String selectClockOut(String empId);
	
	public Map<String, String> printClock(String empId);
	
	public boolean checkAttendance(String empId);
	
	public int calAttendance(String empId);
	
	public String selectAttendtype(String empId);
	
	public int updateAttendtype(Map<String, Object> map);
	
	public int updateUseExtraTime(Map<String, Object> map);
	
	// 출근 버튼 로직
	public void insertAttendanceLogic(String empId);
	
	// 퇴근 버튼 로직
	public void updateAttendanceLogic(String empId, Map<String, String> infoAtten);
	
	// 보상 시간 계산 로직
	public void calculateExtraTime(String empId, String checkInDateStr);
	
	public String avgClockInTime(String empId);
	
	public String avgClockInTimeAll();
	
	public String avgClockOutTime(String empId);
	
	public String avgClockOutTimeAll();
	
	public String avgWorkTime(String empId);
	
	public String avgWorkTimeAll();
	
	public List<Map<String, Object>> avgWorkTimeByDept();
	
	public int selectLate(String empId);
	
	public int selectLateToday();
	
	public List<Map<String, Object>> getLateEmpRank();
	
	// 근무 진행률 계산 로직
	public double calProgress(String empId, String ClockIn);
	
	public List<Map<String, Object>> getCalendar(String empId);
	
}
