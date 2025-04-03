package com.min.edu;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.edu.dto.AttendanceDto;
import com.min.edu.model.service.IAttendanceService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class Attendance_JUnitTest {

	@Autowired
	private IAttendanceService service;
	
	//@Test
	void selectClockOut_Test() {
		String empId = "20180048";
		String clockOut = service.selectClockOut(empId);
		
		log.info("퇴근시간 조회 : {}", clockOut);
		
		assertNotEquals(0, clockOut);
	}
	
	//@Test
	void getAttendanceEvents_Test() {
		
		String empId = "20180050";
		List<AttendanceDto> attendanceList = service.attendanceListByEmpId(empId);
		System.out.println(attendanceList);
		
	}
	
	//@Test
	void getCalendar_Test() {
		
		String empId = "20180050";
		List<Map<String, Object>> list = service.getCalendar(empId);
		System.out.println(list);
		
	}
	
	@Test
	void avgWorkTimeByDept_Test() {
		List<Map<String, Object>> list = service.avgWorkTimeByDept();
		System.out.println(list);
	}
	
}
